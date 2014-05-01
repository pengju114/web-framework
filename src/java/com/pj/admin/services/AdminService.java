/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.services;

import com.pj.admin.beans.AdminUser;
import com.pj.admin.beans.Role;
import com.pj.jdbc.core.JdbcTemplate;
import com.pj.jdbc.core.ResultList;
import org.apache.log4j.Logger;

/**
 *
 * @author lzw
 */
public class AdminService extends JdbcTemplate {
    public static final String ADMIN_TABLE="t_admin";
    public static final String ROLE_TABLE="t_role";
    
    private static final Logger LOGGER=Logger.getLogger(AdminService.class.getName());
    
    public AdminUser findAdminUserByNameAndPassword(String name,String pwd){
        String sql="select * from "+ADMIN_TABLE+" as a where a.admin_name=? and a.admin_password=?";
        try {
            AdminUser user=querySingle(sql, new Object[]{name,pwd}, AdminUser.class);
            return user;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
    
    public ResultList<AdminUser> listAdmins(int startRow,int rowCount){
        return  executeQuery("select * from "+ADMIN_TABLE,null, startRow, rowCount, AdminUser.class);
    }
    
    public ResultList<AdminUser> listAdmins(String name,int startRow,int rowCount){   
        name = "%"+name+"%";
        return  executeQuery("select * from "+ADMIN_TABLE+" a where a.admin_name like ? or a.admin_real_name like ?",new String[]{name,name}, startRow, rowCount, AdminUser.class);
    }
    
    public ResultList<Role> listRoles(){
        String sql = "select * from "+ROLE_TABLE;
        return executeQuery(sql, Role.class);
    }
}
