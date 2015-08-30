<%@ page language="java"  contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>업무 시스템</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/jui/jui.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/lib/animate.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/lib/main.css"/>
<link href='${pageContext.request.contextPath}/resources/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='${pageContext.request.contextPath}/resources/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src="${pageContext.request.contextPath}/resources/SE/js/HuskyEZCreator.js" type="text/javascript" charset="utf-8"></script>
<script src='${pageContext.request.contextPath}/resources/lib/jquery.min.js'></script>
<script src='${pageContext.request.contextPath}/resources/lib/jquery-ui.custom.min.js'></script>
<script src='${pageContext.request.contextPath}/resources/fullcalendar/fullcalendar.min.js'></script>
<script src="${pageContext.request.contextPath}/resources/lib/niee-canvas-chart003.js"></script>
<script src="${pageContext.request.contextPath}/resources/lib/ajaxfileupload.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/base.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/core.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ui/button.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ui/combo.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ui/datepicker.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ui/dropdown.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ui/modal.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/uix/table.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/uix/tree.js"></script>
</head>
<body>
<div class="jui" style="width: 1250px;margin: 0 auto;">
<input type="hidden" id="selectUser">
<input type="hidden" id="selectTab" value="tab_Home">
<!-- 탭 -->
	<ul class="tab tab-top">
		<li class="active">
			<a href="#" id="tab_View" title="main_View">Schedule</a>
		</li>
		<li >
			<a href="#" id="tab_Home" title="main_Home">Employee</a>
		</li>
		<li><a href="#" id="tab_Lib" title="main_Lib">자료실</a></li>
		<li><a href="#" id="tab_Etc" title="main_Etc">Etc</a></li>
	</ul>
<!-- 스케줄 글보기 모달 -->
	<div id="modal" class="msgbox" style="display: none;">
		<div class="head">
			<span id="modal-title"></span>
	    </div>
		<div class="body">
			<span id="modal-contents"></span>
			<div style="text-align: center; margin-top: 45px;" id="contentsBtn">
			</div>
		</div>
	</div>
<!-- 게임모달 -->
	<div id="gameModal" class="msgbox" style="display: none;">
		<div class="head">
			<span id="game-title">지렁이게임</span>
	    </div>
		<div class="body">
			<div id="gameBody"></div>
			<div style="text-align: center; margin-bottom: 10px;">
				<a href="#" class="btn btn-gray btn-small" onclick='javascript:gameModal.hide();'>Close</a>
			</div>
		</div>
	</div>
	<!-- schedule view modal -->
	<div id="sviewModal" class="msgbox" style="display: none;">
		<div class="head">
			<span id="sviewModal-title"></span>
	    </div>
		<div class="body">
			<div class="contents-view" id="sviewModal-contents"></div>
			<div style="text-align: center; margin-bottom: 10px;">
				<a href="#" class="btn btn-gray btn-small" onclick='javascript:sviewModal.hide();'>Close</a>
			</div>
		</div>
	</div>
	<!-- 통계 모달 -->
	<div id="chartModal" class="msgbox" style="display: none;">
		<div class="head">
			<span id="chart-title">사용자별 스케쥴 통계</span>
	    </div>
		<div class="body">
			<div id="chartBody">
				<canvas id="canvas" width="500" height="400"></canvas>
			</div>
			<div style="text-align: center; margin-bottom: 10px;">
				<a href="#" class="btn btn-gray btn-small" onclick='javascript:chartModal.hide();'>Close</a>
			</div>
		</div>
	</div>
<!-- 파일등록모달 -->
	<div id="fileModal" class="msgbox" style="display: none;">
		<div class="head">
			<span id="file-title">파일 등록하기</span>
	    </div>
		<div class="body">
			<div id="file-contents">
				한번에 하나씩 등록해주세요.<br>
				<br>
				<br>
				<input type="file" name="file" id="file">
			</div>
			<div style="text-align: center; margin-top: 45px;" id="fileBtn">
				<a href="#" class="btn btn-gray btn-small" onclick='javascript:fileUpload();'>등록</a>
				<a href="#" class="btn btn-gray btn-small" onclick='javascript:fileModal.hide();'>Close</a>
			</div>
		</div>
	</div>
