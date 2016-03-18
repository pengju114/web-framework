package com.pj.web.jsptag;

import java.lang.reflect.Method;

/**
 *
 * @author pengju 2011-10-23
 */
public class Invoker {

    public static Object invoke(Class c, Object obj, String method, Object[] args) {
        if (c == null) {
            if (obj == null) {
                return null;
            }
            c = obj.getClass();
        }
        if (c != null) {
            if (args == null) {
                args = new Object[0];
            }

            Method ivk = null;

            try {
                Class[] pTypes = null;
                if (args.length > 0) {
                    pTypes = new Class[args.length];
                    for (int idx = 0; idx < args.length; idx++) {
                        pTypes[idx] = args[idx].getClass();
                    }
                }
                ivk = c.getMethod(method, pTypes);
            } catch (Exception e) {
                TagService.showError(e);

                Method[] mtds = c.getMethods();

                int prev = 0, last = mtds.length - 1;
                if (mtds.length % 2 != 0) {
                    Method m = mtds[mtds.length / 2];
                    if (m.getName().equals(method) && m.getGenericParameterTypes().length == args.length) {
                        ivk = m;
                    }
                }
                if (ivk == null) {

                    while (prev < last) {
                        Method m = mtds[prev];
                        if (m.getName().equals(method) && m.getGenericParameterTypes().length == args.length) {
                            ivk = m;
                            break;
                        }
                        m = mtds[last];
                        if (m.getName().equals(method) && m.getGenericParameterTypes().length == args.length) {
                            ivk = m;
                            break;
                        }
                        prev++;
                        last--;
                    }
                }
            }

            try {
                if (ivk != null) {
                    return ivk.invoke(obj, args);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
