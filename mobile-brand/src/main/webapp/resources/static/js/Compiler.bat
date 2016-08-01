TITLE 压缩文件管理By-Anson.Chen@xiu.com
@ECHO OFF
::version:334
::设置当前路径
PATH="%SYSTEMROOT%\System32"
::PATH="E:\svn\ui-v2\trunk\UI-release\static\js\xiu"
cls
echo.
echo. 
echo  请先将compiler.jar放在C 盘根目录
echo.
echo  确定后按任意键继续
pause > nul
:jsMenu
::选择菜单
	
	cls
	echo.
	echo   0.压缩productInfoFG.min.js
	echo.
	echo   1.压缩productInfo_20130619.min.js
	echo.   
	echo   2.压缩productInfoImg.min.js 这个目前线上已用productInfo_20130619.min.js替换。
	echo.
	echo   3.压缩xiu.pop.min.js
	echo.
	echo   4.压缩head.min.js
	echo.
	echo   5.压缩index.2.5.min.js
	echo.
	echo   6.压缩page.famous.min.js
	echo.
	echo   7.压缩login.min.js
	echo.
	echo   8.压缩conver_ag_media.min.js
	echo.
	echo   9.压缩page.search.min.js
	echo. 
	::echo  ----------- 主购物流程页面使用---------------
	::echo.
	echo   a.压缩order-items-common.min.js
	echo. 
	echo   b.压缩order.submit.min.js
	echo.
	echo   c.压缩loginShpopping.min.js
	echo.
	::echo  ----------- /portal/head.min.js---------------
	::echo. 
	echo   d.压缩 portal head.min.js
	echo. 
	echo  ------------------------------------------------------------------
	::echo  ----------- /portal/head.min.js---------------
	::echo. 
	echo   e.压缩 首页 index_201304.min.js
	echo. 
	echo  ------------------------------------------------------------------
	::pause > nul
	echo.
	SET /P menuz= 请选择:

		IF %menuz%==1 (goto cmpi)
		IF %menuz%==0 (goto cmpif)
		IF %menuz%==2 (goto cmpim)
		IF %menuz%==3 (goto cmpxp)
		IF %menuz%==4 (goto cmphm)
		IF %menuz%==5 (goto cmpindm)
		IF %menuz%==6 (goto cmppfm)
		IF %menuz%==7 (goto cmplm)
		IF %menuz%==8 (goto cmpcam)
		IF %menuz%==9 (goto cmppsm)
		IF %menuz%==a (goto cmpoic)
		IF %menuz%==b (goto cmpos)
		IF %menuz%==c (goto cmpls)
		IF %menuz%==d (goto cmpphm)
		IF %menuz%==e (goto cmpnhi)
	echo.	
	echo　　　请输入正确数字或字母
	echo.
	PAUSE
	goto jsMenu

:cmpif
::压缩productInfoFG.min.js
	cls
	echo. 开始压缩  jqzoomFG.js + productInfoPortalFG.js + productInfoCmsFG.js  
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js jqzoomFG.js -js productInfoPortalFG.js -js productInfoCmsFG.js -js_output_file productInfoFG.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo productInfoFG.min.js 压缩完成 
	echo 非iMage商品详情页引用
	echo    按任意键继续...
	PAUSE > nul
	goto jsMenu
	
:cmpi
::压缩productInfo.min.js
	cls
	echo. 开始压缩 productInfo.js + jqzoom.js + share_thirdParty.js  
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js productInfo.js -js jqzoom.js -js share_thirdParty.js -js_output_file productInfo_20130619.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo productInfo_20130619.min.js 压缩完成 
	echo 非iMage商品详情页引用
	echo    按任意键继续...
	PAUSE > nul
	goto jsMenu

:cmpim
::压缩productInfoImg.min.js
	cls
	echo 开始压缩 productInfoImg.js + jqzoom.js + share_thirdParty.js  
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js productInfoImg.js -js jqzoom.js -js share_thirdParty.js -js_output_file productInfoImg.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo productInfoImg.min.js 压缩完成
	echo iMage商品详情页引用
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu
	
:cmpxp
::压缩xiu.pop.min.js
	cls
	echo 开始压缩 loginPop.js + xiupop.js 
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js loginPop.js -js xiupop.js -js_output_file xiu.pop.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo xiu.pop.min.js 压缩完成
	echo 详情页和主购物流程登陆弹出框使用
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu

