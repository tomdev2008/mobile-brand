<!DOCTYPE html >
<html>
<head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<script type="text/javaScript">
	var displayBar = true;
	function switchBar(o) {
		if (displayBar) {
			parent.parent.document.getElementById("leftFrame").parentNode.cols = "0,*";
			displayBar = false;
			o.src = "${rc.getContextPath()}/resources/manager/images/menu_center_open.gif";
		} else {
			parent.parent.document.getElementById("leftFrame").parentNode.cols = "180,*";
			displayBar = true;
			o.src = "${rc.getContextPath()}/resources/manager/images/menu_center_close.gif";
		}
	}
</script>
<style  type="text/css">
body {
	height: 100%;
	background: url(${rc.getContextPath()}/resources/manager/images/menu_centerbg.gif) left top repeat;
}
html {
	height: 100%
}
</style>
</head>
	<body>
	<table width="10" border="0" cellspacing="0" cellpadding="0" style="height: 100%;">
	<tr>
		<td>
		<input title="打开或关闭左侧导航"
			onclick="switchBar(this);this.blur();" type="image"
			src="${rc.getContextPath()}/resources/manager/images/menu_center_close.gif" border=0>
		</td>
	</tr>
    </table>
	</body>
</html>