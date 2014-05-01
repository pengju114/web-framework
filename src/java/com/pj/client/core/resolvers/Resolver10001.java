/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pj.client.core.resolvers;

import com.pj.client.core.ServiceResolver;
import com.pj.client.core.ServiceResult;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PENGJU
 * email:pengju114@163.com
 * 时间:2012-9-20 0:39:17
 */
public class Resolver10001 extends ServiceResolver{

    @Override
    public ServiceResult execute() throws Exception {
        ServiceResult result=new ServiceResult();
        Map<String,Object> m=new HashMap<String, Object>();
        m.put("name", "pengju");
        m.put("age", "123");
        result.getData().add(m);
        return result;
    }
}
