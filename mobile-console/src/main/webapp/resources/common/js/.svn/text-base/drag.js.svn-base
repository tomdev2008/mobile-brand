/**
 * 裁剪图片 js处理
 */
var isIE = (document.all) ? true : false;

var isIE6 = isIE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6);

var $$ = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};

var Class = {
	create: function() {
		return function() { this.initialize.apply(this, arguments); };
	}
};

var Extend = function(destination, source) {
	for (var property in source) {
		destination[property] = source[property];
	}
};

var Bind = function(object, fun) {
	return function() {
		return fun.apply(object, arguments);
	};
};

var BindAsEventListener = function(object, fun) {
	var args = Array.prototype.slice.call(arguments).slice(2);
	return function(event) {
		return fun.apply(object, [event || window.event].concat(args));
	};
};

var CurrentStyle = function(element){
	return element.currentStyle || document.defaultView.getComputedStyle(element, null);
};

function addEventHandler(oTarget, sEventType, fnHandler) {
	if (oTarget.addEventListener) {
		oTarget.addEventListener(sEventType, fnHandler, false);
	} else if (oTarget.attachEvent) {
		oTarget.attachEvent("on" + sEventType, fnHandler);
	} else {
		oTarget["on" + sEventType] = fnHandler;
	}
};

function removeEventHandler(oTarget, sEventType, fnHandler) {
    if (oTarget.removeEventListener) {
        oTarget.removeEventListener(sEventType, fnHandler, false);
    } else if (oTarget.detachEvent) {
        oTarget.detachEvent("on" + sEventType, fnHandler);
    } else { 
        oTarget["on" + sEventType] = null;
    }
};

