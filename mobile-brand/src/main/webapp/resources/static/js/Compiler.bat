TITLE ѹ���ļ�����By-Anson.Chen@xiu.com
@ECHO OFF
::version:334
::���õ�ǰ·��
PATH="%SYSTEMROOT%\System32"
::PATH="E:\svn\ui-v2\trunk\UI-release\static\js\xiu"
cls
echo.
echo. 
echo  ���Ƚ�compiler.jar����C �̸�Ŀ¼
echo.
echo  ȷ�������������
pause > nul
:jsMenu
::ѡ��˵�
	
	cls
	echo.
	echo   0.ѹ��productInfoFG.min.js
	echo.
	echo   1.ѹ��productInfo_20130619.min.js
	echo.   
	echo   2.ѹ��productInfoImg.min.js ���Ŀǰ��������productInfo_20130619.min.js�滻��
	echo.
	echo   3.ѹ��xiu.pop.min.js
	echo.
	echo   4.ѹ��head.min.js
	echo.
	echo   5.ѹ��index.2.5.min.js
	echo.
	echo   6.ѹ��page.famous.min.js
	echo.
	echo   7.ѹ��login.min.js
	echo.
	echo   8.ѹ��conver_ag_media.min.js
	echo.
	echo   9.ѹ��page.search.min.js
	echo. 
	::echo  ----------- ����������ҳ��ʹ��---------------
	::echo.
	echo   a.ѹ��order-items-common.min.js
	echo. 
	echo   b.ѹ��order.submit.min.js
	echo.
	echo   c.ѹ��loginShpopping.min.js
	echo.
	::echo  ----------- /portal/head.min.js---------------
	::echo. 
	echo   d.ѹ�� portal head.min.js
	echo. 
	echo  ------------------------------------------------------------------
	::echo  ----------- /portal/head.min.js---------------
	::echo. 
	echo   e.ѹ�� ��ҳ index_201304.min.js
	echo. 
	echo  ------------------------------------------------------------------
	::pause > nul
	echo.
	SET /P menuz= ��ѡ��:

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
	echo��������������ȷ���ֻ���ĸ
	echo.
	PAUSE
	goto jsMenu

:cmpif
::ѹ��productInfoFG.min.js
	cls
	echo. ��ʼѹ��  jqzoomFG.js + productInfoPortalFG.js + productInfoCmsFG.js  
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js jqzoomFG.js -js productInfoPortalFG.js -js productInfoCmsFG.js -js_output_file productInfoFG.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo productInfoFG.min.js ѹ����� 
	echo ��iMage��Ʒ����ҳ����
	echo    �����������...
	PAUSE > nul
	goto jsMenu
	
:cmpi
::ѹ��productInfo.min.js
	cls
	echo. ��ʼѹ�� productInfo.js + jqzoom.js + share_thirdParty.js  
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js productInfo.js -js jqzoom.js -js share_thirdParty.js -js_output_file productInfo_20130619.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo productInfo_20130619.min.js ѹ����� 
	echo ��iMage��Ʒ����ҳ����
	echo    �����������...
	PAUSE > nul
	goto jsMenu

:cmpim
::ѹ��productInfoImg.min.js
	cls
	echo ��ʼѹ�� productInfoImg.js + jqzoom.js + share_thirdParty.js  
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js productInfoImg.js -js jqzoom.js -js share_thirdParty.js -js_output_file productInfoImg.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo productInfoImg.min.js ѹ�����
	echo iMage��Ʒ����ҳ����
	echo �����������...
	PAUSE > nul
	goto jsMenu
	
:cmpxp
::ѹ��xiu.pop.min.js
	cls
	echo ��ʼѹ�� loginPop.js + xiupop.js 
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js loginPop.js -js xiupop.js -js_output_file xiu.pop.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo xiu.pop.min.js ѹ�����
	echo ����ҳ�����������̵�½������ʹ��
	echo �����������...
	PAUSE > nul
	goto jsMenu

:cmphm
::ѹ��head.min.js
	cls
	echo ��ʼѹ�� common.setting.js + json.js + click_xiu.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js common.setting.js -js json.js -js click_xiu.js -js_output_file head.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo head.min.js ѹ�����
	echo ��ҳ������ҳ������ҳ�͵�½ҳ����
	echo ����·�� http://www.xiustatic.com/static/js/xiu/head.min.js 
	echo ����·�� https://login.xiu.com/assets/static/js/xiu/head.min.js
	echo �����������...
	PAUSE > nul
	goto jsMenu	

