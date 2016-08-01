<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta name="baidu-site-verification" content="" />
<title>走秀网-维权列表</title>
<meta content="走秀网" name="keywords">
<meta content="" name="description">
<link rel="stylesheet" href="../resources/css/lst_base.css?v=2014091701" />
<link rel="stylesheet" href="../resources/css/lst_common.css?v=2014091701" />
<script type="text/javascript" src="../resources/js/zepto.min.js" /></script>
<script type="text/javascript">
/**全选和反选 */
$(function(){
	$("#checkAll").click(function(){
		if ($("#checkAll").attr("checked")) {
			$("input[name='checkboxList']").attr("checked",true);
		}else{
			$("input[name='checkboxList']").removeAttr("checked");
		}
	});
});

function checkAllBoxStatus(){
	var sum = 0;
	$("input[name='checkboxList']").each(function(){
		if($(this).attr("checked")){
			sum++;
		}
	});
	if(sum == $("input[name='checkboxList']").size()){
		$("#checkAll").attr("checked",true);
	}else{
		$("#checkAll").removeAttr("checked");
	}
	return;
}
</script>
</head>
<body>
	 <div class="adminMain_main">
	 	<form theme="simple" action="" id="searchForm">
			<div class="adminMain_wrap">
				<div class="adminContent clearfix">
					<div class="adminContent clearfix">
						<table width="100%" class="table_bg01 table_hg01">
							<thead>
								<tr>
									<c:if test="${feedbackList == null }">
									<th width="5%"></th>
									</c:if>
									<c:if test="${ feedbackList != null }">
									<th width="5%"><input type="checkbox" name="checkAll" id="checkAll" /></th>
									</c:if>
									<th width="13%">用户微信ID</th>
									<th width="10%">公众号ID</th>
									<th width="5%">通知类型</th>
									<th width="12%">投诉单号</th>
									<th width="13%">交易订单号</th>
									<th width="25%">用户投诉原因</th>
									<th width="5%">处理状态</th>
									<th width="15%">创建日期</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${feedbackList == null }">
								<tr><td align="center" style="font-size:12px;" colspan="8">本&nbsp;&nbsp;列&nbsp;&nbsp;表&nbsp;&nbsp;暂&nbsp;&nbsp;无&nbsp;&nbsp;数&nbsp;&nbsp;据.</td></tr>
								</c:if>
								<c:if test="${ feedbackList != null }">
								<c:forEach var="feedback" items="${feedbackList }" varStatus="i">
								<tr>
									<td><input type="checkbox" name="checkboxList" value="${feedback.feedbackId}" onchange="checkAllBoxStatus();"/></td>
									<td>${feedback.openId }</td>
									 <td>${feedback.appId }</td>
									 <td>${feedback.msgType }</td>
									 <td>${feedback.feedbackId }</td>
									 <td>${feedback.transId }</td>
									 <td>${feedback.reason }</td>
									 <td>${feedback.state }</td>
									 <td><fmt:formatDate value="${feedback.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								</tr>
								</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</form>
	 </div>
</body>
</html>
