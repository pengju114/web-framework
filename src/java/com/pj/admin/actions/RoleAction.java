/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.actions;

import com.pj.actions.BaseAction;
import com.pj.admin.beans.AdminUser;
import com.pj.admin.services.AdminService;
import com.pj.admin.services.AuthorityService;
import com.pj.admin.services.RoleService;
import com.pj.utilities.StringUtility;
import com.pj.web.res.Constans;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author luzhenwen
 */
public class RoleAction extends BaseAction{
    
    private String roleName;
    
    public String findRoles(){
        setDeleteAndModifyAuthority(getCurrentAdmin());
        
        if (StringUtility.isEmpty(roleName)) {
            return listRoles();
        }else{
            HttpServletRequest request = ServletActionContext.getRequest();
            RoleService service = new RoleService();
            request.setAttribute("roles", service.findRolesByName(roleName));
            request.setAttribute("roleName", roleName);
            return SUCCESS;
        }
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public String listRoles(){
        RoleService service = new RoleService();
        ServletActionContext.getRequest().setAttribute("roles", service.listRoles());
        return SUCCESS;
    }
    
    private void setDeleteAndModifyAuthority(AdminUser user){
        boolean hasDel = false;
        boolean hasAdd = false;
        if (user != null) {
            AdminService service = new  AdminService();
            hasDel = service.hasAuthorityByAuthorityKey(user.getAdminId(), Constans.AuthorityKey.DELETE_ROLE);
            hasAdd = service.hasAuthorityByAuthorityKey(user.getAdminId(), Constans.AuthorityKey.ADD_ROLE);
        }
        
        ServletActionContext.getRequest().setAttribute("delete", Boolean.valueOf(hasDel));// 有删除权限
        ServletActionContext.getRequest().setAttribute("add", Boolean.valueOf(hasAdd));// 有添加权限
        
    }
    
    
    public String wantToAddRole(){
        AuthorityService service = new AuthorityService();
        ServletActionContext.getRequest().setAttribute("authorities", service.listAuthorities());
        return SUCCESS;
    }
}
