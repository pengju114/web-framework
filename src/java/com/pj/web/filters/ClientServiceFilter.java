/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.web.filters;

import com.pj.client.core.ServiceDispatcher;
import com.pj.client.core.resolvers.RestURIResolver;
import com.pj.web.res.Config;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 客户端服务接口过滤器
 * @author luzhenwen
 */
public class ClientServiceFilter implements Filter{
    private String servletName;

    public void init(FilterConfig filterConfig) throws ServletException {
        servletName = Config.getConfig(RestURIResolver.REST_URI_PATH_CONFIG_KEY, RestURIResolver.REST_URI_PATH);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        String path = req.getContextPath();
        if (servletName.startsWith("/")) {
            path = path+servletName;
        }else{
            path = path + "/" + servletName;
        }
        String reqUri = req.getRequestURI();
        if (reqUri.startsWith(path)) {
            ServiceDispatcher.dispatch(req, res);
        }else{
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        
    }
    
}
