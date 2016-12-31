package cn.lncsa.data.repository;

import cn.lncsa.data.model.Role;

/**
 * Created by catten on 16/6/12.
 */
public interface IRoleDAO extends IBaseDAO<Role> {

    /**
     *
     * Get role by role's name
     *
     * @param name
     * @return
     */
    Role getByName(String name);
}
