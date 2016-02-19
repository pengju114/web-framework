/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.web.res;

/**
 *
 * @author lzw
 */
public interface Constans {
    interface Key{
        String CURRENT_ADMIN  ="session.current.admin";
        String VALIDATION_CODE="session.validation.code";
        String CURRENT_USER  ="session.current.user";
    }
    
    interface AuthorityKey{
        /** 删除管理员权限唯一标示 */
        String DELETE_ADMIN = "scgly";
        /** 删除权限唯一标示 */
        String DELETE_AUTHORITY = "scqx";
        /** 添加权限唯一标示 */
        String ADD_AUTHORITY = "tjqx";
        /** 删除权限唯一标示 */
        String DELETE_ROLE = "scjs";
        /** 添加权限唯一标示 */
        String ADD_ROLE = "tjjs";
    }
}
