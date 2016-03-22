/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.es.keyassistant.invokers;

import com.pj.client.core.ClientException;
import com.pj.client.file.EasyDownload;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author luzhenwen
 */
public class FileInvoker extends AuthenticationInvoker{
    private static final String STREAM_TYPE = "application/octet-stream";
    
    private String contentType = STREAM_TYPE;

    
    @Override
    protected String getContentType(){
        return contentType;
    }
    
    @Override
    protected void writeResult(PrintWriter writer, Map<String, Object> rMap) throws IOException {
        Map<String,Object> header = (Map<String,Object>) rMap.get(KEY_HEADER);
        Number status = (Number) header.get(KEY_HEADER_STATUS_CODE);
        if (status.intValue() != ClientException.REQUEST_OK) {
            getResponse().sendError(HttpServletResponse.SC_NOT_FOUND, (String)header.get(KEY_HEADER_STATUS_TEXT));
            return;
        }
        contentType = STREAM_TYPE;
        List data = (List) rMap.get(KEY_RESULT);
        for (Object d : data) {
            if (d instanceof File) {
                File file = (File) d;
                LOGGER.info("下载文件:"+file.getAbsolutePath());
                downloadFile(file.getAbsolutePath());
                break;
            }else if(d instanceof String){
                String path = (String) d;
                File testFile = new File(path);
                if (testFile.exists()) {
                    LOGGER.info("下载文件:"+path);
                    downloadFile(path);
                    break;
                }else{
                    getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }
    }
    
    private void downloadFile(String path) throws IOException{
        EasyDownload downloader = new EasyDownload(getResponse());
        downloader.setCharset(getCharset());
        downloader.setFilePath(path);
        downloader.download();
    }
}
