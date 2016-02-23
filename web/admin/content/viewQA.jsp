<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>${cfg.appName}</title>
        <jsp:include page="../fn/base.jsp" flush="true" />
    </head>
    <body>
        <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <table width="100%" cellpadding="0" cellspacing="0" class="table">
                        <tr>
                            <td valign="top">
                                <table width="100%" class="top" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td>
                                            当前位置:&nbsp;<a id="nav_title" href="javascript:void(0)"></a>&nbsp;>>&nbsp;
                                            <a id="nav_cat" href="javascript:void(0)"></a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>

                                
                                <c:choose>
                                    <c:when test="${not empty article}">
                                        <table width="100%" cellspacing="0" cellpadding="5" id="result">
                                            <tr>
                                                <td class="gray_border" >
                                                    ${article.articleTitle}
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="gray_border" >
                                                    ${article.articleAbstract}
                                                </td>
                                            </tr>
                                            
                                            <tr>
                                                <td class="gray_border" >
                                                    ${article.articleKeywords}
                                                </td>
                                            </tr>
                                            
                                            <tr>
                                                <td class="gray_border" >
                                                    ${article.articleContent}
                                                </td>
                                            </tr>
                                            
                                        </table>
                                       
                                    </c:when>
                                    <c:otherwise>
                                        没有相关数据
                                    </c:otherwise>
                                </c:choose>

                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
     </body>

</html>