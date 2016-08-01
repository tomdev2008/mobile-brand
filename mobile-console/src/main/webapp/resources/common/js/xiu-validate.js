/**
 * 这里主要放一些校验方法，如果不是采用jquery方式编写的在你要使用到该方法时请把它修改为jquery方式
 */


// 检验手机号码
function checkMobile(value)
{

	if (value > ""){
		var reg=/13[0,1,2,3,4,5,6,7,8,9]\d{8}/;
  	   	if (value.match(reg)== null){
			return false;
	   }
	}
    else
    {
		return false;
    }
	return true;
}

// 检验移动手机号码
function validatePhone(value)
{
/*
 * //检测只能为只能为正整数 //var
 * reg=/13[5,6,7,8,9]\d{8}|134[0,1,2,3,4,5,6,7,8]\d{7}|15[8,9]\d{8}/; var reg=
 * /[\D_]/; if(!(reg.exec(value))&&value.length==11){ return true; }else{ return
 * false; }
 */
   
   var reg=/1[3,4,5,8]\d{9}/;
   if(reg.test(value)&&value.length==11){
    return true;
   }else{
  	 return false;
   }
}


// 函数名：checkNUM
// 功能介绍：检查是否为数字
// 参数说明：要检查的数字
// 返回值：1为是数字，0为不是数字
function checkNum(Num) {
	var i,j,strTemp;
	strTemp = "0123456789.";
	if ( Num.length == 0)
		return 0 ;
	for (i = 0;i < Num.length; i++) {
		j = strTemp.indexOf(Num.charAt(i));
		if (j == -1) {
			// 说明有字符不是数字
			return 0;
		}
	}
	// 说明是数字
	return 1;
}


// 函数名：checkNUM
// 功能介绍：检查是否为数字
// 参数说明：要检查的数字
// 返回值：1为是数字，0为不是数字
function checkIntNum(Num) {
	var i,j,strTemp;
	strTemp = "0123456789";
	if ( Num.length == 0)
		return 0 ;
	for (i = 0;i < Num.length; i++) {
		j = strTemp.indexOf(Num.charAt(i));
		if (j == -1) {
			// 说明有字符不是数字
			return 0;
		}
	}
	// 说明是数字
	return 1;
}


// 函数名：checkEmail
// 功能介绍：检查是否为Email Address
// 参数说明：要检查的字符串
// 返回值：0：不是 1：是
/**
 * 校正对'.'的判断 （判断的规则是不能以'@'或'.'开头，'@'后面至少要有三个字符，不能以'.'结束）
 */
function checkEmail(a){

		re = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
		if (!re.test(a)){

			return 0;
			}
		return 1;
	}

// 函数名：checkTEL
// 功能介绍：检查是否为电话号码
// 参数说明：要检查的字符串
// 返回值：1为是合法，0为不合法
function checkTel(tel)
{
	var i,j,strTemp;
	strTemp = "0123456789-－";
	for (i=0;i<tel.length;i++)
	{
		j = strTemp.indexOf(tel.charAt(i));
		if (j==-1)
		{
			// 说明有字符不合法
			return 0;
		}
	}
	// 说明合法
	return 1;
}

// 函数名：checkLength
// 功能介绍：检查字符串的长度
// 参数说明：要检查的字符串
// 返回值：长度值
function checkLength(strTemp)
{
	var i,sum;
	sum = 0;
	for(i=0;i<strTemp.length;i++)
	{
		if ((strTemp.charCodeAt(i)>=0) && (strTemp.charCodeAt(i)<=128))
			sum = sum + 1;
		else
			sum = sum + 2;
	}
	return sum;
}

// 函数名：checkSafe
// 功能介绍：检查是否含有"'", '"',"<", ">"
// 参数说明：要检查的字符串
// 返回值：0：是 1：不是
function checkSafe(a)
{
	// fibdn = new Array ("'" ,'"',">", "<", "、", ",", ";");
	fibdn = new Array ("'" ,'"',">", "<");
	i = fibdn.length;
	j = a.length;
	for (var ii=0;ii<i;ii++)
	{
		for (var jj=0;jj<j;jj++)
		{
			temp1 = a.charAt(jj);
			temp2 = fibdn[ii];
			if (temp1==temp2)
			{
				return 0;
			}
		}
	}
	return 1;
}