:cmpindm
::ѹ��index.2.5.min.js
	cls
	echo ��ʼѹ�� autocomplete.js  +  index.2.5.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js autocomplete.js -js index.2.5.js -js_output_file index.2.5.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo index.2.5.min.js ѹ�����
	echo ��ҳ����ͨ/iMage����ҳ������ҳ����
	echo �����������...
	PAUSE > nul
	goto jsMenu			
	
:cmppfm
::ѹ��page.famous.min.js
	cls
	echo ��ʼѹ�� common.xiu.js + lazyload.js + page.famous.login.js 
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js common.xiu.js -js lazyload.js -js page.famous.login.js -js_output_file page.famous.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo page.famous.min.js ѹ�����
	echo ��Ʒ������Ʒ����ҳ����
	echo �����������...
	PAUSE > nul
	goto jsMenu	

:cmplm
::ѹ��login.min.js
	cls
	echo ��ʼѹ�� jqueryCookie.js + global.js + login.js + delWCSInvalidateCookie.js + lazyload.js + loginPop.js + xiupop.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js jqueryCookie.js -js global.js -js login.js -js delWCSInvalidateCookie.js -js lazyload.js -js loginPop.js -js xiupop.js -js_output_file login.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo login.min.js ѹ�����
	echo ��½ҳ��ʹ�� 
	echo �����������...
	PAUSE > nul
	goto jsMenu			

:cmpcam
::ѹ��conver_ag_media.min.js
	cls
	echo ��ʼѹ�� queryCookie.js + conversion.js + agwebtracking_224.js + media.js  
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js jqueryCookie.js -js conversion.js -js agwebtracking_224.js -js media.js -js_output_file conver_ag_media.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo conver_ag_media.min.js ѹ�����
	echo �����������...
	PAUSE > nul
	goto jsMenu	

:cmppsm
::ѹ��page.search.min.js
	cls
	echo ��ʼѹ�� global.js + validation.js + treeList.js + ScrollTopSearch.js + loadMenu.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js global.js -js validation.js -js treeList.js -js ScrollTopSearch.js -js loadMenu.js -js_output_file page.search.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo  page.search.min.js ѹ�����
	echo searchҳ��ʹ�� 
	echo �����������...
	PAUSE > nul
	goto jsMenu	

	
:cmpoic
::ѹ�� order-items-common.min.js
	cls
	echo ��ʼѹ�� order-items-common.js + xiu.orderShopp.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js order-items-common.js -js xiu.orderShopp.js -js_output_file order-items-common.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo order-items-common.min.js ѹ�����
	echo ����������ҳ��ʹ��
	echo �����������...
	PAUSE > nul
	goto jsMenu		

:cmpos
::ѹ��order.submit.min.js
	cls
	echo ��ʼѹ�� jquery_form.js + address.js + xiuvalidform.js + ConfirmOrder.js + orderSubmit.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js jquery_form.js -js address.js -js xiuvalidform.js -js ConfirmOrder.js -js orderSubmit.js -js_output_file order.submit.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo order.submit.min.js ѹ�����
	echo ����������ҳ��ʹ��
	echo �����������...
	PAUSE > nul
	goto jsMenu	
	
:cmpls
::ѹ��loginShpopping.min.js
	cls
	echo ��ʼѹ�� loginShpopping.js + AG_Tracking.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js loginShpopping.js -js AG_Tracking.js -js json2.js -js_output_file loginShpopping.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo loginShpopping.min.js ѹ�����
	echo ����������ҳ��ʹ��
	echo �����������...
	PAUSE > nul
	goto jsMenu	
	
:cmpphm
::ѹ��portal head.min.js
	cls
	echo ��ʼѹ�� common.setting.js + json.js + click_xiu.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js setting.js -js json.js -js click_xiu.js -js_output_file head.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo head.min.js ѹ�����
	echo �ļ���portal�鵥��ά��
	echo ����·�� http://www.xiustatic.com/static/js/portal/head.min.js
	echo �����������...
	PAUSE > nul
	goto jsMenu	
	
:cmpnhi
::e.ѹ�� ��ҳ index_201304.min.js
	cls
	echo ��ʼѹ�� index201210/jq.Slide.js + index201210/tabs.app.js
	echo  ------------------------------------------------------------------
		java -jar C:\compiler.jar -js index201210/jq.Slide.js -js index201210/tabs.app.js -js_output_file index_20130517.min.js  --charset utf-8
	echo  ------------------------------------------------------------------
	echo index_201304.min.js ѹ�����
	echo 201304�°���ҳ�õ���JS
	echo �����������...
	PAUSE > nul
	goto jsMenu			