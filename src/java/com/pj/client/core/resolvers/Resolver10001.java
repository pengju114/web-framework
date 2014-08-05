/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pj.client.core.resolvers;

import com.pj.client.core.ServiceResolver;
import com.pj.client.core.ServiceResult;
import com.pj.jdbc.core.ResultList;
import com.pj.jdbc.core.ResultRow;
import com.pj.jdbc.services.BaseService;
import com.pj.jdbc.services.DebugService;
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
        
        DebugService service = new DebugService();
        ResultList<ResultRow> dataList = service.listAll((getPageNumber()-1)*getPageSize(),getPageSize());
        result.getData().addAll(dataList.toList());
        caculatePageProperties(dataList.getTotalRowsCount(),result);
        getRequest().getSession(true).getAttribute("");
        return result;
    }
}
