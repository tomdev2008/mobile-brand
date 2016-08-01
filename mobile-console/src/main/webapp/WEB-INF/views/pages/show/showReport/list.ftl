<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">

</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">菜单管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/showReport/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/report/report_main">走秀后台管理</a></dd>
				<dd>秀客管理</dd>
				<dd class="last"><h3>秀举报管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
                	<label>发布人：
			          <input name="reportedUserName" type="text" id="reportedUserName" value="<#if reportedUserName??>${reportedUserName}</#if>" size="20"  />
			          &nbsp;
			      </label>
			      <label>举报时间：
			         <input name="reportStartDate" type="text" id="reportStartDate" value="<#if reportStartDate??>${reportStartDate}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
			          ---
			          <input name="reportEndDate" type="text" id="reportEndDate" value="<#if reportEndDate??>${reportEndDate}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
			      </label>
			       <label>&nbsp;秀ID：
			          <input name="showId" type="text" id="showId" value="<#if showId??>${showId}</#if>" size="20"  />
			          &nbsp;
			      </label>
				</div>
                <div class="wapbt" style="border-bottom:0px;" align="left">
                	<label>处理人：
			          <input name="handleUserName" type="text" id="handleUserName" value="<#if handleUserName??>${handleUserName}</#if>" size="20"  />
			          &nbsp;
			        </label>
				      <label>处理时间：
				         <input name="handleStartTime" type="text" id="handleStartTime" value="<#if handleStartTime??>${handleStartTime}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				          ---
				          <input name="handleEndTime" type="text" id="handleEndTime" value="<#if handleEndTime??>${handleEndTime}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				      </label>
				      <label>&nbsp;处理状态:
				          <select name="handleStatus" id="handleStatus">
				           <option value="" >全部</option>
				              <option value="0" <#if handleStatus??&&handleStatus=='0'>selected="selected"</#if>>未处理</option>
				              <option value="2" <#if handleStatus??&&handleStatus=='2'>selected="selected"</#if>>已忽略</option>
				              <option value="1" <#if handleStatus??&&handleStatus=='1'>selected="selected"</#if>>已核实</option>
				          </select> 
				          &nbsp;&nbsp;&nbsp; 
				       </label>
					 <label>
					    <a href="javascript:void(0);" title="查询" class="btn" onclick="return submitSeachForm()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
					 </label>
				</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<input type="hidden" name="reportedNumOrder"  class="orderInput" id="reportedNumOrder" value="${reportedNumOrder!''}" />
		<div class="adminContent clearfix">
			<table width="100%" class="table_bg01 table_hg01 table_show">
				<thead>
					<tr>
						<th width="4%"><input type="checkbox" id="SelectAll" name="SelectAll" value="" onclick="selectAll();"/>全选</th>
						<th width="7%">秀ID</th>
						<th width="10%">秀发布人</th>
						<th width="10%">秀状态</th>
						<th width="10%">举报时间</th>
						<th width="15%">举报原因</th>
						<th width="10%">被举报次数 <span class="" id="reportedNumOrderBut"></span></th>
						<th width="7%">处理状态</th>
						<th width="10%">处理时间</th>
						<th width="7%">处理人</th>
						<th width="13%">执行操作</th>
					</tr>
				</thead>
				<tbody>
				   <#if (reportlist?size > 0)>
				   <#list reportlist as report>
				      <tr>
				        <td>
				        <#if report.handleStatus??&&report.handleStatus==0>
				        <input type="checkbox" name="checkboxId" value="${report.objectId!''}" />
				        <#else><input type="checkbox" disabled="true" value="" name="checkboxId">
				        </#if>
				        </td> 
						<td><a href="#" onclick="openShowInfo(${report.objectId!''})">${report.objectId!''}</a></td>
						<td><a href="#" onclick="openUserInfo(${report.reportedUserId!''})">${report.objectUserName!''}</a></td>
						<td>						  
						 <#if  report.objectStatus??&&report.objectStatus == 0>正常
						 <#elseif  report.objectStatus??&&report.objectStatus == 1>用户删除
						 <#elseif  report.objectStatus??&&report.objectStatus == 2>前台管理员删除
						 <#elseif  report.objectStatus??&&report.objectStatus == 3>后台删除
						 </#if>
					 </td>
						<td>${report.reportTime?string('yyyy-MM-dd HH:mm:ss')}</td>
						<td>${report.reportReason!''}</td>
						<td>${report.reportNum!''}</td>
						<td>						  
						 <#if  report.handleStatus??&&report.handleStatus == 0>未处理
						 <#elseif  report.handleStatus??&&report.handleStatus == 1>已核实
						 <#elseif  report.handleStatus??&&report.handleStatus == 2>已忽略
						 </#if>
					 </td>
						<td>
						<#if report.handleTime??>${report.handleTime?string('yyyy-MM-dd HH:mm:ss')} 
						 <#else>--
						</#if>
						</td>
						<td>						
						<#if report.handleUserName??>${report.handleUserName!''}
						 <#else>--
						</#if>
						</td>
						<td>
						  <a href="javascript:void(0);" onclick="viewReport('${report.objectId}');" title="详情">详情</a>
						    <#if report.handleStatus??&&report.handleStatus==0>
						  |<a href="javascript:void(0);" onclick="ignoreReport('${report.objectId}')" title="忽略">忽略</a>
						   <#else>|<span class="gray">忽略</span>
						  </#if>
						   <#if report.handleStatus??&&report.handleStatus!=1>
						  |<a href="javascript:void(0);" onclick="showComfigReport('${report.objectId}')" title="删除">核实</a> 
						   <#else>|<span class="gray">核实</span>
						  </#if>
						</td>
					 </tr>	
					 <tr class="tr_bottomborder">
					    <td colspan="6">
					     <div class="pictureTdDiv">
					     <img class ="pictureShowImg" src="
					       <#list report.pictureList as picture>
					           <#if picture_index == 0>
							   ${picture.originalPicUrl!''}
							     </#if>
					         </#list>
					      "/>
					         <ul class="pictureUl">
					         <#list report.pictureList as picture>
					           <li pictureUrl=" ${picture.originalPicUrl!''}"  <#if picture_index == 0> class="choose"  </#if>>
					              <img src=" ${picture.originalPicUrl!''}"/>
					            </li>
					         </#list>
					         </ul>
					         
					         <div class="showContent">${report.objectContent!''}</div>
					         <div style="clear:both"></div>
					             </div>
                          </td>
                            <td colspan="4">
					         <div class="tagsdiv">
					         <ul class="tagTypeUl">
					         <li>
					         <span class="tagTypeName">品牌标签:</span>
					         <ul class="tagsUl">
					         <#list report.pictureList as picture>
							     <#list picture.tagList as tagobj>
							        <#if tagobj.type??&&tagobj.type==2> <li>  ${tagobj.name!''}</li></#if>
							      </#list>
					         </#list>
					          </ul>
					          </li>
					          
					         <li>
					         <span class="tagTypeName">商品标签:</span>
					         <ul class="tagsUl">
					         <#list report.pictureList as picture>
							     <#list picture.tagList as tagobj>
							        <#if tagobj.type??&&tagobj.type==3> <li>  ${tagobj.name!''}</li></#if>
							      </#list>
					         </#list>
					          </ul>
					          </li>
					         <li>
					         <span class="tagTypeName">自定义标签:</span>
					         <ul class="tagsUl">
					         <#list report.pictureList as picture>
							     <#list picture.tagList as tagobj>
							        <#if tagobj.type??&&tagobj.type==1> <li>  ${tagobj.name!''}</li></#if>
							      </#list>
					         </#list>
					          </ul>
					          </li>

					          </ul>
					         <div style="clear:both"></div>
					         </div>
					     </div>
					    </td>
					  </tr>
				   </#list>
				   <#else>
				     <tr><td colspan="10"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table>
			<#if page.totalCount gt 0 >
				<div class="w mt20 clearfix">
					<p class="fl">
						 <input value="忽略" class="userOperBtn panelBtn" onclick="ignoreBatch()" type="button" onclick="comfigReport()"/>
						 <input value="核实" class="userOperBtn panelBtn" onclick="comfigBatch()" type="button" onclick="comfigReport()"/>
					</p>
				</div>
			</#if>
	</div>	
