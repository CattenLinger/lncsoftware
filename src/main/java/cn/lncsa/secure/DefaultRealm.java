package cn.lncsa.secure;

import cn.lncsa.data.model.User;
import cn.lncsa.data.repository.IUserDAO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
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

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        
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