:cmphm
::压缩head.min.js
	cls
	echo 开始压缩 common.setting.js + json.js + click_xiu.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js common.setting.js -js json.js -js click_xiu.js -js_output_file head.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo head.min.js 压缩完成
	echo 首页，详情页，搜索页和登陆页引用
	echo 引用路径 http://www.xiustatic.com/static/js/xiu/head.min.js 
	echo 引用路径 https://login.xiu.com/assets/static/js/xiu/head.min.js
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu	

:cmpindm
::压缩index.2.5.min.js
	cls
	echo 开始压缩 autocomplete.js  +  index.2.5.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js autocomplete.js -js index.2.5.js -js_output_file index.2.5.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo index.2.5.min.js 压缩完成
	echo 首页，普通/iMage详情页，搜索页引用
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu			
	
:cmppfm
::压缩page.famous.min.js
	cls
	echo 开始压缩 common.xiu.js + lazyload.js + page.famous.login.js 
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js common.xiu.js -js lazyload.js -js page.famous.login.js -js_output_file page.famous.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo page.famous.min.js 压缩完成
	echo 名品特卖商品详情页引用
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu	

:cmplm
::压缩login.min.js
	cls
	echo 开始压缩 jqueryCookie.js + global.js + login.js + delWCSInvalidateCookie.js + lazyload.js + loginPop.js + xiupop.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js jqueryCookie.js -js global.js -js login.js -js delWCSInvalidateCookie.js -js lazyload.js -js loginPop.js -js xiupop.js -js_output_file login.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo login.min.js 压缩完成
	echo 登陆页面使用 
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu			

:cmpcam
::压缩conver_ag_media.min.js
	cls
	echo 开始压缩 queryCookie.js + conversion.js + agwebtracking_224.js + media.js  
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js jqueryCookie.js -js conversion.js -js agwebtracking_224.js -js media.js -js_output_file conver_ag_media.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo conver_ag_media.min.js 压缩完成
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu	

:cmppsm
::压缩page.search.min.js
	cls
	echo 开始压缩 global.js + validation.js + treeList.js + ScrollTopSearch.js + loadMenu.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js global.js -js validation.js -js treeList.js -js ScrollTopSearch.js -js loadMenu.js -js_output_file page.search.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo  page.search.min.js 压缩完成
	echo search页面使用 
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu	

	
:cmpoic
::压缩 order-items-common.min.js
	cls
	echo 开始压缩 order-items-common.js + xiu.orderShopp.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js order-items-common.js -js xiu.orderShopp.js -js_output_file order-items-common.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo order-items-common.min.js 压缩完成
	echo 主购物流程页面使用
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu		

:cmpos
::压缩order.submit.min.js
	cls
	echo 开始压缩 jquery_form.js + address.js + xiuvalidform.js + ConfirmOrder.js + orderSubmit.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js jquery_form.js -js address.js -js xiuvalidform.js -js ConfirmOrder.js -js orderSubmit.js -js_output_file order.submit.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo order.submit.min.js 压缩完成
	echo 主购物流程页面使用
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu	
	
:cmpls
::压缩loginShpopping.min.js
	cls
	echo 开始压缩 loginShpopping.js + AG_Tracking.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js loginShpopping.js -js AG_Tracking.js -js json2.js -js_output_file loginShpopping.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo loginShpopping.min.js 压缩完成
	echo 主购物流程页面使用
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu	
	
:cmpphm
::压缩portal head.min.js
	cls
	echo 开始压缩 common.setting.js + json.js + click_xiu.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js setting.js -js json.js -js click_xiu.js -js_output_file head.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo head.min.js 压缩完成
	echo 文件由portal组单独维护
	echo 引用路径 http://www.xiustatic.com/static/js/portal/head.min.js
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu	
	
:cmpnhi
::e.压缩 首页 index_201304.min.js
	cls
	echo 开始压缩 index201210/jq.Slide.js + index201210/tabs.app.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js index201210/jq.Slide.js -js index201210/tabs.app.js -js_output_file index_20130517.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo index_201304.min.js 压缩完成
	echo 201304新版首页用到的JS
	echo 按任意键继续...
	PAUSE > nul
	goto jsMenu			