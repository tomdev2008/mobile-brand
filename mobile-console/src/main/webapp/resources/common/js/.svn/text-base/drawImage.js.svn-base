/**
 * 保存
 */
function saveP(){
	var idPicUrl = $("#idPicUrl").val();
	if(idPicUrl==null || idPicUrl.length==0) {
		 document.getElementById("uploadtype").innerHTML = "<font color=\"red\">请先选择要上传的图片! </font>";
	     return ;
	}
	var zoomWidth = ic.Width;
	var zoomHeight = ic.Height;
	var x = jQuery("#dragDiv").css("left").replace("px","");
	var y = jQuery("#dragDiv").css("top").replace("px","");
	var width = jQuery("#dragDiv").css("width").replace("px","");
	var height = jQuery("#dragDiv").css("height").replace("px","");
	//alert("X ：" + x + " | Y ：" + y + " | width ：" + width + " | height ：" + height + " | zoomWidth ：" + zoomWidth + " | zoomHeight ：" + zoomHeight);
	if(x < 0) x = 0;
	if(y < 0) y = 0;
	$("#leftWidth").val(x);
	$("#topWidth").val(y);
	$("#dragDivWidth").val(width);
	$("#dragDivHeight").val(height);
	//获取比例
	var ratioType=$("#ratioType").val();
	var $form = $("#uploadproofFrm");
	var params = $form.serialize();
//	$("#savePicture").attr('disabled',"true");
	jQuery.ajax({
		type : "POST",
		url : "editPicture?format=json",
		data : params,
		dataType :"text",
		success : function(data, textStatus) {
			if (isNaN(data)) {
				var objs =  $.parseJSON(data);
				if (objs != null) {
					if(objs.scode == "0"){
						var path=objs.smsg;
						var pathUrl=objs.errorCode;
						var sku_html = "";
						sku_html = sku_html + "<li class='img_li'>";
						sku_html = sku_html+"<img src='"+contextPath+"/resources/manager/images/p_t_exit.gif' onclick='deleteImg(this);'/>";
						sku_html = sku_html + "<img onclick='addGoodSn(event,this);' src="+path+" width='260px;' height='270px;'/>";
						sku_html = sku_html + "<input value="+pathUrl+" id='pictures' name='pictures' type='hidden'/>";
						sku_html = sku_html + "<input value="+ratioType+" id='ratioTypes' name='ratioTypes' type='hidden'/>";
						sku_html = sku_html + "</li>";
						$("#img_ul").append(sku_html);
					//	$("#savePicture").removeAttr("disabled"); 
						showPanelClose();//关闭弹窗
					}else if(objs.scode == "-1"){
						alert(objs.smsg);
					//	$("#savePicture").removeAttr("disabled"); 
					}
				}
			}
		},
		error : function(data) {
			showErrorMsg(true,data);
			$("#savePicture").removeAttr("disabled"); 
		}
	});
};

/**
 * 上传图片按钮
 * @param obj
 */
