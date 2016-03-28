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
                                            
                                            当前位置:&nbsp;<a id="nav_title" href="javascript:void(0)"></a>&nbsp;>>&nbsp;
                                            <a id="nav_cat" href="javascript:void(0)"></a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>

                                <form style="margin-top: 12px;" action="<s:url action="FindDI" namespace="/content" />" method="post" name="find_di" target="I2">
                                    请输入:
                                    <select name="type">
                                        <option value="file_name">文件名</option>
                                        <option value="os_version">系统版本</option>
                                        <option value="phone_brand">手机品牌</option>
                                        <option value="phone_model">手机型号</option>
                                        <option value="result">检测结果</option>
                                        <option value="sn">检测编号</option>
                                    </select>
                                    <input onfocus="this.select()" type="text" size="20" maxlength="100" name="text" value="${text}" />
                                    <input type="submit" value="搜索" />
                                </form>
                                <c:choose>
                                    <c:when test="${not empty dis}">
                                        <table width="100%" cellspacing="0" cellpadding="5" id="result">
                                            <tr>
                                                <td class="gray_border" >&nbsp;&nbsp;</td>
                                                <td class="gray_border">编号</td>
                                                <td class="gray_border">品牌</td>
                                                <td class="gray_border">型号</td>
                                                <td class="gray_border">系统类型</td>
                                                <td class="gray_border">系统版本</td>
                                                <td class="gray_border">检测结果(0:通过;其他:未通过)</td>
                                                <td class="gray_border">检测日期</td>
                                                <td class="gray_border">

                                                    操作
                                                    <a id="a_all" href="javascript:void(0)">全选</a>
                                                    <a id="a_toggle" href="javascript:void(0)">反选</a>
                                                    <a id="a_cancel" href="javascript:void(0)">取消</a>

                                                    <a id="a_del_all" href="javascript:void(0)">删除选中</a>

                                                </td>
                                            </tr>
                                            <c:forEach var="each" items="${dis}">
                                                <tr>
                                                    <td class="gray_border"><input class="select" type="checkbox" name="id" value="${each.detectionId}" /></td>
                                                    <td class="gray_border">${each.detectionSN}</td>
                                                    <td class="gray_border">${each.detectionPhoneBrand}</td>
                                                    <td class="gray_border">${each.detectionPhoneModel}</td>
                                                    <td class="gray_border">${each.detectionOS}</td>
                                                    <td class="gray_border">${each.detectionOSVersion}</td>
                                                    <td class="gray_border">${each.detectionResult}</td>
                                                    <td class="gray_border">
                                                        <fmt:formatDate pattern="yyyy-MM-dd" value="${each.detectionDate}"></fmt:formatDate>
                                                        </td>
                                                        <td class="gray_border">
                                                            <a class="delete_item" aid="${each.detectionId}" href="javascript:void(0)">删除</a>
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
                                                if (window.confirm("你确定要删除选中的记录吗?") === true) {


                                                    ajax({url: "<s:url action="DeleteDI" namespace="/content" />", method: "POST", data: "diIds=" + ids.join("&diIds=")}).getScript(function (json) {

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
                                                if (!window.confirm("确定要删除该记录吗？")) {
                                                    return;
                                                }
                                                pj("a.delete_item").each(function (k) {
                                                    if (this == cur) {
                                                        row = k + 1;
                                                        return false;
                                                    }
                                                });

                                                ajax({url: "<s:url action="DeleteDI" namespace="/content" />", method: "POST", data: "diIds=" + this.getAttribute("aid")}).getScript(function (json) {
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

    </body>

</html>