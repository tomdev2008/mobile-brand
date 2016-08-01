<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">广告管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/adv/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>广告管理</dd>
				<dd class="last"><h3>广告列表</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
            <div class="wapbt" style="border-bottom:0px;" align="left">
			    <label>显示时间：
			         <input name="startDate" type="text" id="startDate" value="<#if startDate??>${startDate}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
			          ---
			          <input name="endDate" type="text" id="endDate" value="<#if endDate??>${endDate}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				</label>
		        <label>&nbsp;广告位：
				          <select name="advPlaceId" id="advPlaceId" style="width:155px;">
				           <option value="" >全部</option>
				           <#if (advPlaces??&&advPlaces?size>0) >
				             <#list advPlaces as advPlace>
				              <option value="#{advPlace.id}" <#if advPlaceId??&&advPlaceId==advPlace.id>selected="selected"</#if>>${advPlace.name}</option>
				              </#list>
				            </#if>
				          </select> 
		        </label>
		    </div>
		    <div class="wapbt" style="border-bottom:0px;" align="left">
		    	 <label>显示状态：
				          <select name="status" id="status" style="width:155px;">
				           <option value="-1" >全部</option>
				              <option value="0" <#if status??&&status=='0'>selected="selected"</#if>>未开始</option>
				              <option value="1" <#if status??&&status=='1'>selected="selected"</#if>>进行中</option>
				              <option value="2" <#if status??&&status=='2'>selected="selected"</#if>>已结束</option>
				          </select> 
		        </label>
		        <label>&nbsp;类别：
				          <select name="linkType" id="linkType" style="width:155px;">
				           <option value="" >全部</option>
				              <option value="1" <#if linkType??&&linkType=='1'>selected="selected"</#if>>URL</option>
				              <option value="2" <#if linkType??&&linkType=='2'>selected="selected"</#if>>话题</option>
				              <option value="3" <#if linkType??&&linkType=='3'>selected="selected"</#if>>秀</option>
				              <option value="4" <#if linkType??&&linkType=='4'>selected="selected"</#if>>卖场</option>
				              <option value="5" <#if linkType??&&linkType=='5'>selected="selected"</#if>>卖场集</option>
				              <option value="6" <#if linkType??&&linkType=='6'>selected="selected"</#if>>商品</option>
				              <option value="7" <#if linkType??&&linkType=='7'>selected="selected"</#if>>商品分类</option>
				              <option value="8" <#if linkType??&&linkType=='8'>selected="selected"</#if>>专题</option>
				              <option value="10" <#if linkType??&&linkType=='10'>selected="selected"</#if>>标签</option>
				              <option value="11" <#if linkType??&&linkType=='11'>selected="selected"</#if>>秀集合</option>
				          </select> 
		        </label>
		        <label>创建人：
					<input name="creatorName" type="text" id="creatorName" value="${(creatorName!'')?html}" size="12" />
		        </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>&nbsp;
			    	<a href="javascript:void(0);" title="创建广告" class="btn" onclick="addNewadv()" style="width:80px;text-align:center;height:22px;">创建广告</a>
			 	</label>
			</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="6%">广告ID</th>
					<th width="12%">标题</th>
					<th width="10%">图片</th>
					<th width="10%">广告帧</th>
					<th width="10%">广告位</th>
					<th width="6%">开始时间</th>
					<th width="6%">结束时间</th>
					<th width="6%">显示状态</th>
					<th width="6%">类别</th>
					<th width="6%">创建时间</th>
					<th width="6%">更新时间</th>
					<th width="16%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (advertisementlist?? && advertisementlist?size > 0)>
			<#list advertisementlist as adv>
			<tr>
			    <td>${adv.id!''}
			    <input type="hidden" id="${adv.id!''}_xiuAppUrl" value="${adv.xiuAppUrl!''}"/>
			    </td>
			    <td>${adv.title!''}</td>
			    <td>
			     <div style="  position: relative;">
			    <img width="75px" height="28px" class="tdFocusPic" src="${adv.picPath!''}"/>
			    <img style="display:none;bottom: 0px;left: 95%;background-color: white;position: absolute;  border: 2px solid #ddd;" src="${adv.picPath!''}" width="380px"  />
			    </div>
			    </td>
			    <td>
			    <#if adv.linkType?? >${adv.advFrameName!''}
			    <#else>--
			    </#if>
			    <td>${adv.advPlaceName!''}</td>
			    </td>
			    <td>${adv.startTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>${adv.endTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>				    
			        <#if adv.status?? && adv.status == 0>未开始
					<#elseif adv.status?? && adv.status==1>进行中
					<#elseif adv.status?? && adv.status==2>已结束
					</#if>
				</td>
				<td>
			  		<#if adv.linkType?? && adv.linkType == 1>URL
					<#elseif adv.linkType?? && adv.linkType==2>话题
					<#elseif adv.linkType?? && adv.linkType==3>秀
					<#elseif adv.linkType?? && adv.linkType==4>卖场
					<#elseif adv.linkType?? && adv.linkType==5>卖场集
					<#elseif adv.linkType?? && adv.linkType==6>商品
					<#elseif adv.linkType?? && adv.linkType==7>商品分类
					<#elseif adv.linkType?? && adv.linkType==8>专题
					<#elseif adv.linkType?? && adv.linkType==10>标签
					<#elseif adv.linkType?? && adv.linkType==11>秀集合
					</#if>  
			    </td>
			    <td>${adv.createDt?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>${adv.lastUpdateDt?string('yyyy-MM-dd HH:mm:ss')}</td>
				<td>
					<a href="javascript:void(0);" onclick="info('${adv.id!''}')" title="编辑">编辑</a>|
					<a href="javascript:void(0);"  onclick="showUpdateLink('${adv.id!''}','${adv.linkType!''}','${adv.linkObject!''}')" title="绑定">绑定</a>|
					<a href="javascript:void(0);" onclick="deleteAdv('${adv.id!''}')" title="删除">删除</a>
				</td>    
		    </#list>
		    <#else>
		    <tr><td colspan="12"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
</div>


<div id="deleteAdvDiv" class="showbox">
 <input id="deleteid" type="hidden" />
		<h3 >确定删除此广告？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>
<!-- 分页文件-->
  <#include "/common/page.ftl">  
</form>
</div>



<div id="updateLinkDiv" class="showbox">
 <input id="updateid" type="hidden" />
 <input id="updateLinkType" type="hidden" />
 <input id="updateLinkObject" type="hidden" />
		<h3 id="updateShowIdTopicsTitle">绑定此广告点击跳转页面</h3>
					   <input type="hidden" value="" name="linkObject" id="linkObject"/>
			 <table id="updateLinkTable">
			 		<tr style="  height: 30px">
					   <td width="80px"><input  id="linkType1"  type="radio" name="linkType"  class="linkType"  value="1"  />URL</td> 
					   <td> url:</td>
					   <td><input id="linkObject1" class="dvalue"  dvalue="例如:http://www.xiu.com" value=""/> 
					   <td style=" width: 80px;text-align: right;"> xiuapp:</td>
					   <td><input id="xiuAppUrl"  value="" style="width:300px"  class="dvalue"  dvalue="例如:xiuApp://xiu.app.index/openwith?page=test" /> 
					   <td><span id="linkObjectMsg1" class="red linkObjectMsg" ></span></tr>
					 </tr>
					<tr  style="  height: 30px">
					   <td width="80px">
					   <input id="linkType2" type="radio" name="linkType" class="linkType" value="2"  />话题 </td> 
					   <td> 话题ID:</td>
					   <td><input id="linkObject2"  value=""/> </td>
					   <td><span id="linkObjectMsg2" class="red linkObjectMsg"></span></tr>
					<tr style="  height: 30px">
					   <td width="80px"><input  id="linkType3"  type="radio" name="linkType"  class="linkType"  value="3"  />秀</td> 
					   <td> 秀ID:</td>
					   <td><input id="linkObject3"  value=""/> 
					   <td><span id="linkObjectMsg3" class="red linkObjectMsg" ></span></tr>
					 </tr>
					<tr style="  height: 30px">
					   <td width="80px"><input  id="linkType4"  type="radio" name="linkType"  class="linkType"  value="4"  />卖场</td> 
					   <td> 卖场ID:</td>
					   <td><input id="linkObject4"  value=""/> 
					   <td><span id="linkObjectMsg4" class="red linkObjectMsg" ></span></tr>
					 </tr>
					<tr style="  height: 30px">
					   <td width="80px"><input  id="linkType5"  type="radio" name="linkType"  class="linkType"  value="5"  />卖场集</td> 
					   <td> 卖场集ID:</td>
					   <td><input id="linkObject5"  value=""/> 
					   <td><span id="linkObjectMsg5" class="red linkObjectMsg" ></span></tr>
					 </tr>
					<tr style="  height: 30px">
					   <td width="80px"><input  id="linkType6"  type="radio" name="linkType"  class="linkType"  value="6"  />商品</td> 
					   <td> 商品ID:</td>
					   <td><input id="linkObject6"  value=""/> 
					   <td><span id="linkObjectMsg6" class="red linkObjectMsg" ></span></tr>
					 </tr>
					<tr style="  height: 30px">
					   <td width="80px"><input  id="linkType7"  type="radio" name="linkType"  class="linkType"  value="7"  />商品分类</td> 
					   <td> 商品分类ID:</td>
					   <td><input id="linkObject7"  value=""/> 
					   <td colspan="2"><span id="linkObjectMsg7" class="red linkObjectMsg" ></span><font class="gray">(该值系统不作真实存在校验,请确认该值是正确的)</font></tr>
					 </tr>
					<tr style="  height: 30px">
					   <td width="80px"><input  id="linkType8"  type="radio" name="linkType"  class="linkType"  value="8"  />专题</td> 
					   <td> 专题ID:</td>
					   <td><input id="linkObject8"  value=""/> 
					   <td><span id="linkObjectMsg8" class="red linkObjectMsg" ></span></tr>
					 </tr>
					<tr style="  height: 30px">
					   <td width="80px"><input  id="linkType10"  type="radio" name="linkType"  class="linkType"  value="10"  />标签</td> 
					   <td> 标签ID:</td>
					   <td><input id="linkObject10"  value=""/> 
					   <td><span id="linkObjectMsg10" class="red linkObjectMsg" ></span></tr>
					 </tr>
					<tr style="  height: 30px">
					   <td width="80px"><input  id="linkType11"  type="radio" name="linkType"  class="linkType"  value="11"  />秀集合</td> 
					   <td> 秀集合ID:</td>
					   <td><input id="linkObject11"  value=""/> 
					   <td><span id="linkObjectMsg11" class="red linkObjectMsg" ></span></tr>
					 </tr>
			    </table>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="updateLinkAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

<script type="text/javascript">

   $(function(){
      $(".tdFocusPic").mouseover(function(){
        $(this).next().css("display","");
      }).mouseout(function(){
        $(this).next().css("display","none");
      });
     
   });

	//查询
	function query() {
	    $('#pageNo').val(1);
     	$("#mainForm").submit();
	}
	
	function info(id){
	  var url="${rc.getContextPath()}/adv/toedit?id="+id;
	  location.href=url;
	}
	
	
	function addNewadv(){
	  var url="${rc.getContextPath()}/adv/bfAdd";
	  location.href=url;
	}
	
	//修改绑定
	function showUpdateLink(id,LinkType,LinkObject) {
		var arg = {
			id:"updateLinkDiv",
			title:"修改绑定",
			height:420,
			width:870
		}
		showPanel(arg);
		$("#xiuAppUrl").val($("#"+id+"_xiuAppUrl").val());
	    setInputDefaultValue();//设置默认值
		$("#updateid").val(id);
		$("#updateLinkType").val(LinkType);
		$("#updateLinkObject").val(LinkObject);
		$("#linkType"+LinkType).attr("checked","checked");
		$("#linkObject"+LinkType).val(LinkObject);
		 	 
	     $("#linkObject1").blur(function(){
	   		 checkUrl();
		 });
		
		 $("#linkObject2").blur(function(){
		 	checkShowTopic();
		 });
		 $("#linkObject3").blur(function(){
		    checkShow();
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
		 $("#xiuAppUrl").blur(function(){
		    checkUrl();
		 });
		$(".linkType").change(function() { 
		   $(".linkObjectMsg").html("");
		}); 
		
	}
	
 function updateLinkAjax(){
    var id=    $("#updateid").val();
    var LinkType = $('#updateLinkTable input[name="linkType"]:checked ').val();
	var LinkObject=	$("#linkObject"+LinkType).val();
	if(LinkType==1){
	  if(!checkUrl()){
	  return false;
	  }
	}
	if(LinkType==2){
	  if(!checkShowTopic()){
	  return false;
	  }
	}
	if(LinkType==3){
	  if(!checkShow()){
	  return false;
	  }
	}
	if(LinkType==4){
	  if(!checkTopic()){
	  return false;
	  }
	}
	if(LinkType==5){
	  if(!checkTopicSet()){
	  return false;
	  }
	}
	if(LinkType==6){
	  if(!checkGoods()){
	  return false;
	  }
	}
	if(LinkType==7){
	  if(!checkGoodType()){
	  return false;
	  }
	}
	if(LinkType==8){
	  if(!checkSubject()){
	  return false;
	  }
	}
	if(LinkType==10){
	  if(!checkLable()){
	  return false;
	  }
	}
	if(LinkType==11){
	  if(!checkShowCollection()){
	  return false;
	  }
	}
	var xiuAppUrl=$("#xiuAppUrl").val();
    var xiuAppUrlDvalue=$("#xiuAppUrl").attr("dvalue");
    if(xiuAppUrl==xiuAppUrlDvalue){
      $("#xiuAppUrl").val("");
    }
	
      $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/adv/updateLink?id=" + id+"&linkType=" + LinkType+"&linkObject=" + LinkObject +"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert("更新成功");
							   location.reload();
			            	}else{
			            	    alert("更新失败");
			            	}
						}
					}
				},
				error : function(data) {
		       }
		       });
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
							   location.reload();
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
	 
function checkUrl(){
  var isSuccess=true;
 $(".linkObjectMsg").html("");
  var linkType = $('#updateLinkTable input[name="linkType"]:checked ').val();
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
    var linkType = $('#updateLinkTable input[name="linkType"]:checked ').val();
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
  var chooselinkType = $('#updateLinkTable input[name="linkType"]:checked ').val();
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
				}
			}); 
		    }else{
		       $("#linkObjectMsg"+linkType).html(params.notNulStr);
		    }
	    }
   return isSuccess;
}
	
</script>
</body>
</html>