package com.pj.web.jsptag;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 2011-10-22
 *
 * @author PENGJU
 */
public class Property {

    /**
     * 获取一个类或者一个对象的属性，如果target为null就取类的静态属性
     *
     * @param clazz
     * @param target
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static Object getProperty(Class clazz, Object target, String fieldName) throws Exception {
        if(target instanceof Map){
            return ((Map)target).get(fieldName);
        }
        Object val = null;
        try {
            val=getPropertyByField(clazz, target, fieldName);
        } catch (Exception e) {
            TagService.showError("field not found: "+e.getMessage());
        }
        
        if (val == null) {
            val = getPropertyByMethod(clazz, target, fieldName);
        }
        return val;
    }

    /**
     * 从当前类中获取一个类或者一个对象的属性，如果target为null就取类的静态属性
     * @param clazz
     * @param target
     * @param fieldName
     * @return
     * @throws Exception 
     */
    private static Object getPropertyByField(Class clazz, Object target, String fieldName) throws Exception {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
            field = clazz.getField(fieldName);
        }
        field.setAccessible(true);
        if (target == null) {
            return field.get(clazz);
        }
        return field.get(target);
    }

    /**
     * 
     * @param clazz
     * @param target
     * @param fieldName
     * @return
     * @throws Exception 
     */
    private static Object getPropertyByMethod(Class clazz, Object target, String fieldName) throws Exception {
        fieldName="get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
        Method method = clazz.getMethod(fieldName, null);
        if (method != null) {
            return method.invoke(target,  null);
        }
        return null;
    }
    
    public static void setProperty(Class clazz, Object target, String fieldName,Object val) throws Exception{
        try {
            setPropertyByField(clazz, target, fieldName, val);
        } catch (Exception e) {
            TagService.showError("field not found: "+e.getMessage());
            setPropertyByMethod(clazz, target, fieldName, val);
        }
    }
    
    private static void setPropertyByField(Class clazz, Object target, String fieldName,Object val) throws Exception{
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
            field = clazz.getField(fieldName);
        }
        field.setAccessible(true);
        if (target == null) {
            field.set(clazz,val);
        }else{
            field.set(target,val);
        }
    }
    
    private static void setPropertyByMethod(Class clazz, Object target, String fieldName,Object val) throws Exception{
        fieldName="set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
        Invoker.invoke(clazz, target, fieldName, new Object[]{val});
    }
}
