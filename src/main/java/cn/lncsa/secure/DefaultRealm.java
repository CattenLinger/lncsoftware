package cn.lncsa.secure;

import cn.lncsa.data.model.Permission;
import cn.lncsa.data.model.Role;
import cn.lncsa.data.model.User;
import cn.lncsa.data.repository.IUserDAO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cattenlinger on 2017/2/8.
 */
@Component
public class DefaultRealm extends AuthorizingRealm{

    private IUserDAO userDAO;

    @Autowired
    public void setUserDAO(IUserDAO userDAO){
        this.userDAO = userDAO;
    }

    public DefaultRealm(){
        setName("DefaultRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Integer userId = (Integer) principalCollection.fromRealm(getName()).iterator().next();
        User user = userDAO.findOne(userId);
        if(user != null){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            for (Role role : user.getRoles()){
                if(role.getEnable()) {
                    simpleAuthorizationInfo.addRole(role.getName());
                    for (Permission permission : role.getPermissions()){
                        if(permission.getEnable()) simpleAuthorizationInfo.addStringPermission(permission.getUri());
                    }
                }
            }
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userDAO.getByName(token.getUsername());
        if(user != null){
            return new SimpleAuthenticationInfo(user.getId(),user.getPassword(),getName());
        }else return null;
    }
}
