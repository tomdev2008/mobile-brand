<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript">
		var shareCont={'title':'${wapH5.title!''}','desc':'${wapH5.shareContent!''}','imgUr':'${wapH5.photo!''}'};
   	</script>
    <script type='text/javascript' src='http://m.xiu.com/static/js/thirdparty/zepto.min.js'></script>
    <script type="text/javascript" src="http://m.xiu.com/H5/H5Demo/swiper.min.js"></script>
	<script type='text/javascript' src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>
	<script type='text/javascript' src='http://m.xiu.com/H5/201512/static/echo.js'></script>
	<script type='text/javascript' src='http://m.xiu.com/H5/201512/static/common.js'></script>
	<script type='text/javascript' src='http://m.xiu.com/H5/H5Demo/xiuUser.js'></script>
    <link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
    <link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css"/>
    <link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
    <link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
    <link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/wap/css/alert.css">
    <link rel="stylesheet" href="http://m.xiu.com/H5/H5Demo/swiper.min.css">
    <link type='text/css' rel='stylesheet' href='http://m.xiu.com/H5/H5Demo/css/H5demo.css'>
    
    <#if (allTemplateList?? && allTemplateList?size > 0)>
        <#list allTemplateList as item>
        	<#if item.cssUrl??>
            <link type="text/css" rel="stylesheet" href="${item.cssUrl!''}" />
            </#if>
        </#list>
    </#if>
    <script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
    <script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
    <script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/xiu-validate.js"></script>
    <script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/ajaxfileupload.js"></script>
    <#if (allTemplateList?? && allTemplateList?size > 0)>
        <#list allTemplateList as item>
        	<#if item.jsUrl??>
        	<script type="text/javascript" src="${item.jsUrl!''}"></script>
            </#if>
        </#list>
    </#if>

    <style type="text/css">
        #applyPageTemple , .addcom {
            width: 700px;
            height: 3em;
            display: block;
            line-height: 3em;
            text-align: center;
            border: solid 1px #369;
            margin-top: 6px;
        }
        .havefilldata{
            background-color: #f1f1ef;
        }

        .moduleitem {
            width: 700px;
            border-bottom: solid 1px #000;
            position: relative;
            min-height: 200px;
        }

        .moduleitem img {
            width: 100%;
            height: 100%;
        }
        
        .moduleitem  .htmlcontent{
        	width:435px;
        }
		
        .actvie-container {
            margin-left: 480px;
            height: 175px;
            position: absolute;
            bottom: 0;
        }

        .actvie-container a {
            width: 100px;
            display: inline-block;
            text-align: center;
            height: 2em;
            line-height: 2em;
            border: solid 1px #000;
            cursor: pointer;
            float: left;
           margin: 0 0 5px 4px;
        }
        
        .actvie-container a input {
            vertical-align: text-top;
        }

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

        #pageTemplatePop {
            width: 600px;
        }

        .doOtherName {
            height: 30px;
            vertical-align: top;
            margin-left: 5px;
            line-height: 30px;
            border: solid 1px #000;
            display: inline-block;
            width: 4em;
            text-align: center;
            cursor: pointer;
        }

        .saveConTitle {
            margin-top: 5px;
        }

        .savaOtherContent {
            margin-top: 5px;
        }

        .savaOtherTitle {
            height: 30px;
            display: inline-block;
        }

        .otherName {
            height: 30px;
            width: 300px;
        }

        #pageTemplatePop .choose ul li {
            width: 100%;
            margin: 2px 0;
            border:none;
        }

        #pageTemplatePop .choose ul li  .simpleName{
            width: 60%;
            margin: 2px 0;
            border: solid 1px #000;
            display: inline-block;
            cursor: pointer;
        }
        #pageTemplatePop .choose ul li  .toModifyNameBtn{
            width: 6em;
            border: solid 1px #000;
            display: inline-block;
            text-align: center;
            cursor: pointer;
        }
        #pageTemplatePop .choose ul li  .modifyNameBtn{
            width: 4em;
            border: solid 1px #000;
            display: inline-block;
            text-align: center;
            cursor: pointer;
        }

        #pageTemplatePop .choose ul li  .nameText{
            width: 60%;
            margin: 2px 0;
            border: solid 1px #000;
            display: inline-block;
            cursor: pointer;
            height: 30px;
        }


        #choosePageTemplate {
            width: 400px;
        }

        #choosePageTemplate a {
            width: 300px;
            margin: 1px 0;
            cursor: pointer;
            list-style: none;
            height: 30px;
            border: 1px solid #a1a1a1;
            line-height: 30px;
            float: left;
            font-size: 12px;
        }
        
        .arrows img {
		    width: 30px;
		}
    </style>
