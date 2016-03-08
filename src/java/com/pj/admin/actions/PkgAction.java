/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.actions;

import com.pj.actions.BaseAction;
import com.pj.admin.beans.Attachment;
import com.pj.admin.services.ContentService;
import com.pj.json.JSONObject;
import com.pj.utilities.ArrayUtility;
import com.pj.utilities.ConvertUtility;
import com.pj.utilities.StringUtility;
import com.pj.utils.parser.APKDecompressor;
import com.pj.utils.parser.AbstractDecompressor;
import com.pj.utils.parser.JARDecompressor;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author luzhenwen
 */
public class PkgAction extends BaseAction {

    
    
    public static enum PKG_TYPE {
        ALL(-1),
        APK(6),
        JAR(7);
        
        private final int value;

        PKG_TYPE(int v) {
            value = v;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    private String type;
    private String name;

    public String findPkg() {
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("types", PKG_TYPE.values());
        PKG_TYPE etype = PKG_TYPE.ALL;
        try {
            etype = PKG_TYPE.valueOf(type);
        } catch (Exception e) {
            etype = PKG_TYPE.ALL;
        }
        
        if (StringUtility.isEmpty(name)) {
            return listPkg(etype);
        } else {
            ContentService service = new ContentService();
            request.setAttribute("pkgs", service.findPkgs(name, etype.getValue()));
            return SUCCESS;
        }
    }
    
    private String listPkg(PKG_TYPE etype) {
        ContentService service = new ContentService();
        ServletActionContext.getRequest().setAttribute("pkgs", service.listPkgs(etype.value));
        return SUCCESS;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * jar 压缩包 文件信息内容：
     * version：版本名
     * versionCode：版本号
     * class：类名
     * description：描述
     * 
     * apk 无。
     */
    private String description;
    private File pkgFile;
    private String pkgFileFileName;
    private String pkgFileContentType;
    public String addPkg(){
        HttpServletRequest request = ServletActionContext.getRequest();
        if (getPkgFile() == null) {
            request.setAttribute("types", PKG_TYPE.values());
            return INPUT;
        }else{
            
            
            String ext = getFileExtension(getPkgFileFileName());
            if (ext == null) {
                request.setAttribute(ERROR, "无法识别的文件类型");
                return INPUT;
            }
            
            ext = ext.toUpperCase();
            
            PKG_TYPE[] types = PKG_TYPE.values();
            String[] stringTypes = new String[types.length - 1];
            for (int i = 0; i < stringTypes.length; i++) {
                stringTypes[i] = types[i+1].name();// 去掉 all
            }
            
            List<String> typeList = Arrays.asList(stringTypes);
            
            if (!typeList.contains(ext)) {
                request.setAttribute("error", "错误的文件类型，只接受后缀为"+ArrayUtility.join(stringTypes, ",")+"的文件。");
                return INPUT;
            }
            PKG_TYPE pkgType = PKG_TYPE.valueOf(getType());
            if (pkgType == PKG_TYPE.ALL) {
                // 自己判断类型
                int index = typeList.indexOf(ext);
                pkgType = PKG_TYPE.valueOf(stringTypes[index]);
            }
            
            AbstractDecompressor.PackageInfo pkgInfo = null;
            
            if (pkgType == PKG_TYPE.APK) {
                APKDecompressor decompressor = new APKDecompressor(getPkgFile());
                pkgInfo = decompressor.decompress();
            }else if (pkgType == PKG_TYPE.JAR) {
                JARDecompressor decompressor = new JARDecompressor(getPkgFile());
                pkgInfo = decompressor.decompress();
            }
            
            if (pkgInfo != null) {
                String path = "/pkgs";
                String realPath = ServletActionContext.getServletContext().getRealPath(path);
                File dir = new File(realPath);
                dir.mkdirs();
                File targetFile = new File(dir, getPkgFileFileName());
                if (targetFile.exists()) {
                    request.setAttribute(ERROR, "文件已存在，不能重复上传。");
                    return INPUT;
                }
                if (getPkgFile().renameTo(targetFile)) {
                    
                    Attachment attachment = new Attachment();
                    
                    attachment.setAttachmentCreateDate(new Date());
                    if (!StringUtility.isEmpty(pkgInfo.description)) {
                        attachment.setAttachmentDescription(pkgInfo.description);
                    }else{
                        attachment.setAttachmentDescription(description);
                    }
                    
                    attachment.setAttachmentFileName(pkgFileFileName);
                    attachment.setAttachmentPath(String.format("%s/%s", path, getPkgFileFileName()));
                    attachment.setAttachmentType(pkgType.getValue());
                    attachment.setAttachmentName(pkgInfo.name);
                    attachment.setAttachmentKeywords(pkgInfo.versionName+"|"+pkgInfo.versionCode);
                    attachment.setAttachmentSize(targetFile.length());
                    attachment.setAttachmentLevel(pkgInfo.versionCode.intValue());
                    
                    ContentService service = new ContentService();
                    int c = service.addPkgs(attachment);
                    if (c < 1) {
                        request.setAttribute(ERROR, "上传失败，请重新尝试");
                    }
                    
                    return c < 1?INPUT:SUCCESS;
                }
            }
        }
        return INPUT;
    }
    
    private String getFileExtension(String fileName){
        if (StringUtility.isEmpty(fileName)) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            return fileName.substring(index+1);
        }
        return null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

    /**
     * @return the pkgFile
     */
    public File getPkgFile() {
        return pkgFile;
    }

    /**
     * @param pkgFile the pkgFile to set
     */
    public void setPkgFile(File pkgFile) {
        this.pkgFile = pkgFile;
    }

    /**
     * @return the pkgFileFileName
     */
    public String getPkgFileFileName() {
        return pkgFileFileName;
    }

    /**
     * @param pkgFileFileName the pkgFileFileName to set
     */
    public void setPkgFileFileName(String pkgFileFileName) {
        this.pkgFileFileName = pkgFileFileName;
    }

    /**
     * @return the pkgFileContentType
     */
    public String getPkgFileContentType() {
        return pkgFileContentType;
    }

    /**
     * @param pkgFileContentType the pkgFileContentType to set
     */
    public void setPkgFileContentType(String pkgFileContentType) {
        this.pkgFileContentType = pkgFileContentType;
    }
    
    
    
    private String[] id;
    public String deletePkg(){
        JSONObject object = new JSONObject();
        try {
            object.put("status", -1);
        } catch (Exception e) {
        }
        
        if (id != null && id.length > 0) {
            Integer[] ids = new Integer[id.length];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = ConvertUtility.parseInt(id[i]);
            }
            
            ContentService service = new ContentService();
            List<Attachment> attachments = service.deletePkgs(ids);
            
            if (attachments != null && attachments.size() > 0) {
                try {
                    object.put("status", 0);
                } catch (Exception e) {
                }
                try {
                    object.put("message", "删除成功");
                } catch (Exception e) {
                }
                
                // 删除本地文件
                for (Attachment attachment : attachments) {
                    String path = ServletActionContext.getServletContext().getRealPath(attachment.getAttachmentPath());
                    File file = new File(path);
                    file.delete();
                    
                }
            }else{
                try {
                    object.put("message","删除失败");
                } catch (Exception e) {
                }
            }
        }else{
            try {
                object.put("message","请选择要删除的文件");
            } catch (Exception e) {
            }
        }
        
        try {
            ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
            ServletActionContext.getResponse().setContentType("text/json");
            ServletActionContext.getResponse().getWriter().write(object.toString());
        } catch (Exception e) {
        }
        
        return null;
    }

    public String[] getId() {
        return id;
    }

    public void setId(String[] id) {
        this.id = id;
    }
    
}
