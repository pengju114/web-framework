/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.es.keyassistant.resolvers;

import com.pj.admin.beans.Article;
import com.pj.admin.services.ContentService;
import com.pj.client.core.ServiceResult;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            String urlPath = getRequest().getRequestURL().toString();
            String scheme = getRequest().getScheme();
            int i = urlPath.indexOf("/", scheme.length()+3);// 跳过协议和"://"
            if (i > (scheme.length() + 3)) {
                urlPath = urlPath.substring(0, i);
            }
            String content = article.getArticleContent();
            String regexp = "(\")([^\"\\s]{14,})(\")";              
            content = content.replaceAll(regexp, "\""+urlPath+"$2"+"\"");// 将图片url替换成完整的url.
            Map<String,Object> e = makeMapByKeyAndValues(
                    "articleId", article.getArticleId(),
                    "articleTitle", article.getArticleTitle(),
                    "articleCreateDate", article.getArticleCreateDate(),
                    "articleAbstract", article.getArticleAbstract(),
                    "articleContent", content
            );
                
            result.getData().add(e);
        }
        
        
        return result;
    }
    
}