</div>
 <!-- 分页文件-->
   <#include "/common/page.ftl">  
</form>

<div id="comfigReportDiv" class="none">
<input id="comfingShowId" type="hidden"/>
		<h3>该秀为不合格内容，将删除该秀吗？</h3>
		<div>删除原因:
		<select id="resultTypeSelect">
		   <option value="1">营销广告</option>
		   <option value="2">淫秽色情</option>
		   <option value="3">虚假信息</option>
		   <option value="4">政治敏感</option>
		   <option value="5">其他</option>
		</select>
		</div>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="comfigReport()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

</div>
</body>
<script type="text/javascript">
   	
   $(function(){ 
               initShowImg();
               initOrderImg("reportedNumOrderBut","reportedNumOrder");
	}); 
   	
   	 function openShowInfo(showId){
      var pageUrl = "${rc.getContextPath()}/show/showInfo?showId="+showId;
	  openPannel(pageUrl);
   }
   	
   	 function openUserInfo(userId){
		var pageUrl = "${rc.getContextPath()}/userManager/userDetail?userId="+userId+"&userPageType=user";
		 openPannel(pageUrl);
}
   	
	function viewReport(showId) {
		var pageUrl = "${rc.getContextPath()}/showReport/info?showId="+showId;
		 openUrl(pageUrl);
	}
   
   function ignoreReport(showid){
		if(confirm("确实要忽略该举报吗？")== true){
			 updateStutas(showid,2,"");  
		}
	}
	
	
	//弹出框显示核实
	function showComfigReport(showId){
	    var arg={
	     id: "comfigReportDiv",
	     title:"核实举报",
	     height:200,
	     width:300
	    }
	   showPanel(arg);
       $("#comfingShowId").val(showId);
  }
  
  
  function updateStutas(showid,status,resultTypeSelect){
	   $.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/showReport/updateStatus?showIds=" + showid+"&status="+status+"&resultType="+resultTypeSelect + "&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
								window.location.reload();
							}else{
							  alert(objs.data);
			            	}
						}
					}
				},
				error : function(data) {
					//showErrorMsg(true,data);
				}
			});
  }
  
   function comfigReport(){
     var showid=$("#comfingShowId").val();
     var resultTypeSelect=$("#resultTypeSelect").val();
	 updateStutas(showid,1,resultTypeSelect);  
  }


	function ignoreBatch(){
		var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		if(ids != ""){
			if(confirm("确实要忽略吗？")== true){
			   	 updateStutas(ids,2,"");   
			 }
		}else{
				alert("请选择要忽略的秀");
		}
	}
  
	function comfigBatch(){
	  var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		if(ids==""){
			alert("请选择要核实的秀");
			 return ;
		}
		 var arg={
	     id: "comfigReportDiv",
	     title:"核实举报",
	     height:200,
	     width:300
	    }
	   showPanel(arg);
       $("#comfingShowId").val(ids);
	}
   
</script>

</html>