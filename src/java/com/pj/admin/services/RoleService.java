/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.services;

import com.pj.admin.beans.Authority;
import com.pj.admin.beans.Role;
import com.pj.jdbc.core.ResultList;
import com.pj.jdbc.services.BaseService;

/**
 *
 * @author luzhenwen
 */
public class RoleService extends BaseService{
    public ResultList<Role> findRolesByName(String name){
        String sql = "select * from t_role where role_name like ?";
        return getJdbcTemplate().executeQuery(sql, new Object[]{"%"+name+"%"}, Role.class);
    }
    
    public ResultList<Role> listRoles(){
        String sql = "select * from t_role";
        return getJdbcTemplate().executeQuery(sql,  Role.class);
    }
    
    public ResultList<Authority> getAuthoritiesByRoleId(Integer roleId){
        if (roleId == null) {
            return null;
        }
        String sql = "select a.* from t_authority a,t_role r, t_authority_role_mapping m where a.authority_id = m.authority_id and r.role_id = m.role_id and r.role_id = ?";
        
        return getJdbcTemplate().executeQuery(sql, new Object[]{roleId}, Authority.class);
    }
}
