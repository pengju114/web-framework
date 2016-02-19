<%-- 
    Document   : authority
    Created on : Mar 30, 2014, 10:34:49 PM
    Author     : lzw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>${cfg.appName}</title>
         <jsp:include page="base.jsp" flush="true" />
         <script type="text/javascript" src="${contextPath}/scripts/ajax.js"></script>
         <script type="text/javascript" src="${contextPath}/scripts/dialog.js"></script>
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
                                            <c:if test="${add}">
                                            <a style="float: right; margin: 0px 12px 0px 0px;" href="javascript:void(0)" id="addAuthority">+添加权限</a>
                                            </c:if>
                                            当前位置:&nbsp;<a id="nav_title" href="javascript:void(0)"></a>&nbsp;>>&nbsp;
                                            <a id="nav_cat" href="javascript:void(0)"></a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td valign="top">
                                
                                <form style="margin-top: 12px;" action="<s:url action="FindAuthority" namespace="admin" />" method="post" name="find_authority" target="I2">
                                    请输入权限名字:<input onfocus="this.select()" type="text" size="20" maxlength="100" name="authorityName" value="${authorityName}" />
                                    <input type="submit" value="搜索" />
                                </form>
                                <c:choose>
                                    <c:when test="${not empty authorities}">
                                        <table width="100%" cellspacing="0" cellpadding="5" id="result">
                                            <tr>
                                                <td class="gray_border" >&nbsp;&nbsp;</td>
                                                <td class="gray_border">权限名</td>
                                                <td class="gray_border">权限标示</td>
                                                <td class="gray_border">
                                                    <c:if test="${delete}">
                                                        操作
                                                        <a id="a_all" href="javascript:void(0)">全选</a>
                                                        <a id="a_toggle" href="javascript:void(0)">反选</a>
                                                        <a id="a_cancel" href="javascript:void(0)">取消</a>

                                                        <a id="a_del_all" href="javascript:void(0)">删除选中</a>
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <c:forEach var="each" items="${authorities}">
                                                <tr>
                                                    <td class="gray_border"><input class="select" type="checkbox" name="id" value="${each.authorityId}" /></td>
                                                    <td class="gray_border">${each.authorityName}</td>
                                                    <td class="gray_border">${each.authorityKey}</td>
                                                    
                                                    <td class="gray_border">
                                                        <c:if test="${delete}">
                                                            <a class="delete_admin" aid="${each.authorityId}" href="javascript:void(0)">删除</a>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                        <script type="text/javascript">
                                            <c:if test="${delete}">
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
                                                if (window.confirm("你确定要删除选中的管理员吗?") === true) {

                                                    var statusDlg = new Dialog("提示", "正在删除...", true);
                                                    statusDlg.show();

                                                    ajax({url: "<s:url action="DeleteAdmin" namespace="/admin" />", method: "POST", data: "adminIds=" + ids.join("&adminIds=")}).getText(function (msg) {

                                                        statusDlg.close();
                                                        var dlg = new Dialog(null, "", true);
                                                        if (msg === "OK") {
                                                            dlg.setContent("删除成功");
                                                            dlg.setAutoHideDelay(3000);
                                                            dlg.show();
                                                            var table = pj.id("result");
                                                            for (var j = rows.length - 1; j >= 0; j--) {
                                                                table.deleteRow(rows[j]);
                                                            }
                                                        } else {
                                                            dlg.setContent(msg);
                                                            dlg.show();
                                                        }
                                                    });
                                                }
                                            });

                                            pj("a.delete_admin").click(function () {
                                                var row = -1, cur = this;
                                                if(!window.confirm("确定要删除该管理员吗？")){return;}
                                                pj("a.delete_admin").each(function (k) {
                                                    if (this == cur) {
                                                        row = k + 1;
                                                        return false;
                                                    }
                                                });
                                                
                                                ajax({url: "<s:url action="DeleteAdmin" namespace="/admin" />", method: "POST", data: "adminIds=" + this.getAttribute("aid")}).getText(function (msg) {
                                                    var dlg = new Dialog(null, "", true);
                                                    if (msg == "OK") {
                                                        dlg.setContent("删除成功");
                                                        dlg.setAutoHideDelay(3000);
                                                        dlg.show();
                                                        pj.id("result").deleteRow(row);
                                                    } else {
                                                        dlg.setContent(msg);
                                                        dlg.show();
                                                    }
                                                });
                                            });
                                            pj("a.modify_admin").click(function () {
//                                                if (pj.trim(this.innerHTML) == "修改") {
//                                                    this.innerHTML = "保存修改";
//                                                    pj("input.authority_" + this.getAttribute("aid")).each(function () {
//                                                        this.disabled = false;
//                                                    });
//                                                } else {
//                                                    this.innerHTML = "修改权限";
//                                                    if(window.confirm("是否要修改该管理员权限"))
//                                                    var id = this.getAttribute("aid"), auy = [];
//                                                    var box = pj("input.authority_" + id).each(function () {
//                                                        auy.push("\"" + this.getAttribute("name") + "\":" + this.checked);
//                                                    });
//                                                    alert(auy);
//                                                    ajax({url: "admin.modify", method: "POST", data: "id=" + id + "&authority={" + auy.join(",") + "}"}).getText(function (msg) {
//                                                        var dlg = new Dialog(null, "", true);
//                                                        if (msg == "OK") {
//                                                            dlg.setContent("修改成功");
//                                                            dlg.setAutoHideDelay(3000);
//                                                            dlg.show();
//                                                            box.each(function () {
//                                                                this.disabled = true;
//                                                            });
//                                                        } else {
//                                                            dlg.setContent(msg);
//                                                            dlg.show();
//                                                        }
//                                                    });
//                                                }
                                            })
                                            </script>
                                        </c:if>
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
