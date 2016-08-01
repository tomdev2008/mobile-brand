<!DOCTYPE html >
<html>
<head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_menu.css" />
</head>
<body>
  <div class="adminTop">
		<div class="adminTop_left">
			<a href="${rc.getContextPath()}/menu/index" target="_top">
			<img src="${rc.getContextPath()}/resources/manager/images/menu_logo.gif" alt="XIU2.0客服服务中心" />
			</a>
		</div>
		<div class="adminTop_right">
			<p class="rTop">
				<span>
					<script>
						var now = new Date();
			            var SY = now.getYear();
			            SY = 1900>SY?(1900+SY):SY;
			            var SM = now.getMonth();
			            var SD = now.getDate();
			            function weekday(){
			                var day = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
			                return(day[now.getDay()]);
			            }
			           document.write(SY+'-'+(SM+1)+'-'+SD);
			           document.write("&#160;"+weekday());
					</script>
				</span>
				<#if user??>
				    <span>欢迎您：
			    	
			    		<strong class="fma f14 fb">
			    		     ${(user.username!'')?html }
			    		</strong>
			    	
			        </span>
				    <span><a href="${rc.getContextPath()}/menu/index" target="_top" title="系统首页">系统首页</a></span>
				    <span><a href="${rc.getContextPath()}/user/editpwd?id=${user.id}&param=top" target="mainFrame" title="修改密码">修改密码</a></span>
				    <span><a href="${rc.getContextPath()}/login/index" target="_top" title="退出登录">退出登录</a></span>
				<#else>
				    <span>
			    		<strong class="fma f14 fb">
			    		     请您重新登录系统！
			    		</strong>
			       </span>
			       <span><a href="${rc.getContextPath()}/login/index" target="_top" title="重新登录">重新登录</a></span>
				</#if>
			    
			</p>
		</div>
	</div>
</body>
</html>