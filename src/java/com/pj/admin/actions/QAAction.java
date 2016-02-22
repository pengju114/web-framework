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
        List<Article> list = service.findQAByTitle(title, ConvertUtility.parseInt(request.getParameter("pageNumber"), 1)-1, ConvertUtility.parseInt(request.getParameter("pageSize"), Integer.MAX_VALUE));
        request.setAttribute("qas", list);
        
        return SUCCESS;
    }
}
