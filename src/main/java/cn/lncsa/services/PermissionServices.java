package cn.lncsa.services;

import cn.lncsa.data.model.Permission;
import cn.lncsa.data.repository.IPermissionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by catten on 2/16/17.
 */
@Service
public class PermissionServices extends BaseServices<Permission>{

    private IPermissionDAO permissionDAO;

    @Autowired
    private void setPermissionDAO(IPermissionDAO permissionDAO){
        this.permissionDAO = permissionDAO;
        setRepository(permissionDAO);
    }

    public Set<Permission> get(List<Integer> id){
        return new HashSet<>(permissionDAO.findAll(id));
    }

}