// 函数名：checkChar
// 功能介绍：检查是否含有非字母字符
// 参数说明：要检查的字符串
// 返回值：0：含有 1：全部为字母
function checkChar(str)
{
	var strSource ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.()& ";
	var ch;
	var i;
	var temp;

	for (i=0;i<=(str.length-1);i++)
	{
		ch = str.charAt(i);
		temp = strSource.indexOf(ch);
		if (temp==-1)
		{
			return 0;
		}
	}
	return 1;
}
/*
 * 检查只有数字和26个字母 //返回值：0：含有 1：全部为数字或字母
 */
function checkCharOrNum(str)
{
	var strSource = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var ch;
	var i;
	var temp;

	for (i = 0;i<=(str.length-1);i++)
	{

		ch = str.charAt(i);
		temp = strSource.indexOf(ch);
		if (temp == -1)
		{
			return 0;
		}
	}
	return 1;
}
/*
 * 检查非法字符 //返回值：0：不正确 1：正确
 */
function checkFormChar(str)
{
	var strSource = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
	var ch;
	var i;
	var temp;

	for (i = 0;i<=(str.length-1);i++)
	{

		ch = str.charAt(i);
		temp = strSource.indexOf(ch);
		if (temp == -1)
		{
			return 0;
		}
	}
	return 1;
}
// 函数名：checkCharOrDigital
// 功能介绍：检查是否含有非数字或字母
// 参数说明：要检查的字符串
// 返回值：0：含有 1：全部为数字或字母
function checkCharOrDigital(str)
{
	var strSource = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.()& ";
	var ch;
	var i;
	var temp;

	for (i = 0;i<=(str.length-1);i++)
	{

		ch = str.charAt(i);
		temp = strSource.indexOf(ch);
		if (temp == -1)
		{
			return 0;
		}
	}
	return 1;
}

// 函数名：checkPackageName
// 功能介绍：检查是否含有非数字或字母或小数点，减号，下划线
// 参数说明：str要检查的字符串
// 返回值：0：含有 1：全部为数字或字母或小数点，减号，下划线
function checkPackageName(str)
{
	var strSource = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.-_";
	var ch;
	var i;
	var temp;

	for (i = 0;i<=(str.length-1);i++)
	{

		ch = str.charAt(i);
		temp = strSource.indexOf(ch);
		if (temp == -1)
		{
			return 0;
		}
	}
	return 1;
}

// 函数名：checkParenthesis
// 功能介绍：检查是否含有半角括号
// 参数说明：str要检查的字符串
// 返回值：0：不含半角括号 1：含有半角括号
function checkParenthesis(str)
{
	if((str.indexOf('(')==-1)&&(str.indexOf(')')==-1)){
	return 0;
	}
	else{
	return 1;
	}
}

// 函数名：checkChinese
// 功能介绍：检查是否含有汉字
// 参数说明：要检查的字符串
// 返回值：0：含有 1：没有
function checkChinese(strTemp)
{
	var i;
	for(i=0;i<strTemp.length;i++)
	{
		if ((strTemp.charCodeAt(i)<0) || (strTemp.charCodeAt(i)>255))
	      return 0;
	}
	return 1;
}

// 函数名：compareTime()
// 功能介绍： 比较时间大小
// 参数说明：beginYear开始年，beginMonth开始月,benginDay开始日,beginH开始小时，beginM开始分钟，
// endYear结束年，endMonth结束月，endMonth结束日,endH结束小时，endM结束分钟
// 返回值：true 表示 开始时间大于结束时间，false 相反
function compareTime(beginYear,beginMonth,benginDay,beginH,beginM,endYear,endMonth,endDay,endH,endM){
  var date1 = new Date(beginYear,beginMonth-1,benginDay,beginH,beginM);
  var date2 = new Date(endYear,endMonth-1,endDay,endH,endM);
  if(date1.getTime()>=date2.getTime()){
    return false;
  }
  return true;
}

// 函数名：compareDate()
// 功能介绍： 比较日期大小
// 参数说明：beginYear开始年，beginMonth开始月,benginDay开始日
// endYear结束年，endMonth结束月，endMonth结束日
// 返回值：0：true 表示 开始时间大于结束时间，false 相反
function compareDate(beginYear,beginMonth,benginDay,endYear,endMonth,endDay){
  var date1 = new Date(beginYear,beginMonth-1,benginDay);
  var date2 = new Date(endYear,endMonth-1,endDay);
  if(date1.getTime()>=date2.getTime()){
    return false;
  }
  return true;
}
function compareDate2(beginYear,beginMonth,benginDay,endYear,endMonth,endDay){
  var date1 = new Date(beginYear,beginMonth-1,benginDay);
  var date2 = new Date(endYear,endMonth-1,endDay);
  if(date1.getTime()>date2.getTime()){
    return false;
  }
  return true;
}

