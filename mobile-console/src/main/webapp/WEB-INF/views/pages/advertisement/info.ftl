<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-validate.js"></script>



</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">广告详情</h3>
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
			<dd>广告管理</dd>
			<dd><a href="${rc.getContextPath()}/adv/list">广告管理</a></dd>
			<dd class="last"><h3>详情菜单</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
<form id="updateFocusForm" name="updateFocusForm" enctype="multipart/form-data" method="post" action="${rc.getContextPath()}/adv/edit">
	<!--菜单内容-->
	<div class="adminContent clearfix">
	 <input id="id"  name="id" type="hidden" value="${advertisement.id!''}"/>
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">广告详情</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row">广告ID：</th>
				<td class="tdBox">
				    ${advertisement.id!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">创建时间：</th>
				<td class="tdBox">
					${advertisement.createDt?string('yyyy-MM-dd HH:mm:ss')} 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row" >广告位：</th>
				<td class="tdBox">
					 	<select name="advPlacesId" id="advPlacesId" >
				                 <option value="" >-请选择-</option>
					 			<#if (advPlaces?? && advPlaces?size > 0)>
			                     <#list advPlaces as advPlace>
				                 <option value="#{advPlace.id!''}"   > ${advPlace.name!''} </option>
				                </#list>
				                </#if>
				          </select> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">广告帧：</th>
				<td class="tdBox">
				          <select name="advFrameId" id="advFrameId" >
				             <option value="">-请选择-</option>
					 			<#if (advFrames?? && advFrames?size > 0)>
			                     <#list advFrames as advFrame>
				                 <option value="#{advFrame.id!''}" <#if advertisement.advFrameId??&&advFrame.id==advertisement.advFrameId>selected=selected</#if> > ${advFrame.name!''} </option>
				                </#list>
				                </#if>
				          </select> 
				          			          
				          <ul id="advFramesValue">
				          		 <#if (advFrames?? && advFrames?size > 0)>
			                     <#list advFrames as advFrame>
				           				 <li advFrameId="#{advFrame.id!''}" advPlaceId="#{advFrame.advPlaceId!''}" advFrameName="${advFrame.name!''}"></li>
					           </#list>
					           </#if>
				          </ul>
				          <div id="frameTimes"></div>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">广告标题：</th>
				<td class="tdBox">
				<input name="title" type="text" id="title" value="${advertisement.title!''}"  style="width:120px;"   />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>广告图片：</th>
				<td class="tdBox">
				        <img src=" ${advertisement.picPath!''}" width="300px"/> <br/>
						<input type="file" name="adv_pic_f" id="adv_pic_f"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>开始时间：</th>
				<td class="tdBox">
				<input name="beginTime" type="text" id="beginTime" value="${advertisement.startTime?string('yyyy-MM-dd HH:mm:ss')}"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd 00:00:00'})"  />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>结束时间：</th>
				<td class="tdBox">
					<input name="endTime" type="text" id="endTime" value="${advertisement.endTime?string('yyyy-MM-dd HH:mm:ss')}"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd 23:59:59'})"  />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>绑定链接：</th>
				<td class="tdBox">
				 <table>
					<tr  style="  height: 30px">
					   <td width="60px"> 
					   <input type="hidden" value="" name="linkObject" id="linkObject"/>
					   <input type="radio" name="linkType" class="linkType" value="1"  <#if advertisement.linkType??&&advertisement.linkType==1> checked="checked"</#if> />URL</td> 
					   <td> url:</td>
					   <td><input id="linkObject1" class="dvalue"  dvalue="例如:http://www.xiu.com"  value="<#if advertisement.linkType??&&advertisement.linkType==1>${advertisement.linkObject!''}</#if>"  maxlength="500"/> </td>
					   <td style="  width: 90px;text-align: right;"> xiuapp:</td>
					   <td><input id="xiuAppUrl" name="xiuAppUrl" class="dvalue" style="width:300px;"  dvalue="例如:xiuApp://xiu.app.index/openwith?page=test" value="<#if advertisement.linkType??&&advertisement.linkType==1>${advertisement.xiuAppUrl!''}</#if>"  maxlength="500"/> </td>
					   <td><span id="linkObjectMsg1" class="red errormsg"></span></tr>
					<tr  style="  height: 30px">
					   <td width="60px"> 
					   <input type="radio" name="linkType" class="linkType" value="2" <#if advertisement.linkType??&&advertisement.linkType==2> checked="checked"</#if> />话题 </td> 
					   <td> 话题ID:</td>
					   <td><input id="linkObject2"  value="<#if advertisement.linkType??&&advertisement.linkType==2>${advertisement.linkObject!''}</#if>"  maxlength="12"/> </td>
					   <td><span id="linkObjectMsg2" class="red errormsg"></span></tr>
					<tr style="  height: 30px">
					   <td width="60px"><input type="radio" name="linkType"  class="linkType"  value="3" <#if advertisement.linkType??&&advertisement.linkType==3> checked="checked"</#if> />秀</td> 
					   <td> 秀ID:</td>
					   <td><input id="linkObject3"  value="<#if advertisement.linkType??&&advertisement.linkType==3>${advertisement.linkObject!''}</#if>"  maxlength="12"/> 
					   <td><span id="linkObjectMsg3" class="red errormsg"></span></tr>
					</tr>
					<tr style="  height: 30px">
					   <td width="60px"><input type="radio" name="linkType"  class="linkType"  value="4" <#if advertisement.linkType??&&advertisement.linkType==4> checked="checked"</#if> />卖场</td> 
					   <td> 卖场ID:</td>
					   <td><input id="linkObject4"  value="<#if advertisement.linkType??&&advertisement.linkType==4>${advertisement.linkObject!''}</#if>"  maxlength="12"/> 
					   <td><span id="linkObjectMsg4" class="red errormsg"></span></tr>
					</tr>
					<tr style="  height: 30px">
					   <td width="60px"><input type="radio" name="linkType"  class="linkType"  value="5" <#if advertisement.linkType??&&advertisement.linkType==5> checked="checked"</#if> />卖场集</td> 
					   <td> 卖场集ID:</td>
					   <td><input id="linkObject5"  value="<#if advertisement.linkType??&&advertisement.linkType==5>${advertisement.linkObject!''}</#if>"  maxlength="12"/> 
					   <td><span id="linkObjectMsg5" class="red errormsg"></span></tr>
					</tr>
					<tr style="  height: 30px">
					   <td width="60px"><input type="radio" name="linkType"  class="linkType"  value="6" <#if advertisement.linkType??&&advertisement.linkType==6> checked="checked"</#if> />商品</td> 
					   <td> 商品ID:</td>
					   <td><input id="linkObject6"  value="<#if advertisement.linkType??&&advertisement.linkType==6>${advertisement.linkObject!''}</#if>"  maxlength="12"/> 
					   <td><span id="linkObjectMsg6" class="red errormsg"></span></tr>
					</tr>
					<tr style="  height: 30px">
					   <td width="80px"><input type="radio" name="linkType"  class="linkType"  value="7" <#if advertisement.linkType??&&advertisement.linkType==7> checked="checked"</#if> />商品分类</td> 
					   <td> 商品分类ID:</td>
					   <td><input id="linkObject7"  value="<#if advertisement.linkType??&&advertisement.linkType==7>${advertisement.linkObject!''}</#if>"  maxlength="12"/> 
					   <td  colspan="2"><span id="linkObjectMsg7" class="red errormsg"></span><font class="gray">(该值系统不作真实存在校验,请确认该值是正确的)</font></tr>
					</tr>
					<tr style="  height: 30px">
					   <td width="60px"><input type="radio" name="linkType"  class="linkType"  value="8" <#if advertisement.linkType??&&advertisement.linkType==8> checked="checked"</#if> />专题</td> 
					   <td> 专题ID:</td>
					   <td><input id="linkObject8"  value="<#if advertisement.linkType??&&advertisement.linkType==8>${advertisement.linkObject!''}</#if>"  maxlength="12"/> 
					   <td><span id="linkObjectMsg8" class="red errormsg"></span></tr>
					</tr>
					<tr style="  height: 30px">
					   <td width="60px"><input type="radio" name="linkType"  class="linkType"  value="10" <#if advertisement.linkType??&&advertisement.linkType==10> checked="checked"</#if> />标签</td> 
					   <td> 标签ID:</td>
					   <td><input id="linkObject10"  value="<#if advertisement.linkType??&&advertisement.linkType==10>${advertisement.linkObject!''}</#if>"  maxlength="12"/> 
					   <td><span id="linkObjectMsg10" class="red errormsg"></span></tr>
					</tr>
					<tr style="  height: 30px">
					   <td width="60px"><input type="radio" name="linkType"  class="linkType"  value="11" <#if advertisement.linkType??&&advertisement.linkType==11> checked="checked"</#if> />秀集合</td> 
					   <td> 秀集合ID:</td>
					   <td><input id="linkObject11"  value="<#if advertisement.linkType??&&advertisement.linkType==11>${advertisement.linkObject!''}</#if>"  maxlength="12"/> 
					   <td><span id="linkObjectMsg11" class="red errormsg"></span></tr>
					</tr>
			    </table>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">显示状态：</th>
				<td class="tdBox">
			        <#if advertisement.status?? && advertisement.status == 0>未开始
					<#elseif advertisement.status?? && advertisement.status==1>进行中
					<#elseif advertisement.status?? && advertisement.status==2>已结束
					</#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"></th>
				<td class="tdBox">
                   <p class="fl">
					<input type="button" onclick="update();" class="userOperBtn" value="确认修改"></input> 
					<input type="button"  onclick="returnMenuList();" class="userOperBtn"  value="返回"/> 
					</p>
				</td>
			</tr>
		</table>
				
	</div>
</form>
</div>
</div>
<input type="hidden" value="${status!''}" id="updateStatus"/>
<input type="hidden" value="${msg!''}" id="msg"/>
<div id="deleteAdvDiv" class="showbox">
 <input id="deleteid" type="hidden" />
		<h3 >确定删除此广告？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>
</body>
<script type="text/javascript">

$(function(){
	 var status=$("#updateStatus").val();
	   var msg=$("#msg").val();
	 if(status==1){
	   alert("更新成功");
	   location.href="${rc.getContextPath()}/adv/list";
	 }else if(msg!=''){
	   alert(msg);
	 }
	 
	$(".linkType").change(function() { 
	   $("#linkObjectTopicMsg").html("");
	   $("#linkObjectShowMsg").html("");
	}); 
	 
	 $("#linkObject2").blur(function(){
	 	checkShowTopic();
	 });
	 $("#linkObject3").blur(function(){
	    checkShow();
	 });
	 $("#linkObject1").blur(function(){
	    checkUrl();
	 });
	 $("#xiuAppUrl").blur(function(){
	    checkUrl();
	 });
	 $("#linkObject4").blur(function(){
	    checkTopic();
	 });
	 $("#linkObject5").blur(function(){
	    checkTopicSet();
	 });
	 $("#linkObject6").blur(function(){
	    checkGoods();
	 });
	 $("#linkObject7").blur(function(){
	    checkGoodType();
	 });
	 $("#linkObject8").blur(function(){
	    checkSubject();
	 });
	 	 
	 $("#linkObject10").blur(function(){
	    checkLable();
	 });
	 $("#linkObject11").blur(function(){
	    checkShowCollection();
	 });
	 	 
	 $("#advFrameId").change(function(){
          frameSelectedChange(this);
	 });
	 
	 
	 
	 $("#advPlacesId").change(function(){
	   var id=$(this).children('option:selected').val();//这就是selected的值 
	   var advFramesValueUlLis=$("#advFramesValue li");
	   $("#advFrameId").empty();
	   $("#advFrameId").append("<option value=''>-请选择-</option>");
	   for(var i=0;i<advFramesValueUlLis.length;i++){
	     var advFrameId=$(advFramesValueUlLis[i]).attr("advFrameId");
	     var advPlaceId=$(advFramesValueUlLis[i]).attr("advPlaceId");
	     var advFrameName=$(advFramesValueUlLis[i]).attr("advFrameName");
	     
	     if(advPlaceId==id){
	       	   $("#advFrameId").append("<option value='"+advFrameId+"'>"+advFrameName+"</option>");
	     }
	   }
	 });
	 
	 
	 frameSelectedChange($("#advFrameId"));
	 
	 setInputDefaultValue();//设置默认值
	
});


function frameSelectedChange(obj){
	   var frameid=$(obj).children('option:selected').val();//这就是selected的值 
	   if(frameid!=""){
	    var advid=$("#id").val();
	    findTimesByAdvFrameId(frameid,advid);
	   }else{
	    $("#frameTimes").html("");
	   }
}

function checkUrl(){
  var isSuccess=true;
 $(".errormsg").html("");
  var linkType = $('input[name="linkType"]:checked ').val();
  if(linkType==1){
    var linkObject1=$("#linkObject1").val();
    var linkObjectDvalue=$("#linkObject1").attr("dvalue");
    var xiuAppUrl=$("#xiuAppUrl").val();
    var xiuAppUrlDvalue=$("#xiuAppUrl").attr("dvalue");
    if((linkObject1==''||linkObject1==linkObjectDvalue)&&(xiuAppUrl==""||xiuAppUrl==xiuAppUrlDvalue)){
       $("#linkObjectMsg1").html("url和xiuapp至少输入一个");
       isSuccess=false;
    }else if(linkObject1.lenght>500||xiuAppUrl.length>500){
       $("#linkObjectMsg1").html("长度不能大于500");
       isSuccess=false;
    }
  }
    return isSuccess;
  
}

function checkShowTopic(){
    	var params={
		    url:"showTopic/checkShowTopic?topicId=",
		    linkType:2,
		    notExistStr:"话题不存在",
		    notNulStr:"请输入话题ID",
	    }
   return checklinkTypeData(params);
}

function checkShow(){
 	   var params={
		    url:"show/checkShow?showId=",
		    linkType:3,
		    notExistStr:"秀不存在",
		    notNulStr:"请输入秀ID",
	    }
   return checklinkTypeData(params);
}
//检查卖场
function checkTopic(){
      var params={
		    url:"topic/checkTopic?activityId=",
		    linkType:4,
		    notExistStr:"卖场不存在",
		    notNulStr:"请输入卖场ID",
	    }
   return checklinkTypeData(params);
}

//检查卖场集
function checkTopicSet(){
   	    var params={
		    url:"topic/checkTopicSet?activityId=",
		    linkType:5,
		    notExistStr:"卖场集不存在",
		    notNulStr:"请输入卖场集ID",
	    }
   return checklinkTypeData(params);
}

//检查商品
function checkGoods(){
	    var params={
		    url:"goods/checkGoodsId?goodsId=",
		    linkType:6,
		    notExistStr:"商品不存在",
		    notNulStr:"请输入商品ID",
	    }
   return checklinkTypeData(params);
}

//检查商品分离
function checkGoodType(){
  var isSuccess=false;
   $("#linkObjectMsg"+7).html("");
    var linkType = $('input[name="linkType"]:checked ').val();
    if(linkType==7){
    		    var val=$("#linkObject"+linkType).val();
		    if(val==''){
		        $("#linkObjectMsg"+7).html("商品分类ID不能为空");
		    }else{
		      isSuccess=true;
		    }
    }
    return isSuccess;
}

//检查专题
function checkSubject(){
	    var params={
		    url:"subject/checkSubjectId?subjectId=",
		    linkType:8,
		    notExistStr:"专题不存在",
		    notNulStr:"请输入专题ID",
	    }
   return checklinkTypeData(params);
}

//检查标签
function checkLable(){
	    var params={
		    url:"label/checkLabelId?labelId=",
		    linkType:10,
		    notExistStr:"标签不存在",
		    notNulStr:"请输入标签ID",
	    }
   return checklinkTypeData(params);
}
//检查秀集合
function checkShowCollection(){
	    var params={
		    url:"show/checkShowCollectionId?showCollectionId=",
		    linkType:11,
		    notExistStr:"秀集合不存在",
		    notNulStr:"请输入秀集合ID",
	    }
   return checklinkTypeData(params);
}


/**
params.url 例如 goods/checkGoodsId?goodsId=
params.linkType
params.notExistStr
params.notNulStr
*/
function checklinkTypeData(params){
  var linkType=params.linkType;
  $(".errormsg").html("");
  var isSuccess=false;
  var chooselinkType = $('input[name="linkType"]:checked ').val();
		if(linkType==chooselinkType){
		    var val=$("#linkObject"+linkType).val();
		    if(val!=''){
		       $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/"+params.url + val +"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "2")
							{
						       $("#linkObjectMsg"+linkType).html(params.notExistStr);
			            	}else{
			            		isSuccess=true;
			            	   $("#linkObjectMsg"+linkType).html("");
			            	}
						}
					}
				},
				error : function(data) {
				    $("#linkObjectMsg"+linkType).html(params.notNulStr);
				}
			}); 
		    }else{
		       $("#linkObjectMsg"+linkType).html(params.notNulStr);
		    }
	    }
   return isSuccess;
}

