<%@ page language="java"  contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi">
<title>업무 시스템</title>
<link rel="stylesheet" href="<c:url value="/resources/jui/jui.min.css"/>"/>
<link rel="stylesheet" href="<c:url value="/resources/lib/animate.min.css"/>"/>
<link rel="stylesheet" href="<c:url value="/resources/lib/main.css"/>"/>
<script type="text/javascript" src="<c:url value="/resources/SE/js/HuskyEZCreator.js"/>" charset="utf-8"></script>
<script src="<c:url value="/resources/lib/jquery-1.8.3.min.js"/>"></script>
<script src="<c:url value="/resources/lib/ajaxfileupload.js"/>"></script>
<script src="<c:url value="/resources/jui/jui.min.js"/>"></script>
</head>
<body>
	<div class="jui" style="width: 1250px;margin: 0 auto;">
		<div class="panel">
			<div class="head"><i class="icon-new-window"></i>사용중 오류가 발생하였습니다.</div>
			<div class="body">
				<c:if test="${code == 'parsererror' }">
					경과시간이 오래되어 자동으로 로그아웃 되었습니다.<br>
				</c:if>
				error code: ${code }<br>
				<a href="<c:url value="/user/loginPage.do"/>">로그인 화면으로</a>
			</div>
		</div>
	</div>
</body>
</html>