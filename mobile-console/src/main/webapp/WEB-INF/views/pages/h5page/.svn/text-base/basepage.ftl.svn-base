<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
    <link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css"/>
    <link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
    <link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
    <link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/wap/css/alert.css">

    <script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
    <script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
    <script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/xiu-validate.js"></script>
    <script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/ajaxfileupload.js"></script>

    <style type="text/css">
        .todo-container {
            height: 3em;
            margin-top: 0.5em;
        }

        .todo-container a {
            border: solid 1px;
            width: 10em;
            height: 2em;
            display: inline-block;
            line-height: 2em;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="adminMain_main">
    <table class="adminMain_top">
        <tbody>
        <tr>
            <td class="td01"></td>
            <td class="td02">
                <h3 class="topTitle fb f14">组件模板添加</h3>
            </td>
            <td class="td03"></td>
        </tr>
        </tbody>
    </table>

    <div class="adminMain_wrap">
        <div class="adminUp_wrap">
            <!-- 导航 -->
            <dl class="adminPath clearfix">
                <dt>您现在的位置：</dt>
                <dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
                <dd><a href="${rc.getContextPath()}/subject/list">组件模板管理</a></dd>
                <dd class="last"><h3>组件化页面</h3>
                </dd>
            </dl>
        </div>
        <!--导航end-->
        <form id="addPageForm" name="addPageForm" enctype="multipart/form-data" method="post"
              action="${rc.getContextPath()}/h5page/modifypage">
            <input type="hidden" name="id" value="${wapH5.id}" id="pageId">
            <!--菜单内容-->
            <div class="adminContent clearfix">
                <table width="100%" class="table_bg05">
                    <tr>
                        <th colspan="3" class="thTitle" scope="row"><span class="cBlue f14 fb pl20"></span></th>
                    </tr>

                    <tr>
                        <th class="thList" scope="row"><span class="red">*</span>H5列表标题：</th>
                        <td class="tdBox">
                            <input name="title" id="title" style="width:300px;" value="${wapH5.title!''}"/>
                        </td>
                    </tr>

                    <tr>
                        <th class="thList" scope="row">列表图片：</th>
                        <td class="tdBox">
                        	<#if wapH5.photo??>
                        	<img src="${wapH5.photo}" width="300px">
                        	</#if>
                            <input type="file" name="listPic" id="topic_pic_f" value="${wapH5.photo!''}" /> 格式：750*330 的jpg类型图片
                        </td>
                    </tr>
                    <tr>
                        <th class="thList" scope="row"><span class="red">*</span>URL链接：</th>
                        <td class="tdBox">
                        	<#if wapH5.h5Url?? && wapH5.id != 0>
								${prexUrl}<input id="h5Url" name="h5Url" value="${wapH5.h5Url}" style="width:300px;" readOnly/>.html
							<#else>
								${prexUrl}
	                            <input id="h5Url" name="h5Url" value="${middleUrl!''}" style="width:300px;"/>
	                            .html
							</#if>
                        </td>
                    </tr>
                    
                   <tr>
                   		<td class="thList" scope="row">背景色:</td>
                   		<td class="tdBox">
                   			<input id="bgColor" name="bgColor" value="${wapH5.bgColor!''}" style="width:100px;" />
                   		</td>
                   </tr>

                    <tr>
                        <th class="thList" scope="row">显示状态：</th>
                        <td class="tdBox">
                            <input type="radio" name="status" value="2"
                            <#if wapH5.status = 0 || wapH5.status=2>checked="checked"</#if>
                            />不显示到H5列表
                            <input type="radio" name="status" value="1"
                            <#if wapH5.status=1>checked="checked"</#if>
                            />显示到H5列表
                        </td>
                    </tr>
	              <tr>
						<th class="thList" scope="row">标签：</th>
						<td class="tdBox">
							<#include "/common/label.ftl">  
						</td>
					</tr>
					 <tr>
	                        <th class="thList" scope="row">功能：</th>
	                        <td class="tdBox">
	                            <input type="checkbox" name="shareCheck"
	                            <#if wapH5.isShare=1>checked</#if> />右下角增加分享按钮
	                        </td>
		             </tr>
					 <tr>
	                        <th class="thList" scope="row">分享标题：</th>
	                        <td class="tdBox">
	                           <input name="shareTitle" style="width:200px;" value="${wapH5.shareTitle!''}"/>
	                        </td>
		             </tr>
					 <tr>
	                        <th class="thList" scope="row">分享内容：</th>
	                        <td class="tdBox">
	                           <input name="shareContent" style="width:200px;" value="${wapH5.shareContent!''}"/>
	                        </td>
	             </tr>					
                    <tr>
                        <th class="thList" scope="row">分享图片：</th>
                        <td class="tdBox">
                        	<#if wapH5.sharePhoto??>
                        	<img src="${wapH5.sharePhoto}" width="300px">
                        	</#if>
                            <input type="file" name="sharePic" id="topic_pic_f" value="${wapH5.sharePhoto!''}" />
                        </td>
                    </tr>
                </table>
				<div class="todo-container">
                    <span  class="userOperBtn" id="userOperBtn" >确认提交</span>
                </div>
            </div>
        </form>
    </div>
</div>
<input type="hidden" value="${msg!''}" id="errorMsg"/>

<script type="text/javascript">
    $(function () {
        var errorMsg = $('#errorMsg').val();
        if(errorMsg != ''){
            alert(errorMsg);
        }
        var popup = function (popupName) {
            var _windowHeight = $(window).height(),//获取当前窗口高度
                    _windowWidth = $(window).width(),//获取当前窗口宽度
                    _popupHeight = popupName.height(),//获取弹出层高度
                    _popupWeight = popupName.width();//获取弹出层宽度
            var _posiTop = (_windowHeight - _popupHeight) / 2;
            var _posiLeft = (_windowWidth - _popupWeight) / 2;
            popupName.css({"left": _posiLeft + "px", "top": _posiTop + "px", "display": "block"});//设置position
        };

	    $("#userOperBtn").on("click",function(){
	        var title = $("#title").val();
	        if(title==""){
	            alert("必须输入标题");
	            return;
	        }
	        var h5Url = $("#h5Url").val();
	        if(h5Url=="" || h5Url == "${middleUrl!''}"){
	            alert("必须绑定一个链接");
	            return;
	        }
	        var r=confirm("确认提交吗");
	        if (r==true)
	        {
	            $("#addPageForm").get(0).submit();
	        }
	    });
    });
</script>
</body>
</html>