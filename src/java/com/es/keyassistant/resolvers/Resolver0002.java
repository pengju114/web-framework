/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.es.keyassistant.resolvers;

import com.pj.admin.beans.Attachment;
import com.pj.admin.services.ContentService;
import com.pj.client.core.ServiceResult;
import java.util.Map;

/**
 * 查询最新检测包
 * @author luzhenwen
 */
public class Resolver0002 extends BaseResolver{

    @Override
    public ServiceResult execute() throws Exception {
        Integer type = getIntParameter("type");
        ContentService service = new ContentService();
        Attachment attachment = service.findLatestPkgByType(type);
        ServiceResult result = new ServiceResult();
        if (attachment != null) {
            
            Map<String,Object> e = makeMapByKeyAndValues(
                    "lastVersion",attachment.getAttachmentLevel(),/*最新检测包版本*/
                    "updateTime",attachment.getAttachmentCreateDate()!=null?attachment.getAttachmentCreateDate().getTime():0,/*更新时间*/
                    "versionInfo",attachment.getAttachmentDescription(),
                    "fileId",attachment.getAttachmentId()
            );
                
            result.getData().add(e);
        }
               
        return result;
    }
    
}