</head>
<body>
<div class="econtainer" id="modulePop">
    <div class="choose">
        <div class="chooseTitle">
            H5组件选择
        </div>
        <ul class="mous">
            <#if (allTemplateList?? && allTemplateList?size > 0)>
                <#list allTemplateList as item>
                    <li data-id="${item.id!''}" img-url="http://6.xiustatic.com${item.thumbnailUrl!''}" data-name="${item.name!''}">
                        ${item.name!''}
                    </li>
                </#list>
            </#if>
        </ul>
    </div>
    <div class="imgShow">
        <div class="chooseTitle">
            样式展示
        </div>
        <div class="showImg"><img id="templateThumbnail" src="" alt="组件模板缩略图"/></div>
    </div>
    <div class="closeAlert">关闭</div>
</div>

<div class="econtainer" id="choosePageTemplate">
    <div class="choose">
        <div>
            H5页面模板选择
        </div>
        <ul class="mous">
        </ul>
    </div>

    <div class="closeAlert">关闭</div>
</div>


<div class="econtainer" id="pageTemplatePop">
    <div class="choose">
        <div class="saveConTitle">
            另存为一个新的页面模板
        </div>
        <div class="savaOtherContent">
            <span class="savaOtherTitle">模板名称</span>
            <input type="text" class="otherName" name="otherName" id="otherName"/><span class="doOtherName"
                                                                                        id="savaAsOtherName">提交</span>
        </div>

        <div class="saveConTitle">
            覆盖原有页面模板
        </div>
        <ul class="mous">
            <#if (pageTemplateList?? && pageTemplateList?size > 0)>
                <#list pageTemplateList as item>
                    <li data-id="${item.id!''}" templateIds="${item.templateIds!''}" class="oldName" title="点击覆盖整个模板">
                        <span class="simpleName">${item.name!''}</span>  <input type="text" name="nameText" class="nameText"  value="${item.name!''}" style="display:none;" /> <span class="toModifyNameBtn">修改名称</span> <span class="modifyNameBtn" style="display:none;">保存</span>
                    </li>
                </#list>
            </#if>
        </ul>
    </div>
    <div class="closeAlert">关闭</div>
</div>

<iframe id="ajaxifr" name="ajaxifr" style="display:none;"></iframe>
<div class="econtainer" id="moduleEditPop">
<input id="editMoudleId" type="hidden" />
<form method="post" enctype="multipart/form-data" action="/h5page/saveModuleEdit" target="ajaxifr">
    <div>
        <div class="chooseTitle">
                                  组件在线编辑
        </div>
        <div class="mous" style="width:100%;">
            
        </div>
        <div>
        <input type="submit" value="保存" class="middlesave"/>
    </div>
    </div>
    
    <div class="closeAlert">关闭</div>
</form>  
</div>

<script>
    var pageTemplateList =[];
    <#if (pageTemplateList?? && pageTemplateList?size > 0)>
    <#list pageTemplateList as item>
    pageTemplateList.push({"id":"${item.id!''}","templateIds":"${item.templateIds!''}","name":"${item.name!''}"});
    </#list>
    </#if>
    window.pageTemplateList = pageTemplateList;
