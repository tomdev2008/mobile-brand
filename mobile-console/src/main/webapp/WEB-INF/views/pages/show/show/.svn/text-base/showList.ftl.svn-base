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
<style type="text/css">
.showsearchBar{
  width: 74%;
  float: left;
}
.table_bg05.thsubject{
		font-weight: bold;
	}
	.tdsubject{
		padding: 5px 6px;
	}
	.tdSelect{
		width:260px;
		margin-left: -117px;
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
			<h3 class="topTitle fb f14">菜单管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/show/showList" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/show/show_main">走秀后台管理</a></dd>
				<dd>秀客管理</dd>
				<dd class="last"><h3>秀列表 </h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
		        <div style="  float: left;width:auto;">
		        	<table>
		        	  <tr><td valign="top">秀ID：</td>
		        	       <td><textarea  name="showIds" id="showIds" style="height: 55px;" ><#if showIds??>${showIds}</#if></textarea>&nbsp;</td>
					    </tr>
					   </table> 
		        </div>
                <div class="wapbt showsearchBar" style="border-bottom:0px;" align="left">
			      <label>品牌标识：
			          <input name="brandTag" type="text" id="brandTag" value="<#if brandTag??>${brandTag}</#if>" size="10"  />
			          &nbsp;
			      </label>
			      <label>发布人：
			          <input name="showUserName" type="text" id="showUserName" value="<#if showUserName??>${showUserName}</#if>" size="10"  />
			          &nbsp;
			      </label>
			        <label>审核状态:
		          <select name="checkStatus" id="checkStatus">
		               <option value="" >全部</option>
		              <option value="0" <#if checkStatus??&&checkStatus=='0'>selected="selected"</#if>>未审核</option>
		              <option value="1" <#if checkStatus??&&checkStatus=='1'>selected="selected"</#if>>审核通过</option>
		              <option value="2" <#if checkStatus??&&checkStatus=='2'>selected="selected"</#if>>审核不通过</option>
		          </select> 
		           <label>推荐：
			            <select name="isRecommend" id="isRecommend">
			           <option value="" >全部</option>
			              <option value="3" <#if isRecommend??&&isRecommend=='3'>selected="selected"</#if>>推荐关注</option>
			              <option value="2" <#if isRecommend??&&isRecommend=='2'>selected="selected"</#if>>推荐发现</option>
			          </select>
			      </label>
			      <label>商品标识：
			          <input name="itemTag" type="text" id="itemTag" value="<#if itemTag??>${itemTag}</#if>" size="10"  />
			          &nbsp;
			      	</label>
				</div>
                <div class="wapbt showsearchBar" style="border-bottom:0px;" align="left">
				   <label>发布时间：
				    	<input name="beginTime" type="text" id="beginTime" value="<#if beginTime??>${beginTime}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				    	-
				    	<input name="endTime" type="text" id="endTime" value="<#if endTime??>${endTime}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				    </label>
				    <label>精选状态:
			            <select name="isSelection" id="isSelection">
			           <option value="" >全部</option>
			              <option value="0" <#if isSelection??&&isSelection=='0'>selected="selected"</#if>>否</option>
			              <option value="1" <#if isSelection??&&isSelection=='1'>selected="selected"</#if>>是</option>
			          </select>
			      </label>
			      <label>话题标题：
			          <input name="topicTitle" type="text" id="topicTitle" value="<#if topicTitle??>${topicTitle}</#if>" size="18"  />
			          &nbsp;
			      </label>
			      <label>是否有商品:
			            <select name="goodType" id="goodType">
			           <option value="" >全部</option>
			              <option value="1" <#if goodType??&&goodType=='1'>selected="selected"</#if>>否</option>
			              <option value="3" <#if goodType??&&goodType=='3'>selected="selected"</#if>>是</option>
			          </select>
			      </label>
			       </div>
			       <div class="wapbt showsearchBar" style="border-bottom:0px;" align="left">
			     <label>标签名称：
					<input name="labelName" type="text" id="labelName" value="${labelName!''}" size="12" />
		        </label>
					 <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    <a href="javascript:void(0);" title="查询" class="btn" onclick="return submitSeachForm()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
					    <a href="javascript:void(0);" title="发布秀" class="btn" onclick="addShow()" style="width:50px;text-align:center;height:22px;" id="query">发布秀</a>
					 </label>
				</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<div class="adminContent clearfix">
		<input type="hidden" name="praiseNumOrder" class="orderInput" id="praiseNumOrder"  value="${praiseNumOrder!''}" />
		<input type="hidden" name="commentNumOrder"  class="orderInput" id="commentNumOrder"value="${commentNumOrder!''}" />
		<input type="hidden" name="shareNumOrder"  class="orderInput" id="shareNumOrder"  value="${shareNumOrder!''}" />
		<input type="hidden" name="reportedNumOrder"  class="orderInput" id="reportedNumOrder" value="${reportedNumOrder!''}" />
			<table width="100%" class="table_bg01 table_hg01 table_show">
				<thead>
					<tr>
						<th width="4%"><input type="checkbox" id="SelectAll" name="SelectAll" value="" onclick="selectAll();"/>全选</th>
						<th width="6%">秀ID</th>
						<th width="10%">话题标题</th>
						<th width="4%">点击数</th>
						<th width="4%">收藏数</th>
						<th width="5%">被赞数 <span class="" id="praiseNumOrderBut"></span> </th>
						<th width="5%">评论数 <span class="" id="commentNumOrderBut"></span></th>
						<th width="5%">分享数 <span class="" id="shareNumOrderBut"></span></th>
						<th width="10%">发布时间</th>
						<th width="7%">发布人</th>
						<th width="5%">秀状态</th>
						<th width="6%">审核状态</th>
						<th width="6%">推荐到关注</th>
						<th width="6%">推荐到发现</th>
						<th width="5%">精选状态</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody>
				   <#if (showlist?? && showlist?size > 0)>
				   <#list showlist as show>
				      <tr>
				        <td>
				        <#if show.deleteFlag??&&show.deleteFlag==0>
				        <input type="checkbox" name="checkboxId" value="${show.id!''}" checkFlag="${show.checkFlag!''}"/>
				        <#else><input type="checkbox" disabled="true" value="" name="checkboxId">
				        </#if>
				        </td> 
						<td><a href="#" onclick="openShowInfo(${show.id!''})">${show.id!''} </a></td>
						<td>
						 <#if show.topicId??>
						   ${show.topicTitle!''}
						   <#else>--
						 </#if>
						</td>
						<td>${show.browseNum!''}</td>
						<td>${show.collectNum!''}</td>
						<td>${show.praisedNum!''}</td>
						<td>${show.commentNum!''}</td>
						<td>${show.shareNum!''}</td>
						<td>${show.publishTime!''}  </td>
						<td><a href="#" onclick="openUserInfo(${show.userId!''})" >${show.userName!''}</a></td>
						<td>
						 <#if  show.deleteFlag??&&show.deleteFlag == 0>正常
						 <#elseif  show.deleteFlag??&&show.deleteFlag == 1>用户删除
						 <#elseif  show.deleteFlag??&&show.deleteFlag == 2>前台管理员删除
						 <#elseif  show.deleteFlag??&&show.deleteFlag == 3>后台删除
						 </#if>
						</td>
						<td>
						 <#if  show.checkFlag??&&show.checkFlag == 0>未审核
						 <#elseif  show.checkFlag??&&show.checkFlag == 1>审核通过
						 <#elseif  show.checkFlag??&&show.checkFlag ==2>审核不通过
						 </#if>
						</td>
						<td>
						 <#if  show.recommendFlag??&&show.recommendFlag == 0>否
						 <#else>是
						 </#if>
						</td>
						<td>
						 <#if  show.recommendFindFlag??&&show.recommendFindFlag == 0>否
						 <#else>是
						 </#if>
						</td>
						<td>
						 <#if  show.selectionFlag??&&show.selectionFlag == 0>否
						 <#elseif  show.selectionFlag??&&show.selectionFlag == 1>是
						 <#else>--
						 </#if>
						</td>
						<td>
						<a href="javascript:void(0);" onclick="openShowInfoUpdate(${show.id!''})"
						   title="编辑">编辑
						   </a> 
						    <#if  show.checkFlag??&&show.checkFlag == 0&&show.deleteFlag??&&show.deleteFlag == 0>
					    	<a href="javascript:void(0);" onclick="showUpdateCheckPanel(${show.id!''})"
						   title="审核">审核
						   </a> 
						    <#else>审核
						    </#if>
						   <#if show.deleteFlag??&&show.deleteFlag==0>
							 <#if show.recommendFlag??&&show.recommendFlag==0>
							  <a href="javascript:void(0);" onclick="showCecommendPanel(${show.id!''},3)"
							   title="推荐到关注">推荐到关注
							   </a>
							   <#else> 
							   <span>推荐到关注</span>
							  </#if>
							  <#if show.recommendFindFlag??&&show.recommendFindFlag==0>
							   <a href="javascript:void(0);" onclick="showRecommendFind(${show.id!''},2)"
							   title="推荐到发现">推荐到发现
							   </a>
							   <#else>
							   <span>推荐到发现</span>
							   </#if> 
							   <a href="javascript:void(0);" onclick="inertShowCollection(${show.id!''})"
							   title="加入到秀集合">加入到秀集合
							   </a>
						    <#if  show.selectionFlag??>
						     <input  type="hidden" class="selectionFlag" value="${show.selectionFlag}" /> 
						    </#if>
						     <#if  show.selectionFlag??&&show.selectionFlag == 0>
						     	<a href="javascript:void(0);" onclick="showTopicSelectionPanel(${show.id!''},${show.topicId!''},1)"
								   title="话题精选">话题精选
								</a> 
							 <#elseif  show.selectionFlag??&&show.selectionFlag == 1>
							 	<a href="javascript:void(0);" onclick="showTopicSelectionPanel(${show.id!''},${show.topicId!''},0)"
								   title="取消精选">取消精选
								</a> 
							 </#if> 
						  </#if>
						  <a href="javascript:void(0);" onclick="addComment('${show.id!''}')" title="评论">评论</a>
						   <#if show.isVisual??&&show.isVisual==1>
						   	<a href="javascript:void(0);" onclick="showVisual('${show.id}',0)" title="取消仅自己可见">取消仅自己可见</a> 
						   </#if>
						</td>
					 </tr>	
					 <tr class="tr_bottomborder">
					    <td colspan="10">
					     <div class="pictureTdDiv">
					     <img class ="pictureShowImg" 
					     src="<#list show.pictureList as picture><#if picture_index == 0>${picture.originalPicUrl!''}
							     </#if>
					         </#list>"/>
					         <ul class="pictureUl">
					         <#list show.pictureList as picture>
					           <li pictureUrl=" ${picture.originalPicUrl!''}"  <#if picture_index == 0> class="choose"  </#if>  >
					              <img src="${picture.originalPicUrl!''}"/>
					            </li>
					         </#list>
					         </ul>
					         
					         <div class="showContent">${show.content!''}</div>
					         <div style="clear:both"></div>
					             </div>
                          </td>
                            <td colspan="7">
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
				     <tr><td colspan="14"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table>
			<#if page.totalCount gt 0 >
				<div class="w mt20 clearfix">
					<p class="fl">
						 <input value="审核" class="userOperBtn panelBtn" onclick="updateShowCheckStatusBatch()" type="button" />
						 <input value="批量移入秀集合" class="userOperBtn panelBtn" onclick="inertShowCollectionAll()" type="button" />
						 <input value="删除" class="userOperBtn panelBtn" onclick="showBatchDeleteShowPannel()" type="button" />
						  <input value="仅自己可见" class="userOperBtn panelBtn" onclick="showVisualBatch(1)" type="button" />
					</p>
				</div>
			</#if>
	</div>	
</div>
<div id="addComment" class="showbox">
	<div class="centerDiv" >
	<input type="hidden" id="showId"/>
		<table width="100%" class="table_bg05">
			<tr>
				<th class="thsubject">评论用户ID</td>
				<td class="tdsubject">
					<select class="tdSelect" name="">
						<option value="">--请选择--</option>
						<#if (whiteList?? && whiteList?size > 0)>
						<#list whiteList as item>
						<option value="${item.userId!''}">${item.userId!''},${item.userName!''}</option>
						</#list>
						</#if>
					</select>
				</td>
			</tr>
			<tr>
				<th class="thsubject">评论内容</td>
				<td class="tdsubject"><textarea name="" class="commentConect" style="width:370px;height:210px;"></textarea></td>
			</tr>
			<tr>
				<th class="thsubject"></td>
				<td class="tdsubject">
					<input type="button" class="userOperBtn operBtn" value="确定" onclick="addSubjectComment()" />
					<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()"/>
				</td>
			</tr>
		</table>
	</div>
</div>
 <!-- 分页文件-->
   <#include "/common/page.ftl">  
</form>

<div id="updateBatchShowDiv" class="none">
<input id="updateShowIds" type="hidden"/>
<input id="recommendChannel" type="hidden"/>
		<h3>推荐时间：</h3>
		<div>
          <input name="updateBatchBeginTime" type="text" id="updateBatchBeginTime" value=""  maxlength="20" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
		</div>
		<br />
		<h3>排序：</h3>
		<input type="text" name="orderSeq" id="orderSeq" value="100" maxlength="20" style="width:120px;" />
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="addShowCecommendBatch()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

<div id="deleteShowDiv" class="none">
<input id="deleteShowIds" type="hidden"/>
		<h3>秀ID：</h3>
		<div id="deleteShowIdDiv" style="  overflow-x: scroll; width: 100%;height: 40px;"></div>
		<h3>确定删除选中秀？</h3>
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
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteShowAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>


<div id="updateCheckShowDiv" class="none">
<input id="updateCheckShowIds" type="hidden"/>
		<h3>秀ID：</h3>
		<div id="checkShowIdDiv" style="  overflow-x: scroll; width: 100%;height: 40px;"></div>
		<h3>审核状态：</h3>
		<div>
		  <input type="radio" name="showcheckstatus" value="2">审核不通过</input>
		 <input type="radio" name="showcheckstatus" value="1" checked="checked">审核通过</input>
		</div>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="updateShowCheckStatusAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

<div id="updateShowTopicDivBatch" class="none">
<input id="updateTopicsShowIds" type="hidden"/>
<input id="updateTopicId" type="hidden"/>
<input id="updateShowIdTopicsSelection" type="hidden"/>
		<h3 id="updateShowIdTopicsTitle"></h3>
		<h3>秀ID：</h3>
		<div id="topicShowIdsDiv" style="  overflow-x: scroll; width: 100%;height: 40px;"></div>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="updateTopicShowSelectionAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

<div id="deleteVisualDiv" class="showbox">
 <input id="commentid" type="hidden" />
 <input id="isVisual" type="hidden" />
		<h3 class="is_visual_title">确定设置为仅自己可见吗？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="updateVisualAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>
<div id="inertShowCollection" class="none">
<input id="insertShowIds" type="hidden"/>
		<h3>加入秀集合</h3>
		<div>
          秀集合ID:<input name="showCollectionId" type="text" id="showCollectionId" value=""  maxlength="20" style="width:120px;" onblur="addCollection();" />
			<span class="collectionMsg" style="color:red;"></span>
		</div>
		<div id="collectionObject">
			
		</div>		
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="addShowCollection()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>
</div>
</body>
<script type="text/javascript">
//仅自己可见弹出框显示核实
function showVisual(showId,visaul){
	var arg={
	     id: "deleteVisualDiv",
	     title:"仅自己可见确认",
	     height:200,
	     width:300
	    }
	   showPanel(arg);
	   if(visaul==0){
	   	$(".is_visual_title").html("确定取消仅自己可见吗");
	   }
       $("#commentid").val(showId);
       $("#isVisual").val(visaul);
}
//批量加入仅自己可见
function showVisualBatch(visaul){
	var ids = "";
	$(":checkbox").each(function(){
		if($(this).attr("checked")){
			ids += $(this).val() + ",";
		}
	});
	var arg={
	     id: "deleteVisualDiv",
	     title:"仅自己可见确认",
	     height:200,
	     width:300
	 }
	 showPanel(arg);
	  $("#commentid").val(ids);
	 $("#isVisual").val(visaul);
}
//仅自己可见后台请求
function updateVisualAjax(){
	var showId=$("#commentid").val();
	var isVisual=$("#isVisual").val();
	$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/show/updateShowVisual?id=" + showId +"&isVisual="+isVisual+ "&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0")
						{
							alert("设置成功");
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
 function openShowInfo(showId){
   var pageUrl = "${rc.getContextPath()}/show/showInfo?showId="+showId;
	  openUrl(pageUrl);
}
 function openShowInfoUpdate(showId){
   var pageUrl = "${rc.getContextPath()}/show/showInfoUpdate?showId="+showId;
	  openUrl(pageUrl);
}  		
 function openUserInfo(userId){
		var pageUrl = "${rc.getContextPath()}/userManager/userDetail?userId="+userId+"&userPageType=user";
	  openPannel(pageUrl);
}
   	
   $(function(){ 
           initShowImg();
           initOrderImg("praiseNumOrderBut","praiseNumOrder");
           initOrderImg("commentNumOrderBut","commentNumOrder");
           initOrderImg("shareNumOrderBut","shareNumOrder");
           initOrderImg("reportedNumOrderBut","reportedNumOrder");
	}); 
   	
   	
	
  function showDeleteShowPannel(showIds){
     var arg={
	     id: "deleteShowDiv",
	     title:"删除确定",
	     height:200,
	     width:300
	    }
	   showPanel(arg);
       $("#deleteShowIds").val(showIds);
       $("#deleteShowIdDiv").html(showIds);
  }
  
  function showBatchDeleteShowPannel(){
		var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
			    if(ids!=""){
			      ids += ",";
			    }
			    var id=$(this).val();
			    if(id!=""){
				ids += $(this).val() ;
				}
			}
		});
		if(ids != ""){
			showDeleteShowPannel(ids);
		}else{
		    alert("请选择数据");
		}
	}
  
  function deleteShowAjax(){
        var showids= $("#deleteShowIds").val();
        var resultTypeSelect= $("#resultTypeSelect").val();
	      $.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/show/deleteShowBatch?showIds=" + showids +"&resultType="+resultTypeSelect+ "&format=json",
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
  


  
	function showCecommendBatchPanel(){
	  var ids = "";
		$(":checkbox").each(function(){
		  var checkFlag=$(this).attr("checkFlag");
			if($(this).attr("checked")&&checkFlag!=2){
			    if(ids!=""){
			      ids += ",";
			    }
			    var id=$(this).val();
			    if(id!=""){
				ids += $(this).val() ;
				}
			}
		});
		if(ids==""){
		alert("请选择数据");
		 return ;
		}
		 var arg={
	     id: "updateBatchShowDiv",
	     title:"推荐时间",
	     height:200,
	     width:400
	    }
	   showPanel(arg);
       $("#updateShowIds").val(ids);
	}
	
	function addShowCecommendBatch(){
	  var showIds=$("#updateShowIds").val();
	  var channel=$("#recommendChannel").val();
	  var updateBatchBeginTime=$("#updateBatchBeginTime").val();
	  var orderSeq=$("#orderSeq").val();
	  if(updateBatchBeginTime==null||updateBatchBeginTime==""){
	    alert("请选择时间");
	    return ;
	  }
	  if(orderSeq==null||orderSeq==""){
	    alert("请选择排序号");
	    return ;
	  }
	  addShowCecommendAjax(showIds,channel,updateBatchBeginTime,orderSeq);
	}
	
	function showCecommendPanel(showId,channel){
       var arg={
	     id: "updateBatchShowDiv",
	     title:"推荐至关注",
	     height:200,
	     width:400
	    }
	   showPanel(arg);
	   $("#updateShowIds").val(showId);
	    $("#recommendChannel").val(channel);
	}
	function showRecommendFind(showId,channel){
       var arg={
	     id: "updateBatchShowDiv",
	     title:"推荐至发现",
	     height:200,
	     width:400
	    }
	   showPanel(arg);
	   $("#updateShowIds").val(showId);
	    $("#recommendChannel").val(channel);
	}
	
	function addShowCecommendAjax(showIds,channel,beginTime,orderSeq){
	      $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/show/addShowRecommendBatch?showIds="+showIds+"&channel=" + channel+"&recommendType=1&beginTime=" + beginTime+"&orderSeq="+orderSeq + "&format=json",
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
	
	
		
	function showUpdateCheckPanel(showId){
       var arg={
	     id: "updateCheckShowDiv",
	     title:"秀审核",
	     height:200,
	     width:400
	    }
	   showPanel(arg);
	   $("#updateCheckShowIds").val(showId);
	   $("#checkShowIdDiv").html(showId);
	}
	
	
	//批量审核
	 function updateShowCheckStatusBatch(){
	 var ids = "";
		$(":checkbox").each(function(){
			 var checkFlag=$(this).attr("checkFlag");
			if($(this).attr("checked")&&checkFlag!=2){
			    if(ids!=""){
			      ids += ",";
			    }
			    var id=$(this).val();
			    if(id!=""){
				ids += $(this).val() ;
				}
			}
		});
		if(ids==""){
		 alert("请选择数据");
		 return ;
		}
	  var arg={
	     id: "updateCheckShowDiv",
	     title:"秀批量审核",
	     height:200,
	     width:400
	    }
	   showPanel(arg);
	   $("#updateCheckShowIds").val(ids);
	   $("#checkShowIdDiv").html(ids);
	}
	
	//审核ajax
	function updateShowCheckStatusAjax(){
	var showIds=$("#updateCheckShowIds").val();
	var status=$("input[name='showcheckstatus']:checked").val();
	      $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/show/updateShowCheckBatch?showIds="+showIds+ "&status="+status+"&format=json",
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
	
		
		
	function showTopicSelectionPanel(showIds,topicId,flag){
       var arg={
	     id: "updateShowTopicDivBatch",
	     title:"秀话题精选修改",
	     height:200,
	     width:300
	    }
	    var title="确定要取消以下秀的话题精选?";
	    if(flag==1){
	       title="确定要把以下秀选为话题精选?";
	    }
	    $("#updateShowIdTopicsTitle").html(title);
	   showPanel(arg);
	   $("#topicShowIdsDiv").html(showIds);
	   $("#updateTopicsShowIds").val(showIds);
	   $("#updateTopicId").val(topicId);
	   $("#updateShowIdTopicsSelection").val(flag);
	}
	
	function updateTopicShowSelectionAjax(){
	  var showIds= $("#updateTopicsShowIds").val();
	  var topicId=  $("#updateTopicId").val();
	  var flag= $("#updateShowIdTopicsSelection").val();
      $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/show/updateShowTopicSelection?showIds="+showIds+ "&topicId="+topicId+"&flag="+flag+"&format=json",
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
	
   //添加评论
	function addComment(obj) {
		var div="addComment";
		var arg = {
			id:div,
			title:"添加评论",
			height:455,
			width:500
		}
		showPanel(arg);
		$("#showId").val(obj);
	}
		//评论确定
	function addSubjectComment(){
		var userId=$(".tdSelect").val();
		var content=$(".commentConect").val();
		var showId=$("#showId").val();
		if(userId=='' || userId==null){
			alert("请选择评论人ID");
			return ;
		}
		if(content=='' || content==null){
			alert("请填写评论内容");
			return ;
		}
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/showComment/addComment?showId="+showId+"&userId="+userId+"&content="+content+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs = $.parseJSON(data);
					alert(objs.smsg);
				    if(objs.scode == "0") {
				    	closeDiv();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	// 关闭浮层
	function closeDiv() {
		showPanelClose();
	}
	//跳转添加秀
	function addShow(){
		location.href="${rc.getContextPath()}/show/createUp";
	}
	//单加入秀集合
	function inertShowCollection(showId){
		 var arg={
	     id: "inertShowCollection",
	     title:"加入秀集合",
	     height:200,
	     width:400
	    }
	    $("#collectionObject").html("");
	   showPanel(arg);
	   $("#insertShowIds").val(showId);
	}
	//批量加入秀集合
	function inertShowCollectionAll(){
		var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		var arg={
	     id: "inertShowCollection",
	     title:"加入秀集合",
	     height:200,
	     width:400
	    }
	     $("#collectionObject").html("");
		 showPanel(arg);
	   $("#insertShowIds").val(ids);
	}
	//去查询秀集合ID是否存在
	function addCollection(){
		var showIds=$("#showCollectionId").val();
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/showRecommend/findShowCollectionById?showId="+showIds+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0"){
							var stml="<label>秀集合名称: </label><span id='collectionName'>"+objs.data.name+"</span>";
							$("#collectionObject").html(stml);
						}else{
						  $(".collectionMsg").html(objs.smsg);
		            	}
					}
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	//插入秀集合
	function addShowCollection(){
		var ids=$("#insertShowIds").val();
		var collectionId=$("#showCollectionId").val();
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/showRecommend/addShowCollection?ids="+ids+"&collectionId="+collectionId+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0"){
							alert(objs.smsg);
							window.location.reload();
							
						}else if(objs.scode == "2"){
						  alert("该"+objs.smsg+"秀已经添加过");
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
</script>

</html>