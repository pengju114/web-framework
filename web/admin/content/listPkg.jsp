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
                                            <a style="float: right; margin: 0px 12px 0 0;" href="<s:url action="AddPkg" namespace="/content" />">+上传文件</a>
                                            当前位置:&nbsp;<a id="nav_title" href="javascript:void(0)"></a>&nbsp;>>&nbsp;
                                            <a id="nav_cat" href="javascript:void(0)"></a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>

                                <form style="margin-top: 12px;" action="<s:url action="FindPkg" namespace="/content" />" method="post" name="find_pkg" target="I2">
                                    文件类型:
                                    <select name="type">
                                        <c:forEach items="${types}" var="type">
                                            <option value="${type}">${type}</option>
                                        </c:forEach>
                                    </select>
                                    请输入文件名:
                                    <input onfocus="this.select()" type="text" size="20" maxlength="100" name="title" value="${title}" />
                                    <input type="submit" value="搜索" />
                                </form>
                                <c:choose>
                                    <c:when test="${not empty pkgs}">
                                        <table width="100%" cellspacing="0" cellpadding="5" id="result">
                                            <tr>
                                                <td class="gray_border" >&nbsp;&nbsp;</td>
                                                <td class="gray_border">标题</td>
                                                <td class="gray_border">摘要</td>
                                                <td class="gray_border">关键字</td>
                                                <td class="gray_border">最后修改</td>
                                                <td class="gray_border">

                                                    操作
                                                    <a id="a_all" href="javascript:void(0)">全选</a>
                                                    <a id="a_toggle" href="javascript:void(0)">反选</a>
                                                    <a id="a_cancel" href="javascript:void(0)">取消</a>

                                                    <a id="a_del_all" href="javascript:void(0)">删除选中</a>

                                                </td>
                                            </tr>
                                            <c:forEach var="each" items="${pkgs}">
                                                <tr>
                                                    <td class="gray_border"><input class="select" type="checkbox" name="id" value="${each.articleId}" /></td>
                                                    <td class="gray_border"><a href="<s:url action="ViewQA" namespace="/content" />?id=${each.articleId}">${each.articleTitle}</a></td>
                                                    <td class="gray_border">${each.articleAbstract}</td>
                                                    <td class="gray_border">${each.articleKeywords}</td>
                                                    <td class="gray_border">
                                                        <fmt:formatDate pattern="yyyy-MM-dd" value="${each.articleLastModifyDate}"></fmt:formatDate>
                                                        </td>
                                                        <td class="gray_border">
                                                            <a class="delete_item" aid="${each.articleId}" href="javascript:void(0)">删除</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                        <script type="text/javascript">
                                            pj("#a_all").click(function () {
                                                pj("input.select").each(function () {
                                                    this.checked = true;
                                                });
                                            });
                                            pj("#a_toggle").click(function () {
                                                pj("input.select").each(function () {
                                                    this.checked = !this.checked;
                                                });
                                            });
                                            pj("#a_cancel").click(function () {
                                                pj("input.select").each(function () {
                                                    this.checked = false;
                                                });
                                            });
                                            pj("#a_del_all").click(function () {

                                                var ids = [], rows = [];
                                                pj("input.select").each(function (i) {
                                                    if (this.checked) {
                                                        ids.push(this.value);
                                                        rows.push(i + 1);
                                                    }
                                                });
                                                if (ids.length <= 0)
                                                    return;
                                                if (window.confirm("你确定要删除选中的文章吗?") === true) {


                                                    ajax({url: "<s:url action="DeleteQA" namespace="/content" />", method: "POST", data: "qaIds=" + ids.join("&qaIds=")}).getScript(function (json) {

                                                        var dlg = new Dialog("提示", json.message, true);
                                                        if (json.status == 0) {
                                                            dlg.autoHide(2000);
                                                            var table = pj.id("result");
                                                            for (var j = rows.length - 1; j >= 0; j--) {
                                                                table.deleteRow(rows[j]);
                                                            }
                                                        } else {
                                                            dlg.show();
                                                        }
                                                        
                                                        dlg.show();
                                                    });
                                                }
                                            });

                                            pj("a.delete_item").click(function () {
                                                var row = -1, cur = this;
                                                if (!window.confirm("确定要删除该文章吗？")) {
                                                    return;
                                                }
                                                pj("a.delete_item").each(function (k) {
                                                    if (this == cur) {
                                                        row = k + 1;
                                                        return false;
                                                    }
                                                });

                                                ajax({url: "<s:url action="DeleteQA" namespace="/content" />", method: "POST", data: "qaIds=" + this.getAttribute("aid")}).getScript(function (json) {
                                                    var dlg = new Dialog("提示", json.message, true);
                                                    if (json.status === 0) {
                                                        dlg.autoHide(2000);
                                                        pj.id("result").deleteRow(row);
                                                    } 
                                                    dlg.show();
                                                });
                                            });

                                        </script>
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
        <c:if test="${addQA}">
            <script type="text/javascript">
                <c:choose>
                    <c:when test="${success}">
                        var tip = "添加成功";
                    </c:when>
                    <c:otherwise>
                        var tip = "添加失败";
                        <c:if test="${ not empty tip}">tip += ",${tip}"</c:if>;
                    </c:otherwise>
                </c:choose>
                var d = new Dialog("信息", tip, true);
                d.autoHide(2000);
                d.show();
            </script>
        </c:if>

    </body>

</html>