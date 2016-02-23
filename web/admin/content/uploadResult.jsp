<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>上传结果</title>
    </head>
    <body>
        <script type="text/javascript">
            if(window.top.uploadHandler){
                window.top.uploadHandler({"location":"${url}","status":${status}});
            }
        </script>
    </body>
</html>