//返回值：0：true 表示 开始时间大于结束时间，false 相反
function compareDate(beginTime,endTime){  
    var beginDates = beginTime.substring(0,10).split('-');  
    var endDates   =  endTime.substring(0,10).split('-');  
    var beginTimes = beginTime.substring(11,19).split(':');  
    var endTimes   =  endTime.substring(11,19).split(':');  
    var date1 = new Date(beginDates[0],beginDates[1],beginDates[2],beginTimes[0],beginTimes[1],beginTimes[2]);
    var date2 = new Date(endDates[0],endDates[1],endDates[2],endTimes[0],endTimes[1],endTimes[2]);
    var isbeginSmall=true;
     if(date1.getTime()>=date2.getTime()){
    	     isbeginSmall=false;
    }
     return isbeginSmall;
 }  
// 函数名：checkURL
// 功能介绍：检查Url是否合法
// 参数说明：要检查的字符串
// 返回值：true：合法 false：不合法。
function checkURL(strTemp)
{
	if(strTemp.length==0) return false;
	if(checkChinese(strTemp)==0) return false;
	if (strTemp.toUpperCase().indexOf("HTTP://") != 0 && strTemp.toUpperCase().indexOf("HTTPS://") != 0){
		return false;
	}
	return true;
}


// obj:数据对象
// dispStr :失败提示内容显示字符串
function checkUrlValid( obj,  dispStr)
{
	if(obj  == null)
	{
		alert("传入对象为空");
		return false;
	}
	var str = obj.value;

	var  urlpatern0 = /^https?:\/\/.+$/i;
	if(!urlpatern0.test(str))
	{
		alert(dispStr+"不合法：必须以'http:\/\/'或'https:\/\/'开头!");
		obj.focus();
		return false;
	}

	var  urlpatern2= /^https?:\/\/(([a-zA-Z0-9_-])+(\.)?)*(:\d+)?.+$/i;
	if(!urlpatern2.test(str))
	{
		alert(dispStr+"端口号必须为数字且应在1－65535之间!");
		obj.focus();
		return false;
	}


	var	urlpatern1 =/^https?:\/\/(([a-zA-Z0-9_-])+(\.)?)*(:\d+)?(\/((\.)?(\?)?=?&?[a-zA-Z0-9_-](\?)?)*)*$/i;

	if(!urlpatern1.test(str))
	{
		alert(dispStr+"不合法,请检查!");
		obj.focus();
		return false;
	}

	var s = "0";
	var t =0;
     var re = new RegExp(":\\d+","ig");
	while((arr = re.exec(str))!=null)
	{

		s = str.substring(RegExp.index+1,RegExp.lastIndex);

		if(s.substring(0,1)=="0")
		{
			alert(dispStr+"端口号不能以0开头!");
			obj.focus();
			return false;
		}

		t = parseInt(s);
		if(t<1 || t >65535)
		{
			alert(dispStr+"端口号必须为数字且应在1－65535之间!");
			obj.focus();
			return false;
		}


	}
	return true;
}

