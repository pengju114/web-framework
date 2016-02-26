/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.beans;

import com.pj.jdbc.annotation.Column;
import com.pj.jdbc.annotation.Table;
import java.util.Date;

/**
 *
 * @author luzhenwen
 */
@Table(name = "t_attachment")
public class Attachment {
    @Column(name = "attachment_id",save = false)
    private Integer attachmentId;
    @Column(name = "attachment_url")
    private String attachmentUrl;
    @Column(name = "attachment_path")
    private String attachmentPath; 
    @Column(name = "attachment_name")
    private String attachmentName; 
    @Column(name = "attachment_description")
    private String attachmentDescription;
    @Column(name = "attachment_file_name")
    private String attachmentFileName; 
    @Column(name = "attachment_size")
    private Long attachmentSize; 
    @Column(name = "attachment_level")
    private Integer attachmentLevel; 
    @Column(name = "attachment_keywords")
    private String attachmentKeywords; 
    @Column(name = "attachment_create_date")
    private Date attachmentCreateDate;
    @Column(name = "attachment_status")
    private Integer attachmentStatus;
    @Column(name = "attachment_user_id")
    private Integer attachmentUserId;
    @Column(name = "attachment_type")
    private Integer attachmentType;

    /**
     * @return the attachmentId
     */
    public Integer getAttachmentId() {
        return attachmentId;
    }

    /**
     * @param attachmentId the attachmentId to set
     */
    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    /**
     * @return the attachmentUrl
     */
    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    /**
     * @param attachmentUrl the attachmentUrl to set
     */
    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    /**
     * @return the attachmentPath
     */
    public String getAttachmentPath() {
        return attachmentPath;
    }

    /**
     * @param attachmentPath the attachmentPath to set
     */
    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    /**
     * @return the attachmentName
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * @param attachmentName the attachmentName to set
     */
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    /**
     * @return the attachmentDescription
     */
    public String getAttachmentDescription() {
        return attachmentDescription;
    }

    /**
     * @param attachmentDescription the attachmentDescription to set
     */
    public void setAttachmentDescription(String attachmentDescription) {
        this.attachmentDescription = attachmentDescription;
    }

    /**
     * @return the attachmentFileName
     */
    public String getAttachmentFileName() {
        return attachmentFileName;
    }

    /**
     * @param attachmentFileName the attachmentFileName to set
     */
    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    /**
     * @return the attachmentSize
     */
    public Long getAttachmentSize() {
        return attachmentSize;
    }

    /**
     * @param attachmentSize the attachmentSize to set
     */
    public void setAttachmentSize(Long attachmentSize) {
        this.attachmentSize = attachmentSize;
    }

    /**
     * @return the attachmentLevel
     */
    public Integer getAttachmentLevel() {
        return attachmentLevel;
    }

    /**
     * @param attachmentLevel the attachmentLevel to set
     */
    public void setAttachmentLevel(Integer attachmentLevel) {
        this.attachmentLevel = attachmentLevel;
    }

    /**
     * @return the attachmentKeywords
     */
    public String getAttachmentKeywords() {
        return attachmentKeywords;
    }

    /**
     * @param attachmentKeywords the attachmentKeywords to set
     */
    public void setAttachmentKeywords(String attachmentKeywords) {
        this.attachmentKeywords = attachmentKeywords;
    }

    /**
     * @return the attachmentCreateDate
     */
    public Date getAttachmentCreateDate() {
        return attachmentCreateDate;
    }

    /**
     * @param attachmentCreateDate the attachmentCreateDate to set
     */
    public void setAttachmentCreateDate(Date attachmentCreateDate) {
        this.attachmentCreateDate = attachmentCreateDate;
    }

    /**
     * @return the attachmentStatus
     */
    public Integer getAttachmentStatus() {
        return attachmentStatus;
    }

    /**
     * @param attachmentStatus the attachmentStatus to set
     */
    public void setAttachmentStatus(Integer attachmentStatus) {
        this.attachmentStatus = attachmentStatus;
    }

    /**
     * @return the attachmentUserId
     */
    public Integer getAttachmentUserId() {
        return attachmentUserId;
    }

    /**
     * @param attachmentUserId the attachmentUserId to set
     */
    public void setAttachmentUserId(Integer attachmentUserId) {
        this.attachmentUserId = attachmentUserId;
    }

    /**
     * @return the attachmentType
     */
    public Integer getAttachmentType() {
        return attachmentType;
    }

    /**
     * @param attachmentType the attachmentType to set
     */
    public void setAttachmentType(Integer attachmentType) {
        this.attachmentType = attachmentType;
    }
    
    
}