</script>


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
            <input type="hidden" name="id" value="${wapH5.id}" id="pageId">
            <!--菜单内容-->
            <div class="adminContent clearfix">
            	<table width="100%" class="table_bg05">
                	<th class="thList" scope="row">H5列表标题：</th>
                    <td class="tdBox">
                        ${wapH5.title!''}
                    </td>
                </table>
                <#if wapH5.id != 0  && ownerModuleList?size ==0>
                    <a class="applyPageTemple" id="applyPageTemple">应用模板的H5组件</a>
                </#if>

                <div id="ownermodules">
                    <#if (ownerModuleList?? && ownerModuleList?size > 0)>
                        <#list ownerModuleList as item>
                            <div data-id="${item.id!''}" template-id="${item.templateId!''}"
                                 rowIndex="${item.rowIndex!''}" class="moduleitem">
                                 <div class="htmlcontent"><img src="http://6.xiustatic.com${item.thumbnailUrl!''}" alt="组件${item.id!''}缩略图"/></div>
                                <div class="actvie-container">
									<#if (item.rowIndex > 3)>                                
	                                	<a class="lazyloadCheck" href="javascript:;">
	                                		<span title="发布后的页面中该组件是否异步加载"><input type="checkbox" name="isLazyLoad" moduleid="${item.id!''}" class="lazyload" <#if item.isLazyLoad?? && item.isLazyLoad == '1'>checked</#if>>是否懒加载</span>
	                                	</a>
	                                </#if>
                                    <a moduleid="${item.id!''}" rowindex="${item.rowIndex!''}" pageid="${wapH5.id}"
                                       class="dodelete">删除</a>
                                    <a moduleid="${item.id!''}" rowindex="${item.rowIndex!''}"
                                       pageid="${item.pageId!''}" class="up">上移</a>
                                    <a moduleid="${item.id!''}" rowindex="${item.rowIndex!''}"
                                       pageid="${item.pageId!''}" class="down">下移</a>
                                    <a pageid="${wapH5.id}" templateid="${item.templateId!''}"
                                       rowindex="${item.rowIndex!''}" backward=true class="doinsert">插入组件</a>
                                    <a href="${rc.getContextPath()}/h5page/exportcomponents?moduleId=${item.id!''}">下载模板</a>
                                    <a moduleid="${item.id?c}" class="doedit">编辑内容</a>

                                    <input type="file" name="dataUpload_${item.id}" id="dataUpload_${item.id}"><br/>
                                    <input type="button" onclick="uploadModuleData('${item.id}')" value="上传" style="width:68px;cursor:pointer;">
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>

                <#if wapH5.id != 0>
                    <a class="addcom" id="addcom">新增组件</a>
	                <div class="todo-container">
	                    <span>上传:</span> <a href="${rc.getContextPath()}/h5page/exportallcomponents?pageId=${wapH5.id}"
	                                        style="margin-left: 20px;">下载模板</a>
	                </div>
	                <div class="todo-container">
	                    选择文件: <input type="file" name="allDataUpload" id="allDataUpload" title="导入文件">
	                    <input type="button" id="allButtonUpload" pageId=${wapH5.id} value="上传" style="width:68px;cursor:pointer;">
	                </div>
	                <div class="todo-container">
	                    <a href="${rc.getContextPath()}/h5page/previewpage?pageId=${wapH5.id}" target="_blank">预览</a>
	                    <a href="javascript:void(0);" onclick="publishPage('${wapH5.id}');">发布</a>
	                    <a id="savePageTemplatebtn">另存为模板</a>
	                </div>
                </#if>
            </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        var getModuleCount = function(pageId){
            $.ajax({
                type: 'GET',
                dataType: "json",
                url: "${rc.getContextPath()}/h5page/getmodulehasdata?pageId="+pageId,
                success: function (data) {
                    if (data.data) {
                        var dataMap = data.data;
                        $("#ownermodules .moduleitem").each(function (i, d) {
                            var id =d.getAttribute("data-id");
                            if(dataMap[id]){
                                $(d).addClass("havefilldata");
                            }
                        });
                    }
                }
            });
        };
        var pageId =$("#pageId").val();
        if(pageId!=""){
            getModuleCount(pageId);
        }

        var appendModule = function (item, backward) {
            var pageId = $("#pageId").val();
            var htm = '';
            var htm_lazyload = '<a class="lazyloadCheck" href="javascript:;"><span title="发布后的页面中该组件是否异步加载"><input type="checkbox" name="isLazyLoad" checked="checked" class="lazyload" moduleid="' + item.id + '" >是否懒加载</span></a>';
            htm += '<div data-id= "' + item.id + '" template-id="' + item.templateId + '" rowIndex="' + item.rowIndex + '" class="moduleitem">';
            htm += '<div class="htmlcontent"><img src="http://6.xiustatic.com' + item.thumbnailUrl + '"  alt="组件' + item.id + '缩略图" /></div>';
            htm += '<div class="actvie-container">';
            if(item.rowIndex > 3){
                htm += htm_lazyload
            }
            htm += '<a moduleid="' + item.id + '"  rowindex="' + item.rowIndex + '"  pageid="' + pageId + '"  class="dodelete">删除</a>';
            htm += '<a moduleid="' + item.id + '" rowindex="' + item.rowIndex + '"  pageid="' + pageId + '" class="up">上移</a>';
            htm += '<a moduleid="' + item.id + '" rowindex="' + item.rowIndex + '"  pageid="' + pageId + '" class="down">下移</a>';
            htm += '<a pageid="' + pageId + '" templateid="' + item.templateId + '"  rowindex="' + item.rowIndex + '" backward=true class="doinsert">插入组件</a>';
            htm += '<a href="${rc.getContextPath()}/h5page/exportcomponents?moduleId='+item.id+'"}">下载模板</a>';
            htm += '<a moduleid="' + item.id + '" class="doedit">编辑内容</a>';
            htm += '<input type="file" name="dataUpload_' + item.id + '" id="dataUpload_' + item.id + '" >' + '<br/>';
            htm += '<input type="button" onclick="uploadModuleData(\'' + item.id + '\')" style="width:68px;cursor:pointer;" value="上传">';
            htm += '</div></div>';
			
			if(backward){
				if(item.rowIndex < 4){
					$("#ownermodules").children("[rowindex=3]").find(".actvie-container").prepend(htm_lazyload);
					updateIsLazyLoad($("#ownermodules").children("[rowindex=3]").attr("data-id"), 1);
				}
				$("#ownermodules").children("[rowindex="+item.rowIndex+"]").before(htm);
			}else{
				$("#ownermodules").append(htm);
			}
			changeRowIndex();
            //$("#ownermodules").html(htm);
            //if(item.length>0){
                $(".applyPageTemple").hide();
            //}else{
                //$(".applyPageTemple").show();
            //}
            getModuleCount(pageId);
        };

        var appendPageTemplateList = function (data) {
            var htm = "";
            var pageTemplateList =[];
            for (var i = 0, len = data.length; i < len; i++) {
                var item = data[i];
                htm += '<li data-id="' + item.id + '" templateids="' + item.templateIds + '" class="oldName" title="点击覆盖整个模板">';
                htm += '<span class="simpleName">'+item.name+'</span>  <input type="text" name="nameText" class="nameText"  value="'+item.name+'" style="display:none;" /> <span class="toModifyNameBtn">修改名称</span> <span class="modifyNameBtn" style="display:none;">保存</span>';
                htm += '</li>';
                pageTemplateList.push({"id":item.id,"templateIds":item.templateIds,"name":item.name});
            }
            $("#pageTemplatePop .mous").html(htm);

            window.pageTemplateList = pageTemplateList;
        };

        var popup = function (popupName) {
            var _windowHeight = $(window).height(),//获取当前窗口高度
                    _windowWidth = $(window).width(),//获取当前窗口宽度
                    _popupHeight = popupName.height(),//获取弹出层高度
                    _popupWeight = popupName.width();//获取弹出层宽度
            var _posiTop = (_windowHeight - _popupHeight) / 2;
            var _posiLeft = (_windowWidth - _popupWeight) / 2;
            popupName.css({"left": _posiLeft + "px", "top": _posiTop + "px", "display": "block", "z-index": 999});//设置position
        };

        $('.addcom').on('click', function () {
            var container = $('#modulePop');
            popup(container.show());
        });
        $('#modulePop .closeAlert').on('click', function () {
            $('#modulePop').removeAttr("rowindex");
            $('#modulePop').hide();
        });
        $('#modulePop .mous li').on('mouseover', function () {
            var _this = $(this);
            var imgurl = _this.attr('img-url');
            var name = _this.attr('data-name');
            if (imgurl && imgurl != '') {
                $('#templateThumbnail').attr({"src": imgurl, "alt": name + "缩略图"});
            } else {
                $('#templateThumbnail').attr({"alt": name + "缩略图"});
            }
        });

        $('#modulePop .mous li').on('click', function () {
            var _this = $(this);
            var pageId = $("#pageId").val();
            var templateId = _this.attr('data-id');
            var index =  $('#modulePop').attr("rowindex");
            var backward = false;
            if(index){
                backward = true;
            }else{
                var len = $("#ownermodules .moduleitem");
                index = len.length + 1;
            }
            $('#modulePop').removeAttr("rowindex");

            $('#modulePop').hide();
            $.ajax({
                type: 'POST',
                dataType: "json",
                url: "${rc.getContextPath()}/h5page/addcomponents",
                data: {"pageId": pageId, "templateId": templateId, "index": index, "backward": backward},
                success: function (data) {
                    if (data.data) {
                        appendModule(data.data, backward);
                    }
                }
            });
        });

        var movehandler = function (targetDom) {
            var _this = targetDom;
            var firstModuleId = _this.attr("moduleid");
            var secondToIndex = _this.attr("rowindex");
            var pageId = $("#pageId").val();
            var other = {};
            if (_this.hasClass("up")) {
                other = _this.parents(".moduleitem").prev().find(".up");
                if (other.length == 0) {
                    alert("已经最上面了");
                    return;
                }
            } else {
                other = _this.parents(".moduleitem").next().find(".down");
                if (other.length == 0) {
                    alert("已经最下面了");
                    return;
                }
            }
            var secondModuleId = other.attr("moduleid");
            var firstToIndex = other.attr("rowindex");
            $.ajax({
                type: 'POST',
                dataType: "json",
                url: "${rc.getContextPath()}/h5page/movecomponents",
                data: {
                    "firstModuleId": firstModuleId,
                    "secondToIndex": secondToIndex,
                    "secondModuleId": secondModuleId,
                    "firstToIndex": firstToIndex,
                    "pageId": pageId
                },
                success: function (data) {
                    if (data.data) {
                    	var lazyloadHtml1 = '<a class="lazyloadCheck" href="javascript:;"><span title="发布后的页面中该组件是否异步加载">' 
                    		+ '<input type="checkbox" name="isLazyLoad" moduleid="'+ firstModuleId +'" class="lazyload">是否懒加载</span></a>';
                    	var lazyloadHtml2 = '<a class="lazyloadCheck" href="javascript:;"><span title="发布后的页面中该组件是否异步加载">' 
                    		+ '<input type="checkbox" name="isLazyLoad" moduleid="'+ secondModuleId +'" class="lazyload">是否懒加载</span></a>';
                        if(_this.hasClass("up")){
                        	if(secondToIndex == 4){
                        		_this.parents(".moduleitem").find(".lazyloadCheck").remove();
                        		_this.parents(".moduleitem").prev().find(".actvie-container").prepend(lazyloadHtml2);
                        	}
                        	_this.parents(".moduleitem").after(_this.parents(".moduleitem").prev());
                        }else{
                        	if(secondToIndex == 3){
                        		_this.parents(".moduleitem").find(".actvie-container").prepend(lazyloadHtml1);
                        		_this.parents(".moduleitem").next().find(".lazyloadCheck").remove();
                        	}
                        	_this.parents(".moduleitem").next().after(_this.parents(".moduleitem"));
                        }
                        
                    	changeRowIndex();
                    }
                }
            });
        };

        $("#ownermodules").on('click', '.up', function () {
            movehandler($(this));
        });

        $("#ownermodules").on('click', '.down', function () {
            movehandler($(this));
        });

        $("#ownermodules").on("click", ".dodelete", function () {
        	if(window.confirm('确定删除此组件吗？')){
	            var _this = $(this);
	            var moduleId = _this.attr("moduleid");
	            var rowIndex = _this.attr("rowindex");
	            var pageId = _this.attr("pageid");
	            $.ajax({
	                type: 'POST',
	                dataType: "json",
	                url: "${rc.getContextPath()}/h5page/deletecomponents",
	                data: {
	                    "moduleId": moduleId,
	                    "rowIndex": rowIndex,
	                    "pageId": pageId
	                },
	                success: function (data) {
	                    if (data.data) {
                        	if(rowIndex < 4){
                        		$("#ownermodules").children("[rowindex=4]").find(".lazyloadCheck").remove();
                        	}
							_this.parents(".moduleitem").remove();
	                        if(data.data.length > 0){
	                        	changeRowIndex();
	                        }else if(document.getElementById("applyPageTemple")){
	                        	$("#applyPageTemple").show();
	                        }else{
	                        	$("#ownermodules").before("<a class='applyPageTemple' id='applyPageTemple'>应用模板的H5组件</a>");
	                        }
	                    }
	                }
	            });
	         } 
        });

        $("#ownermodules").on("click", ".doinsert", function () {
            var _this = $(this);
            var container = $('#modulePop');
            var rowIndex = _this.attr("rowindex");
            popup(container.show().attr("rowIndex",rowIndex));


        });


        $("#savePageTemplatebtn").on("click", function () {
            var container = $('#pageTemplatePop');
            popup(container.show());
        });
        $('#pageTemplatePop .closeAlert').on('click', function () {
            $('#pageTemplatePop').hide();
        });

        var getNowModuleIds = function(){
            var ids = [];
            $("#ownermodules .moduleitem").each(function (i, d) {
                ids.push(d.getAttribute("template-id"));
            });
            return ids.toString();
        };

        var savaPageTemplate = function(params){
            $.ajax({
                type: 'POST',
                dataType: "json",
                url: "${rc.getContextPath()}/h5page/savepagetemplate",
                data: params,
                success: function (data) {
                    if (data.data) {
                        alert("保存成功");
                        appendPageTemplateList(data.data);
                    }
                }
            });
        };

        $('#pageTemplatePop .doOtherName').on('click', function () {
            var otherName = $("#otherName").val();
            if (otherName == '') {
                alert("页面模板名字不能为空");
            } else {
                var name = otherName;
                var templateIds = getNowModuleIds();
                var params = {"name": name, "templateIds": templateIds};

                $.ajax({
                    type: 'POST',
                    dataType: "json",
                    url: "${rc.getContextPath()}/h5page/checkpagetemplatename",
                    data: {"name": otherName},
                    success: function (data) {
                        if (data) {
                            var isNameExist = data.data;
                            if (isNameExist) {
                                alert("已经存在这个名字的模板了");
                            } else {
                                savaPageTemplate(params);
                            }
                        }
                    }
                });
            }
        });

        $('#pageTemplatePop .mous').on('click', ".simpleName",function () {
            var _this= $(this).parent();
            var id = _this.attr("data-id");
            var name = _this.find(".simpleName").text();
            var ids = [];
            $("#ownermodules .moduleitem").each(function (i, d) {
                ids.push(d.getAttribute("template-id"));
            });
            var templateIds = ids.toString();
            var params = {"name": name, "templateIds": templateIds,"id":id};
            savaPageTemplate(params);
        });

        $('#pageTemplatePop .mous').on('click', ".toModifyNameBtn",function () {
            var _this =$(this);
            _this.siblings(".simpleName").hide();
            _this.siblings(".nameText").show();
            _this.next().show();
            _this.hide();
        });

        $('#pageTemplatePop .mous').on('click', ".modifyNameBtn",function () {
            var _this =$(this);
            var parent= $(this).parent();
            var id = parent.attr("data-id");
            var otherName = _this.siblings(".nameText").val();
            if (otherName == '') {
                alert("页面模板名字不能为空");
            } else {
                var params = {"name": otherName, "id": id};
                $.ajax({
                    type: 'POST',
                    dataType: "json",
                    url: "${rc.getContextPath()}/h5page/checkpagetemplatename",
                    data: {"name": otherName},
                    success: function (data) {
                        if (data) {
                            var isNameExist = data.data;
                            if (isNameExist) {
                                alert("已经存在这个名字的模板了");
                            } else {
                                savaPageTemplate(params);
                            }
                        }
                    }
                });
            }
        });


        $('input[id^="buttonUpload_"]').on('click', function () {
            var _this = $(this);
            var moduleId = _this.attr('moduleId');
            
            var fileValue = $('#dataUpload_'+moduleId).val();
            if(fileValue == null || fileValue == ""){
            	alert('请选择文件再上传');
            	return;
            }
            
            $.ajaxFileUpload({
                url: '${rc.getContextPath()}/h5page/importcomponents?moduleId=' + moduleId,
                secureuri: false,
                fileElementId: 'dataUpload_' + moduleId,
                success: function (data) {
                    var msg = $(data).find("body").text();//获取返回的字符串
                    alert(msg);
                    if(msg ="导入成功"){
                    	window.location.reload();
                    }
                },
                error: function (data, status, e) {
                    alert(e);
                }
            });
        });


        $(".adminContent").on("click","#applyPageTemple",function(){
            var htm ="";
            var data = window.pageTemplateList;
            var pageId =$("#pageId").val();
            for (var i = 0, len = data.length; i < len; i++) {
                var item = data[i];
                htm += '<a  href="${rc.getContextPath()}/h5page/applypagetemplate?pageId='+pageId+'&templateIds='+ item.templateIds+'" class="oldName" title="点击应用这个模板">' + item.name + '</a>';
            }
            $("#choosePageTemplate .mous").html(htm);

            popup($("#choosePageTemplate").show());
        });

        $('#choosePageTemplate .closeAlert').on('click', function () {
            $('#choosePageTemplate').hide();
        });

        $("#allButtonUpload").on('click', function () {
            var _this = $(this);
            var pageId = _this.attr('pageId');
            
            var fileValue = $('#allDataUpload').val();
            if(fileValue == null || fileValue == ""){
            	alert('请选择文件再上传');
            	return;
            }
            
            $.ajaxFileUpload({
                url: '${rc.getContextPath()}/h5page/importallcomponents?pageId=' + pageId,
                secureuri: false,
                fileElementId: 'allDataUpload',
                success: function (data) {
                    var msg = $(data).find("body").text();//获取返回的字符串
                    alert(msg);
                    if(msg ="导入成功"){
                    	loadallmodulehtml();
                    }
                },
                error: function (data, status, e) {
                    alert(e);
                }
            });
        });   
        
        // 在线编辑相关脚本  start
	    $('#moduleEditPop .closeAlert').on('click', function () {
            $('#moduleEditPop').hide();
        });
        
        $("#ownermodules").on("click", ".doedit", function () {
            var _this = $(this);
            var moduleId = _this.attr('moduleid');
            $("#editMoudleId").val(moduleId);
            $.ajax({
            	type: 'GET',
            	url: '/h5page/editOnline',
            	dataType: 'html',
            	data: {moduleId : moduleId},
            	success: function(data){
            		$('#moduleEditPop .mous').html(data);
		            var container = $('#moduleEditPop');
		            popup(container.show());
            	}
            });
        });
        
        $("#ajaxifr").load(function(){
			var msg = $(this.contentWindow.document.body).text();
			var moduleId = $("#editMoudleId").val();
			if(msg != ''){
				alert(msg);
				if('保存成功' == msg){
					loadonemodulehtml(moduleId);
			 		$('#moduleEditPop').hide();
				}
			}
		});
		
        // 在线编辑相关脚本 end
        
        $("#ownermodules").on("change", ".lazyload", function () {
            var _this = $(this);
            var moduleId = _this.attr('moduleid');
            var value = _this.attr('checked');
			if(value == 'checked'){
				value = "1";
			}else{
				value = "0";
			}
			            
            updateIsLazyLoad(moduleId, value);
        });
	    
	    loadallmodulehtml();
	    
    });

	function uploadModuleData(moduleId){
        var fileValue = $('#dataUpload_'+moduleId).val();
        if(fileValue == null || fileValue == ""){
        	alert('请选择文件再上传');
        	return;
        }
        
        $.ajaxFileUpload({
            url: '${rc.getContextPath()}/h5page/importcomponents?moduleId=' + moduleId,
            secureuri: false,
            fileElementId: 'dataUpload_' + moduleId,
            success: function (data) {
                var msg = $(data).find("body").text();//获取返回的字符串
                alert(msg);
                if(msg ="导入成功"){
                	//window.location.reload();
                	loadonemodulehtml(moduleId);
                }
            },
            error: function (data, status, e) {
                alert(e);
            }
        });
	}
	
	 function loadallmodulehtml(){
        $("#ownermodules .moduleitem").each(function (i, d) {
            var id =d.getAttribute("data-id");
        	loadonemodulehtml(id);
        });
    }
	
	 function loadonemodulehtml(moduleId){
        $.ajax({
            type: 'GET',
            dataType: "html",
            url: "${rc.getContextPath()}/h5page/previewmoduleforajax",
            data: {moduleId: moduleId},
            success: function (data) {
            	if(data != ''){
                	$("#ownermodules .moduleitem[data-id='"+moduleId+"'] .htmlcontent").html(data);
                	echo.init({
				   		offset: 1000,
				    	throttle: 150
				 	});
                }
            }
        });
        
    }
    
    function changeRowIndex(){
    	$(".moduleitem").each(function(i,t){
    		var index = i+1;
    		var moduleId = $(t).attr("data-id");
    		$(t).attr("rowindex",index);
    		$(t).find("[rowindex]").attr("rowindex",index);
    		if(index <= 3){
    			updateIsLazyLoad(moduleId, "0")
    		}
    	});
    }
    
    function updateIsLazyLoad(moduleId, isLazyLoad){
    	$.ajax({
    		type: 'GET',
    		dataType: 'json',
    		url: "${rc.getContextPath()}/h5page/updatelazyload",
    		data: {"moduleId":moduleId,"isLazyLoad":isLazyLoad},
    		success: function(data){
    			alert(data);
    		}
    	});
    }
    
    function publishPage(pageId){
    	if(window.confirm('确认发布此H5吗？')){
	        $.ajax({
	            type: 'GET',
	            dataType: "json",
	            url: "${rc.getContextPath()}/h5page/publishpage",
	            data: {pageId: pageId},
	            success: function (data) {
	                if (data == "1") {
	                    alert('发布成功');
	                }else{
	                    alert('发布失败');
	                }
	            }
	        });
	     } 
    }


</script>
</body>
</html>