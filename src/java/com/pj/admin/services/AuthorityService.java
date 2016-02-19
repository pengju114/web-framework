/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.services;

import com.pj.admin.beans.Authority;
import com.pj.jdbc.core.ResultList;
import com.pj.jdbc.services.BaseService;

/**
 *
 * @author luzhenwen
 */
public class AuthorityService extends BaseService{
    public ResultList<Authority> findAuthorityByName(String name){
        String sql = "select * from t_authority where authority_name like ?";
        return getJdbcTemplate().executeQuery(sql, new Object[]{"%"+name+"%"}, Authority.class);
    }
    
    public ResultList<Authority> listAuthorities(){
        String sql = "select * from t_authority";
        return getJdbcTemplate().executeQuery(sql, Authority.class);
    }
}
