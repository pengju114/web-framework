/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pj.client.core.invokers;

import com.pj.client.core.ServiceInvoker;
import com.pj.client.core.ServiceResult;

/**
 *
 * @author PENGJU
 * email:pengju114@163.com
 * 时间:2012-9-20 16:37:41
 */
public abstract class BaseServiceInvoker extends ServiceInvoker{

    @Override
    public void invokePrepare() throws Exception {
    }

    @Override
    public void invokeComplete(ServiceResult result) throws Exception {
    }

}
