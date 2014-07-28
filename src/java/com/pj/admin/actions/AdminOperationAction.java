/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pj.admin.actions;

import com.pj.actions.BaseAction;
import com.pj.admin.beans.AdminUser;
import com.pj.admin.services.AdminService;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author lzw
 */
public class AdminOperationAction extends BaseAction{
    public String listRoles(){
        AdminService service = new AdminService();
        ServletActionContext.getRequest().setAttribute("roles", service.listRoles());
        return SUCCESS;
    }
    
    private AdminUser admin;
    private String    roleList;
    public  String addNewAdmin(){
        return listRoles();
    }

    public AdminUser getAdmin() {
        return admin;
    }

    public void setAdmin(AdminUser admin) {
        this.admin = admin;
    }

    public String getRoleList() {
        return roleList;
    }

    public void setRoleList(String roleList) {
        this.roleList = roleList;
    }
    
}
