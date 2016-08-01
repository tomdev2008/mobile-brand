<style type="text/css">
.labelDiv{
	margin-top: 10px;
}
.labelImg{
	margin-left: -27px;
}
.labelA{
	font-size: 14px;
	cursor: pointer;
	color: #333333;
	display: inline-block;
	padding: 4px 12px;
	position: relative;
	background-color: #d3d3d3;
	margin-bottom: 8px;
}
.labelSpan{
	display: inline-block;
	 margin-top: 8px;
}
.labelList{
	width: 450px;
	margin-left: 82px;
	margin-top: -16px;
}
</style>
<script type="text/javascript">
	function labelAdd(obj){
		 $("#label").val(obj);
	}
	function labelBlur(){
		var label=$("#label").val();
		if(label!=''){
			$.ajax({
				type : "POST",
				url : "${rc.getContextPath()}/label/getLabelIdByName?format=json",
				data : {"labelName":label},
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							    var labelIds=$(".labelIds");
							    for(var i=0;i<labelIds.length;i++){
							        var val=$(labelIds[i]).val();
							        if(val==objs.data){
							            alert("该标签已经添加");
							            return;
							        }
							    }
						        var sku_html = "";
								sku_html = sku_html + "<li class='label_li'>";
								sku_html = sku_html + "<span class='labelA'>"+label+"</span>";
								sku_html = sku_html + "<input name='labelIds' class='labelIds' type='hidden' id='labelIds' value="+objs.data+" />";
								sku_html = sku_html + "<input class='userOperBtn' type='button' value='删除' onclick='delLabelItem(this)' />";
								sku_html = sku_html + "</li>";
								$(".label_ul").append(sku_html);
								$("#label").val(""); 
							}else{
							  alert(objs.data);
							}
						}
					}
				},
				error : function(data) {
					
				}
		   });
		}
	}
	function delLabelItem(obj){
	  var parentDivs=$(obj).parents(".label_li");
	  var parentDiv=$(parentDivs[0]);
	  $(parentDiv).remove();
	}
	
	
</script>
 <ul class="label_ul">
 	<#if (updateLabelList?? && updateLabelList?size > 0)>
		<#list updateLabelList as item>
	    	<li class="label_li">
	    	<span class="labelA">${item.title!''}</span>
	    	<input name="labelIds" type="hidden" class="labelIds" id="labelIds" value="${item.labelId!''}"/>
	    	<input class="userOperBtn" type="button" value="删除" onclick="delLabelItem(this)" />
			</li>
		</#list>
	</#if>
</ul>
<input name="label" id="label" list="country" style="height:23px;width: 200px;" />
<img src="${rc.getContextPath()}/resources/manager/images/member_undercarriage.gif" class="labelImg" onclick="labelBlur();"/>
<datalist id="country">
	<#if (newAddList?? && newAddList?size > 0)>
		<#list newAddList as item>
			<option value="${item.title!''}">
		</#list>
	</#if>
</datalist>
<br />
<div class="labelDiv">
<#if (historyList?? && historyList?size > 0)>
<span class="labelSpan">常用标签</span>
	<div class="labelList">
	<#list historyList as item>
		<a href="javascript:void(0);" onclick="labelAdd('${item.title!''}');" class="labelA">${item.title!''}</a>
	</#list>
	</div>
</#if>
<br />
<#if (newAddList?? && newAddList?size > 0)>
<span class="labelSpan">最新添加</span>
	<div class="labelList">
	<#list newAddList as item>
		<#if item_index<15>
			<a href="javascript:void(0);" onclick="labelAdd('${item.title!''}');" class="labelA">${item.title!''}</a>
		</#if>
	</#list>
	</div>
</#if>
</div>
