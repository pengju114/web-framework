/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.actions;

import com.pj.actions.BaseAction;
import com.pj.admin.beans.Article;
import com.pj.admin.services.ContentService;
import com.pj.utilities.ConvertUtility;
import com.pj.utilities.StringUtility;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author luzhenwen
 */
public class QAAction extends BaseAction{
    
    private String title;
    public String findQA(){
        if (StringUtility.isEmpty(title)) {
            return listQA();
        }else{
            ContentService service = new ContentService();
            HttpServletRequest request = ServletActionContext.getRequest();
            List<Article> list = service.findQAByTitle(title, ConvertUtility.parseInt(request.getParameter("pageNumber"), 1)-1, ConvertUtility.parseInt(request.getParameter("pageSize"), Integer.MAX_VALUE));
            request.setAttribute("qas", list);
            request.setAttribute("title", title);
            return SUCCESS;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String listQA(){
        ContentService service = new ContentService();
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Article> list = service.listQA(ConvertUtility.parseInt(request.getParameter("pageNumber"), 1)-1, ConvertUtility.parseInt(request.getParameter("pageSize"), Integer.MAX_VALUE));
        request.setAttribute("qas", list);
        return SUCCESS;
    }
    
    
    private Article article;
    public String addQA(){
        ServletActionContext.getRequest().setAttribute("addQA", true);
        
        if (article != null) {
            
            if (StringUtility.isEmpty(article.getArticleTitle()) || StringUtility.isEmpty(article.getArticleContent())) {
                ServletActionContext.getRequest().setAttribute("tip", "标题或内容为空");
            }else{
                Date date = new Date();
                article.setArticleCreateDate(date);
                article.setArticleLastModifyDate(date);
                article.setArticlePublishDate(date);
                article.setArticleStatus(0);

                ContentService service = new ContentService();
                int c = service.addQA(article);
                ServletActionContext.getRequest().setAttribute("success", c > 0);
            }
        }
        return SUCCESS;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    
    
    private String id;
    public String viewQA(){
        Integer qaId  = ConvertUtility.parseInt(id,0);
        if (qaId > 0) {
            ContentService service = new ContentService();
            Article myArticle = service.findArticleById(qaId);
            ServletActionContext.getRequest().setAttribute("article", myArticle);
        }
        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
