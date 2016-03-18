/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.es.keyassistant.invokers;

import com.pj.client.core.ClientException;
import static com.pj.client.core.ServiceInvoker.getRequest;

/**
 *
 * @author luzhenwen
 */
public class AuthenticationInvoker extends com.pj.client.core.invokers.JSONInvoker{
    @Override
    public void invokePrepare() throws Exception {
        // 身份校验
        String token = getRequest().getParameter("token");
        if (!checkToken(token)) {
            throw new ClientException(ClientException.REQUEST_ILLEGAL_ACCESS, "非法访问");
        }
    }
    
    
    protected boolean checkToken(String token){
        return true;// 暂时不做校验
    }
}