<!-- 비번변경모달 -->
	<div id="passwdModal" class="msgbox" style="display: none;">
		<div class="head">
			<span id="passwd-title">비밀번호 변경</span>
	    </div>
		<div class="body">
			<div id="passwd-contents">
				<input class="input input-rect" type="password" id="oldpass" name="oldpass" style="width: 612px" maxlength="100" placeholder="현재 비밀번호"/>
				<br>
				<br>
				<input class="input input-rect" type="password" id="newpass" name="newpass" style="width: 612px" maxlength="100" placeholder="새로운 비밀번호"/>
			</div>
			<div style="text-align: center; margin-top: 45px;" id="passBtn">
				<a href="#" class="btn btn-gray btn-small" onclick='javascript:changePasswd();'>변경</a>
				<a href="#" class="btn btn-gray btn-small" onclick='javascript:passwdModal.hide();'>Close</a>
			</div>
		</div>
	</div>
	<!-- 스케줄등록모달 -->
	<div id="writeModal" class="msgbox" style="display: none;">
		<div class="head">
			<span>등록 폼</span>
	    </div>
		<div class="body">
			<table>
				<tr>
					<td>
						<input class="input input-rect" id="title" name="title" style="width: 612px" maxlength="100" placeholder="제목"/>
					</td>
				</tr>
				<tr>
					<td>
						<div id="schedulePicker">
						<div style="float: left;">
							<input class="input input-rect" id="starttime" name="starttime" style="width: 192px" placeholder="시작일" readonly="readonly"/>
							<div id="spicker" class="datepicker">
							    <div class="head">
							        <div class="prev"></div>
							        <div class="title"></div>
							        <div class="next"></div>
							    </div>
							    <table class="body">
							        <tr>
							            <th>S</th><th>M</th><th>T</th><th>W</th><th>T</th><th>F</th><th>S</th>
							        </tr>
							    </table>
							</div>
						</div>
						<div style="float: left;margin-top: 100px;margin-left: 100px;">
							<a class="btn btn-gray btn-small"><i class="icon-chevron-right"></i></a>
						</div>
						<div style="float: right;">
						<input class="input input-rect" id="endtime" name="endtime" style="width: 192px" placeholder="종료일" readonly="readonly"/>
						<script data-jui="#spicker" data-tpl="dates" type="text/template">
    						<tr>
        						<! for(var i = 0; i < dates.length; i++) { !>
        						<td><!= dates[i] !></td>
        						<! } !>
    						</tr>
						</script>
						<div id="epicker" class="datepicker">
						    <div class="head">
						        <div class="prev"></div>
						        <div class="title"></div>
						        <div class="next"></div>
						    </div>
						    <table class="body">
						        <tr>
						            <th>S</th><th>M</th><th>T</th><th>W</th><th>T</th><th>F</th><th>S</th>
						        </tr>
						    </table>
						</div>
						<script data-jui="#epicker" data-tpl="dates" type="text/template">
    						<tr>
        						<! for(var i = 0; i < dates.length; i++) { !>
        						<td><!= dates[i] !></td>
        						<! } !>
    						</tr>
						</script>
						</div>
						</div>
					</td>
				</tr>
				<tr>
					<td style="border-spacing: 0px;border-collapse: 0px;border: 1px solid #BEBeBe;">
						<span id="schedulefileName"></span>
						<div id="scheduleFiles">
							<input type="file" name="schedulefile" id="schedulefile"><br>
							<a href="#" id="scheduleFileAddBtn" class="btn btn-gray btn-small">파일등록</a>(파일선택 후 등록해야 글 저장시 함께 등록 됩니다.)
						</div>
					</td>
				</tr>
				<tr>
					<td style="border-spacing: 0px;border-collapse: 0px;height:25px;border: 1px solid #BEBeBe;">
						공유 여부 : <input type="checkbox" id="etcYn"><font color="red">(체크시 Etc 에 표시 되지 않습니다.)</font>
					</td>
				</tr>
				<tr>
					<td>
						<textarea class="input" id="contents" name="contents" style="width: 610px;height: 300px;" placeholder="내용"></textarea>
					</td>
				</tr>
			</table>
			<div style="text-align: center;">
				<a href="#" id="writeBtn" class="btn btn-gray btn-small">저장</a>
				<a href="#" id="writeClose" class="btn btn-gray btn-small">Close</a>
			</div>
		</div>
	</div>
	<!-- 메일등록모달 -->
	<div id="mailModal" class="msgbox" style="display: none;">
		<div class="head">
			<span>메일전송 폼</span>
	    </div>
		<div class="body">
			<table>
				<tr>
					<td>
						<input class="input input-rect" id="mail-title" name="mail-title" style="width: 612px" maxlength="100" placeholder="제목"/>
					</td>
				</tr>
				<tr>
					<td style="border-spacing: 0px;border-collapse: 0px;border: 1px solid #BEBeBe;">
						To : <span id="mailTo"></span><br><br>
						C.C : <div id="cclist" style="width: 612px;"></div>
						<ul class="tree_1 tree tree-arrow"></ul>
		  				<script id="tpl_tree" type="text/template">
							<li>
								<div><i></i> <!= title !></div>
								<ul></ul>
							</li>
						</script>
					</td>
				</tr>
				<tr>
					<td style="border-spacing: 0px;border-collapse: 0px;border: 1px solid #BEBeBe;">
						<span id="fileName"></span>
						<div id="mailFiles">
							<input type="file" name="mailfile" id="mailfile"><br>
							<a href="#" id="mailFileAddBtn" class="btn btn-gray btn-small">파일등록</a>(파일선택 후 등록해야 글 저장시 함께 등록 됩니다.)
						</div>
					</td>
				</tr>
				<tr>
					<td style="border-spacing: 0px;border-collapse: 0px;border: 1px solid #BEBeBe;">
						<font color="red">메일 전송</font> : <input type="checkbox" id="mail-yn" name="mail-yn"/>(체크시 작성한 내용이 메일로 발송됩니다.)<br>
					</td>
				</tr>
				<tr>
					<td>
						<textarea class="input" id="mail-contents" name="mail-contents" style="width: 610px;height: 300px;" placeholder="내용"></textarea>
					</td>
				</tr>
			</table>
			<div style="text-align: center;">
				<a href="#" id="mailBtn" class="btn btn-gray btn-small">저장</a>
				<a href="#" id="mailClose" class="btn btn-gray btn-small">Close</a>
			</div>
		</div>
	</div>
	<!-- 내용영역 -->
	<div class="panel main-container">
		<div class="head">
			<i class="icon-home"></i>업무 시스템(&nbsp;<span id="head-year"></span>년&nbsp;<span id="head-month"></span>월&nbsp;<span id="head-day"></span>일&nbsp;<span id="head-hour"></span>시&nbsp;<span id="head-min"></span>분&nbsp;)
			<a class="btn btn-mini btn-gray-black" style="width: 70px;" id="btnLogout"><span>logout</span><i class="icon-upload icon-edge"></i></a>
			<a class="btn btn-gray btn-small" id="sessionBtn"><span id="sessionText">세션유지(꺼짐)</span><i class="icon-gear"></i></a>
			<a class="btn btn-mini btn-gray-black" style="float:right" id="changePasswd">
				<span>비밀번호 변경</span>&nbsp;<i class="icon-refresh"></i>
			</a>
			<c:if test="${sessionScope.isAdmin}">
				<a class="btn btn-mini btn-gray-black" style="float:right" id="addEmployee">
					<span>사용자 추가</span>&nbsp;<i class="icon-gear"></i>
				</a>
				
				<!-- 사용자 추가 모달 -->
				<div id="userModal" class="msgbox" style="display: none;">
					<div class="head">
						<span id="userModal-title">
							사용자 추가
						</span>
				    </div>
					<div class="body">
						이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름 : <input class="input input-rect" type="text" id="user-name" name="user-name" maxlength="100" placeholder="이름"/><br>
						메일주소 : <input class="input input-rect" type="email" id="user-email" name="user-email" maxlength="100" placeholder="id@host.com"/><br>
						전화번호 : <input class="input input-rect" type="text" id="user-phone" name="user-phone" maxlength="100" placeholder="전화번호"/><br>
						<font color="red">(최초 비밀번호는 123456입니다.)</font><br>
					</div>
					<div style="text-align: center; margin-bottom: 5px;">
						<a href="#" class="btn btn-gray btn-small" id="addUserBtn">추가</a>
						<a href="#" class="btn btn-gray btn-small" onclick='javascript:userModal.hide();'>Close</a>
					</div>
				</div>
				
			</c:if>
		</div>
		<div class="body">
		<!-- 홈내용 -->
		<div id="main_Home" style="display: none;">
	    <div class="msg" style="margin-bottom: 5px;">
	    	<div id="searchCombo" class="combo">
				<a class="btn btn-gray">Select...</a>
				<a class="btn btn-gray btn-toggle"><i class="icon-arrow2"></i></a>
				<ul>
					<li value="3">이름</li>
					<li value="4">전화번호</li>
					<li value="5">메일주소</li>
				</ul>
			</div>
			<input class="input input-rect" id="eText"><a class="btn btn-gray" id="employeeSearch">검색</a>
	    </div>
	    <div class="user-work notify">
			<div style="width: 90%;height:90%;margin: 10px auto;">
				<div class="group">
					<a class="btn btn-mini btn-gray-black" id="work-email"><span id="work-name"></span>&nbsp;<i class="icon-document"></i></a>
					<a class="right btn btn-mini btn-gray-black" id="work-refresh"><span>새로고침</span>&nbsp;<i class="icon-refresh"></i></a>
				</div>
				<table id="userArticle" class="table table-classic" style="width: 100%;margin-top: 5px;">
					<colgroup>
						<col width="45px">
						<col width="*">
						<col width="60px">
						<col width="60px">
						<col width="40px">
						<col width="105px">
					</colgroup>
					<thead>
						<tr>
							<th>Seq.</th>
							<th>Title</th>
							<th>FROM</th>
							<th>TO</th>
							<th>확인</th>
							<th>RegTime</th>
						</tr>
					</thead>
					<tbody id="userArticleBody"></tbody>
				</table>
				<div id="userArticlePaging" class="paging" style="width: 100%; margin-top: 3px;">
				</div>
				<div style="margin: 10px auto;width: 400px;">
					<div id="userRadio" class="group">
						<a class="btn btn-small btn-gray-purple" value="title">제목</a>
						<a class="btn btn-small btn-gray-purple" value="contents">내용</a>
						<a class="btn btn-small btn-gray-purple" value="writer">FROM</a>
						<a class="btn btn-small btn-gray-purple" value="receiver">TO</a>
					</div>
					<input class="input input-rect" id="mText"><a class="btn btn-purple-gray" id="userMailSearch">검색</a>
				</div>
				<script data-jui="#userArticle" data-tpl="row" type="text/template">
					<tr>
						<td><!= seq !></td>
						<td><!= title !></td>
						<td><!= name !></td>
						<td><!= receivername !></td>
						<td><!= viewYn !></td>
						<td><!= regtime !></td>
					</tr>
				</script>
			</div>
		</div>
		</div>
		<!-- ETc영역 -->
		<div id="main_Etc" style="display: none;">
			<div>
				<div class="group" >
					<a class="left btn btn-mini btn-gray-black" id="etc-refresh"><span>새로고침</span>&nbsp;<i class="icon-refresh"></i></a>
					<a class="left btn btn-mini btn-gray-black" id="etc-chart"><span>통계</span>&nbsp;<i class="icon-gear"></i></a>
					<a class="left btn btn-mini btn-gray-black" id="etc-game"><span>지렁이 게임</span>&nbsp;<i class="icon-image"></i></a>
				</div>
				<span id="etcSelect">
					<select id="syear">
						<script>
							var date = new Date();
							for(var i = (date.getFullYear()-1) ; i > (date.getFullYear()-2) ; i --){
								document.write("<option value='"+i+"'>"+i+"년</option>");
							}
							document.write("<option selected='selected' value='"+date.getFullYear()+"'>"+date.getFullYear()+"월</option>");
							for(var i = (date.getFullYear()+1) ; i < (date.getFullYear()+2) ; i ++){
								document.write("<option value='"+i+"'>"+i+"년</option>");
							}
						</script>
					</select>
					<select id="smonth">
						<script>
							var date = new Date();
							for(var i = 0 ; i < 12 ; i ++){
								if(i == date.getMonth()){
									document.write("<option selected='selected' value='"+(i+1)+"'>"+(i+1)+"월</option>");
								}else{
									document.write("<option value='"+(i+1)+"'>"+(i+1)+"월</option>");
								}
							}
						</script>
					</select>
					~
					<select id="eyear">
						<script>
							var date = new Date();
							for(var i = (date.getFullYear()-1) ; i > (date.getFullYear()-2) ; i --){
								document.write("<option value='"+i+"'>"+i+"년</option>");
							}
							document.write("<option selected='selected' value='"+date.getFullYear()+"'>"+date.getFullYear()+"월</option>");
							for(var i = (date.getFullYear()+1) ; i < (date.getFullYear()+2) ; i ++){
								document.write("<option value='"+i+"'>"+i+"년</option>");
							}
						</script>
					</select>
					<select id="emonth">
						<script>
							var date = new Date();
							for(var i = 0 ; i < 12 ; i ++){
								if(i == date.getMonth()){
									document.write("<option selected='selected' value='"+(i+1)+"'>"+(i+1)+"월</option>");
								}else{
									document.write("<option value='"+(i+1)+"'>"+(i+1)+"월</option>");
								}
							}
						</script>
					</select>
					<a class="left btn btn-mini btn-gray-black" id="etc-search" onclick="getEtc()"><span>검색</span></a>
				</span>
				<div class="group" style="float: right;">
					<a class="left btn btn-mini btn-gray-black" id="etc-print"><span>출력</span>&nbsp;<i class="icon-document"></i></a>
				</div>
			</div>
			<div id="etc-contents" style="margin-top: 5px;">
				
			</div>
		</div>
	<!-- 자료실영역 -->
		<div id="main_Lib" style="display: none;">
			<div>
				<div class="group" >
					<a class="left btn btn-mini btn-gray-black" id="lib-refresh"><span>새로고침</span>&nbsp;<i class="icon-refresh"></i></a>
					<a class="left btn btn-mini btn-gray-black" id="lib-fileadd"><span>파일 추가</span>&nbsp;<i class="icon-add-dir"></i></a>
				</div>
			</div>
			<div id="lib-contents" style="margin-top: 5px;">
				<table id="fileArticle" class="table table-classic" style="width: 100%;margin-top: 5px;">
					<colgroup>
						<col width="5%">
						<col width="*">
						<col width="15%">
						<col width="15%">
					</colgroup>
					<thead>
						<tr>
							<th>Seq</th>
							<th>FileName</th>
							<th>Uploader</th>
							<th>Regtime</th>
						</tr>
					</thead>
					<tbody id="filebody">
					</tbody>
				</table>
				<script data-jui="#fileArticle" data-tpl="row" type="text/template">
					<tr>
						<td><!= seq !></td>
						<td>
							<!= realname !>
							<div class="group" style="float:right;">
								<a class="left btn btn-mini btn-gray-black" seq="<!= seq !>" name="<!= realname !>" id="file-download"><span>Download</span>&nbsp;<i class="icon-download"></i></a>
								<a class="left btn btn-mini btn-gray-black" seq="<!= seq !>" name="<!= realname !>" id="file-delete"><span>Delete</span>&nbsp;<i class="icon-close"></i></a>
							</div>
						</td>
						<td><!= name !></td>
						<td><!= regtime !></td>
					</tr>
				</script>
			</div>
		</div>
		<!-- Schedule_View -->
		<div id="main_View" style="display: block;">
			<div class="group" >
				<a class="left btn btn-mini btn-gray-black" id="sview-refresh"><span>새로고침</span>&nbsp;<i class="icon-refresh"></i></a>
				(날짜의 빈공간을 클릭하면 스케줄을 등록 할 수 있습니다.)
			</div>
			<div id='schcalendar'></div>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
function getContextPath(){
    var context = '${pageContext.request.contextPath}'
    return context;
}
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/intranet_JUI.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/intranet_JQUERY.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/intranet_FUNCTION.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/intranet_DATE.js"></script>
</body>
</html>