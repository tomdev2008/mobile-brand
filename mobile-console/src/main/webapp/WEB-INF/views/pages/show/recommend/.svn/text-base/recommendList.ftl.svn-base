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

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/showRecommend/findRecommendList" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/show/show_main">走秀后台管理</a></dd>
				<dd>秀客管理</dd>
				<dd class="last"><h3>发现推荐列表 </h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
					      <label>秀(秀集合)ID：&nbsp; 
					          <input name="showId" type="text" id="showId" value="<#if showId??>${showId}</#if>" size="20"  />
					          &nbsp;
					      </label>
					      <label>显示：
					         <select name="status" id="showStatus" style="width:120px;">
					           <option value="" >全部</option>
					              <option value="0" <#if status??&&status=='0'>selected="selected"</#if>>隐藏</option>
					              <option value="1" <#if status??&&status=='1'>selected="selected"</#if>>显示</option>
					          </select>
					          &nbsp;
					      </label>
					      <label>类型：
					         <select name="recommendType" id="recommendType" style="width:120px;">
					           <option value="" >全部</option>
					              <option value="1" <#if recommendType??&&recommendType=='1'>selected="selected"</#if>>秀</option>
					              <option value="2" <#if recommendType??&&recommendType=='2'>selected="selected"</#if>>秀集合</option>
					          </select>
					          &nbsp;
					      </label>
					      <label>关注推荐时间：
					         <input name="beginTime" type="text" id="beginTime" value="<#if beginTime??>${beginTime}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
					          ---
					         <input name="endTime" type="text" id="endTime" value="<#if endTime??>${endTime}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
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
						<th width="7%">ID</th>
						<th width="10%">类型</th>
						<th width="30%">推荐时间</th>
						<th width="15%">秀集合名称</th>
						<th width="7%">排序值</th>
						<th width="10%">状态</th>
						<th width="17%">操作</th>
					</tr>
				</thead>
				<tbody>
				   <#if (recommendList?size > 0)>
				   <#list recommendList as show>
				      <tr>
				        <td>
				        <input type="checkbox" name="checkboxId" value="${show.id!''}" />
				        </td> 
						<td> <a href="#" onclick="openShowInfo(${show.id!''})">${show.id!''} </a></td>
						<td>
						 <#if  show.recommendType??&&show.recommendType == 1>秀
						 <#elseif  show.recommendType??&&show.recommendType == 2>秀集合
						 </#if>
						</td>
						<td>${show.recommendBeginTime!''}</td>
						<td>${show.showTitle!''}</td>
						<td style="cursor:pointer">
						<span><input style="width:44px;" value="${show.orderSeq!''}" onblur="updateOrder(this,${show.id!''},${show.orderSeq!''});"/></span>
						</td>
						<td>		
						<a href="javascript:void(0);" onclick="isDisplay(${show.status},${show.id})">
							 <#if  show.status??&&show.status == 1>显示
							 <#elseif  show.status??&&show.status == 0>隐藏
							 </#if>
						 </a>
						</td>
					 	<td>
						  <a href="javascript:void(0);" onclick="showUpdateTimePannel(this)"
						   showId="${show.id!''}" 
						   beginTime="${show.recommendBeginTime!''}" 
						   endTime="${show.recommendEndTime!''}" 
						   orderSeq="${show.orderSeq!''}" 
						   title="修改推荐时间">修改推荐时间
						   </a> 
						</td>
					 </tr>	
					 <tr class="tr_bottomborder">
					    <#if show.recommendType?? && show.recommendType==1>
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
					              <img src="${picture.originalPicUrl!''}"/>
					            </li>
					         </#list>
					         </ul>
					         
					         <div class="showContent">${show.content!''}</div>
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
					    <#elseif  show.recommendType??&&show.recommendType == 2>
					    <td>
					     <td colspan="5">
					    	<div class="pictureTdDiv">
						     <img class ="pictureShowImg" src="
								   ${show.originalThumbnailUrl!''}
						      "/>
					         </div>
					        </div>
					    </td>
					    </#if>
					  </tr>
				   </#list>
				   <#else>
				     <tr><td colspan="9"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table><#if page.totalCount gt 0 >
			<div class="w mt20 clearfix">
				<p class="fl">
					  <a id="link" onclick="deleteAll()"><img id="imageId" src="${rc.getContextPath()}/resources/manager/images/button_del22.gif"></a>
				</p>
			</div>
		</#if>
	</div>	
</div>
 <!-- 分页文件-->
   <#include "/common/page.ftl">  
</form>

<div id="updateShowDiv" class="none">
<input id="updateShowId" type="hidden"/>
		   <h3> 秀ID：</h3>
		<div>
          <span id="updateShowIdStr"></span>
		</div>
      <h3>  推荐时间：</h3>
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
	
	function showUpdateTimePannel(obj){
	   var arg={
	     id: "updateShowDiv",
	     title:"推荐时间修改",
	     height:250,
	     width:400
	    }
	   showPanel(arg);
						   
	   var showId=$(obj).attr("showId");
	   var beginTime=$(obj).attr("beginTime");
	   var orderSeq=$(obj).attr("orderSeq");
	   
	   $("#updateShowIdStr").html(showId);
	   $("#updateShowId").val(showId);
	   $("#updateBeginTime").val(beginTime);
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
			url : "${rc.getContextPath()}/show/updateRecommendTime?showIds="+showIds+"&beginTime=" + beginTime+"&channel=2&orderSeq="+orderSeq + "&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0")
						{
							alert("修改成功");
							window.location.reload();
						}else{
						  alert(objs.smsg);
		            	}
					}
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
   //显示隐藏
   function isDisplay(status,id){
   		if(status==1){
   			status=0;
   		}else if(status==0){
   			status=1;
   		}
   		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/showRecommend/updateDisplay?showId="+id+"&status=" + status+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0")
						{
							window.location.reload();
						}else{
						  alert(objs.smsg);
		            	}
					}
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
   }
   function updateOrder(obj,showId,orderSeq){
   	var order=$(obj).val();
   	if(order==""){
   		alert("排序值不能为空");
   		return ;
   	}
   	 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/showRecommend/updateOrderSeq?showId="+showId+"&orderSeq=" + order+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0")
						{
							window.location.reload();
						}else{
						  alert(objs.smsg);
		            	}
					}
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
   }
   //批量删除秀推荐
   function deleteAll(){
	   	var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		if(ids != ""){
			if(confirm("确定要删除吗？")== true){
			    $.ajax({
					type : "GET",
					url : "${rc.getContextPath()}/showRecommend/deleteRecommendShow?ids=" + ids + "&format=json",
		            dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
									alert("删除成功");
									window.location.reload();
								}else{
								  alert(objs.smsg);
				            	}
							}
						}
					},
					error : function(data) {
						//showErrorMsg(true,data);
					}
				});
			}
		}else{
			if($.trim(ids) == ""){
				alert("请选择要删除的列表");
			}
		}
   }
</script>

</html>