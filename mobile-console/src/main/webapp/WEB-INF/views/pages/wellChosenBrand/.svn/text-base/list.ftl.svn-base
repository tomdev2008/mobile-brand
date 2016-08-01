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
			<h3 class="topTitle fb f14">精选品牌</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/wellChosenBrand/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>活动平台</dd>
				<dd class="last"><h3>精选品牌</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
					      <label>品牌编码：
					          <input name="brandCode" type="text" id="brandCode" value="${(brandCode!'')?html}" size="10" />
					      </label>
					      <label>品牌名称：
					          <input name="brandName" type="text" id="brandName" value="${(brandName!'')?html}" size="15" />
					      </label>
					      
					      <label>创建时间：
					           <input name="startDate" id="startDate" type="text"  value="${(startDate!'')?html}"  maxlength="20" style="width:120px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd  HH:mm:ss',minDate:'#F{$dp.$D(\'endDate\')}'})" /></label>
                            <label>-- <input name="endDate" id="endDate" type="text"  value="${(endDate!'')?html}"  maxlength="20" style="width:120px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\')}'})" /></label>
                            
						    
					      <label> banner图：
					          <select name="status" id="status">
					           <option value="-1" <#if status?? && status == -1>selected="selected"</#if>>请选择</option>
					           <option value="0" <#if status?? && status == 0>selected="selected"</#if>>全部</option>
					           <option value="1" <#if status?? && status == 1>selected="selected"</#if>>有</option>
							   <option value="2" <#if status?? && status ==2>selected="selected"</#if>>无</option>
					          </select> 
					       </label>  
					      
						 <label>
						    <a href="javascript:void(0);" title="查询" class="btn" onclick="query();return false;" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
						 </label>
						       <label>
						    <a href="javascript:void(0);" title="添加" class="btn" onclick="add();return false;" style="width:50px;text-align:center;height:22px;">添&nbsp;&nbsp;加</a>
						 </label>
						 <label>
						    <a href="javascript:void(0);" title="导入品牌" class="btn" onclick="toImport();return false;" style="width:50px;text-align:center;height:22px;" >导入品牌</a>
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
						<th style="display:none">Id</th>
						<th width="5%">
						<input type="checkbox" id="allcheck"  onclick="selectedAll()" />全选
						</th>
						<th width="10%">品牌展示图片</th>
						<th width="7%">品牌编码</th>
						<th width="15%">品牌中文名称</th>
						<th width="15%">品牌英文名称</th>
						<th width="5%">排序</th>
						<th width="8%">banner图</th>
						<th width="7%">创建时间</th>
						<th width="8%">在线商品数量</th>
						<th width="6%">粉丝数</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody>
				   <#if (wellChosenBrandList?size > 0)>
				   <#list wellChosenBrandList as brand>
				      <tr>
				        <td style="display:none">${brand.id!''} </td>
				        <td valign="bottom">
						<input type="checkbox" name="deleteId" id="deleteId" value="${brand.id!''}" onclick="selectedNotAll()"/>
						</td>
				        <td>
						<div style="position: relative;">
						 <#if brand.brandImg??>    <img class="smallImg" style="width:50px;height:22px;" src="http://images.xiustatic.com/UploadFiles/xiu/brand${brand.brandImg!''}"  /> 
						  <div class="showBigImg" style="position: absolute;left: 65px;top: -100px; display:none;"><img style="width:480px;height:219px;" src="http://images.xiustatic.com/UploadFiles/xiu/brand${brand.brandImg!''}" /></div>
						   </#if>
						   </div>
						</td>
						<td>${brand.brandCode!''}</td>
						<td>${brand.brandName!''}</td>
						<td>${brand.enName!''}</td>
						<td>${brand.orderSequence!''}</td>
						 <td>
						<div style="position: relative;">
						 <#if brand.bannerPic??>    <img class="smallImg" style="width:50px;height:22px;" src="http://images.xiustatic.com${brand.bannerPic!''}"  /> 
						  <div class="showBigImg" style="position: absolute;left: 65px;top: -100px; display:none;"><img style="width:480px;height:219px;" src="http://images.xiustatic.com${brand.bannerPic!''}" /></div>
						   </#if>
						   </div>
						</td>
						<td>${brand.createDate?string('yyyy-MM-dd HH:mm:ss')}</td>
						<td>${brand.onlineGoods! ''}</td>
						<td>${brand.followNum! ''}</td>
						<td>
                          <a href="javascript:void(0);" onclick="editWellChosenBrand(${brand.id})" >编辑</a>|
						  <a href="javascript:void(0);" onclick="del(${brand.id})", '删除','center:Y');" >删除</a>
   					</td>    
			 </tr>	
				   </#list>
				   <#else>
				     <tr><td colspan="10"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table>
			<div class="w mt20 clearfix">
				<p class="fl">
					  <a id="link" onclick="deleteAll()"><img id="imageId" src="${rc.getContextPath()}/resources/manager/images/button_del22.gif"></a>
				</p>
			</div>
	</div>	
