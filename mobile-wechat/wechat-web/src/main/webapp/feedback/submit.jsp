<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<body>
	<%
		request.getRequestDispatcher("/feedback/submit").forward(request, response);
	%>
</body>
</html>
