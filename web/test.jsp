<%-- 
    Document   : test
    Created on : 2016-3-22, 2016-3-22 11:01:21
    Author     : luzhenwen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="${ pageContext.request.contextPath }/scripts/pj-2.4.mini.js"></script>
        <script type="text/javascript" src="${ pageContext.request.contextPath }/scripts/ajax.js"></script>
        <title>Test Page</title>
    </head>
    <body>
        <textarea id="input"></textarea>
        <input type="button" id="submit" value="提交" />
        <div id="output"></div>
        
        <script type="text/javascript">
            pj("#submit").click(function(){
                pj("#output").html("");
                ajax({url:"${contextPath}/client.service",data:ajax.parseToObject(pj("#input").value()),method:"POST"}).getText().appendTo("output");
            });
        </script>
    </body>
</html>
