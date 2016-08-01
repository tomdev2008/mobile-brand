<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
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

<form id="saveFindGoods" name="saveFindGoods">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd><a href="${rc.getContextPath()}/findgoods/list">单品发现</a></dd>
			<dd class="last"><h3>添加商品</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">添加商品</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>走秀码：</th>
				<td class="tdBox">
					<input type="text" name="goodsSn" id="goodsSn" value="" style="width:300px;" onblur="checkSn()"/>
					<span id="goodsSn_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">商品缩略图：</th>
				<td class="tdBox">
					<span id="goodsImage"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">商品名称：</th>
				<td class="tdBox">
					<span id="goodsName"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>商品发现名称：</th>
				<td class="tdBox">
					<input type="text" name="title" id="title" value="" maxlength="100" style="width:300px;"/>&nbsp;1/100字数
					<span id="title_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>编辑语：</th>
				<td class="tdBox">
					<textarea id="content" name="content" style="width:300px;height:80px;"></textarea>&nbsp;1/200字数
					<span id="content_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>展示开始时间：</th>
				<td class="tdBox">
					<input type="text" name="sDate" id="sDate" value="" style="width:300px;" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'eDate\')}'})"/>
					<span id="sDate_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>展示结束时间：</th>
				<td class="tdBox">
					<input type="text" name="eDate" id="eDate" value="" style="width:300px;" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'sDate\')}'})"/>
					<span id="eDate_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">排序：</th>
				<td class="tdBox">
					<input type="text" name="orderSequence" id="orderSequence" value="" maxlength="5" style="width:300px;" />
					<span id="orderSequence_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">人工人气数：</th>
				<td class="tdBox">
					<input type="text" name="hotIncNum" id="hotIncNum" value="0" maxlength="5" style="width:300px;" />
					<span id="hotIncNum_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>标签：</th>
				<td class="tdBox">
					<ul class="sku_ul">
						<li class="show_li">
							<select id="labelName">
								<option value="" >-请选择-</option>
								<#if (listLabel?? && listLabel?size > 0)>
			                     <#list listLabel as label>
				                 <option value="#{label.labelId!''}" > ${label.title!''} </option>
				                </#list>
				                </#if>
							</select>
							<input class=" userOperBtn" type="button" value="删除" onclick="delLabelItem(this)">
						</li>
					</ul>
					<a id="add_sku" class="btns_2 block" onclick="addLabels()">+ 添加标签</a>
					<input type="hidden" name="labelId" value="" class="subject_label"/>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return check();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="return returnGoodsList();"class="ml10"><img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
					<span id="error_c" style="color: red"></span>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
</div>
</body>
<script type="text/javascript">
	var flag = false; // 标记goodsSn是否存在
	// 验证goodsSn
	function checkSn(){
		var sn = $("#goodsSn").val();
		if(sn != ""){
			$.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/findgoods/checkGoodsSn?goodsSn=" + sn + "&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0"){
								flag = true;
								$("#goodsSn_c").text(objs.data);
								// 设置商品图片
								var goodsImage = "http://images.xiustatic.com/upload/goods" + objs.data.strDate + "/" +
													objs.data.goodsSn + "/" + objs.data.mainSku + "/g1_100_100.jpg";
								var con = "<img src=" + goodsImage + " border='0'/>";
								$("#goodsImage").html(con);
								// 设置商品名称
								$("#goodsName").html(objs.data.goodsName);
			            	}else{
			            		flag = false;
			            		$("#goodsSn_c").text(objs.data);
			            		$("#goodsImage").html("");
			            		$("#goodsName").html("");
			            	}
						}
					}
				},
				error : function(data) {
					alert(data);
				}
			});
		}
	}

    //验证信息
    function check(){
      var title=$("#title").val();
      if(title==""){
        $("#title_c").text("商品发现名称不能为空！");
        return false;
      }else{
      	if(title.length > 100){
      		$("#title_c").text("商品发现名称不能超过100个字！");
        	return false;
      	}
        $("#title_c").text("");
      }
      
      var goodsSn=$("#goodsSn").val();
      if(goodsSn==""){
        $("#goodsSn_c").text("走秀码不能为空！");
        return false;
      }else{
        $("#goodsSn_c").text("");
      }
      
      var content=$("#content").val();
      if(content==""){
        $("#content_c").text("编辑语不能为空！");
        return false;
      }else{
      	if(content.length > 200){
      		$("#content_c").text("编辑语太长，不能超过200个字！");
        	return false;
      	}
        $("#content_c").text("");
      }
      
      var sDate=$("#sDate").val();
      if(sDate==""){
        $("#sDate_c").text("请选择展示开始时间！");
        return false;
      }else{
        $("#sDate_c").text("");
      }
      
      var eDate=$("#eDate").val();
      if(eDate==""){
        $("#eDate_c").text("请选择展示结束时间！");
        return false;
      }else{
        $("#eDate_c").text("");
      }
      
      var orderSequence=$("#orderSequence").val();
      if(orderSequence != ""){
        if(isNaN(orderSequence)){
            $("#orderSequence_c").text("排序只能是数字!");
            return false;
          }else{
            $("#orderSequence_c").text("");
          }
      }
      
      var hotIncNum = $("#hotIncNum").val();
      if(hotIncNum != ""){
        if(isNaN(hotIncNum)){
            $("#hotIncNum_c").text("只能是数字!");
            return false;
          }else{
            $("#hotIncNum_c").text("");
          }
      }
       //标签处理
	   	var labelnames=$(".show_li");
	    var label_i=labelnames.length;
	    var skuArr=new Array();
    	for(var i=0;i<label_i;i++){
    		var _target=labelnames.eq(i);
    		var labelId=_target.find("#labelName").val();
    		if(labelId!=''){
    			skuArr[i]=labelId;
    		}
    	}
    	$(".subject_label").val(skuArr);
      if(flag){
	      //提交信息 
	      save();
      }else{
		$("#goodsSn_c").text("该走秀码不存在！");
      }
    }
    
    //用Ajax提交信息
	function save(){
	    var params=$("#saveFindGoods").serialize();
	    $.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/findgoods/save?format=json",
					data : params,
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //保存成后,刷新列表页
								   location.href = "${rc.getContextPath()}/findgoods/list";
								}else{
								  $("#error_c").text(objs.data);
				            	}
							}
						}
					},
					error : function(data) {
						//showErrorMsg(true,data);
					}
			});
	}
	
	function del(id){
		if(confirm("确实要删除该记录吗？")== true){
		    $.ajax({
				type : "POST",
				url : "${rc.getContextPath()}/findgoods/delete?id=" + id + "&format=json",
				data : params,
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
	
	//取消事件，返回单品发现管理列表
	function returnGoodsList(){
	  location.href = "${rc.getContextPath()}/findgoods/list";
	}
//添加标签
function addLabels(){
	var labelnames=$(".show_li");
	var label_i=labelnames.length;
	var sku_html = "";
	sku_html = sku_html + "<li class='show_li'>";
	sku_html = sku_html + "<select id='labelName'><option value='' >-请选择-</option><#if (listLabel?? && listLabel?size > 0)><#list listLabel as label><option value='#{label.labelId!''}' > ${label.title!''} </option> </#list></#if></select>";
	sku_html = sku_html + "<input class='userOperBtn' type='button' value='删除' onclick='delLabelItem(this)'>";
	sku_html = sku_html + "</li>";
	$(".sku_ul").append(sku_html);
}
//删除标签
function delLabelItem(obj){
  var parentDivs=$(obj).parents(".show_li");
  var parentDiv=$(parentDivs[0]);
  $(parentDiv).remove();
}
</script>
</html>