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
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-validate.js"></script>
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

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/show/listRecommend" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/show/show_main">走秀后台管理</a></dd>
				<dd>秀客管理</dd>
				<dd class="last"><h3>关注推荐秀列表 </h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
					      <label>秀ID：&nbsp; 
					          <input name="showId" type="text" id="showId" value="<#if showId??>${showId}</#if>" size="20"  />
					          &nbsp;
					      </label>
					      <label>秀状态：
					         <select name="showStatus" id="showStatus" style="width:120px;">
					           <option value="" >全部</option>
					              <option value="0" <#if showStatus??&&showStatus=='0'>selected="selected"</#if>>正常</option>
					              <option value="1" <#if showStatus??&&showStatus=='1'>selected="selected"</#if>>用户删除</option>
					              <option value="2" <#if showStatus??&&showStatus=='2'>selected="selected"</#if>>前台管理员删除</option>
					              <option value="3" <#if showStatus??&&showStatus=='3'>selected="selected"</#if>>后台删除</option>
					          </select>
					          &nbsp;
					      </label>
					      <label>关注推荐时间：
					         <input name="beginTime" type="text" id="beginTime" value="<#if beginTime??>${beginTime}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
					          ---
					         <input name="endTime" type="text" id="endTime" value="<#if endTime??>${endTime}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
					      </label>
				</div>
                <div class="wapbt" style="border-bottom:0px;" align="left">
				      <label>操作人：
				          <input name="operUserName" type="text" id="operUserName" value="<#if operUserName??>${operUserName}</#if>" size="20"  />
				          &nbsp;
				      </label>
				      <label>显示状态:
				          <select name="recommendStatus" id="recommendStatus" style="width:115px;">
				           <option value="" >全部</option>
				              <option value="0" <#if recommendStatus??&&recommendStatus=='0'>selected="selected"</#if>>未开始</option>
				              <option value="1" <#if recommendStatus??&&recommendStatus=='1'>selected="selected"</#if>>进行中</option>
				              <option value="2" <#if recommendStatus??&&recommendStatus=='2'>selected="selected"</#if>>已结束</option>
				          </select> 
				          &nbsp; 
				       </label>  
				      <label>修改时间：
					         <input name="updateBeginTimeStr" type="text" id="updateBeginTimeStr" value="<#if updateBeginTimeStr??>${updateBeginTimeStr}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
					          ---
					         <input name="updateEndTimeStr" type="text" id="updateEndTimeStr" value="<#if updateEndTimeStr??>${updateEndTimeStr}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
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
			<input type="hidden" name="recommendOrder" class="orderInput" id="recommendOrder"  value="${recommendOrder!''}" />
		<div class="adminContent clearfix">
			<table width="100%" class="table_bg01 table_hg01 table_show">
				<thead>
					<tr>
						<th width="4%"><input type="checkbox" id="SelectAll" name="SelectAll" value="" onclick="selectAll();"/>全选</th>
						<th width="7%">秀ID</th>
						<th width="10%">秀状态</th>
						<th width="30%">关注推荐时间</th>
						<th width="7%">排序<span class="" id="recommendOrderBut"></span> </th>
						<th width="10%">显示状态</th>
						<th width="10%">更新时间</th>
						<th width="7%">操作人</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody>
				   <#if (showlist?size > 0)>
				   <#list showlist as show>
				      <tr>
				        <td>
				        <#if show.showDeleteFlag??&&show.showDeleteFlag==0>
				        <input type="checkbox" name="checkboxId" value="${show.showId!''}" />
				        <#else><input type="checkbox" disabled="true" value="" name="checkboxId">
				        </#if>
				        </td> 
						<td> <a href="#" onclick="openShowInfo(${show.showId!''})">${show.showId!''} </a></td>
						<td>
						 <#if  show.showDeleteFlag??&&show.showDeleteFlag == 0>正常
						 <#elseif  show.showDeleteFlag??&&show.showDeleteFlag == 1>用户删除
						 <#elseif  show.showDeleteFlag??&&show.showDeleteFlag == 2>前台管理员删除
						 <#elseif  show.showDeleteFlag??&&show.showDeleteFlag == 3>后台删除
						 </#if>
						</td>
						</td>
						<td>${show.beginTime?string('yyyy-MM-dd HH:mm:ss')}  <#if show.showDeleteFlag??>至</#if>  ${show.endTime?string('yyyy-MM-dd HH:mm:ss')}</td>
						<td>${show.orderSeq!''}</td>
						<td>
						 <#if  show.showDeleteFlag??&&show.showDeleteFlag == 0>
						 <#if  show.status??&&show.status == 0>未开始
						 <#elseif  show.status??&&show.status == 1>进行中
						 <#elseif  show.status??&&show.status == 2>已结束
						 </#if>
						 <#else>--
						 </#if>
						 
						 <#if  show.showDeleteFlag??&&show.showDeleteFlag == 0>
						<#if  show.status??&&show.status == 1&&show.recommendFlag??&&show.recommendFlag==1>
						   <a href="#" onclick="updateFlag(${show.showId!''},0)">暂停</a>
						</#if>
						<#if  show.status??&&show.status == 1&&show.recommendFlag??&&show.recommendFlag==0>
						   <a href="#" onclick="updateFlag(${show.showId!''},1)">开始</a>
						</#if>
						</#if>
						</td>
						<td>${show.updateDate?string('yyyy-MM-dd HH:mm:ss')}</td>
						<td>${show.operUserName!''}</td>
						<td>
						   <#if show.showDeleteFlag??&&show.showDeleteFlag==0>
						  <a href="javascript:void(0);" onclick="showUpdateTimePannel(this)"
						   showId="${show.showId!''}" 
						   beginTime="${show.beginTime?string('yyyy-MM-dd HH:mm:ss')}" 
						   endTime="${show.endTime?string('yyyy-MM-dd HH:mm:ss')}" 
						   orderSeq="${show.orderSeq!''}" 
						   title="修改首页时间">修改关注时间
						   </a> 
						   <#else><span class="gray">修改关注时间</span>
						  </#if>
						</td>
					 </tr>	
					 <tr class="tr_bottomborder">
					    <td colspan="5">
					     <div class="pictureTdDiv">
					     <img class ="pictureShowImg" src="
					       <#list show.pictureList as picture>
					           <#if picture_index == 0>
							   ${picture.originalPicUrl!''}
							     </#if>
					         </#list>
					      "/>
					         <ul class="pictureUl">
					         <#list show.pictureList as picture>
					           <li pictureUrl=" ${picture.originalPicUrl!''}" <#if picture_index == 0> class="choose"  </#if>  >
					              <img src=" ${picture.originalPicUrl!''}"/>
					            </li>
					         </#list>
					         </ul>
					         
					         <div class="showContent">${show.showContent!''}</div>
					         <div style="clear:both"></div>
					             </div>
                          </td>
                            <td colspan="4">
					         <div class="tagsdiv">
					         <ul class="tagTypeUl">
					         <li>
					         <span class="tagTypeName">品牌标识:</span>
					         <ul class="tagsUl">
					         <#list show.pictureList as picture>
							     <#list picture.tagList as tagobj>
							        <#if tagobj.type??&&tagobj.type==2> <li>  ${tagobj.name!''}</li></#if>
							      </#list>
					         </#list>
					          </ul>
					          </li>
					          
					         <li>
					         <span class="tagTypeName">商品标识:</span>
					         <ul class="tagsUl">
					         <#list show.pictureList as picture>
							     <#list picture.tagList as tagobj>
							        <#if tagobj.type??&&tagobj.type==3> <li>  ${tagobj.name!''}</li></#if>
							      </#list>
					         </#list>
					          </ul>
					          </li>
					         <li>
					         <span class="tagTypeName">自定义标识:</span>
					         <ul class="tagsUl">
					         <#list show.pictureList as picture>
							     <#list picture.tagList as tagobj>
							        <#if tagobj.type??&&tagobj.type==1> <li>  ${tagobj.name!''}</li></#if>
							      </#list>
					         </#list>
					          </ul>
					          </li>
								<li>
							         <span class="tagTypeName">标签:</span>
							         <ul class="tagsUl">
							         <#if show.labelList??>
							         	<#list show.labelList as label>
									        <li>${label.title!''}</li>
							         </#list>
							         </#if>
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
				     <tr><td colspan="9"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table>
			<#if page.totalCount gt 0 >
				<div class="w mt20 clearfix">
					<p class="fl">
						 <input value="修改首页显示时间" class="userOperBtn panelBtn" onclick="showUpdateTimeBatchPanel()" type="button" />
					</p>
				</div>
			</#if>
	</div>	
