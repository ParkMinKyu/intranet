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
			<div class="head">
				<c:if test="${ !empty schedule }">
					${schedule.title }
				</c:if>
				<c:if test="${ empty schedule }">
					해당 글이 없습니다.
				</c:if>
			</div>
			<div class="body">
			<span id="modal-contents">
				<c:if test="${ !empty schedule }">
					<div class="label label-red" style="min-width:300px;">
						<script>
							var stime = '${schedule.starttime }';
							var etime = '${schedule.endtime }';
							stime = stime.split(' ')[0]; 
							etime = etime.split(' ')[0]; 
							if(stime == etime){
								document.write(stime);	
							}else{
								document.write(stime + " ~ " + etime);
							}
						</script>
					</div>
					<div class="notify contents-view" style="margin-top:5px;">
						${schedule.contents }
					</div>
				</c:if>
				<c:if test="${ empty schedule }">
					해당 글이 없습니다.
				</c:if>
			</span>
			</div>
		</div>
	</div>
</body>
</html>