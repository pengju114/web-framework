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
        <script type="text/javascript" src="${contextPath}/scripts/ajax.js"></script>
        <script type="text/javascript" src="${contextPath}/scripts/dialog.js"></script>
        <script type="text/javascript" src="${contextPath}/scripts/validator.js"></script>
        <link href="${contextPath}/css/dialog/pjdialog.css" rel="stylesheet" type="text/css" />
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

                                <form style="margin-top: 12px;" enctype="multipart/form-data" action="<s:url action="AddPkg" namespace="/content" />" method="post" name="add_pkg" target="I2">

                                    <table width="100%" cellspacing="0" cellpadding="5" id="result">
                                        <tr>
                                            <td class="gray_border require" >文件类型</td>
                                            <td class="gray_border">
                                                <select id="pkg-type" name="type">
                                                    <c:forEach var="t" items="${types}">
                                                        <option value="${t}">${t}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr id="des-row">
                                            <td class="gray_border" >文件描述</td>
                                            <td class="gray_border">
                                                <input alt="文件描述" title="请输入文件描述" name="description" type="text" />
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="gray_border require" >选择文件</td>
                                            <td class="gray_border">
                                                <input validate="true" alt="文件" title="请选择文件" type="file" name="pkgFile" />
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="gray_border" align="right" >
                                                <input type="submit" value="确定" />
                                            </td>
                                            <td class="gray_border">
                                                <input type="reset" value="重置" />
                                            </td>
                                        </tr>
                                    </table>

                                </form>

                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

        <script type="text/javascript">
            
            <c:if test="${not empty error}">
                var  d = new Dialog("提示","${error}",true);
                d.show();
            </c:if>
            FormValidator.validate("add_pkg");
            pj("#pkg-type").change(function(){
                var val = pj(this).value();
                document.forms["add_pkg"]["description"].value="";
                if(val == "JAR"){
                    pj("#des-row").css({display:"none"});
                }else{
                    pj("#des-row").css({display:""});
                }
            });
        </script>
        
    </body>

</html>