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
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">单品发现</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/findgoods/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>活动平台</dd>
				<dd class="last"><h3>单品发现</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
					      <label>商品货号：
					          <input name="goodsSn" type="text" id="goodsSn" value="${(goodsSn!'')?html}" size="10" />
					          &nbsp;&nbsp;&nbsp;
					      </label>
					      <label>商品发现名称：
					          <input name="title" type="text" id="title" value="${(title!'')?html}" size="15" />
					          &nbsp;&nbsp;&nbsp;
					      </label>
					      
					      <label>开始时间:
					      <input name="sDate" id="sDate" type="text"  value="${(sDate!'')?html}"  maxlength="11" style="width:80px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'eDate\')}'})" />
                              &nbsp;&nbsp;&nbsp;
					      </label>
					      
					      <label>结束时间:
					      <input name="eDate" id="eDate" type="text"  value="${(eDate!'')?html}"  maxlength="11" style="width:80px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'sDate\')}'})" />
                              &nbsp;&nbsp;&nbsp;
					      </label>
					      
					       <label> 状态:
					          <select name="status" id="status">
					           <option value="-1" <#if status?? && status == -1>selected="selected"</#if>>请选择状态</option>
					           <option value="0" <#if status?? && status == 0>selected="selected"</#if>>未开始</option>
					           <option value="1" <#if status?? && status == 1>selected="selected"</#if>>进行中</option>
							   <option value="2" <#if status?? && status == 2>selected="selected"</#if>>已结束</option>
					          </select> 
					          &nbsp;&nbsp;&nbsp; 
					       </label>  
						   <label>标签：
					        <select name="labelId" id="labelId" style="width:155px;">
					        	<option value="" >全部</option>
					        	<#if (listLabel??&&listLabel?size>0)>
						        	<#list listLabel as label>
						        		<option value="${label.labelId!''}" <#if labelId??&&labelId==label.labelId>selected="selected"</#if>>${label.title!''}</option>
						        	</#list>
					        	</#if>
					        </select>
					        </label>
						 <label>
						    <a href="javascript:void(0);" title="查询" class="btn" onclick="query();return fasle;" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
						 </label>
						 
						 <label>
						    <a href="javascript:void(0);" title="添加商品" class="btn" onclick="create();return false;" style="width:50px;text-align:center;height:22px;" id="query">添加商品</a>
						 </label>
						 
						 <label>
						    <a href="javascript:void(0);" title="导入商品" class="btn" onclick="toImport();return false;" style="width:50px;text-align:center;height:22px;" id="query">导入商品</a>
						 </label>

				</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<div class="adminContent clearfix">
			<table width="100%" class="table_bg01 table_hg01">
				<thead>
					<tr>
						<th width="4%"><input type="checkbox" id="SelectAll" name="SelectAll" value="" onclick="selectAll();"/>全选</th>
						<th width="11%">展示图片</th>
						<th width="6%">走秀码</th>
						<th width="10%">商品发现名称</th>
						<th width="10%">编辑语</th>
						<th width="8%">标签</th>
						<th width="4%">库存</th>
						<th width="6%">人工人气数</th>
						<th width="4%">人气数</th>
						<th width="4%">排序</th>
						<th width="5%">状态</th>
						<th width="10%">展示开始时间</th>
						<th width="10%">展示结束时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<#if (findGoodsList?? && findGoodsList?size > 0)>
					   <#list findGoodsList as goods>
					   		<tr>
					   			<td>
					   				<input type="checkbox" name="ids" value="${goods.id}"/>
					   			</td>
					   			<td>
						   			<div style="position: relative;">
									 <#if goods.goodsImage??>
										<img class="smallImg" style="width:50px;height:66px;" src="http://images.xiustatic.com/${goods.goodsImage!''}" title="商品名称：${goods.goodsName!''}" boder=0  /> 
										<div class="showBigImg" style="position: absolute;left: 65px;top: -100px; display:none;"><img style="width:162px;height:216px;" src="http://images.xiustatic.com/${goods.goodsImage!''}" /></div>
									 </#if>
									</div>
								</td>
					   			<td>${goods.goodsSn}</td>
					   			<td>
						   			${goods.title}
					   			</td>
					   			<td>
					   				<#if (goods.content?length gt 20)>
					   					<span title="${goods.content}">${goods.content[0..20]}...</span>
					   				<#else>
						   				${goods.content}
					   				</#if>
					   			</td>
					   			<td>
					   			<#if (goods.labelCentre?? && goods.labelCentre?size > 0)>
					   				<#list goods.labelCentre as label>
					   					${label.title}
					   				</#list>
					   			</#if>
					   			</td>
					   			<td>${goods.stock}</td>
					   			<td>${goods.hotIncNum}</td>
					   			<td>${goods.loveGoodsCnt + goods.hotIncNum}</td>
					   			<td>${goods.orderSequence}</td>
					   			<td>
					   				<#if goods.status == 0>
					   					未开始
					   				<#elseif goods.status == 1>
					   					进行中
					   				<#elseif goods.status == 2>
					   					已结束
					   				</#if>
					   			</td>
					   			<td>${goods.startDate?string("yyyy-MM-dd HH:mm")}</td>
					   			<td>${goods.endDate?string("yyyy-MM-dd HH:mm")}</td>
					   			<td>
					   			  <a href="javascript:;" onclick="editFindGoods(${goods.id})">编辑</a> 
					   			| <a href="javascript:;" onclick="del(${goods.id});return false;">删除</a></td>
					   		</tr>
					   </#list>
				 	<#else>
						<tr><td colspan="11"><font color="red">暂时没有查询到记录</font></td></tr>
					</#if>
				</tbody>
			</table>
			<#if page.totalCount gt 0 >
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
</div>
<script type="text/javascript">

	// 显示大图
	$('.smallImg').hover(function(){
		$(this).siblings('.showBigImg').show();
	},function(){
		$(this).siblings('.showBigImg').hide();
	})
