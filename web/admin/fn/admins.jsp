<%-- 
    Document   : role
    Created on : Mar 30, 2014, 10:35:16 PM
    Author     : lzw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>${cfg.appName}</title>
         <jsp:include page="base.jsp" flush="true" />
    </head>
    <body>
        <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <table width="100%" height="269" cellpadding="0" cellspacing="0" class="table">
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
                                <form action="<s:url action="FindAdmin" />" method="post" name="find_admin" target="I2">
                                    请输入管理员名字:<input onfocus="this.select()" type="text" size="20" maxlength="100" name="adminName" value="${userName}" />
                                    <input type="submit" value="搜索" />
                                </form>
                                <c:choose>
                                    <c:when test="${not empty admins}">
                                        <table width="100%" cellspacing="5" id="result" border="0">
                                            <tr>
                                                <td class="gray_border" >&nbsp;&nbsp;</td>
                                                <td class="gray_border">用户名</td>
                                                <td class="gray_border">注册日期</td>
                                                <td class="gray_border">权限</td>
                                                <td class="gray_border">
                                                    操作
                                                    <a id="a_all" href="javascript:void(0)">全选</a>
                                                    <a id="a_toggle" href="javascript:void(0)">反选</a>
                                                    <a id="a_cancel" href="javascript:void(0)">取消</a>
                                                    <c:if test="${delete}">
                                                        <a id="a_del_all" href="javascript:void(0)">删除选中</a>
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <c:forEach var="each" items="${admins}">
                                                <tr>
                                                    <td class="gray_border"><input class="select" type="checkbox" name="id" value="${each.id}" /></td>
                                                    <td class="gray_border">${each.userName}</td>
                                                    <td class="gray_border">${each.registerDate}</td>
                                                    <td class="gray_border">
                                                           <% Admin cur = (Admin) pageContext.findAttribute("each");%>
                                                           <c:forEach varStatus="s" var="auy" items="${each.keys}">
                                                               <input class="authority_${each.id}" type="checkbox" name="${auy}" value="true" disabled="true" <% String n = (String) pageContext.findAttribute("auy");
                                                            if (cur.hasAuthority(n)) {
                                                                out.write("checked");
                                                            }%> />${auy}
                                                           <c:if test="${s.index%4==0 and s.index!=0}"><br /></c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <td class="gray_border">
                                                        <c:if test="${delete}">
                                                            <a class="delete_admin" aid="${each.id}" href="javascript:void(0)">删除</a>
                                                        </c:if>
                                                        <c:if test="${modify}">
                                                            <a class="modify_admin" aid="${each.id}" href="javascript:void(0)">修改权限</a>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                        <script type="text/javascript">
                                            pj("#a_all").click(function(){
                                                pj("input.select").each(function(){
                                                    this.checked=true;
                                                });
                                            });
                                            pj("#a_toggle").click(function(){
                                                pj("input.select").each(function(){
                                                    this.checked=!this.checked;
                                                });
                                            });
                                            pj("#a_cancel").click(function(){
                                                pj("input.select").each(function(){
                                                    this.checked=false;
                                                });
                                            });
                                            pj("#a_del_all").click(function(){
                                                if(window.confirm("你确定要删除选中的管理员吗?")===true){
                                                    var ids=[],rows=[];
                                                    pj("input.select").each(function(i){
                                                        if(this.checked){
                                                            ids.push(this.value);
                                                            rows.push(i+1);
                                                        }
                                                    });
                                                    rows.sort();
                                                    if(ids.length<=0)return;
                                                    ajax({url:"admin.delete",method:"POST",data:"id="+ids.join("&id=")}).getText(function(msg){
                                                        var dlg=new Dialog(null,"",true);
                                                        if(msg=="OK"){
                                                            dlg.setContent("删除成功");
                                                            dlg.setAutoHideDelay(3000);
                                                            dlg.show();
                                                            var table=pj.id("result");
                                                            for(var j=rows.length-1;j>=0;j--){
                                                                table.deleteRow(rows[j]);
                                                            }
                                                        }else{
                                                            dlg.setContent(msg);
                                                            dlg.show();
                                                        }
                                                    });
                                                }
                                            });

                                            pj("a.delete_admin").click(function(){
                                                var row=-1,cur=this;
                                                pj("a.delete_admin").each(function(k){
                                                    if(this==cur){row=k+1;return false;}
                                                });
                                                ajax({url:"admin.delete",method:"POST",data:"id="+this.getAttribute("aid")}).getText(function(msg){
                                                    var dlg=new Dialog(null,"",true);
                                                    if(msg=="OK"){
                                                        dlg.setContent("删除成功");
                                                        dlg.setAutoHideDelay(3000);
                                                        dlg.show();
                                                        pj.id("result").deleteRow(row);
                                                    }else{
                                                        dlg.setContent(msg);
                                                        dlg.show();
                                                    }
                                                });
                                            });
                                            pj("a.modify_admin").click(function(){
                                                if(pj.trim(this.innerHTML)=="修改权限"){
                                                    this.innerHTML="保存修改";
                                                    pj("input.authority_"+this.getAttribute("aid")).each(function(){
                                                        this.disabled=false;
                                                    });
                                                }else{
                                                    this.innerHTML="修改权限";
                                                    var id=this.getAttribute("aid"),auy=[];
                                                    var box=pj("input.authority_"+id).each(function(){
                                                        auy.push("\""+this.getAttribute("name")+"\":"+this.checked);
                                                    });
                                                    ajax({url:"admin.modify",method:"POST",data:"id="+id+"&authority={"+auy.join(",")+"}"}).getText(function(msg){
                                                        var dlg=new Dialog(null,"",true);
                                                        if(msg=="OK"){
                                                            dlg.setContent("修改成功");
                                                            dlg.setAutoHideDelay(3000);
                                                            dlg.show();
                                                            box.each(function(){this.disabled=true;});
                                                        }else{
                                                            dlg.setContent(msg);
                                                            dlg.show();
                                                        }
                                                    });
                                                }
                                            })
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