//图片切割
var ImgCropper = Class.create();
ImgCropper.prototype = {
  //容器对象,控制层,图片地址
  initialize: function(container, handle, url, options) {
	this._Container = $$(container);//容器对象
	this._layHandle = $$(handle);//控制层
	this.Url = url;//图片地址
	
	this._layBase = this._Container.appendChild(document.createElement("img"));//底层
	this._layCropper = this._Container.appendChild(document.createElement("img"));//切割层
	this._layCropper.onload = Bind(this, this.SetPos);
	//用来设置大小
	this._tempImg = document.createElement("img");
	this._tempImg.onload = Bind(this, this.SetSize);
	
	this.SetOptions(options);
	
	this.Opacity = Math.round(this.options.Opacity);
	this.Color = this.options.Color;
	this.Scale = !!this.options.Scale;
	this.Ratio = Math.max(this.options.Ratio, 0);
	this.Width = Math.round(this.options.Width);
	this.Height = Math.round(this.options.Height);
	
	//设置预览对象
	var oPreview = $$(this.options.Preview);//预览对象
	if(oPreview){
		oPreview.style.position = "relative";
		oPreview.style.overflow = "hidden";
		this.viewWidth = Math.round(this.options.viewWidth);
		this.viewHeight = Math.round(this.options.viewHeight);
		//预览图片对象
		this._view = oPreview.appendChild(document.createElement("img"));
		this._view.style.position = "absolute";
		this._view.onload = Bind(this, this.SetPreview);
	}
	//设置拖放
	this._drag = new Drag(this._layHandle, { Limit: true, onMove: Bind(this, this.SetPos), Transparent: true });
	//设置缩放
	this.Resize = !!this.options.Resize;
	if(this.Resize){
		var op = this.options, _resize = new Resize(this._layHandle, { Max: true, onResize: Bind(this, this.SetPos) });
		//设置缩放触发对象
		op.RightDown && (_resize.Set(op.RightDown, "right-down"));
		op.LeftDown && (_resize.Set(op.LeftDown, "left-down"));
		op.RightUp && (_resize.Set(op.RightUp, "right-up"));
		op.LeftUp && (_resize.Set(op.LeftUp, "left-up"));
		op.Right && (_resize.Set(op.Right, "right"));
		op.Left && (_resize.Set(op.Left, "left"));
		op.Down && (_resize.Set(op.Down, "down"));
		op.Up && (_resize.Set(op.Up, "up"));
		//最小范围限制
		this.Min = !!this.options.Min;
		this.minWidth = Math.round(this.options.minWidth);
		this.minHeight = Math.round(this.options.minHeight);
		//设置缩放对象
		this._resize = _resize;
	}
	//设置样式
	this._Container.style.position = "absolute";
	this._Container.style.overflow = "hidden";
	this._layHandle.style.zIndex = 200;
	this._layCropper.style.zIndex = 100;
	this._layBase.style.position = this._layCropper.style.position = "absolute";
	this._layBase.style.top = this._layBase.style.left = this._layCropper.style.top = this._layCropper.style.left = 0;//对齐
	//初始化设置
	this.Init();
  },
  //设置默认属性
  SetOptions: function(options) {
    this.options = {//默认值
		Opacity:	50,//透明度(0到100)
		Color:		"",//背景色
		Width:		0,//图片高度
		Height:		0,//图片高度
		//缩放触发对象
		Resize:		false,//是否设置缩放
		Right:		"",//右边缩放对象
		Left:		"",//左边缩放对象
		Up:			"",//上边缩放对象
		Down:		"",//下边缩放对象
		RightDown:	"",//右下缩放对象
		LeftDown:	"",//左下缩放对象
		RightUp:	"",//右上缩放对象
		LeftUp:		"",//左上缩放对象
		Min:		false,//是否最小宽高限制(为true时下面min参数有用)
		minWidth:	0,//最小宽度
		minHeight:	0,//最小高度
		Scale:		false,//是否按比例缩放
		Ratio:		0,//缩放比例(宽/高)
		//预览对象设置
		Preview:	"",//预览对象
		viewWidth:	0,//预览宽度
		viewHeight:	0//预览高度
    };
    Extend(this.options, options || {});
  },
  //初始化对象
  Init: function() {
	//设置背景色
	this.Color && (this._Container.style.backgroundColor = this.Color);
	//设置图片
	this._tempImg.src = this._layBase.src = this._layCropper.src = this.Url;
	//设置透明
	if(isIE){
		this._layBase.style.filter = "alpha(opacity:" + this.Opacity + ")";
	} else {
		this._layBase.style.opacity = this.Opacity / 100;
	}
	//设置预览对象
	this._view && (this._view.src = this.Url);
	//设置缩放
	if(this.Resize){
		with(this._resize){
			Scale = this.Scale; Ratio = this.Ratio; Min = this.Min; minWidth = this.minWidth; minHeight = this.minHeight;
		}
	}
  },
  //设置切割样式
  SetPos: function() {
	//ie6渲染bug
	if(isIE6){ with(this._layHandle.style){ zoom = .9; zoom = 1; }; };
	//获取位置参数
	var p = this.GetPos();
	//按拖放对象的参数进行切割
	this._layCropper.style.clip = "rect(" + p.Top + "px " + (p.Left + p.Width) + "px " + (p.Top + p.Height) + "px " + p.Left + "px)";
	//设置预览
	this.SetPreview();
  },
  //设置预览效果
  SetPreview: function() {
	if(this._view){
		//预览显示的宽和高
		var p = this.GetPos(), s = this.GetSize(p.Width, p.Height, this.viewWidth, this.viewHeight), scale = s.Height / p.Height;
		if(scale==Infinity){
			return;
		}
		//按比例设置参数
		var pHeight = this._layBase.height * scale, pWidth = this._layBase.width * scale, pTop = p.Top * scale, pLeft = p.Left * scale;
		//设置预览对象
		with(this._view.style){
			//设置样式
			width = pWidth + "px"; height = pHeight + "px"; top = - pTop + "px "; left = - pLeft + "px";
			//切割预览图
			clip = "rect(" + pTop + "px " + (pLeft + s.Width) + "px " + (pTop + s.Height) + "px " + pLeft + "px)";
		}
	}
  },
  //设置图片大小
  SetSize: function() {
	var s = this.GetSize(this._tempImg.width, this._tempImg.height, this.Width, this.Height);
	//设置底图和切割图
	this._layBase.style.width = this._layCropper.style.width = s.Width + "px";
	this._layBase.style.height = this._layCropper.style.height = s.Height + "px";
	//设置拖放范围
	this._drag.mxRight = s.Width; this._drag.mxBottom = s.Height;
	//设置缩放范围
	if(this.Resize){ this._resize.mxRight = s.Width; this._resize.mxBottom = s.Height; }
  },
  //获取当前样式
  GetPos: function() {
	with(this._layHandle){
		return { Top: offsetTop, Left: offsetLeft, Width: offsetWidth, Height: offsetHeight };
	}
  },
  //获取尺寸
  GetSize: function(nowWidth, nowHeight, fixWidth, fixHeight) {
	var iWidth = nowWidth, iHeight = nowHeight, scale = iWidth / iHeight;
	//按比例设置
	if(fixHeight){ iWidth = (iHeight = fixHeight) * scale; }
	if(fixWidth && (!fixHeight || iWidth > fixWidth)){ iHeight = (iWidth = fixWidth) / scale; }
	//返回尺寸对象
	return { Width: iWidth, Height: iHeight };
  }
};


