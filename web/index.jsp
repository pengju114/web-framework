<%-- 
    Document   : index
    Created on : 2012-7-14, 0:13:20
    Author     : PENGJU
--%>


<%@page import="com.pj.admin.beans.AdminUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
//        TestService service=new TestService();
////        Session sedb=SessionFactory.getSession("SEDB");
////        ResultList<ResultRow> sedbRows=sedb.executeQuery("select * from Users");
//        ResultList<ResultRow> sedbRows=service.getSEDBUsers();
//        for(ResultRow row:sedbRows){
//            out.println(row.toJSONObject());
//        }
////        Session wcs=SessionFactory.getSession("wcs");
////        ResultList<ResultRow> wcsRows=wcs.executeQuery("select * from tb_customer");
//        ResultList<ResultRow> wcsRows=service.getWCSCustomers();
//        for(ResultRow row:wcsRows){
//            out.println(row.toJSONObject());
//        }
//        sedb.close();
//        wcs.close();
            
            AdminUser admin = (AdminUser)session.getAttribute("admin");
            if(admin != null){
                response.getWriter().write(admin.getAdminName());
            }
        %>
        <a href="${ pageContext.request.contextPath }/add.jsp">add</a>
    </body>
</html>
