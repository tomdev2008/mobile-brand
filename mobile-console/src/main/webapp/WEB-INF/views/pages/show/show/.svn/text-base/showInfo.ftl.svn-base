<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>


<style type="text/css">
.info_pictureUl {
  float: left;
  display: block;
  width: 100%;
  margin-left: 0;
  padding: 0;
}
.info_pictureUl li {
  margin-right: 5px;
  float: left;
  cursor: pointer;
  margin-bottom: 5px;
  height: 30px;
  width: 40px;
  border: 1px solid #ddd;
}

.info_pictureUl li img {
  width: 100%;
  height: 100%;
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
			<h3 class="topTitle fb f14">秀详情</h3>
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
			<dd>秀客管理</dd>
			<dd><a href="${rc.getContextPath()}/show/showList">秀列表</a></dd>
			<dd class="last"><h3>详情</h3>
			</dd>
		</dl>
			<#include "/pages/show/show/showInfoNav.ftl">  
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">秀详情</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>秀ID：</th>
				<td class="tdBox">
				    <input type="hidden" name="id" value="${show.id!''}"/>
					${show.id!''}
				</td>
			</tr>
			
			<tr>
				<th class="thList" scope="row">发布时间：</th>
				<td class="tdBox">
				   ${show.publishTime!''} 
				</td>
			</tr>
			
			<tr>
				<th class="thList" scope="row">发布人：</th>
				<td class="tdBox">
				  <a href="#" onclick="openUserInfo(${show.userId!''})" > ${show.userName!''}</a>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">秀内容：</th>
				<td class="tdBox">
				<div>
				 <ul class="info_pictureUl">
			         <#list show.pictureList as picture>
			           <li pictureUrl="${picture.originalPicUrl!''}">
			              <img src=" ${picture.originalPicUrl!''}"/>
			            </li>
			         </#list>
			      </ul>
					         </div>
				<textarea id="content" name="content" style="width:500px;height:100px;" >${(show.content!'')?html}</textarea>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">被赞数：</th>
				<td class="tdBox">
				   ${show.praisedNum!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">评论数：</th>
				<td class="tdBox">
					 ${show.commentNum!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">分享数：</th>
				<td class="tdBox">
					 ${show.shareNum!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">分享数：</th>
				<td class="tdBox">
					 ${show.reportedNum!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">品牌标签：</th>
				<td class="tdBox">
			        <ul class="tagsUl">
			         <#list show.pictureList as picture>
					     <#list picture.tagList as tagobj>
					        <#if tagobj.type??&&tagobj.type==2> <li>  ${tagobj.name!''}</li></#if>
					      </#list>
			         </#list>
			          </ul>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">商品标签：</th>
				<td class="tdBox">
					 <ul class="tagsUl">
					         <#list show.pictureList as picture>
							     <#list picture.tagList as tagobj>
							        <#if tagobj.type??&&tagobj.type==3> <li>  ${tagobj.name!''}</li></#if>
							      </#list>
					         </#list>
					          </ul>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">自定义标签：</th>
				<td class="tdBox">
						 <ul class="tagsUl">
					         <#list show.pictureList as picture>
							     <#list picture.tagList as tagobj>
							        <#if tagobj.type??&&tagobj.type==1> <li>  ${tagobj.name!''}</li></#if>
							      </#list>
					         </#list>
					          </ul>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">话题ID：</th>
				<td class="tdBox">
				    ${show.topicId!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">话题标题：</th>
				<td class="tdBox">
				   ${show.topicTitle!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">秀状态：</th>
				<td class="tdBox">
				     <input type="radio" disabled="disabled" name="status" <#if  show.deleteFlag??&&show.deleteFlag == 0>checked="true"</#if> /><label>正常</label>
				     <input type="radio" disabled="disabled" name="status" <#if  show.deleteFlag??&&show.deleteFlag == 1>checked="true"</#if> /><label>用户删除</label>
				     <input type="radio" disabled="disabled" name="status"    <#if  show.deleteFlag??&&show.deleteFlag ==2>checked="true"</#if> /><label>前台管理员删除</label>
				     <input type="radio" disabled="disabled" name="status"  <#if  show.deleteFlag??&&show.deleteFlag == 3>checked="true"</#if> /><label>后台删除</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">审核状态：</th>
				<td class="tdBox">
					 <input type="radio" disabled="disabled"  name="checkFlag"    <#if  show.checkFlag??&&show.checkFlag == 0>checked="true"</#if> /><label>未审核</label>
					 <input type="radio" disabled="disabled"  name="checkFlag"   <#if  show.checkFlag??&&show.checkFlag == 1>checked="true"</#if> /><label>审核通过</label>
					 <input type="radio" disabled="disabled"  name="checkFlag" <#if  show.checkFlag??&&show.checkFlag == 2>checked="true"</#if> /><label>审核不通过</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">推荐状态： </th>
				<td class="tdBox">
					<input type="radio" disabled="disabled"  name="recommendFlag" <#if  show.recommendFlag??&&show.recommendFlag == 0>checked="true"</#if> /><label>否</label>
					<input type="radio" disabled="disabled"  name="recommendFlag"   <#if  show.recommendFlag??&&show.recommendFlag == 1>checked="true"</#if> /><label>是</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"> </th>
				<td class="tdBox">
						<p class="fl">
				     <#if  show.deleteFlag??&&show.deleteFlag == 0>
					<#if show.checkFlag==0><button onclick="return showUpdateCheckPanel(${show.id!''});" class="userOperBtn">审核</button> </#if>
					</#if>
					<button onclick="return returnShowList();" class="userOperBtn">返回</button> 
					</p>
				</td>
			</tr>
		</table>
	</div>
</div>
</div>


<div id="updateBatchShowDiv" class="none">
<input id="updateShowIds" type="hidden"/>
		<h3>在首页显示时间：</h3>
		<div>
          <input name="updateBatchBeginTime" type="text" id="updateBatchBeginTime" value=""  maxlength="20" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
					          ---
		<input name="updateBatchEndTime" type="text" id="updateBatchEndTime" value=""  maxlength="20" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
		</div>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="addShowCecommendBatch()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>


<div id="deleteShowDiv" class="none">
<input id="deleteShowIds" type="hidden"/>
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
		<div>
		  <input type="radio" name="showcheckstatus" value="2">审核不通过</input>
		 <input type="radio" name="showcheckstatus" value="1" checked="checked">审核通过</input>
		</div>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="updateShowCheckStatus()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

</body>
<script type="text/javascript">

 function openUserInfo(userId){
		var pageUrl = "${rc.getContextPath()}/userManager/userDetail?userId="+userId+"&userPageType=user";
	  openPannel(pageUrl);
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
	}
  //单个审核操作
	function updateShowCheckStatus(){
	  var showid=$("#updateCheckShowIds").val();
	    updateShowCheckStatusAjax(showid);
	}
	
	//审核ajax
	function updateShowCheckStatusAjax(showIds){
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
	
	//取消事件，返回菜单管理列表
	function returnShowList(){
	  location.href = "${rc.getContextPath()}/show/showList";
	}
</script>
</html>