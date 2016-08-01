function gotoPage(){
	$("form").submit();
}

function prePage(){
	var pageNo = $("#pageNo").val();
	if (pageNo == 1) {
		alert("当前已是第一页");
	}else{
		$("#pageNo").val(pageNo-1);
		$("form").submit();
	}
}

function nextPage(){
	var pageNo = parseInt($("#pageNo").val());
	var totalPage = parseInt($("#totalPage").val());
	if (pageNo == totalPage) {
		alert("当前已是最后一页");
	}else{
		$("#pageNo").val(pageNo+1);
		$("form").submit();
	}
}