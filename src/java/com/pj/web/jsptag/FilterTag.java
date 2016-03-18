package com.pj.web.jsptag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author pengju 2011-2-26
 */
public class FilterTag extends TagSupport {

    private String var;
    private String type;
    private Object object;
    private String charset;

    @Override
    public int doStartTag() {
        try {
            if (object == null) {
                if (type != null && !type.isEmpty()) {
                    object = Class.forName(type).newInstance();
                }
            }
            if (object!=null) {
                HttpServletRequest req=(HttpServletRequest)pageContext.getRequest();
                if(charset!=null && !charset.isEmpty()){
                    req.setCharacterEncoding(charset);
                }else{
                    req.setCharacterEncoding("UTF-8");
                }
                BeanUtil.filter(req, object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(var!=null && !var.isEmpty()){
            pageContext.setAttribute(var, object, PageContext.REQUEST_SCOPE);
        }
        return SKIP_BODY;
    }

    /**
     * @return the var
     */
    public String getVar() {
        return var;
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * @param object the object to set
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * @return the charset
     */
    public String getCharset() {
        return charset;
    }

    /**
     * @param charset the charset to set
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }
}
