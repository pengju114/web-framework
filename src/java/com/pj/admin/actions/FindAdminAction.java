/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.actions;

import com.pj.actions.BaseAction;
import com.pj.admin.services.AdminService;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author lzw
 */
public class FindAdminAction extends BaseAction {
    private final int rowCount = 10;
    
    private int   startRow;
    public String listAdmins(){
        AdminService service = new AdminService();
        ServletActionContext.getRequest().setAttribute("admins", service.listAdmins(startRow, rowCount));
        return SUCCESS;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }
    
    
    private String adminName;
    public String findAdmin(){
        return SUCCESS;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    
}