// 函数名：checkVisibleEnglishChr
// 功能介绍：检查是否为可显示英文字符(
// !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~)
// 参数说明：要检查的字符串
// 返回值：true|false
function checkVisibleEnglishChr(strTemp)
{
	var i;
	for(i=0;i<strTemp.length;i++)
	{
		if ((strTemp.charCodeAt(i)<32) || (strTemp.charCodeAt(i)>126))
			return false;
	}
	return true;
}
// 函数名：checkareaInput
// 功能介绍：检查是否含有非textarea字符
// 参数说明：要检查的字符串
// 返回值：false：含有 true：全部为可textarea字符
function checkareaInput(str)
{
	var notinput = "<>";
 var i;
 for (i = 0; notinput != null && i < notinput.length; i++) {
     if (str.indexOf(notinput.charAt(i)) >= 0) {// 若有
       return false;
     }
   }
 return true;

}
// 函数名：checkInputChr
// 功能介绍：检查是否含有非Input字符
// 参数说明：要检查的字符串
// 返回值：false：含有 true：全部为可Input字符
function checkInputChr(str)
{
	var notinput = "\"'<>@#$%^&*()";
 var i;
 for (i = 0; notinput != null && i < notinput.length; i++) {
     if (str.indexOf(notinput.charAt(i)) >= 0) {// 若有
       return false;
     }
   }
 return true;

}
// 函数名：checktextareaInput
// 功能介绍：检查是否含有非Input字符<,>
// 参数说明：要检查的字符串
// 返回值：false：含有 true：全部为可Input字符
function checktextareaInput(str)
{
	var notareainput = "<>_";
 var i;
 for (i = 0; notareainput != null && i < notareainput.length; i++) {
     if (str.indexOf(notareainput.charAt(i)) >= 0) {// 若有
       return false;
     }
   }
 return true;

}
// 函数名：checkFormdata
// 功能介绍：检查Form对象
// 参数说明：
// obj：要检查的对象，
// name：要检查的对象的中文名称，
// length：检查的对象的长度（<0不检查），
// notnull:为true则检查非空，
// notSpecChar:为true则检查有无特殊字符，
// notChinessChar:为true则检查有无中文字符，
// numOrLetter:为true则检查只能为数字或英文字母，
// pNumber:为true则检查只能为正整数，
// 返回值：false：检查不通过 true：全部为可Input字符
function checkFormdata(obj,name,length,notnull,notSpecChar,notChinessChar,numOrLetter,pNumber){
	// 检查对象
	if (!obj) {alert("目标不是对象，处理失败!");return false;}
	var msg;
	var ilen;
	// 检测汉字
     if (notChinessChar&&(checkChinese(obj.value) != 1)){
        msg=name+"不能包含汉字！";
        alert(msg);
        obj.focus();
        return false;
       }
     // 检测特殊字符
      if(notSpecChar){
     	if(obj.type=="textarea"){
    			 if(!checktextareaInput(obj.value)){
	     			 var noinput = " < > _ ";
       			msg=name+"有非法字符（"+noinput+"）！";
       			alert(msg);
       			obj.focus();
       			return false;
     			}

		}
		else{
     		if(!checkInputChr(obj.value)){
	     		 var notinput = " \" ' < > @ # $ % ^ & * ( )";
       		msg=name+"有非法字符（"+notinput+"）！";
       		alert(msg);
     		  obj.focus();
      		 return false;
     		}

   		  }
	}
     // 检测长度
     if(length>=0&&(checkLength(obj.value)>length)){
       ilen=length/2;
       if(pNumber){
           msg=name+"不能超过"+length+"个数字！";
       }else if(notChinessChar){
           msg=name+"不能超过"+length+"个英文！";
       }else if(numOrLetter){
           msg=name+"不能超过"+length+"个英文或数字！";
       }else{
           msg=name+"不能超过"+length+"个英文或"+ilen+"个汉字！";
       }
       alert(msg);
       obj.focus();
       return false;
     }
     // 检测非空
	if(notnull&&obj.value.trim()==""){
         msg="请输入"+name+"！";
         alert(msg);
	    obj.focus();
	    return false;
	}
     // 检测只能为数字或英文字母
     re = /[\W_]/;
     if (numOrLetter&&re.exec(obj.value)) {
         msg=name+"只能为数字或英文字母！";
         alert(msg);
	    obj.focus();
         return false;
     }
     // 检测只能为只能为正整数
     re = /[\D_]/;
     if (pNumber&&re.exec(obj.value)) {
         msg=name+"只能为正整数！";
         alert(msg);
	    obj.focus();
         return false;
     }

	return true;

}
// 函数名：checkFormdata
// 功能介绍：检查Form对象
// 参数说明：
// obj：要检查的对象，
// name：要检查的对象的中文名称，
// length：检查的对象的长度（<0不检查），
// notnull:为true则检查非空，
// notSpecChar:为true则检查有无特殊字符，
// notChinessChar:为true则检查有无中文字符，
// numOrLetter:为true则检查只能为数字或英文字母，
// pNumber:为true则检查只能为正整数，
// 返回值：false：检查不通过 true：全部为可Input字符
function checkFormdata1120(obj,name,length,notnull,notSpecChar,notChinessChar,numOrLetter,pNumber){
	// 检查对象
	if (!obj) {alert("目标不是对象，处理失败!");return false;}
	var msg;
	var ilen;
	// 检测汉字
     if (notChinessChar&&(checkChinese(obj.value) != 1)){
        msg=name+"不能包含汉字！";
        alert(msg);
        obj.focus();
        return false;
       }
     // 检测特殊字符
      if(notSpecChar){
     	if(obj.type=="textarea"){
    			 if(!checktextareaInput(obj.value)){
	     			 var noinput = " < > ";
       			msg=name+"有非法字符（"+noinput+"）！";
       			alert(msg);
       			obj.focus();
       			return false;
     			}

		}
		else{
     		if(!checkInputChr(obj.value)){
	     		 var notinput = " \" ' < > @ # $ % ^ & * ( )";
       		msg=name+"有非法字符（"+notinput+"）！";
       		alert(msg);
     		  obj.focus();
      		 return false;
     		}

   		  }
	}
     // 检测长度
     if(length>=0&&(checkLength(obj.value)>length)){
       ilen=length/2;
       if(pNumber){
           msg=name+"不能超过"+length+"个数字！";
       }else if(notChinessChar){
           msg=name+"不能超过"+length+"个英文！";
       }else if(numOrLetter){
           msg=name+"不能超过"+length+"个英文或数字！";
       }else {
           msg=name+"不能超过"+length+"个英文或"+ilen+"个汉字！";
       }
       alert(msg);
       obj.focus();
       return false;
     }
     // 检测非空
	if(notnull&&obj.value.trim()==""){
         msg="请输入"+name+"！";
         alert(msg);
	    obj.focus();
	    return false;
	}
     // 检测只能为数字或英文字母
     re = /[\W_]/;
     if (numOrLetter&&re.exec(obj.value)) {
         msg=name+"只能为数字或英文字母！";
         alert(msg);
	    obj.focus();
         return false;
     }
     // 检测只能为只能为正整数
     re = /[\D_]/;
     if (pNumber&&re.exec(obj.value)) {
         msg=name+"只能为正整数！";
         alert(msg);
	    obj.focus();
         return false;
     }

	return true;

}

