<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
</head>
<body>
	<div class="jui" style="width: 800px;margin: 0 auto;">
		<div class="panel" style="display: inline-block;width: 100%;position: relative;text-align: left;text-decoration: none;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;box-sizing: border-box;font-size: 12px;color: #333;">
			<div class="head" style="height: 24px;line-height: 22px;padding: 4px 15px;background-color: #f5f5f5;background-image: -moz-linear-gradient(top,#f5f5f5 0,#f1f1f1 100%);background-image: linear-gradient(top,#f5f5f5 0,#f1f1f1 100%);background-image: -webkit-linear-gradient(top,#f5f5f5 0,#f1f1f1 100%);background-image: -o-linear-gradient(top,#f5f5f5 0,#f1f1f1 100%);background-image: -ms-linear-gradient(top,#f5f5f5 0,#f1f1f1 100%);border: 1px solid #dcdcdc;-webkit-border-top-left-radius: 5px;-moz-border-radius-topleft: 5px;border-top-left-radius: 5px;-webkit-border-top-right-radius: 5px;-moz-border-radius-topright: 5px;border-top-right-radius: 5px;">
				${title}
			</div>
			<div class="body" style="background-color: #fff;border: 1px solid #dcdcdc;padding: 15px;overflow: auto;text-overflow: clip;white-space: normal;-webkit-border-bottom-left-radius: 5px;-moz-border-radius-bottomleft: 5px;border-bottom-left-radius: 5px;-webkit-border-bottom-right-radius: 5px;-moz-border-radius-bottomright: 5px;border-bottom-right-radius: 5px;">
				${name}(${email})님의 비밀번호가<br>
				<font color="red">${passwd}</font>로 변경 되었습니다.<br>
				<a href="http://116.84.12.57:8080/uriel/user/loginPage.do">사이트 바로가기</a>
			</div>
		</div>
	</div>
</body>
</html>