function changePicture(obj) {
	var fileName = document.getElementById('file').value;
	if(null == fileName || "" == fileName){
		return;
	}
    var suffixArray = fileName.split(".");
    var suffix = suffixArray[suffixArray.length-1];
    var suffixPicture = "jpg,gif,png,jpeg";
    var rs = suffixPicture.indexOf(suffix.toLowerCase());
    if(rs<0){
        document.getElementById("uploadtype").innerHTML = "<font color=\"red\">支持jpg/gif/png格式!</font>";
        return;
    } 
	$("#uploadproofFrm").ajaxForm({    
		success:function(data) {
			if(data!=null && data!=""){
				if(data==0){
					document.getElementById("uploadtype").innerHTML = "<font color=\"red\">图片大小最大不能超过2M!</font>";
				}
				else{
					var jsonData = eval("("+data+")");
					var imgWidth = jsonData.width;
					var imgHeight = jsonData.height;
					var a=800;
					var b=700;
					if(imgWidth>700){
						a=imgWidth+50;
					}
					if(imgHeight>600){
						b=imgHeight+100;
					}
					popUp(a,b);
					var bgTop = -5;
					var bgLeft = 16;
					$("#bgDiv").css({width:imgWidth,height:imgHeight,marginTop:bgTop,marginLeft:bgLeft});
					var wid="";
					var hei="";
					if(ratio==1){
						if(imgWidth>imgHeight){
							wid=imgHeight;
							hei=imgHeight;
						}else{
							wid=imgWidth;
							hei=imgWidth;
						}
					}else if(ratio==2){
						if(3/4 > imgWidth/imgHeight){
							wid=imgWidth;
							hei=imgWidth*4/3;
						}else{
							hei=imgHeight;
							wid=imgHeight*3/4;
						}
					}else{
						if(3/4 > imgHeight/imgWidth){
							hei=imgHeight;
							wid=imgHeight*4/3;
						}else{
							wid=imgWidth;
							hei=imgWidth*3/4;
						}
					}
					
					$("#dragDiv").css({width:wid,height:hei,left:"0px",top:"0px"});
					
					ic.Url = jsonData.path;
					ic.SetOptions({
						Width:imgWidth,
						Height:imgHeight
					});
					$$("idPicUrl").value = jsonData.fileName;
					ic.Init();  
					$("#pictrueTab").show();
					document.getElementById("uploadtype").innerHTML = "";
				}
			}
			else{
				document.getElementById("uploadtype").innerHTML = "<font color=\"red\">系统异常,上传图片失败! </font>";
			}
			ratio=1;
		},      
		dataType:"text"   
	}).submit(); 
};
function drag(elementToDrag,event)
{
 var startX=event.clientX; startY=event.clientY;//div中间坐标

 var origX=elementToDrag.offsetLeft; origY=elementToDrag.offsetTop;//div左上角的坐标
 
 var deltaX=startX-origX;  deltaY=startY-origY;
 if(document.addEventListener){//firfox and so on
  document.addEventListener('mousemove',moveHandler,true);
        document.addEventListener('mouseup',upHandler,true);
 }else if(document.attachEvent){//IE 5+
      elementToDrag.setCapture();
   elementToDrag.attachEvent('onmousemove',moveHandler);
   elementToDrag.attachEvent('onmouseup',upHandler);

   elementToDrag.attachEvent('onlosecapture',upHandler);
 }else{//IE 4 EVENT MODE
  var oldmoveHandler=document.onmousemove;
  var oldupHandler=document.mouseup;
  document.onmousemove=moveHandler;
  document.onmouseup=upHandler;
 }
 if(event.stopPropagation) event.stopPropagation();//firefox 阻止事件冒泡，使得元素的父节点也响应事件
 else event.cancelBubble=true; //IE

 if(event.preventDefault) event.preventDefault();//阻止默认的方法
 else event.returnValue=false;
  function moveHandler(e)
 {
  if(!e) e=window.event;
  elementToDrag.style.left=(e.clientX-deltaX)+'px';
  elementToDrag.style.top=(e.clientY-deltaY)+'px';

  if(e.stopPropagation) e.stopPropagation();
  else e.cancelBubble=true;
 }
  
     function  upHandler(e)
  {
   if(!e) e=window.event;
   if(document.removeEventListener){
    document.removeEventListener("mouseup",upHandler,true);
    document.removeEventListener("mousemove",moveHandler,true);
   }else if(document.detachEvent)
   {
    elementToDrag.detachEvent("onlosecapture",upHandler);
    elementToDrag.detachEvent("onmouseup",upHandler);
    elementToDrag.detachEvent("onmousemove",moveHandler);
    elementToDrag.releaseCapture;
   }else{
    document.onmouseup=oldupHandler;
    document.onmousemove=oldmoveHandler;
   }

   if(e.stopPropagation) e.stopPropagation();
   else e.cancelBubble=true;
  }
}