</div>
 <!-- 分页文件-->
   <#include "/common/page.ftl">  
</form>
</div>
</body>
<script type="text/javascript">
 	//添加
   	function add(){
   	 window.open ('${rc.getContextPath()}/wellChosenBrand/create', 's_blank', 'height=450, width=603, top=250, left=350, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	// location.href="${rc.getContextPath()}/wellChosenBrand/create";
   	}
   	//修改
   	function edit(id){
   	window.showModalDialog('${rc.getContextPath()}/wellChosenBrand/toUpdate?id="+id+"', '编辑精选品牌', 'dialogWidth:600px;dialogHeight:600px;dialogLeft:450px;dialogTop:150px;center:Y');
   	//location.href="${rc.getContextPath()}/wellChosenBrand/toUpdate?id="+id;
   	}
   	//导入
   	function toImport(){
	location.href="${rc.getContextPath()}/wellChosenBrand/toimport";
}
	// 显示大图
	
	$('.smallImg').hover(function(){
		$(this).siblings('.showBigImg').show();
	},function(){
		$(this).siblings('.showBigImg').hide();
	})
	
   //查询
   function query(){
   //校验查询条件
   var code=$("#brandCode").val();
   var name=$("#brandName").val();
   if(null!=name){
   		if(name.length>128){
   			alert('品牌名称字数不能超过128');
   			return;
   		}
   	}
    if(null!=code){
   		if(code.length>256){
   			alert('品牌编码字数不能超过256');
   			return;
   		}
   	}
    
     $("#query").removeClass("btn").addClass("quanbtn");
	 $("#query").attr("onclick","");
	 $("#query").text("正在查询");
     $("#mainForm").submit();
   }
   //批量删除
   function selectedAll(){
	    var checkboxs = document.getElementsByName("deleteId");
		var check = true;
		if($('#allcheck').prop("checked")==false){
			check = false;
		}else{
			check = true;
		}
		for(var i=0;i<checkboxs.length;i++){
			checkboxs[i].checked = check;			
		}
		 
  }
  function selectedNotAll(){
  	var cnt=0;
    var checkboxs = document.getElementsByName("deleteId");
    for(var i=0;i<checkboxs.length;i++){
			if(checkboxs[i].checked==true){
			cnt+=1;
			}		
		}
	if(cnt==checkboxs.length){
		$("#allcheck").attr("checked",true);
	}else{
	$("#allcheck").attr("checked",false);
	}
  }
  function deleteAll(){
	var count=0;
	var ids="";
	if(document.getElementsByName("deleteId")){
		var checkboxs = document.getElementsByName("deleteId");
		var check = true;
		
		if(typeof(checkboxs.length) == "undefined"){
			if(checkboxs.checked == true) count = 1;
		}else{
			for(var i=0;i<checkboxs.length;i++){
				if(checkboxs[i].checked == true){
				 	count = 1;
				 	ids+=checkboxs[i].value+"," ;
				}		
			}
		}
	}
	if(count==0){
		alert('请选取要批量删除的品牌!');
		return false;
	}			
	if (window.confirm("确定批量删除这些品牌吗？")){
		$.ajax({
					type : "GET",
					url : "/wellChosenBrand/deleteList?ids=" + ids + "&format=json",
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //修改成后,刷新列表页
								   location.href = "/wellChosenBrand/list";
								}else{
									alert(objs.data);
				        		 }
							}
						}
					},
					error : function(data) {
					alert('批量删除品牌异常');
					}
			});
	}
}
     //删除
   function del(id){
      if(window.confirm('您确定要删除该品牌吗?')){
			$.ajax({
					type : "GET",
					url : "/wellChosenBrand/delete?id=" + id + "&format=json",
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //修改成后,刷新列表页
								   location.href = "/wellChosenBrand/list";
								}else{
									alert(objs.data);
				        		 }
							}
						}
					},
					error : function(data) {
					alert('删除该品牌异常');
					}
			});
	  }
   }

function editWellChosenBrand(id) {
	var pageUrl = "${rc.getContextPath()}/wellChosenBrand/toUpdate?id="+id;
	var title = "编辑精选品牌";
	var parameters = "scrollbars=yes";
	var winOption = "width=600px,height=600px,left=450px,top=150px;";
	if(navigator.userAgent.indexOf("Chrome") > 0) {
		//如果是chrome浏览器
		window.open(pageUrl, title, winOption);
	} else {
		window.open(pageUrl, title, parameters);
	}
}

</script>
</html>