<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/manager/js/jquery-huadong.js" ></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_menu.css" />
<script type="text/javascript">
	$(document).ready(function() {
		$(".list").hide();
		$("h3.trigger").toggle(function() {
			$(this).addClass("sole");
		}, function() {
			$(this).removeClass("sole");
		});
		$("h3.trigger").click(function() {
			$(this).next(".list").slideToggle("slow,");
		});
	});
</script>
</head>
<body>
<body>
<div class="adminLeft">
    <#if menulist??>
     <#list menulist as menu>
          <h3 class="trigger">${menu.menuName!''}</h3>
          <#if menu.mlist??>
          <ul class="list">
                <#list menu.mlist as _menu>
                   <li><a href="${rc.getContextPath()}${_menu.menuUrl!''}" target="mainFrame">${_menu.menuName!''}</a></li>
                </#list>
          </ul>
          </#if>
        </#list>
    </#if>    
    
    
</div>
</body>
</html>