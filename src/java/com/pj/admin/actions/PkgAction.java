/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.actions;

import com.pj.actions.BaseAction;
import com.pj.admin.beans.Attachment;
import com.pj.admin.services.ContentService;
import com.pj.utilities.StringUtility;
import java.io.File;
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
        DEX(7);
        
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
    
    
    private Attachment pkg;
    private File pkgFile;
    private String pkgFileFileName;
    private String pkgFileContentType;
    public String addPkg(){
        if (getPkg() == null) {
            ServletActionContext.getRequest().setAttribute("types", PKG_TYPE.values());
            return INPUT;
        }else{
            
        }
        return SUCCESS;
    }
    
    /**
     * @return the pkg
     */
    public Attachment getPkg() {
        return pkg;
    }

    /**
     * @param pkg the pkg to set
     */
    public void setPkg(Attachment pkg) {
        this.pkg = pkg;
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
}