</div>
 <!-- 分页文件-->
   <#include "/common/page.ftl">  
</form>

<div id="updateBatchShowDiv" class="none">
<input id="updateShowIds" type="hidden"/>
		<h3>在首页显示时间：</h3>
		<div>
          <input name="updateBatchBeginTime" type="text" id="updateBatchBeginTime" value=""  maxlength="20" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
					          ---
		<input name="updateBatchEndTime" type="text" id="updateBatchEndTime" value=""  maxlength="20" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
		</div>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="updateShowTimeBatch()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

<div id="updateShowDiv" class="none">
<input id="updateShowId" type="hidden"/>
		   <h3> 秀ID：</h3>
		<div>
          <span id="updateShowIdStr"></span>
		</div>
      <h3>  在首页显示时间：</h3>
		<div>
        <input name="updateBeginTime" type="text" id="updateBeginTime" value=""  maxlength="20" style="width:140px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
		</div>
		   <h3> 排序：</h3>
		<div>
         <input name="updateShowDivOrderSeq" type="text" id="updateShowDivOrderSeq" value=""  maxlength="6" style="width:120px;" />
		</div>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="updateShowTime()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

</div>
</body>
<script type="text/javascript">
   	
   	
   	 function openShowInfo(showId){
      var pageUrl = "${rc.getContextPath()}/show/showInfo?showId="+showId;
	  openPannel(pageUrl);
   }

   $(function(){ 
          initShowImg();
          initOrderImg("recommendOrderBut","recommendOrder");
	}); 
   	
   	
  
  function updateFlag(showid,status){
  var desc="开始";
  if(status==0){
    desc="暂停";
  }
   if(confirm("确实要"+desc+"该秀?")== true){
	      $.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/show/updateRecommendFlag?showId=" + showid+"&recommendFlag="+status + "&format=json",
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
  }
  


  
	function showUpdateTimeBatchPanel(){
	  var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		if(ids==""){
		alert("请选择数据");
		 return ;
		}
		 var arg={
	     id: "updateBatchShowDiv",
	     title:"首页推荐时间修改",
	     height:200,
	     width:400
	    }
	   showPanel(arg);
       $("#updateShowIds").val(ids);
	}
	
	function updateShowTimeBatch(){
	  var showIds=$("#updateShowIds").val();
	  var updateBatchBeginTime=$("#updateBatchBeginTime").val();
	  var updateBatchEndTime=$("#updateBatchEndTime").val();
	  if(updateBatchBeginTime==null||updateBatchBeginTime==""||updateBatchEndTime==null||updateBatchEndTime==""){
	    alert("请选择时间");
	    return ;
	  }
	  updateTime(showIds,updateBatchBeginTime,updateBatchEndTime,"");
	}
	
	
	function showUpdateTimePannel(obj){
	   var arg={
	     id: "updateShowDiv",
	     title:"首页推荐时间修改",
	     height:250,
	     width:400
	    }
	   showPanel(arg);
						   
	   var showId=$(obj).attr("showId");
	   var beginTime=$(obj).attr("beginTime");
	   var endTime=$(obj).attr("endTime");
	   var orderSeq=$(obj).attr("orderSeq");
	   
	   $("#updateShowIdStr").html(showId);
	   $("#updateShowId").val(showId);
	   $("#updateBeginTime").val(beginTime);
	   $("#updateEndTime").val(endTime);
	   $("#updateShowDivOrderSeq").val(orderSeq);
	}
	
    function updateShowTime(){
	  var showId=$("#updateShowId").val();
	  var updateBeginTime=$("#updateBeginTime").val();
	  var updateShowDivOrderSeq=$("#updateShowDivOrderSeq").val();
	   if(updateBeginTime==null||updateBeginTime==""){
	    alert("请选择时间");
	    return ;
	   }
	   if(updateShowDivOrderSeq==null||updateShowDivOrderSeq==""){
	    alert("请输入顺序");
	    return ;
	   }
	   if(checkIntNum(updateShowDivOrderSeq)==0){
	       alert("顺序只能为大于且等于0的整数");
	       return ;
	   }
	  updateTime(showId,updateBeginTime,updateShowDivOrderSeq);
	}
	
	
	function updateTime(showIds,beginTime,orderSeq){
	      $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/show/updateRecommendTime?showIds="+showIds+"&beginTime=" + beginTime+"&channel=3&orderSeq="+orderSeq + "&format=json",
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
   
</script>

</html>