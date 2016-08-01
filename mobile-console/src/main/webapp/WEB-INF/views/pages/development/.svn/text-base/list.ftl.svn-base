<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/brand_list.css">
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">开发者选项</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<!--主表单-->
<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/development/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 start -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>开发者选项</dd>
				<dd class="last"><h3>自助SQL查询</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
					      <label>
					          <b>SQL语句：</b></br>
					          <div style="margin-left:60px;">
					              <textarea id="sql" name="sql" style="width:1000px;height:80px;">${(sql!'')?html}</textarea>
					          </div>
					      </label>
						  <label>
						      <div style="margin-left:60px;">
						          <a href="javascript:void(0);" title="查询" class="btn" onclick="return selectList()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
						          <span class="quanbtn" id="sql_tip" style="display:none;">${(error!'')?html}</span>
						      </div>
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
					    <#if  (listmap?? && listmap?size > 0)>
					      <#list listmap as map> 
					       <#if map_index == 0>
							    <#list map?keys as key> 
				                   <th>${key}</th>
				                </#list>
			               </#if> 
					      </#list>
						</#if>
					</tr>   
				</thead>
				<tbody>
				    <#if  (listmap?? && listmap?size > 0)>
					      <#list listmap as map> 
						      <tr>
							    <#list map?keys as key> 
				                   <td>${map[key]}</td>
				                </#list>
				              </tr>  
					      </#list>
					</#if>
				</tbody>
			</table>
	</div>	
</div>
</form>
</div>
</body>
<script type="text/javascript">
   $(function(){
      var sql_tip=$("#sql_tip").text();
      if(sql_tip != ""){
        $("#sql_tip").show();
      }
   })
   
   //查询
   function selectList(){
     var sql=$("#sql").val();
     if(sql == ""){
       $("#sql_tip").text("提示：SQL语句不能为空！");
       $("#sql_tip").show();
       return false;
     }else{
       $("#sql_tip").text("");
       $("#sql_tip").hide();
     }
     
     if(sql.length < 6){
       $("#sql_tip").text("提示:请检查您的SQL语句是否规范正确！");
       $("#sql_tip").show();
       return false;
     }else{
       $("#sql_tip").text("");
       $("#sql_tip").hide();
     }
     
     $("#query").removeClass("btn").addClass("quanbtn");
	 $("#query").attr("onclick","");
	 $("#query").text("正在执行");
     $("#mainForm").submit();
   }
</script>
</html>