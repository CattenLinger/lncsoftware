package cn.lncsa.data.repository;

import cn.lncsa.data.model.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by cattenlinger on 2016/10/8.
 */
public interface IUserProfileDAO extends IBaseDAO<UserProfile>{

}
