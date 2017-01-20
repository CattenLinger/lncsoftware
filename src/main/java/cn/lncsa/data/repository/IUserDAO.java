package cn.lncsa.data.repository;

import cn.lncsa.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * Created by catte on 2016/6/12.
 */
public interface IUserDAO extends IBaseDAO<User> {

    /**
     * Get user by name
     *
     * @param name username
     * @return a user
     */
    @Query("select u from User u where u.name = ?1")
    User getByName(String name);

}
