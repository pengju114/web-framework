package com.pj.web.jsptag;

import java.util.ArrayList;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author pengju 2011-2-27
 */
public class InvokeTag extends TagSupport implements Analyzer {

    private ArrayList<Object> params = new ArrayList<Object>(3);
    private String var;
    private String method;

    @Override
    public int doStartTag() {
        params.clear();
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() {
        if (method != null) {
            replaceEl();
            Object val = eval();

            if (var != null) {
                pageContext.setAttribute(var, val, PageContext.REQUEST_SCOPE);
            } else if (val != null) {
                try {
                    pageContext.getOut().print(val);
                } catch (Exception e) {
                    TagService.showError(e);
                }
            }
        }
        return EVAL_PAGE;
    }

    /**
     * @return the params
     */
    public ArrayList<Object> getParams() {
        return params;
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
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    public void replaceEl() {
        try {
            this.method = TagService.replaceEl(pageContext, this.method);
        } catch (Exception e) {
            TagService.showError(e);
        }
    }

    public Object eval() {
        return TagService.invoke(pageContext,method,params.toArray(new Object[0]));
    }
}
