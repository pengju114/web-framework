/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.es.keyassistant.beans;

import com.pj.jdbc.annotation.Column;
import com.pj.jdbc.annotation.Table;
import java.util.Date;

/**
 *
 * @author luzhenwen
 */
@Table(name = "t_detection_info")
public class DetectionInfo {
    @Column(name = "detection_id", save = false)
    private Integer detectionId; 
    
    @Column(name = "detection_phone_id")
    private String  detectionPhoneId;
    
    @Column(name = "detection_file_name")
    private String  detectionFileName;
    
    @Column(name = "detection_file_path")
    private String  detectionFilePath; 
    
    @Column(name = "detection_os_version")
    private String  detectionOSVersion;
    
    @Column(name = "detection_phone_brand")
    private String  detectionPhoneBrand;
    
    @Column(name = "detection_phone_model")
    private String  detectionPhoneModel;
    
    @Column(name = "detection_result")
    private Integer detectionResult;
    
    @Column(name = "detection_sn")
    private String detectionSN;
    
    @Column(name = "detection_date")
    private Date detectionDate;
    
    @Column(name = "detection_os")
    private String detectionOS;
    

    /**
     * @return the detectionId
     */
    public Integer getDetectionId() {
        return detectionId;
    }

    /**
     * @param detectionId the detectionId to set
     */
    public void setDetectionId(Integer detectionId) {
        this.detectionId = detectionId;
    }

    /**
     * @return the detectionPhoneId
     */
    public String getDetectionPhoneId() {
        return detectionPhoneId;
    }

    /**
     * @param detectionPhoneId the detectionPhoneId to set
     */
    public void setDetectionPhoneId(String detectionPhoneId) {
        this.detectionPhoneId = detectionPhoneId;
    }

    public String getDetectionFileName() {
        return detectionFileName;
    }

    public void setDetectionFileName(String detectionFileName) {
        this.detectionFileName = detectionFileName;
    }

    

    /**
     * @return the detectionFilePath
     */
    public String getDetectionFilePath() {
        return detectionFilePath;
    }

    /**
     * @param detectionFilePath the detectionFilePath to set
     */
    public void setDetectionFilePath(String detectionFilePath) {
        this.detectionFilePath = detectionFilePath;
    }

    /**
     * @return the detectionOSVersion
     */
    public String getDetectionOSVersion() {
        return detectionOSVersion;
    }

    /**
     * @param detectionOSVersion the detectionOSVersion to set
     */
    public void setDetectionOSVersion(String detectionOSVersion) {
        this.detectionOSVersion = detectionOSVersion;
    }

    /**
     * @return the detectionPhoneBrand
     */
    public String getDetectionPhoneBrand() {
        return detectionPhoneBrand;
    }

    /**
     * @param detectionPhoneBrand the detectionPhoneBrand to set
     */
    public void setDetectionPhoneBrand(String detectionPhoneBrand) {
        this.detectionPhoneBrand = detectionPhoneBrand;
    }

    /**
     * @return the detectionPhoneModel
     */
    public String getDetectionPhoneModel() {
        return detectionPhoneModel;
    }

    /**
     * @param detectionPhoneModel the detectionPhoneModel to set
     */
    public void setDetectionPhoneModel(String detectionPhoneModel) {
        this.detectionPhoneModel = detectionPhoneModel;
    }

    /**
     * @return the detectionResult
     */
    public Integer getDetectionResult() {
        return detectionResult;
    }

    /**
     * @param detectionResult the detectionResult to set
     */
    public void setDetectionResult(Integer detectionResult) {
        this.detectionResult = detectionResult;
    }

    public String getDetectionSN() {
        return detectionSN;
    }

    public void setDetectionSN(String detectionSN) {
        this.detectionSN = detectionSN;
    }

    public Date getDetectionDate() {
        return detectionDate;
    }

    public void setDetectionDate(Date detectionDate) {
        this.detectionDate = detectionDate;
    }

    public String getDetectionOS() {
        return detectionOS;
    }

    public void setDetectionOS(String detectionOS) {
        this.detectionOS = detectionOS;
    }
    
    
    
}