// 函数名：ValidateIP
// 功能介绍：验证ip地址格式
// 参数说明：
// obj：要检查的对象，
// name：要检查的对象的中文名称，
function ValidateIP(name,obj,notnull){
	var msg="";
	if(obj==null){
		 msg="对象不能为空！";
		 return false;
	}
	// 检测非空
	if(notnull&&obj.value.trim()==""){
		 msg="请输入"+name+"！";
		 alert(msg);
		 obj.focus();
		 return false;
	}
	right_title="是一个非法的IP地址段！\nIP段为：xxx.xxx.xxx.xxx（xxx为0-255)！";
	re=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
	
	if(!re.test(obj.value)){
		 msg=name+right_title;
		 alert(msg);
		 obj.focus();
		 return false;
	}
	return true;
}
//函数名：checkMaxLength
//功能介绍：检查字符串的长度，并提示错误信息
//参数说明：obj为需要验证的对象，title为对象的名称，length为对象的最大长度
function checkMaxLength(obj,title,length)
{
	if(checkLength(obj.value)>length)
		{
			alert(title+"最大长度不能超过"+length);
			obj.focus();
			return false;
		}
		else
		{
			return true;
		}
}
// 函数名：checkMaxLengthChar
// 功能介绍：检查字符串的长度，并提示错误信息 校验字符并要去掉回车
// 参数说明：obj为需要验证的对象，title为对象的名称，length为对象的最大长度
function checkMaxLengthChar(obj,title,length)
{
	var strBuffer = obj.value;
	var countr = 0;
	for(var i=0 ;i< strBuffer.length;i++){
       var c = strBuffer.charAt(i);
       if(c=='\r'){
           countr++;
       }
    }
	if(obj.value.length - countr > length)
		{
			alert(title+"最大长度不能超过"+length);
			obj.focus();
			return false;
		}
		else
		{
			return true;
		}
}
/**
 * 是否只包含数字和字母.
 * @param obj
 * @returns {Boolean}
 */
function CheckNC(obj){
	var reg = /[a-zA-Z0-9]+-/;
	if(obj.value.match(reg) == null){
		return true;
	}
	return false;
}