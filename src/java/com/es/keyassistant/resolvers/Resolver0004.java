/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.es.keyassistant.resolvers;

import com.es.keyassistant.beans.DetectionInfo;
import com.pj.admin.services.ContentService;
import com.pj.client.core.ClientException;
import com.pj.client.core.ServiceResult;
import com.pj.utilities.ConvertUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 上传检测结果
 * @author luzhenwen
 */
public class Resolver0004 extends BaseResolver{
    
    private static final String TMP_PATH = "/detectionTmp";
    private static final String STORE_PATH = "/detection";

    @Override
    public ServiceResult execute() throws Exception {
        File dir = new File(getRequest().getServletContext().getRealPath(TMP_PATH));
        dir.mkdirs();
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(5 * 1024);
        factory.setRepository(dir);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        upload.setSizeMax(5 * 1024 * 1024);
        List<FileItem> formItems = upload.parseRequest(getRequest());
        
        DetectionInfo info = new DetectionInfo();
        assignPropertiesTo(formItems,info);
        
        for (FileItem formItem : formItems) {
            if (!formItem.isFormField()) {
                String fileName = formItem.getName();
                String targetFileName = generateDectionFileName(fileName, info);
                info.setDetectionFileName(targetFileName);
                info.setDetectionFilePath(String.format("%s/%s", STORE_PATH,targetFileName));
                
                File storeDir = new File(getRequest().getServletContext().getRealPath(STORE_PATH));
                storeDir.mkdirs();
                File detectionFile = new File(storeDir, targetFileName);
                
                formItem.write(detectionFile);
                
                formItem.delete();
                break;
            }
        }
        
        if (info.getDetectionSN() == null) {
            throw new ClientException(ClientException.REQUEST_ERROR, "上传失败");
        }
        
        ContentService service = new ContentService();
        if (service.addDetectionInfo(info) < 0) {
            throw new ClientException(ClientException.REQUEST_ERROR, "存储数据失败，请重新上传");
        }
        
        ServiceResult result = new ServiceResult();
        result.getData().add(makeMapByKeyAndValues("receiptNumber",info.getDetectionSN()));
        
        return result;
    }

    private void assignPropertiesTo(List<FileItem> formItems, DetectionInfo info) {
        HashMap<String,String> params = new HashMap<String, String>();
        for (FileItem formItem : formItems) {
            if (formItem.isFormField()) {
                try {
                    params.put(formItem.getFieldName(), formItem.getString("UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Resolver0004.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        info.setDetectionOSVersion(params.get("androidVersion"));
        info.setDetectionPhoneId(params.get("identity"));
        info.setDetectionPhoneBrand(params.get("brand"));
        info.setDetectionPhoneModel(params.get("module"));
        info.setDetectionResult(ConvertUtility.parseInt(params.get("result"),-1));
        info.setDetectionOS(params.containsKey("os")?params.get("os"):"Android");
        info.setDetectionDate(new Date());
    }
    
    private String generateDectionFileName(String tmpName,DetectionInfo info){
        String suffix = ".dat";
        int index = tmpName.lastIndexOf(".");
        if (index > -1) {
            suffix = tmpName.substring(index);
        }
        
        info.setDetectionSN(genarateDetectionSN(info));
        
        return info.getDetectionSN()+suffix;
    }
    
    private String genarateDetectionSN(DetectionInfo info){
        Date now = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmss");
        Random random = new Random();
        byte[] bs = new byte[8];
        random.nextBytes(bs);
        
        StringBuilder sb = new StringBuilder(bs.length);
        for (int i = 0; i < bs.length; i++) {
            byte b = bs[i];
            sb.append(b);
        }
        String bm = (info.getDetectionPhoneBrand()+"."+info.getDetectionPhoneModel()).replaceAll("\\s+", "_");
        return String.format("DSN%s-%s-%s-%s", fmt.format(now),bm,info.getDetectionPhoneId(),sb);
    }
    
}
