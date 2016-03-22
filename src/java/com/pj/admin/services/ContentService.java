/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.services;

import com.es.keyassistant.beans.DetectionInfo;
import com.pj.admin.beans.Article;
import com.pj.admin.beans.Attachment;
import com.pj.jdbc.core.ResultList;
import com.pj.jdbc.services.BaseService;
import com.pj.utilities.ArrayUtility;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

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
    public ResultList<Article> findQAByTitle(String title, int pageNumber,int pageSize){
        if (title == null) {
            title = "";
        }
        String sql = "select * from t_article where (article_status is null or article_status = 0) and article_title like ? order by article_id desc";
        String param = "%"+title+"%";
        ResultList<Article> rs = getJdbcTemplate().executeQuery(sql, new Object[]{param}, pageNumber-1, pageSize, Article.class);
        return rs;
    }
    
    public List<Article> listQA(int pageNumber,int pageSize){
        String sql = "select * from t_article where article_status is null or article_status = 0 order by article_id desc";
        ResultList<Article> rs = getJdbcTemplate().executeQuery(sql, null, pageNumber, pageSize, Article.class);
        return rs == null?null:rs.toList();
    }
    
    public int addQA(Article article){
        return getJdbcTemplate().save(article);
    }
    
    public int deleteQAsByIds(Integer[] ids){
        int c = 0;
        try {
            String sql = "delete from t_article where article_id in("+ArrayUtility.join("?", ids.length, ",")+")";
            c = getJdbcTemplate().executeUpdate(sql, ids);
        } catch (Exception e) {
            Logger.getLogger(ContentService.class).error(e, e);
        }
        return c;
    }
    
    public Article findArticleById(Integer id){
        Article article = null;
        try {
            article = getJdbcTemplate().querySingle("select * from t_article where article_id=?", new Object[]{id}, Article.class);
        } catch (Exception e) {
            Logger.getLogger(ContentService.class).error(e, e);
        }
        return article;
    }
    
    public List<Attachment> findPkgs(String name,Integer type){
        String sql = "select * from t_attachment where (attachment_name like ? or attachment_description like ?)";
        Object[] vals = null;
        String label = "%"+name+"%";
        if (type > 0) {
            sql += " and attachment_type = ?";
            vals = new Object[]{label,label,type};
        }else{
            vals = new Object[]{label,label};
        }
        ResultList<Attachment> rs = getJdbcTemplate().executeQuery(sql, vals, Attachment.class);
        return rs == null?null:rs.toList();
    }
    
    public Attachment findPkgById(Integer id){
        String sql = "select * from t_attachment where attachment_id = ?";
        try {
            return getJdbcTemplate().querySingle(sql, new Object[]{id}, Attachment.class);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ContentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Attachment> listPkgs(Integer type){
        String sql = "select * from t_attachment";
        Object[] vals = null;
        if (type > 0) {
            sql += " where attachment_type = ?";
            vals = new Object[]{type};
        }
        ResultList<Attachment> rs = getJdbcTemplate().executeQuery(sql, vals, Attachment.class);
        return rs == null?null:rs.toList();
    }
    
    public int addPkgs(Attachment pkg){
        return getJdbcTemplate().save(pkg);
    }
    
    public List<Attachment> deletePkgs(Integer[] ids){
        String quySql = "select * from t_attachment where attachment_id in(%s)";
        String delSql = "delete from t_attachment where attachment_id in(%s)";
        String qus = ArrayUtility.join("?", ids.length, ",");
        quySql = String.format(quySql, qus);
        delSql = String.format(delSql, qus);
        
        ResultList<Attachment> attachments = getJdbcTemplate().executeQuery(quySql, ids, Attachment.class);
        int c = getJdbcTemplate().executeUpdate(delSql, ids);
        return attachments == null?null:attachments.toList();
    }
    
    public Attachment findLatestPkgByType(Integer type){
        String quySql = "select * from t_attachment where attachment_type = ? and attachment_level = (select max(b.attachment_level) from t_attachment b where b.attachment_type = ?)";
        try {
            return getJdbcTemplate().querySingle(quySql, new Object[]{type,type}, Attachment.class);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ContentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int addDetectionInfo(DetectionInfo info){
        return getJdbcTemplate().save(info);
    }
}