function update(){
    var linkType = $('input[name="linkType"]:checked ').val();
    var linkObject =$("#linkObject"+linkType).val();
    var beginTime=$("#beginTime").val();
    var endTime=$("#endTime").val();
    var pic=$("#adv_pic_f").val();
    var pic=$("#adv_pic_f").val();
    
    if(beginTime==""||endTime==""){
      alert("请输入完整数据");
      return ;
    }
    
    var title=$("#title").val();
	    
	if(title.length>50){
		alert("标题不能大于50个字符");
		return false;
	}
    
   	if(pic!=""&&pic.toLowerCase().indexOf(".jpg")<0){
   		alert("请上传.jpg类型的图片！");
   		return false;
    }
    if(!compareDate(beginTime,endTime)){
	       alert("开始时间不能大于结束时间");
	      return ;
	 }
	 
	 
    if(linkType==1){
      if(!checkUrl()){
        return false;
      }else{
       linkObject=$("#linkObject1").val();
      }
    }
    if(linkType==2){
      if(!checkShowTopic()){
        return false;
      }
    }
    if(linkType==3){
      if(!checkShow()){
        return false;
      }
    }	 
    if(linkType==4){
      if(!checkTopic()){
        return false;
      }
   }  
    if(linkType==5){
      if(!checkTopicSet()){
        return false;
      }
   }  
    if(linkType==6){
      if(!checkGoods()){
        return false;
      }
   }  
    if(linkType==7){
      if(!checkGoodType()){
        return false;
      }
    }
    if(linkType==8){
      if(!checkSubject()){
        return false;
      }
   }  
    var xiuAppUrl=$("#xiuAppUrl").val();
    var xiuAppUrlDvalue=$("#xiuAppUrl").attr("dvalue");
    if(xiuAppUrl==xiuAppUrlDvalue){
      $("#xiuAppUrl").val("");
    }
    
    $("#linkObject").val(linkObject);
   $("#updateFocusForm").submit();
}

	
	//修改绑定
	function deleteAdv(id) {
		var arg = {
			id:"deleteAdvDiv",
			title:"删除确定",
			height:150,
			width:300
		}
		showPanel(arg);
		$("#deleteid").val(id);
		
	}	
	
 function deleteAjax(){
    var id=    $("#deleteid").val();
      $.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/adv/delete?id=" + id+"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert("删除成功");
							  returnMenuList();
			            	}else{
			            	    alert("删除失败");
			            	}
						}
					}
				},
				error : function(data) {
		       }
		       });
 }
	
	
	function returnMenuList(){
		 var url="${rc.getContextPath()}/adv/list";
	    location.href=url;
	}
	
		
	function findTimesByAdvFrameId(advFrameId,advId){
	  $(".errormsg").html("");
			       $.ajax({
					type : "GET",
					async: false,
					url : "${rc.getContextPath()}/adv/getTimesByAdvFrameId?advFrameId=" + advFrameId +"&advId="+advId+"&format=json",
		            dataType: "text",
					success : function(data, textStatus) {
					        data=data.replace(/"/gm,'');
					       if(data!=""){
					       data="以下为该广告帧已经分配的日期:<br/>"+data;
				            	$("#frameTimes").html(data);
					       }else{
					            $("#frameTimes").html("该广告帧还没分配其他广告");
					       }
					},
					error : function(data) {
					}
				}); 
   }

</script>

</html>