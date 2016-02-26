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
        <script type="text/javascript" src="${contextPath}/scripts/tinymce/tinymce.min.js"></script>
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

                                <form style="margin-top: 12px;" action="<s:url action="AddQA" namespace="/content" />" method="post" name="add_qa" target="I2">

                                    <table width="100%" cellspacing="0" cellpadding="5" id="result">
                                        <tr>
                                            <td class="gray_border require" >标题</td>
                                            <td class="gray_border">
                                                <input validate="true" title="请输入标题" name="article.articleTitle" type="text" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="gray_border" >摘要</td>
                                            <td class="gray_border">
                                                <input name="article.articleAbstract" type="text" />
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="gray_border" >关键字</td>
                                            <td class="gray_border">
                                                <input name="article.articleKeywords" type="text" />
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="gray_border require" valign="top" >内容</td>
                                            <td class="gray_border">
                                                <input id="content" validate="true" title="请输入内容" name="article.articleContent" type="text" />
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
            tinymce.init({
                selector: "#content",
                height: 200,
                theme: 'modern',
                plugins: [
                    'advlist autolink lists link image charmap print preview hr anchor pagebreak',
                    'searchreplace wordcount visualblocks visualchars code fullscreen',
                    'insertdatetime media nonbreaking save table contextmenu directionality',
                    'emoticons template paste textcolor colorpicker textpattern imagetools'
                ],
                toolbar1: 'insertfile undo redo | styleselect | bold italic forecolor backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image preview',
                image_advtab: true,
                automatic_uploads: true,
                file_browser_callback_types: "image",
                file_picker_types: 'image',
                document_base_url: "/",
                convert_urls: false,
                images_upload_url: '<s:url action="UploadImage" namespace="/content" />',
                language: "zh_CN",
                file_browser_callback: function (field_name, url, type, win) {
                    win.document.getElementById(field_name).value = "正在上传图片...";
                    UploadImage(function(jsonObj){
                        if(jsonObj && jsonObj.location){
                            win.document.getElementById(field_name).value = jsonObj.location;
                        }else{
                            win.document.getElementById(field_name).value = "上传失败";
                        }
                    });
                }
            });
            
            function UploadImage(callback){
                if(pj.isFunction(callback)){
                    var trigger = false;
                    pj("#fileButton").change(function(){
                        if(!trigger){ // 防止onchange调用多次
                            trigger = true;
                            var v = pj(this).value();
                            if(v && v.length > 1){
                                window.top.uploadHandler = callback;
                                document.forms["upload_image"].submit();
                            }
                        }
                        
                    })[0].click();
                }
            }
            
            FormValidator.validate("add_qa");
        </script>
        
        <iframe src="#" name="insideFrame"  style="margin: 0px; padding: 0px; visibility: hidden; position: absolute; "></iframe>
        <form style="margin: 0px; padding: 0px; visibility: hidden; position: absolute; " enctype="multipart/form-data" method="POST" name="upload_image" action="<s:url action="UploadImage" namespace="/content" />" target="insideFrame">
            <input type="file" id="fileButton" name="image" value="" />
            <input type="submit" value="提交" id="fileSubmit" /> 
        </form>
    </body>

</html>