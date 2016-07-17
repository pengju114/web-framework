/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.actions.rest;

import com.pj.actions.BaseAction;
import com.pj.client.core.ServiceInvoker;
import com.pj.client.core.resolvers.RestURIResolver;
import com.pj.client.rest.RestURIBuilderDataSource;
import com.pj.client.rest.Tuple;
import com.pj.jdbc.core.ResultList;
import com.pj.jdbc.core.ResultRow;
import com.pj.jdbc.services.RestURIService;
import com.pj.utilities.ConvertUtility;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 * 处理REST URI的action,uri 是:/rest/operation/target/condition...
 * 配置是 /rest/*
 * @author luzhenwen
 */
public class RestAction extends BaseAction{
    public static final String REST_PREFIX = "/rest";
    

    @Override
    public String execute() throws Exception {
        HttpServletRequest request = (HttpServletRequest) ServletActionContext.getRequest(); 
        String uri = request.getRequestURI();
        String prefix = getDirtyPrefix();
        int index = uri.indexOf(prefix);
        if (index != -1) {
            uri = uri.substring(index + prefix.length());
            String suffix = getDirtySuffix();
            index = uri.lastIndexOf(suffix);
            if (index != -1) {
                uri = uri.substring(0, index);
            }
            
            return executeRestURI(uri, request);
        }else{
            request.setAttribute("exception", new IllegalAccessException("illegal rest uri"));
        }
        return null;
    }
    
    public String executeRestURI(String restURI, HttpServletRequest request){
        RestURIResolver resolver = new RestURIResolver();
        Tuple<CharSequence,Object> tuple;
        try {
            tuple = resolver.executeRestURI(restURI, getBuilderDataSource());
        } catch (Exception ex) {
            ServletActionContext.getRequest().setAttribute("exception", ex);
            Logger.getLogger(RestAction.class.getName()).log(Level.SEVERE, null, ex);
            return ERROR;
        }
        return handleRestResult(tuple,request);
    }
    
    protected String getDirtyPrefix(){
        return REST_PREFIX;
    }
    
    protected String getDirtySuffix(){
        return ".action";
    }
    
    public String handleRestResult(Tuple<CharSequence,Object> tuple,HttpServletRequest request){
        ServiceInvoker.setRequest(request);
        RestURIService service = new RestURIService();
        if (tuple.isUpdate) {
            int update = service.update(tuple);
            HashMap<String, Integer> ret = new HashMap<String, Integer>(1);
            ret.put("update", update);
            request.setAttribute("result", ret);
            ServiceInvoker.clear();
            return SUCCESS;
        }else{
            int page = ConvertUtility.parseInt(ServletActionContext.getRequest().getParameter(ServiceInvoker.KEY_HEADER_PAGENUMBER), 1);
            int pageSize = ConvertUtility.parseInt(ServletActionContext.getRequest().getParameter(ServiceInvoker.KEY_HEADER_PAGESIZE), Integer.MAX_VALUE);
            ResultList<ResultRow> resultList = service.get(tuple, page, pageSize);
           
            request.setAttribute("result", resultList);
            request.setAttribute(ServiceInvoker.KEY_HEADER_PAGECOUNT, calculatePageCount(resultList == null?0:resultList.getTotalRowsCount(), page, pageSize));
            request.setAttribute(ServiceInvoker.KEY_HEADER_PAGENUMBER, page);
            request.setAttribute(ServiceInvoker.KEY_HEADER_PAGESIZE, pageSize);
            ServiceInvoker.clear();
            return SUCCESS;
        }
    }
    
    protected RestURIBuilderDataSource getBuilderDataSource(){
        return null;
    }
    
    
    public static int calculatePageCount(long total,int pageNumber,int pageSize){
                
        if (total>0) {
            int pg = (int)(total/pageSize);
            if (total%pageSize>0) {
                pg++;
            }
            return pg;
        }
        
        return 0;
    }
}
