/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.es.keyassistant.resolvers;

import com.pj.admin.beans.Article;
import com.pj.admin.services.ContentService;
import com.pj.client.core.ServiceResult;
import com.pj.jdbc.core.ResultList;
import java.util.Map;

/**
 * 获取常见QA接口
 * @author luzhenwen
 */
public class Resolver0001 extends BaseResolver{

    @Override
    public ServiceResult execute() throws Exception {
        int pageNumber = getPageNumber();
        int pageSize   = getPageSize();
        String title = getStringParameter("title");
        ContentService service = new ContentService();
        ResultList<Article> articles = service.findQAByTitle(title, pageNumber, pageSize);
        ServiceResult result = new ServiceResult();
        for (Article article : articles) {
            
            Map<String,Object> e = makeMapByKeyAndValues(
                    "articleId", article.getArticleId(),
                    "articleTitle", article.getArticleTitle(),
                    "articleCreateDate", article.getArticleCreateDate(),
                    "articleAbstract", article.getArticleAbstract()
            );
                
            result.getData().add(e);
        }
        
        calculatePageProperties(articles.getTotalRowsCount(), result);
        
        return result;
    }
    
}
