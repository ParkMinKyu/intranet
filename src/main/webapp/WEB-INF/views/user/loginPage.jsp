<%@ page language="java"  contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<title>업무 시스템</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/jui/jui.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/lib/main.css"/>
<script src="${pageContext.request.contextPath}/resources/lib/jquery-1.8.3.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/jui/jui.min.js"></script>
<script type="text/javascript">
jui.ready(function(ui, uix, _) {
	var table_1 = uix.table("#table_1", {
    	animate: true
    });
});
$(function(){
	$('#loginForm').on("submit",function(){
		if($('#email').val() == '' ){
			alert("아이디를 입력하세요.");
			return false;
		}
		if($('#passwd').val() == ''){
			alert("비밀번호를 입력하세요.");
			return false;
		}
		return true;
	});
	$('#loginBtn').click(function(){
		$('#loginForm').submit();
		/* $.ajax({
			url : "",
			data : {email:$('#email').val(),passwd:$('#passwd').val()},
			type : 'post',
			success:function(response){
				var result = JSON.parse(response);
				if(result.isLogin){
					location.href = '<c:url value="/home/main.do"/>';
				}else{
					alert(result.msg);
				}
			},
			error:function(response){
				console.log(response);
			}
		}); */
	});
	
	$('#findPasswd').click(function(){
		var email = prompt("가입 하신 메일 주소를 입력하세요.","");
		if(confirm(email + ' 이 가입한 메일 주소가 맞습니까?')){
			$.ajax({
				url : "<c:url value="/user/findPasswd.do"/>",
				data : {email:email},
				type : 'post',
				success:function(response){
					var result = response;
					if(result.success){
						alert(response.msg);
					}else{
						alert(response.msg);
					}
				}
			});
		}
	});
});
</script>
</head>
<body>
	<div class="jui" style="width: 1250px;margin: 0 auto;">
		<div class="panel">
			<div class="head"><i class="icon-new-window"></i>로그인 페이지</div>
			<div class="body">
			<form action="<c:url value="/user/login"/>" method="post" id="loginForm">
				<table id="table_1" class="table table-classic" style="width: 50%;margin: 5px auto;" >
					<thead>
						<tr>
							<th align="center">아이디</th>
							<th align="center">비밀번호</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td align="center"><input type="email" id="email" name="email" placeholder="email@email.com" class="input input-large input-black"></td>
							<td align="center"><input type="password" id="passwd" name="passwd" class="input input-large input-black"></td>
						</tr>
						<c:if test="${!empty errMsg }">
						<tr>
							<td colspan="2" align="center">
								${errMsg }
							</td>
						</tr>
						</c:if>
						<tr>
							<td colspan="2" align="center">
								<a class="btn btn-gray-black" id="findPasswd">비밀번호찾기<i class="icon-check"></i></a>
								<a class="btn btn-gray-black" id="loginBtn" href='#'>로그인<i class="icon-edit"></i></a>
								<input id="remember_me" name="_spring_security_remember_me" type="checkbox"/>Remember me
							</td>
						</tr>

						<tr>
							<td colspan="2" align="center">
								<div class="navbar navbar-black-flat" style="overflow: hidden;">
									<div class="inline-left">
										<span style="color: white;">본 사이트는 HTML5를 지원하는 브라우저에 최적화 되었습니다.(IE는 9이상부터)</span>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			</div>
		</div>
	</div>
	<div class="git-group">
		<div>
			<iframe src="https://ghbtns.com/github-btn.html?user=ParkMinKyu&amp;repo=sudoku&amp;type=star&amp;count=true" frameborder="0" scrolling="0" width="75px" height="20px"></iframe>
			<iframe src="https://ghbtns.com/github-btn.html?user=ParkMinKyu&amp;repo=sudoku&amp;type=fork&amp;count=true" frameborder="0" scrolling="0" width="75px" height="20px"></iframe>
			<iframe src="https://ghbtns.com/github-btn.html?user=ParkMinKyu&amp;type=follow&amp;count=true" frameborder="0" scrolling="0" width="165px" height="20px"></iframe>
		</div>
	</div>
</body>
</html>