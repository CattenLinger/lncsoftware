package cn.lncsa.services;

import cn.lncsa.data.model.User;
import cn.lncsa.data.model.UserProfile;
import cn.lncsa.data.repository.IUserDAO;
import cn.lncsa.data.repository.IUserProfileDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by catten on 12/31/16.
 */
@Service
public class UserServices extends BaseServices<User>{

    private IUserDAO userDAO;
    private IUserProfileDAO userProfileDAO;

    @Autowired
    private void setUserDAO(IUserDAO userDAO){
        this.userDAO = userDAO;
        setRepository(userDAO);
    }

    @Autowired
    private void setUserProfileDAO(IUserProfileDAO userProfileDAO){
        this.userProfileDAO = userProfileDAO;
    }

    public UserProfile saveProfile(UserProfile userProfile){
        return userProfileDAO.save(userProfile);
    }
    
    public User getByName(String username) {
        return userDAO.getByName(username);
    }

    
    public Page<User> findByUsername(String keyword, Pageable pageable) {
        return null;
    }

    
    public UserProfile getProfile(Integer userId, boolean ignoreSecret) {
        User user = userDAO.findOne(userId);
        return getProfile(user,ignoreSecret);
    }

    public UserProfile getProfile(User user, boolean ignoreSecret){
        UserProfile userProfile = user.getProfile();

        if(userProfile == null) return null;
        else if (ignoreSecret) return userProfile;
        else return userProfile.getSecret() ? null : userProfile;
    }

    public UserProfile saveProfile(Integer userId, UserProfile userProfile) {
        User user = userDAO.findOne(userId);
        if(userProfile.getSecret() == null) userProfile.setSecret(false);

        if(userProfile.getId() == null){
            userProfile.setUser(user);
            userProfileDAO.save(userProfile);
            user.setProfile(userProfile);
            userDAO.save(user);
            return userProfile;
        }

        if(user.getProfile().getId().equals(userProfile.getId()))
        userProfileDAO.save(userProfile);
        return userProfile;
    }
}
