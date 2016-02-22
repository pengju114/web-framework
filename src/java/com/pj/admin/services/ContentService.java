/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.services;

import com.pj.admin.beans.Article;
import com.pj.jdbc.core.ResultList;
import com.pj.jdbc.services.BaseService;
import java.util.List;

/**
 *
 * @author luzhenwen
 */
public class ContentService extends BaseService{
    
    /**
     * 查找常见问题
     * @param title
     * @param pageNumber
     * @param pageSize
     * @return 
     */
    public List<Article> findQAByTitle(String title, int pageNumber,int pageSize){
        String sql = "select * from t_article where (article_status is null or article_status = 0) and article_title like %";
        String param = "%"+title+"%";
        ResultList<Article> rs = getJdbcTemplate().executeQuery(sql, new Object[]{param}, pageNumber, pageSize, Article.class);
        return rs == null?null:rs.toList();
    }
    
    public List<Article> listQA(int pageNumber,int pageSize){
        String sql = "select * from t_article where article_status is null or article_status = 0";
        ResultList<Article> rs = getJdbcTemplate().executeQuery(sql, null, pageNumber, pageSize, Article.class);
        return rs == null?null:rs.toList();
    }
}
