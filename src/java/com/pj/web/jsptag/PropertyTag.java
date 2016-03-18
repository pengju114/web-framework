package com.pj.web.jsptag;

import java.io.IOException;
import java.util.regex.Matcher;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author pengju 2011-2-26
 */
public class PropertyTag extends TagSupport implements Analyzer {

    private String field;
    private String var;

    @Override
    public int doStartTag() {
        Object val = null;
        if (field != null) {
            replaceEl();
            val=eval();
        }
        if (val == null) {
            val = "";
        }
        if (var == null) {
            try {
                pageContext.getOut().print(val);
            } catch (IOException ex) {
            }
        } else {
            pageContext.setAttribute(var, val, PageContext.REQUEST_SCOPE);
        }
        return SKIP_BODY;
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
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

    public void replaceEl() {
        try {
            this.field = TagService.replaceEl(pageContext, this.field);
        } catch (Exception ex) {
            TagService.showError(ex);
        }
    }

    public Object eval() {
        Matcher matcher = simplePattern.matcher(this.field);
        if (matcher.find()) {
            try {
                return TagService.getValue(pageContext, matcher.group(2), matcher.group(4));
            } catch (Exception e) {
                TagService.showError(e);
            }
        }
        return null;
    }
}
