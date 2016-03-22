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
 * 获取单个常见QA接口
 * @author luzhenwen
 */
public class Resolver0005 extends BaseResolver{

    @Override
    public ServiceResult execute() throws Exception {
        Integer id = getIntParameter("articleId");
        ContentService service = new ContentService();
        Article article = service.findArticleById(id);
        ServiceResult result = new ServiceResult();
        
        if (article != null) {
            Map<String,Object> e = makeMapByKeyAndValues(
                    "articleId", article.getArticleId(),
                    "articleTitle", article.getArticleTitle(),
                    "articleCreateDate", article.getArticleCreateDate(),
                    "articleAbstract", article.getArticleAbstract(),
                    "articleContent", article.getArticleContent()
            );
                
            result.getData().add(e);
        }
        
        
        return result;
    }
    
}
