/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.jdbc.services;

import com.pj.jdbc.core.ResultList;
import com.pj.jdbc.core.ResultRow;

/**
 *
 * @author lzw
 */
public class DebugService extends BaseService{
    public ResultList<ResultRow> listDetail(){
        return getJdbcTemplate().executeQuery("select * from t_view_authority_detail");
    }
    
    public ResultList<ResultRow> listAll(){
        return getJdbcTemplate().list("t_admin");
    }
    
    public ResultList<ResultRow> listAll(int pn,int ps){
        return getJdbcTemplate().list("t_admin",pn,ps);
    }
}
