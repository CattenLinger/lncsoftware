package cn.lncsa.controller;

import cn.lncsa.data.model.Permission;
import cn.lncsa.data.model.Role;
import cn.lncsa.services.PermissionServices;
import cn.lncsa.services.RoleServices;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by catten on 1/11/17.
 */
@RestController
@RequiresRoles("admin")
@RequestMapping("/management")
public class AdminController {

    private RoleServices roleServices;
    private PermissionServices permissionServices;

    @Autowired
    private void setRoleServices(RoleServices roleServices) {
        this.roleServices = roleServices;
    }

    @Autowired
    private void setPermissionServices(PermissionServices permissionServices){
        this.permissionServices = permissionServices;
    }

    /*
    *
    * Controller Method
    *
    * */

    /**
     *
     * Get all roles
     *
     * @param page
     * @param pageCount
     * @return
     */
    @GetMapping("/role/all")
    public Page<Role> getRole(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "pageCount", defaultValue = "10") int pageCount){
        return roleServices.findAll(new PageRequest(page,pageCount));
    }

    /**
     *
     * Create a role
     *
     * @param role
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/role")
    public Model createRole(
            Role role,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors() && roleServices.getByName(role.getName()) != null){
            model.addAttribute("success",false);
            return model;
        }

        roleServices.save(role);

        model.addAttribute("success",true);
        return model;
    }

    /**
     *
     * Update a role information
     *
     * @param role
     * @param bindingResult
     * @param id
     * @param model
     * @return
     */
    @PostMapping("/role/{id}")
    public Model updateRole(
            Role role,
            BindingResult bindingResult,
            @PathVariable("id") int id,
            Model model){

        if(bindingResult.hasErrors() || role.getId() == null){
            model.addAttribute("success",false);
            return model;
        }

        Role existedRole = roleServices.get(id);
        if(existedRole == null){
            model.addAttribute("success",false);
            return model;
        }

        roleServices.save(role);
        model.addAttribute("success",true);
        return model;
    }

    /**
     *
     * Delete a role
     *
     * @param id
     * @param model
     * @return
     */
    @DeleteMapping("/role/{id}")
    public Model deleteRole(@PathVariable("id") int id, Model model){
        roleServices.delete(id);
        model.addAttribute("success",true);
        return model;
    }

    /**
     *
     * Grant role permissions
     *
     * @param id
     * @param permissionIds
     * @param model
     * @return
     */
    @PatchMapping("/role/{id}/permission")
    public Model grantPermissions(
            @PathVariable("id") int id,
            @RequestParam(value = "perIds") List<Integer> permissionIds,
            Model model){

        Role role = roleServices.get(id);
        if(role == null){
            model.addAttribute("success",false);
            return model;
        }

        role.setPermissions(permissionServices.get(permissionIds));
        roleServices.save(role);
        model.addAttribute("success",true);
        return model;
    }

    /**
     *
     * List all available permissions
     *
     * @param page
     * @param pageCount
     * @return
     */
    @GetMapping("/permission")
    public Page<Permission> getPermissions(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "pageCount",defaultValue = "10") int pageCount){
        return permissionServices.findAll(new PageRequest(page,pageCount));
    }
}
