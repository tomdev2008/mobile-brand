/*+++++++++++++++++
+ FileName :setting.js
+ XIU.com 配置文件
+ Anson.chen@xiu.com
+ v0.5
+ 2011-8-19
++++++++++++++++++*/

var url = [];

url['wcsurl'] = 'http://portal.xiu.com/business/';
url['cmsurl'] = 'http://www.xiu.com/';
// 联合登录
url['union'] = 'http://union.xiu.com/';

// 参数
url['parm'] = {
	langId : -7,
	storeId : 10154,
	catalogId : 10101
};

// 随机数
url['nowDate'] = Number(new Date());
url['random'] = Math.round(Math.random() * 10000);

// 参数
url['parms'] = $.param(url['parm']);

// 登录JSON URL
url['login'] = url['wcsurl'] + "XUserCmd?" + url['parms'] + '&jsoncallback=?';

// 检查是否登录
url['checkloginurl'] = url['wcsurl'] + "checkloginControlCmd?" + url['parms']
		+ "&timeStamp=" + url['nowDate'] + "&jsoncallback=?";
url['loginfloaturl'] = url['wcsurl'] + "loginControlCmd?" + url['parms']
		+ "&timeStamp=" + url['nowDate'] + "&jsoncallback=?";
// 判断用户是否登录
url['checkloginControl'] = url['wcsurl'] + "checkloginControlCmd?"
		+ url['parms'] + "&jsoncallback=?";

// 判断用户是否登录(名品特卖)
url['jsCheckloginControl'] = url['wcsurl'] + "chanelCheckLoginCmd?"
		+ url['parms'];

// 购物车JSON URL
url['shoppingCart'] = url['wcsurl'] + "XOrderItemCmd?" + url['parms']
		+ '&jsoncallback=?';

url['shoppingCartNum'] = url['wcsurl'] + "XOrderItemAmountCmd?" + url['parms']
		+ "&jsoncallback=?";

// 搜索关键字 JSON URL
url['searchKey'] = url['wcsurl']
		+ 'XAutoSuggest?coreName=MC_10001_CatalogEntry_zh_CN&jsoncallback=?';

// 搜索表单 URL
url['searchForm'] = "http://search.xiu.com/s?kw=";

// 登录 HTML
url['loginHTML'] = 'https://login.xiu.com/';

// 注册 HTML
url['regHTML'] = 'https://login.xiu.com/reg';

// 去购物袋并结算

url['cartHTML'] = url['wcsurl'] + "XOrderItemDisplayCmd?" + url['parms']
		+ "&sellType=5";

// 删除购物车请求
url['cartDelete'] = url['wcsurl'] + "XOrderItemDeleteForIndexCmd?"
		+ url['parms'];

// 购物袋全部放入收藏夹
url['favoHTML'] = "/userCenter/favorites.shtml";

// 登出 HTML
url['logoutHTML'] = url['wcsurl'] + "LogoutCmd?" + url['parms']
		+ '&jsoncallback=?';
// 详情页跳转 HTML
url['detail_jump'] = url['wcsurl'] + "PrdDetailURLProcessCmd?" + url['parms']
		+ '&jsoncallback=?';
// 我的走秀 菜单 HTML
url['myXiu'] = {
	url : 'http://my.xiu.com/',
	// 子菜单列表
	list : [
			{
				name : "我的订单",
				url : "http://my.xiu.com/business/OrderManageCmd?"+ url['parms'] + '&querySign=2'
			},
			{
				name : "我的收藏夹",
				url : "http://my.xiu.com/business/InterestIitemListCmd?"+ url['parms'] + '&opType=query'
			} 
	]
};
