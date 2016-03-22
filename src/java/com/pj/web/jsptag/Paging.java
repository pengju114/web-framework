package com.pj.web.jsptag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import static javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE;
import static javax.servlet.jsp.tagext.Tag.EVAL_PAGE;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author pengju 2011-9-24 15:55
 */
public class Paging extends TagSupport implements ParamSupportTag{
    private final ArrayList<ParamTag> params = new ArrayList<ParamTag>(3);

    private int pageSize;           //每页显示的记录数
    private int totalResults;       //记录总数
    private int currentPage;        //当前页
    private String link;            //点击指定页要连接到的目标页的URL,相对于当前页
    private int buttonCount;           //指定要显示页码按钮的个数
    private boolean useSelectList;  //是否使用下拉列表
    private String parameterName;   //就是附加到URL后面的参数名,可通过request.getParameter(parameterName)取得当前页
    private String inputForm = "";    //如果设置了此项则在点击链接的时候会提交制定名字的表单而不是跳到指定页,参数比较多时推荐使用
    private String buttonAttibute=" ";     //给每个按钮（即链接）添加属性,如：id="current" 则 <a id="current" ...>...</a>

    public Paging() {
        setDefaults();
    }
    
    
    private void setDefaults(){
        pageSize = 10;
        totalResults = 0;
        currentPage = 1;
        link = "javascript:void(0)";
        buttonCount = 9;
        useSelectList = false;
        parameterName = "page";
        buttonAttibute = " ";
        inputForm = "";
    }
    

    private String createSelectList() {
        String s = "";
        if (getInputForm().isEmpty()) {
            s = "<select name=\"pageList\" id=\"pageList\" onchange=\"location.href='" + getURL() + "'+this.options[this.selectedIndex].value\">";
        } else {
            s = "<select name=\"pageList\" id=\"pageList\" onchange=\"pagingFormDispatcher(this.options[this.selectedIndex].value)\">";
        }
        int size = this.getPageCount();
        String sel = " ";
        for (int i = 1; i <= size; i++) {
            if (i == getCurrentPage()) {
                sel = " selected ";
            }
            s += "<option" + sel + "value=\"" + String.valueOf(i) + "\">第" + String.valueOf(i) + "页</option>";
            sel = " ";
        }
        s += "</select>";
        return s;
    }

    private String getLinkString(int page) {
        return getLinkString(page, String.valueOf(page));
    }

    private String getLinkString(int page, String text) {
        if (!inputForm.isEmpty()) {
            return "<a href=\"javascript:void(0);\""+getButtonAttibute()+"target=\"_self\" onclick=\"pagingFormDispatcher('" + String.valueOf(page) + "')\">" + text + "</a>";
        }
        return "<a target=\"_self\""+getButtonAttibute()+"href=\"" + getURL() + String.valueOf(page) + "\">" + text + "</a>";
    }


