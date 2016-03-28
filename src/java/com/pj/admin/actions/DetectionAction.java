/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.actions;

import com.pj.actions.BaseAction;

/**
 *
 * @author luzhenwen
 */
public class DetectionAction extends BaseAction{
    private String type;
    private String text;
    private Integer pageNumber;
    private Integer pageSize;
    public String findDetectionInfo(){
        return SUCCESS;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    
    
    private Integer[] diIds;
    public String deleteDetectionInfo(){
        return null;
    }

    public Integer[] getDiIds() {
        return diIds;
    }

    public void setDiIds(Integer[] diIds) {
        this.diIds = diIds;
    }
    
}
