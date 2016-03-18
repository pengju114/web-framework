/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.es.keyassistant.resolvers;

import com.pj.admin.beans.Attachment;
import com.pj.admin.services.ContentService;
import com.pj.client.core.ClientException;
import com.pj.client.core.ServiceResult;
import com.pj.utilities.ConvertUtility;

/**
 * 下载apk或者jar包服务
 * @author luzhenwen
 */
public class Resolver0003 extends BaseResolver{

    @Override
    public ServiceResult execute() throws Exception {
        String fileId = getSafeStringParameter("fileId");
        ContentService service = new ContentService();
        Attachment attachment = service.findPkgById(ConvertUtility.parseInt(fileId));
        if (attachment == null) {
            throw new ClientException(ClientException.REQUEST_ERROR, "文件未找到");
        }else{
            ServiceResult result = new ServiceResult();
            String path = getRequest().getServletContext().getRealPath(attachment.getAttachmentPath());
            result.getData().add(path);
            return result;
        }
    }
    
}