function query(){
	$("#query").removeClass("btn").addClass("quanbtn");
	 $("#query").attr("onclick","");
	 $("#query").text("正在查询");
	 
	 // 回到第一页
	 $("#pageNo").val(1);
     $("#mainForm").submit();
}
function create(){
	location.href="${rc.getContextPath()}/findgoods/create";
}
function toImport(){
	location.href="${rc.getContextPath()}/findgoods/toimport";
}
function edit(id){
	location.href="${rc.getContextPath()}/findgoods/toedit?id=" + id;
}
function del(id){
	if(confirm("确实要删除该记录吗？")== true){
	    $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/findgoods/delete?id=" + id + "&format=json",
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
function selectAll(){
	if($("#SelectAll").attr("checked")){
        $(":checkbox").attr("checked",true);
    }else {
        $(":checkbox").attr("checked",false);
    }
}
function deleteAll(){
	var ids = "";
	$(":checkbox").each(function(){
		if($(this).attr("checked")){
			ids += $(this).val() + ",";
		}
	});
	if(ids != ""){
		if(confirm("确实要删除吗？")== true){
		    $.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/findgoods/deleteAll?ids=" + ids + "&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
								location.href="${rc.getContextPath()}/findgoods/list";
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
	}else{
		if($.trim(ids) == ""){
			alert("请选择要删除的商品");
		}
	}
}

function editFindGoods(id) {
	var pageUrl = "${rc.getContextPath()}/findgoods/toedit?id="+id;
	var title = "修改商品";
	var parameters = "scrollbars=yes";
	var winOption = "width=600px,height=650px,left=450px,top=100px;";
	if(navigator.userAgent.indexOf("Chrome") > 0) {
		//如果是chrome浏览器
		window.open(pageUrl, title, winOption);
	} else {
		window.open(pageUrl, title, parameters);
	}
}
</script>
</body>
</html>