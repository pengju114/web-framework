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
import com.pj.utilities.StringUtility;
import com.pj.web.res.Constans;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author luzhenwen
 */
public class AuthorityAction extends BaseAction{
    
    private String authorityName;
    public String findAuthority(){
        
        setDeleteAndModifyAuthority(getCurrentAdmin());
        
        if (StringUtility.isEmpty(authorityName)) {
            return listAuthorities();
        }else{
            HttpServletRequest request = ServletActionContext.getRequest();
            request.setAttribute("authorityName", authorityName);
            AuthorityService service = new AuthorityService();
            request.setAttribute("authorities", service.findAuthorityByName(authorityName));
            return SUCCESS;
        }
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }
    
    public String listAuthorities(){
        HttpServletRequest request = ServletActionContext.getRequest();
        AuthorityService service = new AuthorityService();
        request.setAttribute("authorities", service.listAuthorities());
        return SUCCESS;
    }
    
    private void setDeleteAndModifyAuthority(AdminUser user){
        boolean hasDel = false;
        boolean hasAdd = false;
        if (user != null) {
            AdminService service = new  AdminService();
            hasDel = service.hasAuthorityByAuthorityKey(user.getAdminId(), Constans.AuthorityKey.DELETE_AUTHORITY);
            hasAdd = service.hasAuthorityByAuthorityKey(user.getAdminId(), Constans.AuthorityKey.ADD_AUTHORITY);
        }
        
        ServletActionContext.getRequest().setAttribute("delete", Boolean.valueOf(hasDel));// 有删除权限
        ServletActionContext.getRequest().setAttribute("add", Boolean.valueOf(hasAdd));// 有添加权限
        
    }
}
