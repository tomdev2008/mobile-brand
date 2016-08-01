/**
 * 列表checkbox全选
 */
function selectAll(){
	if($("#SelectAll").attr("checked")){
        $(":checkbox[disabled!='true']").attr("checked",true);
    }else {
        $(":checkbox[disabled!='true']").attr("checked",false);
    }
}

/**
 * 打开弹出层
 * @param pageUrl
 * @param title
 * @param option
 */
function openUrl(url){
	location.href=url;
}

/**
 * 打开弹出层
 * @param pageUrl
 * @param title
 * @param option
 */
function openPannel(pageUrl,title,option){
	var parameters = "scrollbars=yes";
	var winOption = "";
	if(option!=""&&option!=undefined){
		alert(option);
		winOption=option;
	}
	if(navigator.userAgent.indexOf("Chrome") > 0) {
		//如果是chrome浏览器
		window.open(pageUrl, title, winOption);
	} else {
		window.open(pageUrl, title, parameters);
	}
}

/**
 * 提交查询表单
 * 防止重复提交
 */
function submitSeachForm(butId,formId,beforeFun){
	var butIsstr="query";
	if(butId!=''&&butId!=undefined){
		butIsstr=query;
	}
    $("#"+butIsstr).removeClass("btn").addClass("quanbtn");
	 $("#"+butIsstr).attr("onclick","");
	 $("#"+butIsstr).text("正在查询");
	 if(beforeFun!=null){
		 beforeFun();
	 }
	$("#pageNo").val(1);
    $("#mainForm").submit();
  }

var panelDivId="showPanelDiv_show_div";
var hideId="zhezhao";
var showPanelCotentId="showPanelCotent";
var showPanelarg;
/**
 * 显示弹出框
 */
var showPanel= function (arg) {
	showPanelarg=arg;
    var divhtml=$("#"+arg.id).html();
    var box = 300;
    var width=300;
    var height=500;
    if(arg.width!=undefined){
    	width=arg.width;
    }
    if(arg.height!=undefined){
    	height=arg.height;
    }
	var th = $(window).scrollTop()+($(window).height()-height)/2;//距离屏幕顶部
	var h = document.body.clientHeight;
	var rw = $(window).width()/2-width/2;//距离屏幕右边
	var parentdiv=$('<div></div>');        //创建一个父div
    parentdiv.attr('id',panelDivId);        //给父div设置id
    parentdiv.addClass('showbox');    //添加css样式
    var childdiv=$("<h2>"+arg.title+"<input type='button' value='关闭' class='closeBtn' /></h2>");        //创建标题栏
    childdiv.appendTo(parentdiv);        //将子div添加到父div中
    
    var contentdiv=$("<div>"+divhtml+"</div>");   
    contentdiv.attr("id",showPanelCotentId);
    contentdiv.addClass('showContentbox');
     contentdiv.appendTo(parentdiv);     
    parentdiv.appendTo('body');            //将父div添加到body中
    //生成遮掩层
    var hideIddiv=$('<div></div>'); 
    hideIddiv.attr('id',hideId);  
    hideIddiv.appendTo('body');
	$("#"+panelDivId).animate({top:th,opacity:'show',width:width,height:height,right:rw},500);
	$("#"+hideId).css({
		display:"block",height:$(document).height()
	});
	$(".closeBtn").click(function(){
	   showPanelClose();
	});
	
	//原div清空
	$("#"+arg.id).html("");
};
/**
 * 显示弹出框2
 */
