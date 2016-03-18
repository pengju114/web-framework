/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.pj.web.jsptag;

import java.util.regex.Matcher;
import javax.servlet.jsp.PageContext;

/**
 * 2011-10-23
 *
 * @author PENGJU
 */
public class TagService {
    /**
     * 根据字符串取一个类的属性或者在pageContext里面的对象的属性
     * @param pageContext
     * @param className 完整类名,包之间用:号隔开;类似于 java:lang:String将被处理为java.lang.String,可为null,null则取对象属性
     * @param attrs 要获取的对象属性名,类似于user.name,则在pageContext中寻找user属性映射的对象,然后取user的name属性
     * @return the property value
     * @throws Exception 
     */
    public static Object getValue(PageContext pageContext, String className, String attrs) throws Exception {
        if (className != null) {
            return getClassField(className, attrs);
        } else {
            return getSimpleValue(pageContext, attrs);
        }
    }

    /**
     * 取一个类的静态属性
     * @param className 完整类名,包之间用:号隔开;类似于 java:lang:String将被处理为java.lang.String
     * @param attrs 要获取的对象属性名,类似于user.name
     * @return
     * @throws Exception 
     */
    private static Object getClassField(String className, String attrs) throws Exception {
        if (attrs == null) {
            return null;
        }
        Class c = findClass(className);

        String[] fs = attrs.split("\\.");
        return get(c, null, fs, 0, fs.length-1);
    }

    /**
     * 取对象属性
     * @param pageContext
     * @param attrs 要获取的对象属性名,类似于user.name,则在pageContext中寻找user属性映射的对象,然后取user的name属性
     * @return
     * @throws Exception 
     */
    private static Object getSimpleValue(PageContext pageContext, String attrs) throws Exception {
        if (attrs == null) {
            return null;
        }
        String[] fs = attrs.split("\\.");
        Object val = pageContext.findAttribute(fs[0]);
        if (val == null) {
            return null;
        }
        return get(val.getClass(), val, fs, 1,fs.length-1);
    }

    public static void showError(Object msg) {
        System.out.println(msg);
    }
    
    static Object get(Class c,Object object,String[] fields,int start,int end) throws Exception{
        while (start<= end) {
            object = Property.getProperty(c, object, fields[start++]);
            if (object == null) {
                return null;
            }
            c=object.getClass();
        }
        return object;
    }
    
    /**
     * 
     * @param pageContext
     * @param string
     * @return
     * @throws Exception 
     */
    public static String replaceEl(PageContext pageContext, String string) throws Exception{
        Matcher el=Analyzer.myElPattern.matcher(string);
        while(el.find()){
            string=el.replaceFirst(String.valueOf(getValue(pageContext, el.group(2), el.group(4))));
            el.reset(string);
        }
        return string;
    }
    
    private static Class findClass(String c) throws Exception{
        c = c.replaceAll(":", ".");
        Class clazz = null;
        try {
            clazz = Class.forName(c);
        } catch (Exception e) {
            showError("class not found:" + c);
            throw e;
        }
        return clazz;
    }
    
    
    public static Object invoke(PageContext pageContext,String method,Object[] params){
        Matcher matcher=Analyzer.simplePattern.matcher(method);
        Object val=null;
        
        if(matcher.find()){
            String cn=matcher.group(2);//class
            String attr=matcher.group(4);//fields
            if(cn!=null){
                val= invoke0(cn,attr,params);//class invoke
            }else{
                val=invoke1(pageContext,attr,params); //object invoke
            }
        }
        return val;
    }

    private static Object invoke0(String cn, String attr, Object[] params) {
        Class c=null;
        try {
            c=findClass(cn);
        } catch (Exception e) {
        }
        Object val=null;
        
        if(c!=null&&attr!=null){
            String[] fs=attr.split("\\.");
            if(fs.length==1){//class static method
                val=Invoker.invoke(c, val, fs[0], params);
            }else{//class static object method
                try {
                    val=get(c, val, fs, 0, fs.length-2);//最后一个是方法名
                    if(val!=null){
                        val=Invoker.invoke(null, val, fs[fs.length-1], params);
                    }
                } catch (Exception e) {
                    showError(e);
                }
                
            }
        }
        return val;
    }

    private static Object invoke1(PageContext pageContext,String attr, Object[] params) {
        if (attr == null) {
            return null;
        }
        String[] fs = attr.split("\\.");
        Object val = pageContext.findAttribute(fs[0]);
        if (val == null) {
            return null;
        }
        
        try {
            val=get(val.getClass(), val, fs, 1, fs.length-2);//最后一个是方法名
            val=Invoker.invoke(null, val, fs[fs.length-1], params);
        } catch (Exception e) {
            showError(e);
        }
        return val;
    }
}
