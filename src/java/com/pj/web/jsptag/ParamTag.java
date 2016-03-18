
package com.pj.web.jsptag;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author pengju 2011-2-26
 */
public class ParamTag extends TagSupport{
    private String name;
    private Object value;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public int doStartTag(){
        Tag parent=getParent();

        if(parent instanceof InvokeTag){
            InvokeTag itg=(InvokeTag)parent;
            itg.getParams().add(value);
        }
        
        return SKIP_BODY;
    }
    @Override
    public int doEndTag(){
        return EVAL_PAGE;
    }

}