    /**
     * 获取总页数
     * @return 总页数
     */
    public int getPageCount() {
        int c = getTotalResults() / getPageSize();
        if (getTotalResults() % getPageSize() != 0) {
            c += 1;
        }
        return c;
    }

    

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(wrapWithTag("第 " + String.valueOf(getCurrentPage()) + "/" + String.valueOf(getPageCount()) + " 页 共" + getTotalResults() + "条记录", "td"));
        for (String s : getRawHTMLAsArray()) {
            b.append(wrapWithTag(s, "td"));
        }
        return "<table border=\"0\"><tr>" + b.toString() + "</tr></table><script type=\"text/javascript\">" + getFunction() + "</script>";
    }

    /**
     * 取生成的连接按钮,如上一页、1、2...下一页等的连接
     * @return HTML字符串
     */
    public String getRawHTML() {
        StringBuilder b = new StringBuilder();
        for (String s : getRawHTMLAsArray()) {
            b.append(s);
        }
        return b.toString();
    }

    /**
     * 取包含生成的连接按钮的字符串数组,如上一页、1、2...下一页等的连接
     * @return 包含生成的连接按钮的字符串数组
     */
    public String[] getRawHTMLAsArray() {
        int size = getPageCount();

        ArrayList<String> arr = new ArrayList<String>();
        if (getCurrentPage() > 1) {
            arr.add(getLinkString(getCurrentPage() - 1, "上一页"));
        }

        int page = 1;
        if (getCurrentPage() >= getButtonCount()) {
            page = (getCurrentPage() - getButtonCount() / 2);
            if (page <= 0) {
                page = 1;
            }
        }
        for (int i = 0; (i < getButtonCount()) && (page <= size); i++, page++) {
            arr.add(getLinkString(page));
        }

        if (getCurrentPage() < size) {
            arr.add(getLinkString(getCurrentPage() + 1, "下一页"));
        }

        if (isUseSelectList()) {
            arr.add(createSelectList());
        }
        return arr.toArray(new String[]{});
    }

    private String wrapWithTag(String str, String tag) {
        return "<" + tag + ">" + str + "</" + tag + ">";
    }


    /**
     * 以当前页为基准返回应跳过的记录个数,getSkipCount()就是当前页开始记录的序号[在总记录中的下标(从0开始)]
     * @return 应跳过的记录数
     */
    public int getSkipCount() {
        return (getCurrentPage() - 1) * getPageSize();
    }

    /**
     * 通过此方法可以取最终的URL，比如你设置的link为view.jsp并且参数名为page,此方法就会返回view.jsp?page=
     * @return 附带参数的URL
     */
    public String getURL() {
        String regexp = "&?" + getParameterName() + "=\\d+";
        if (Pattern.compile(regexp).matcher(getLink()).find()) {//如果已存在参数则替换掉它
            setLink(getLink().replaceAll(regexp, ""));
        }
        if (getLink().indexOf("?") < 0) {
            return getLink() + "?" + getParameterName() + "=";
        } else if (getLink().contains("?")) {
            if (getLink().endsWith("&") || getLink().endsWith("?")) {
                return getLink() + getParameterName() + "=";
            }
            return getLink() + "&" + getParameterName() + "=";
        } else {
            return getLink() + "?" + getParameterName() + "=";
        }

    }

    /**
     * 当使用inputForm时会生成一个函数，用来设置参数和提交表单,接收一个参数，要跳到的页码
     * @return 函数字符串
     */
    public String getFunction() {
        StringBuilder sb = new StringBuilder();
        if (!inputForm.isEmpty()) {
            sb.append("function pagingFormDispatcher(targetPage){");
            sb.append("var form=document.forms[\"").append(getInputForm()).append("\"];").append("if(form){");
            sb.append("var elems=form.elements;").append("if(elems[\"").append(getParameterName()).append("\"]){elems[\"").append(getParameterName()).append("\"].value=targetPage;");
            sb.append("}else{var hid=document.createElement(\"input\");hid.setAttribute(\"type\",\"hidden\");hid.setAttribute(\"name\",\"").append(getParameterName()).append("\");hid.value=targetPage;form.insertBefore(hid,form.firstChild);}");
            sb.append("form.submit();}}");
        }
        return sb.toString();
    }


    /**
     * @param buttonAttibute the buttonAttibute to set
     */
    public void setButtonAttibute(Map buttonAttibute) {
        if(buttonAttibute!=null){
            StringBuilder sb=new StringBuilder(' ');
            for(Entry e:(Set<Entry>)buttonAttibute.entrySet()){
                sb.append(e.getKey()).append('=').append('"').append(e.getValue()).append('"').append(' ');
            }
            this.buttonAttibute=sb.toString();
        }
    }
    
    public void setButtonAttibute(String buttonAttibute) {
        this.buttonAttibute=" "+buttonAttibute+" ";
    }

    /**
     * @return the params
     */
    public List getParams() {
        return params;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the totalResults
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     * @param totalResults the totalResults to set
     */
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * @return the currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the buttonCount
     */
    public int getButtonCount() {
        return buttonCount;
    }

    /**
     * @param buttonCount the buttonCount to set
     */
    public void setButtonCount(int buttonCount) {
        this.buttonCount = buttonCount;
    }

    /**
     * @return the useSelectList
     */
    public boolean isUseSelectList() {
        return useSelectList;
    }

    /**
     * @param useSelectList the useSelectList to set
     */
    public void setUseSelectList(boolean useSelectList) {
        this.useSelectList = useSelectList;
    }

    /**
     * @return the parameterName
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * @param parameterName the parameterName to set
     */
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    /**
     * @return the inputForm
     */
    public String getInputForm() {
        return inputForm;
    }

    /**
     * @param inputForm the inputForm to set
     */
    public void setInputForm(String inputForm) {
        this.inputForm = inputForm;
    }

    /**
     * @return the buttonAttibute
     */
    public String getButtonAttibute() {
        StringBuilder sb = new StringBuilder(buttonAttibute);
        for (ParamTag param : params) {
            sb.append(param.getName()).append('=').append('"').append(param.getValue()).append('"').append(' ');
        }
        return sb.toString();
    }
    
    
    /**
     * tag support
     * @return 
     */
    
    @Override
    public int doStartTag() {
        params.clear();
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() {
        try {
            pageContext.getOut().println(this);
        } catch (Exception e) {
            TagService.showError(e);
        }
        setDefaults();
        
        return EVAL_PAGE;
    }
}