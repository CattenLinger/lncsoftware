package cn.lncsa.services;

import cn.lncsa.data.model.Role;
import cn.lncsa.data.repository.IRoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by catten on 2/14/17.
 */
@Service
public class RoleServices extends BaseServices<Role> {

    private IRoleDAO roleDAO;

    @Autowired
    private void setRoleDAO(IRoleDAO roleDAO) {
        this.roleDAO = roleDAO;
        setRepository(roleDAO);
    }

    public Role getByName(String name) {
        return get((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"),name));
    }
}
