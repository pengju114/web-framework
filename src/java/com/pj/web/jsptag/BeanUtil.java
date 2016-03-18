package com.pj.web.jsptag;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pengju 2011-02-25
 */
public class BeanUtil {

    public static Object filter(HttpServletRequest request, Class c) throws Exception {
        return filter(request,c.newInstance());
    }

    public static Object filter(HttpServletRequest request, Object obj)throws Exception{
        if(obj==null)return null;
        Method[] mds = obj.getClass().getMethods();
        HashMap<String, Method> mdMap = new HashMap<String, Method>();
        if (mds == null || mds.length <= 0) {
            return obj;
        }

        for (Method m : mds) {
            if (m.getName().startsWith("set")&&m.getGenericParameterTypes().length==1) {
                mdMap.put(m.getName(), m);//取所有的setter方法
            }
        }

        Enumeration pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = (String) pNames.nextElement();
            Method m = mdMap.get(ms(name, "set"));
            if (m != null) {//存在此setter
                String[] vs = request.getParameterValues(name);
                Class[] myCls = m.getParameterTypes();
                if (myCls.length > 0) {
                    if (myCls[0].equals(String.class)) {
                        m.invoke(obj, vs[0]);
                    } else if (myCls[0].equals(Integer.TYPE)||myCls[0].equals(Integer.class)) {
                        m.invoke(obj, new Integer(vs[0].trim()));
                    }else if (myCls[0].equals(vs.getClass())) {
                        m.invoke(obj, new Object[][]{vs});
                    }else if (myCls[0].equals(java.sql.Date.class)) {
                        java.text.SimpleDateFormat fmt=new java.text.SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date d=fmt.parse(vs[0].trim());
                        m.invoke(obj,new java.sql.Date(d.getTime()));
                    } else if (myCls[0].equals(java.util.Date.class)) {
                        java.text.SimpleDateFormat fmt=new java.text.SimpleDateFormat("yyyy-MM-dd");
                        m.invoke(obj,fmt.parse(vs[0].trim()));
                    } else if (myCls[0].equals(Float.TYPE)||myCls[0].equals(Float.class)) {
                        m.invoke(obj, new Float(vs[0].trim()));
                    } else if (myCls[0].equals(Long.TYPE)||myCls[0].equals(Long.class)) {
                        m.invoke(obj, new Long(vs[0].trim()));
                    } else if (myCls[0].equals(Double.TYPE)||myCls[0].equals(Double.class)) {
                        m.invoke(obj, new Double(vs[0].trim()));
                    }  else if (myCls[0].equals(Boolean.TYPE)||myCls[0].equals(Boolean.class)) {
                        m.invoke(obj, Boolean.valueOf(vs[0].trim()));
                    } else if (myCls[0].equals(java.sql.Time.class)) {
                        m.invoke(obj, java.sql.Time.valueOf(name.trim()));
                    } else{
                        m.invoke(obj,vs[0]);
                    }

                }
            }
        }

        return obj;
    }

    private static String ms(String n, String prefix) {
        return (prefix + n.substring(0, 1).toUpperCase() + n.substring(1));//根据参数名产生setter方法名
    }
}
