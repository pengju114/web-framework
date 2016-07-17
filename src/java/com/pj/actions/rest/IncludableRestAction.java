/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.actions.rest;

/**
 *
 * @author luzhenwen
 */
public class IncludableRestAction extends RestAction{

    @Override
    public String execute() throws Exception {
        super.execute();
        return  null;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String getDirtySuffix() {
        return "/include.action"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