//拖放程序
var Drag = Class.create();
Drag.prototype = {
  //拖放对象
  initialize: function(drag, options) {
	this.Drag = $$(drag);//拖放对象
	this._x = this._y = 0;//记录鼠标相对拖放对象的位置
	this._marginLeft = this._marginTop = 0;//记录margin
	//事件对象(用于绑定移除事件)
	this._fM = BindAsEventListener(this, this.Move);
	this._fS = Bind(this, this.Stop);
	
	this.SetOptions(options);
	
	this.Limit = !!this.options.Limit;
	this.mxLeft = parseInt(this.options.mxLeft);
	this.mxRight = parseInt(this.options.mxRight);
	this.mxTop = parseInt(this.options.mxTop);
	this.mxBottom = parseInt(this.options.mxBottom);
	
	this.LockX = !!this.options.LockX;
	this.LockY = !!this.options.LockY;
	this.Lock = !!this.options.Lock;
	
	this.onStart = this.options.onStart;
	this.onMove = this.options.onMove;
	this.onStop = this.options.onStop;
	
	this._Handle = $$(this.options.Handle) || this.Drag;
	this._mxContainer = $$(this.options.mxContainer) || null;
	
	this.Drag.style.position = "absolute";
	//透明
	if(isIE && !!this.options.Transparent){
		//填充拖放对象
		with(this._Handle.appendChild(document.createElement("div")).style){
			width = height = "100%"; backgroundColor = "#fff"; filter = "alpha(opacity:0)"; fontSize = 0;
		}
	}
	//修正范围
	this.Repair();
	addEventHandler(this._Handle, "mousedown", BindAsEventListener(this, this.Start));
  },
  //设置默认属性
  SetOptions: function(options) {
	this.options = {//默认值
		Handle:			"",//设置触发对象（不设置则使用拖放对象）
		Limit:			false,//是否设置范围限制(为true时下面参数有用,可以是负数)
		mxLeft:			0,//左边限制
		mxRight:		300,//右边限制
		mxTop:			0,//上边限制
		mxBottom:		300,//下边限制
		mxContainer:	"",//指定限制在容器内
		LockX:			false,//是否锁定水平方向拖放
		LockY:			false,//是否锁定垂直方向拖放
		Lock:			false,//是否锁定
		Transparent:	false,//是否透明
		onStart:		function(){},//开始移动时执行
		onMove:			function(){},//移动时执行
		onStop:			function(){}//结束移动时执行
	};
	Extend(this.options, options || {});
  },
  //准备拖动
  Start: function(oEvent) {
	if(this.Lock){ return; }
	this.Repair();
	//记录鼠标相对拖放对象的位置
	this._x = oEvent.clientX - this.Drag.offsetLeft;
	this._y = oEvent.clientY - this.Drag.offsetTop;
	//记录margin
	this._marginLeft = parseInt(CurrentStyle(this.Drag).marginLeft) || 0;
	this._marginTop = parseInt(CurrentStyle(this.Drag).marginTop) || 0;
	//mousemove时移动 mouseup时停止
	addEventHandler(document, "mousemove", this._fM);
	addEventHandler(document, "mouseup", this._fS);
	if(isIE){
		//焦点丢失
		addEventHandler(this._Handle, "losecapture", this._fS);
		//设置鼠标捕获
		this._Handle.setCapture();
	}else{
		//焦点丢失
		addEventHandler(window, "blur", this._fS);
		//阻止默认动作
		oEvent.preventDefault();
	};
	//附加程序
	this.onStart();
  },
  //修正范围
  Repair: function() {
	if(this.Limit){
		//修正错误范围参数
		this.mxRight = Math.max(this.mxRight, this.mxLeft + this.Drag.offsetWidth);
		this.mxBottom = Math.max(this.mxBottom, this.mxTop + this.Drag.offsetHeight);
		//如果有容器必须设置position为relative或absolute来相对或绝对定位，并在获取offset之前设置
		!this._mxContainer || CurrentStyle(this._mxContainer).position == "relative" || CurrentStyle(this._mxContainer).position == "absolute" || (this._mxContainer.style.position = "relative");
	}
  },
  //拖动
  Move: function(oEvent) {
	//判断是否锁定
	if(this.Lock){ this.Stop(); return; };
	//清除选择
	window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
	//设置移动参数
	var iLeft = oEvent.clientX - this._x, iTop = oEvent.clientY - this._y;
	//设置范围限制
	if(this.Limit){
		//设置范围参数
		var mxLeft = this.mxLeft, mxRight = this.mxRight, mxTop = this.mxTop, mxBottom = this.mxBottom;
		//如果设置了容器，再修正范围参数
		if(!!this._mxContainer){
			mxLeft = Math.max(mxLeft, 0);
			mxTop = Math.max(mxTop, 0);
			mxRight = Math.min(mxRight, this._mxContainer.clientWidth);
			mxBottom = Math.min(mxBottom, this._mxContainer.clientHeight);
		};
		//修正移动参数
		iLeft = Math.max(Math.min(iLeft, mxRight - this.Drag.offsetWidth), mxLeft);
		iTop = Math.max(Math.min(iTop, mxBottom - this.Drag.offsetHeight), mxTop);
	}
	//设置位置，并修正margin
	if(!this.LockX){ this.Drag.style.left = iLeft - this._marginLeft + "px"; }
	if(!this.LockY){ this.Drag.style.top = iTop - this._marginTop + "px"; }
	//附加程序
	this.onMove();
  },
  //停止拖动
  Stop: function() {
	//移除事件
	removeEventHandler(document, "mousemove", this._fM);
	removeEventHandler(document, "mouseup", this._fS);
	if(isIE){
		removeEventHandler(this._Handle, "losecapture", this._fS);
		this._Handle.releaseCapture();
	}else{
		removeEventHandler(window, "blur", this._fS);
	};
	//附加程序
	this.onStop();
  }
};

