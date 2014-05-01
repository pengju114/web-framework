/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pj.client.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务类，可以实现execute处理业务
 * 并返回数据ServiceResult
 * @author PENGJU
 * email:pengju114@163.com
 * 时间:2012-9-18 22:29:58
 */
public abstract class ServiceResolver {
    public HttpServletRequest getRequest(){
        return ServiceInvoker.getRequest();
    }
    public HttpServletResponse getResponse(){
        return ServiceInvoker.getResponse();
    }
    public abstract ServiceResult execute() throws Exception;
}
