/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.actions;

import com.pj.actions.BaseAction;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author luzhenwen
 */
public class UploadAction extends BaseAction{
    
    private File image;
    private String imageContentType;
    private String imageFileName;
    
    public String uploadImage(){
        String url = "";
        int status = -1;
        if (getImage() != null) {
            String uploadDir = "/images/upload";
            String path = ServletActionContext.getServletContext().getRealPath(uploadDir);
            File dir = new File(path);
            dir.mkdirs();
            
            DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
            Random random = new Random();
            
            String fileName = fmt.format(new Date())+Long.toString(System.currentTimeMillis())+"_"+random.nextInt(100000)+getExtension();
            File target = new File(dir, fileName);
            
            if(getImage().renameTo(target)){
                status = 0;
                url = ServletActionContext.getServletContext().getContextPath() + uploadDir + "/" + fileName;
            }
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("url", url);
        request.setAttribute("location", url);
        request.setAttribute("status", status);
        return SUCCESS;
    }
    
    private String getExtension(){
        if (imageFileName != null) {
            int index = imageFileName.lastIndexOf(".");
            if (index > 0) {
                return imageFileName.substring(index);
            }
        }
        return ".tmp";
    }

    /**
     * @return the image
     */
    public File getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(File image) {
        this.image = image;
    }

    /**
     * @return the imageContentType
     */
    public String getImageContentType() {
        return imageContentType;
    }

    /**
     * @param imageContentType the imageContentType to set
     */
    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    /**
     * @return the imageFileName
     */
    public String getImageFileName() {
        return imageFileName;
    }

    /**
     * @param imageFileName the imageFileName to set
     */
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    
}