var showPanels= function (arg) {
	showPanelarg=arg;
    var divhtml=$("#"+arg.id).html();
    var box = 300;
    var width=300;
    var height=500;
    if(arg.width!=undefined){
    	width=arg.width;
    }
    if(arg.height!=undefined){
    	height=arg.height;
    }
	var th = 40;//距离屏幕顶部
	var h = document.body.clientHeight;
	var rw = $(window).width()/2-width/2;//距离屏幕右边
	var parentdiv=$('<div></div>');        //创建一个父div
    parentdiv.attr('id',panelDivId);        //给父div设置id
    parentdiv.addClass('showbox');    //添加css样式
    var childdiv=$("<h2>"+arg.title+"<input type='button' value='关闭' class='closeBtn' /></h2>");        //创建标题栏
    childdiv.appendTo(parentdiv);        //将子div添加到父div中
    
    var contentdiv=$("<div>"+divhtml+"</div>");   
    contentdiv.attr("id",showPanelCotentId);
    contentdiv.addClass('showContentbox');
     contentdiv.appendTo(parentdiv);     
    parentdiv.appendTo('body');            //将父div添加到body中
    //生成遮掩层
    var hideIddiv=$('<div></div>'); 
    hideIddiv.attr('id',hideId);  
    hideIddiv.appendTo('body');
	$("#"+panelDivId).animate({top:th,opacity:'show',width:width,height:height,right:rw},500);
	$("#"+hideId).css({
		display:"block",height:$(document).height()
	});
	$(".closeBtn").click(function(){
	   showPanelClose();
	});
	
	//原div清空
	$("#"+arg.id).html("");
};
/**
 * 关闭弹出框
 */
 var showPanelClose = function () {
		//恢复原div 内容
		$("#"+showPanelarg.id).html($("#"+showPanelCotentId).html());
     $("#"+panelDivId).animate({top:0,opacity:'hide',width:0,height:0,right:0},500,function(){
         $("#"+panelDivId).remove();
     });
		$("#"+hideId).remove();
		
 };
 
 
 /**
  *	 $(".pictureShowImg").click(function(){
		var src= $(this).attr("src");
		pannelShowImg(src);
	 });
		
  * 初始化秀图片展示
  */
 function initShowImg(){
	 
	 $(".pictureShowImg").click(function(){
			var src= $(this).attr("src");
			pannelShowImg(this,src);
		 });
	 
     $(".pictureUl li").click(
             function(){
                var url=$(this).attr("pictureUrl");
                var pdiv= $(this).parents('.pictureTdDiv'); 
                $(pdiv[0]).find("img.pictureShowImg").attr("src",url);
                var pUl= $(this).parents('.pictureUl'); 
                $(pUl[0]).find("li").removeClass("choose");
                $(this).addClass("choose");
             });
 }
 
 
 function pannelShowImg(obj,imgUrl){
	var lis= $(obj).next().find("li");
	 var id ="pannelShowDiv";
	 var ulid ="pannelShowUl";
	 var imgid ="pannelShowImg";
	 var isExist=false;
	 var idObj=$("#"+id);
	 if(!$("#"+id)[0]){
		 var pannelHtml="";
			var parentdiv=$('<div class="none"></div>');        //创建一个父div
		    parentdiv.attr('id',id);        //给父div设置id
		    var childdiv=$("<img id='"+imgid+"'   class='pannelShowImg'/>");        //创建标题栏
		    childdiv.appendTo(parentdiv);        //将子div添加到父div中
		    var uls=$('<ul id="'+ulid+'" class="pannelShowImgUl"></ul>'); 
		    uls.appendTo(parentdiv);        //将子div添加到父div中
		    parentdiv.appendTo('body');  
      }
	 var uls=$("#"+ulid);
	 $(uls).html("");
	 for(var i=0;i<lis.length;i++){
		 var li=$('<li ></li>'); 
		 var lisrc=$(lis[i]).attr("pictureurl");
		 li.attr("pictureurl",lisrc);
		 if($.trim(imgUrl)==$.trim(lisrc)){
			 li.attr("class","choose");
		 }
		 var lishtml="<img src='"+lisrc+"'/>";
		 li.html(lishtml);
		 li.appendTo(uls);        //将子div添加到父div中
	 }
	 
	 
	 $("#"+imgid).attr("src",imgUrl);
        var arg={
    	     id: id,
    	     title:"秀图查看",
    	     height:600,
    	     width:660
    	 }
    	showPanel(arg);
        $("#"+ulid).find("li").click(function(){
        	var clicklisrc=$(this).attr("pictureurl");
        	$("#"+imgid).attr("src",clicklisrc);
        	$("#"+ulid).find("li").removeClass("choose");
        	$(this).addClass("choose");
        });
 }
 
 
 

	
 /**
  * 初始化排序图片按钮样式及点击事件
  * id 为排序图标控件id
  * name 为排序参数值的控件id
  */
 function initOrderImg(imgId,valueId){
 var value=$("#"+valueId).val();
   var idobj= $("#"+imgId);
   if(value==""){
     idobj.addClass("order_defaul");
   }else if(value=="asc"){
    idobj.addClass("order_up");
   }else if(value=="desc"){
    idobj.addClass("order_down");
   }
   $("#"+imgId).click(
        function(){
          $(".orderInput").val("");
          var className=$(this).attr("class");
          if(className=='order_defaul'){
             $("#"+valueId).val("desc");//降序
          }else if(className=='order_up'){
             $("#"+valueId).val("");//无序
          }else if(className=='order_down'){
             $("#"+valueId).val("asc");//升序
          }
           submitSeachForm();
        });
 } 	
	


/**
 * 输入框默认值设置
 * input控件需键入dvalue属性 属性值则为默认值
 */
 function setInputDefaultValue(){
 	 var dvalues=$(".dvalue");
 	 for(var i=0;i<dvalues.length;i++){
 	   var dvalue=dvalues[i];
 	   var val=  $(dvalue).val();
 	   if(val==""){
 		   $(dvalue).val($(dvalue).attr("dvalue"));
 		   $(dvalue).addClass("gray");
 	   }
 	    $(dvalue).click(function(){
 	      var val=$(this).val();
 	      var dval=$(this).attr("dvalue");
 	      if(val==dval){
 	        $(this).removeClass("gray");
 	        $(this).val("");
 	      }
 	    });
 	    $(dvalue).blur(function(){
 	      var val=$(this).val();
 	      var dval=$(this).attr("dvalue");
 	      if(val==""){
 	        $(this).val(dval);
 	        $(this).addClass("gray");
 	      }
 	    });
 	 }
 }


 
