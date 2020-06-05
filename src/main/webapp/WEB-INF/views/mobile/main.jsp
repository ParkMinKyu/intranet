<%@ page language="java"  contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>업무 시스템</title>
<link rel="stylesheet" href="/uriel/resources/jui/jui.min.css"/>
<link rel="stylesheet" href="/uriel/resources/lib/animate.min.css"/>
<script src="/uriel/resources/lib/jquery-1.8.3.min.js"></script>
<script src="/uriel/resources/jui/jui.min.js"></script>
<!--[if lt IE 9]>
 <script type="text/javascript" src="/uriel/resources/lib/html5.js"></script>
<![endif]-->

<style type="text/css">
*{
	font-family: "돋움";
	font-size: 12px;
	font-weight: normal;
}

font{
	font-weight: bold;
}

div > img{
	width:60px;
	float:right;
	height:60px;
}

div.main-container{
	width: 1200px;
	padding: 5px 5px 5px 5px;
	margin: 0 auto;
}

div.clicked{
	opacity:1 !important;
}

div.user-work{
	float:right;
	width: 780px;
	display : none;
}

.contents-view{
	min-width: 300px;
	min-height: 300px;
	padding: 5px;
	max-height: 500px;
	max-width: 800px;
	overflow-y: scroll;
	overflow-x: hidden;
}
</style>
</head>
<body>
<div class="jui" style="width: 1250px;margin: 0 auto;">
<input type="hidden" id="selectUser">
<input type="hidden" id="selectTab" value="tab_Home">
	<ul class="tab tab-top">
		<!-- li>
			<a href="#" id="tab_Home" title="main_Home">Home</a>
		</li -->
		<li class="active"><a href="#" id="tab_Schedule" title="main_Schedule">Schedule</a></li>
		<li><a href="#" id="tab_Etc" title="main_Etc">Etc</a></li>
	</ul>

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
	
	<div id="writeModal" class="msgbox" style="display: none;">
		<div class="head">
			<span id="modal-title">등록 폼</span>
	    </div>
		<div class="body">
			<table>
				<tr>
					<td>
						<input class="input input-rect" id="title" name="title" style="width: 500px" maxlength="100" placeholder="제목"/>
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
						<div style="float: left;margin-top: 100px;margin-left: 40px;">
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
					<td>
						<textarea class="input" id="contents" name="contents" style="width: 500px;height: 300px;" placeholder="내용"/></textarea>
					</td>
				</tr>
			</table>
			<div style="text-align: center;">
				<a href="#" id="writeBtn" class="btn btn-gray btn-small">저장</a>
				<a href="javascript:writeModal.hide();" id="modalBtn" class="btn btn-gray btn-small">Close</a>
			</div>
		</div>
	</div>
	
	<div class="panel main-container">
		<div class="head">
			<i class="icon-home"></i>업무 시스템(&nbsp;<span id="head-year"></span>년&nbsp;<span id="head-month"></span>월&nbsp;<span id="head-day"></span>일&nbsp;<span id="head-hour"></span>시&nbsp;<span id="head-min"></span>분&nbsp;)
			<a class="btn btn-mini btn-gray-black" style="width: 70px;" id="btnLogout"><span>logout</span><i class="icon-upload icon-edge"></i></a>
		</div>
		<div class="body">
		
		<!-- div id="main_Home">
	    <div class="msg" style="margin-bottom: 5px;">
	    	<div id="searchCombo" class="combo">
				<a class="btn btn-gray">Select...</a>
				<a class="btn btn-gray btn-toggle"><i class="icon-arrow2"></i></a>
				<ul>
					<li value="1">회사명</li>
					<li value="2">부서명</li>
					<li value="3">사원명</li>
					<li value="4">전화번호</li>
					<li value="5">메일주소</li>
				</ul>
			</div>
			<input class="input input-rect"><a class="btn btn-gray">검색</a>
	    </div>
	    <div class="user-work notify">
			<div style="width: 90%;height:90%;margin: 10px auto;">
				<div class="group">
					<a class="btn btn-mini btn-gray-black" id="work-email"><span id="work-name"></span>&nbsp;<i class="icon-document"></i></a>
				</div>
				<div class="group">
					<a class="left btn btn-mini btn-gray-black" id="work-write"><span>글쓰기</span>&nbsp;<i class="icon-edit"></i></a>
				</div>
				<table id="userArticle" class="table table-classic" style="width: 100%;margin-top: 5px;">
					<colgroup>
						<col width="45px">
						<col width="*">
						<col width="60px">
						<col width="105px">
					</colgroup>
					<thead>
						<tr>
							<th>Seq.</th>
							<th>Title</th>
							<th>Writer</th>
							<th>RegTime</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
				<div id="userPaging" class="paging" style="width: 100%; margin-top: 3px;">
					<a href="#" class="prev">Previous</a>
					<a href="#" class="prev">Previous</a>
					<div class="list"></div>
					<a href="#" class="next">Next</a>
					<a href="#" class="next">Next</a>
				</div>
				<script data-jui="#userPaging" data-tpl="pages" type="text/template">
					<! for(var i = 0; i < pages.length; i++) { !>
						<a href="#" class="page"><!= pages[i] !></a>
					<! } !>
				</script>
				<div style="margin: 10px auto;width: 400px;">
					<div id="userRadio" class="group">
						<a class="btn btn-small btn-gray-purple" value="title">제목</a>
						<a class="btn btn-small btn-gray-purple" value="writer">작성자</a>
						<a class="btn btn-small btn-gray-purple" value="contents">내용</a>
					</div>
					<input class="input input-rect"><a class="btn btn-purple-gray">검색</a>
				</div>
				<script data-jui="#userArticle" data-tpl="row" type="text/template">
					<tr>
						<td><!= seq !></td>
						<td><!= title !></td>
						<td><!= writer !></td>
						<td><!= regtime !></td>
					</tr>
				</script>
			</div>
		</div>
		</div-->
		
		<div id="main_Schedule">
			<div id="datepicker" class="datepicker">
			    <div class="head">
			        <div class="prev"></div>
			        <div class="title"></div>
			        <div class="next"></div>
			    </div>
			    <table class="body" style="width: 100%">
			        <tr>
			            <th>S</th><th>M</th><th>T</th><th>W</th><th>T</th><th>F</th><th>S</th>
			        </tr>
			    </table>
			<script data-jui="#datepicker" data-tpl="dates" type="text/template">
    			<tr>
        			<! for(var i = 0; i < dates.length; i++) { !>
        			<td><!= dates[i] !></td>
        			<! } !>
    			</tr>
			</script>
			</div>
			<div class="schedule-work notify" style="float: right;width: 1000px;margin-top: -170px;">
			<div style="width: 90%;height:90%;margin: 10px auto;">
				<div class="group">
					<a class="left btn btn-mini btn-gray-black" id="schedule-write"><span>Schedule 등록</span>&nbsp;<i class="icon-edit"></i></a>
					<a class="right btn btn-mini btn-gray-black" id="schedule-refresh"><span>새로고침</span>&nbsp;<i class="icon-refresh"></i></a>
				</div>
				<table id="scheduleArticle" class="table table-classic" style="width: 100%;margin-top: 5px;">
					<colgroup>
						<col width="45px">
						<col width="*">
						<col width="60px">
						<col width="105px">
						<col width="105px">
					</colgroup>
					<thead>
						<tr>
							<th>Seq.</th>
							<th>Title</th>
							<th>writer</th>
							<th>Starttime</th>
							<th>Endtime</th>
						</tr>
					</thead>
					<tbody id="schedulebody">
					</tbody>
				</table>
				<script data-jui="#scheduleArticle" data-tpl="row" type="text/template">
					<tr>
						<td><!= seq !></td>
						<td><!= title !></td>
						<td><!= writer !></td>
						<td><!= starttime !></td>
						<td><!= endtime !></td>
					</tr>
				</script>
				
				<div id="schedulePaging" class="paging" style="width: 100%; margin-top: 3px;">
				</div>
				
				<div style="margin: 10px auto;width: 400px;">
					<div id="scheduleRadio" class="group">
						<a class="btn btn-small btn-gray-purple" value="title">제목</a>
						<a class="btn btn-small btn-gray-purple" value="writer">작성자</a>
						<a class="btn btn-small btn-gray-purple" value="contents">내용</a>
					</div>
					<input class="input input-rect" id="sText"><a class="btn btn-purple-gray" id="scheduleSearch">검색</a>
				</div>
			</div>
		</div>
		</div>
		
		<div id="main_Etc" style="display: none;">
			<div>
				<div class="group" >
					<a class="left btn btn-mini btn-gray-black" id="etc"><span>일일업무</span>&nbsp;<i class="icon-edit"></i></a>
					<a class="left btn btn-mini btn-gray-black" id="etc"><span>주간업무</span>&nbsp;<i class="icon-edit"></i></a>
					<a class="left btn btn-mini btn-gray-black" id="etc"><span>월간업무</span>&nbsp;<i class="icon-edit"></i></a>
					<a class="left btn btn-mini btn-gray-black" id="etc"><span>분기업무</span>&nbsp;<i class="icon-edit"></i></a>
				</div>
				<div class="group" style="float: right;">
					<a class="left btn btn-mini btn-gray-black" id="etc"><span>출력</span>&nbsp;<i class="icon-document"></i></a>
				</div>
			</div>
			<div class="notify" style="height: 700px;margin-top: 5px;">
				
			</div>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
var modal;
var writeModal;
var searchCombo;
var userArticle;
var userRadio;
var scheduleArticle;
var scheduleRadio;
var datepicker;
var spicker;
var epicker;
var userPaging;
var schedulePaging;
var scheduleParam = {};
var vo = { 
		url:'<c:url value="/home/company.do"/>', 
		id:'COMPANY', 
		seq : "seq", 
		name : "회사명", 
		address : "주소", 
		phone : "대표번호", 
		email : "대표메일", 
		nextVo : { 
			url:'<c:url value="/home/department.do"/>', 
			id:'DEPARTMENT', 
			seq : "seq", 
			name : "부서명", 
			leader : "부서장", 
			phone : "부서번호", 
			email : "부서메일", 
			nextVo : { 
				url:'<c:url value="/home/employee.do"/>', 
				id:'EMPLOYEE', 
				seq : "seq", 
				name : "사원명", 
				level : "직급", 
				phone : "전화번호", 
				email : "메일주소"
			}
		}
	};

jui.ready(function(ui, uix, _) {
	modal = ui.modal("#modal", {
		color: "black",
		autoHide : false
	});
	
	writeModal = ui.modal("#writeModal", {
		color: "black",
		autoHide : false
	});
	
	searchCombo = ui.combo("#searchCombo", {
		width: 200,
		event: {
			change: function(data) {
				alert("text(" + data.text + "), value(" + data.value + ")");
			}
		},
		position: "bottom"
	});

	var userRadio_options2 = {
		type: "radio",
		event: {
			change: function(data) {
				alert("index(" + data.index + "), value(" + data.value + ")");
			}
		}
	};
	
	userRadio = ui.button("#userRadio", userRadio_options2);
	
	var scheduleRadio_options2 = {
		type: "radio"
	};
	
	scheduleRadio = ui.button("#scheduleRadio", scheduleRadio_options2);
	
	datepicker = ui.datepicker("#datepicker", {
	    titleFormat: "yyyy년 MM월",
	    format: "yyyy/MM/dd",
	    event: {
	       select: function(date, e) {
	    	   refrashRow(scheduleArticle,{url: '<c:url value="/home/scheduleArticle.do"/>', param:{page : 1, today : date}});
	       },
	       prev: function(e) {
	       },
	       next: function(e) {
	       }
	    }
	});
	
	spicker = ui.datepicker("#spicker", {
	    titleFormat: "yyyy년 MM월",
	    format: "yyyy/MM/dd",
	    event: {
	       select: function(date, e) {
	    	   if(spicker.getTime() > epicker.getTime()){
					var sdate = new Date(spicker.getTime());
		    		epicker.select(sdate.getFullYear(),sdate.getMonth()+1,sdate.getDate());
		    		$('#starttime').val(date);
				}else{
					$('#starttime').val(date);
				}
	       },
	       prev: function(e) {
	    	   var sdate = new Date(spicker.getTime()-(1000*60*60*24*30));
	    	   var date = new Date(spicker.getTime()); 
	    	   if( date.getMonth() != new Date().getMonth()){
	    		   spicker.select(sdate.getFullYear(),sdate.getMonth()+1,sdate.getDate());
	    	   }else{
	    		   spicker.select(date.getFullYear(),date.getMonth()+1,date.getDate());
	    	   }
	       },
	       next: function(e) {
	    	   var sdate = new Date(spicker.getTime()+(1000*60*60*24*30));
    		   spicker.select(sdate.getFullYear(),sdate.getMonth()+1,sdate.getDate());
	       }
	    }
	});

	epicker = ui.datepicker("#epicker", {
	    titleFormat: "yyyy년 MM월",
	    format: "yyyy/MM/dd",
	    event: {
	       select: function(date, e) {
	    	   if(spicker.getTime() > epicker.getTime()){
	    		alert("종료일이 시작일보다 빠릅니다.");
	    		var sdate = new Date(spicker.getTime());
	    		epicker.select(sdate.getFullYear(),sdate.getMonth()+1,sdate.getDate());
	    	   }else{
	    	   	$('#endtime').val(date);
	    	   }
	       },
	       prev: function(e) {
	    	   var edate = new Date(epicker.getTime()-(1000*60*60*24*30));
	    		epicker.select(edate.getFullYear(),edate.getMonth()+1,edate.getDate());
	       },
	       next: function(e) {
	    	   var edate = new Date(epicker.getTime()+(1000*60*60*24*30));
	    		epicker.select(edate.getFullYear(),edate.getMonth()+1,edate.getDate());
	       }
	    }
	});
	
	userArticle = uix.table("#userArticle", {
    	animate: true
    });
	
	scheduleArticle = uix.table("#scheduleArticle", {
    	animate: true
    });
	
	userPaging = ui.paging("#userPaging", {
		count: 2000,
		pageCount: 15,
		screenCount: 15, 
		event: {
			page: function(pNo,o,j) {
				refrashRow(userArticle, {url: '<c:url value="/home/userArticle.do"/>', param:{page : pNo, email : $('#selectUser').val()}});
			}
		}
	});
	
	/* schedulePaging = ui.paging("#schedulePaging", {
		count:10,
		pageCount: 15,
		screenCount: 15, 
		event: {
			page: function(pNo) {
				refrashRow(scheduleArticle, {url: '<c:url value="/home/scheduleArticle.do"/>', param:{page : pNo, today : datepicker.getFormat()}});
			}
		}
	}); */
});

$(function(){
	getJSON(vo,{seq:1});
	
	$('#starttime').val(spicker.getFormat());
	$('#endtime').val(epicker.getFormat());
	
	$('#btnLogout').click(function(){
		location.href="<c:url value="/user/logout.do"/>";
	});
	
	$('#scheduleSearch').click(function(){
		var sType = scheduleRadio.getValue();
		var sText = $('#sText').val();
		refrashRow(scheduleArticle, {url: '<c:url value="/home/scheduleArticle.do"/>', param:{page : 1, today : datepicker.getFormat(), sType : sType, sText : sText}});
		
	});
	
	$('#schedule-refresh').click(function(){
		var date = new Date();
		scheduleParam = {seq : 0, title : '', contents : '', starttime : date.getTime(), endtime : date.getTime(), writer:''};
		$('#sText').val('');
		$('#title').val(scheduleParam.title);
		$('#contents').val(scheduleParam.contents);
		spicker.select(date.getFullYear(),date.getMonth()+1,date.getDate());
		epicker.select(date.getFullYear(),date.getMonth()+1,date.getDate());
		datepicker.select(date.getFullYear(),date.getMonth()+1,date.getDate());
	});
	
	$('#schedule-write').click(function(){
		var date = new Date(datepicker.getTime());
		scheduleParam = {seq : 0, title : '', contents : '', starttime : date.getTime(), endtime : date.getTime(), writer:''};
		$('#title').val(scheduleParam.title);
		$('#contents').val(scheduleParam.contents);
		spicker.select(date.getFullYear(),date.getMonth()+1,date.getDate());
		epicker.select(date.getFullYear(),date.getMonth()+1,date.getDate());
		writeModal.show();
	});

	$('#writeBtn').click(function(){
		var title = $('#title').val();
		var contents = $('#contents').val();
		var starttime = $('#starttime').val();
		var endtime = $('#endtime').val();
		
		if(trim(title) == ''){
			alert('제목을 입력하세요');
			$('#title').val('');
			$('#title').focus();
		}
		else if(trim(contents) == ''){
			alert('내용을 입력하세요');
			$('#contents').val('');
			$('#contents').focus();
		}else{
			scheduleParam.title=title;
			scheduleParam.contents=contents;
			scheduleParam.starttime = starttime;
			scheduleParam.endtime = endtime;
			var url = '<c:url value="/home/scheduleWrite.do"/>';
			if(scheduleParam.seq > 0){
				url = '<c:url value="/home/scheduleUpdate.do"/>';
			}
			$.ajax({
				url : url,
				data : scheduleParam,
				type : 'post',
				success : function(response){
					refrashRow(scheduleArticle,{url: '<c:url value="/home/scheduleArticle.do"/>', param:{page : 1,today : datepicker.getFormat()}});
					writeModal.hide();
					$('#title').val('');
					$('#contents').val('');
					var date = new Date();
		    		spicker.select(date.getFullYear(),date.getMonth()+1,date.getDate());
		    		epicker.select(date.getFullYear(),date.getMonth()+1,date.getDate());
				}
			});
		}
	});
	
	$('#contentsUpdate').click(function(){
		alert();
	});
	
	$('#tab_Home,#tab_Schedule,#tab_Etc').click(function(){
		var id = $(this).attr('id');
		$('#selectTab').val(id);
		$('.tab').find('li').each(function(){
			var $child = $(this).find('a'); 
			if($child.attr('id') == id){
				$(this).addClass('active');
				$('#'+ $child.attr('title')).show();
			}else{
				$(this).removeClass('active');
				$('#'+ $child.attr('title')).hide();
			}
		});
	});
	refrashRow(scheduleArticle,{url: '<c:url value="/home/scheduleArticle.do"/>', param:{page : 1, today : datepicker.getFormat()}});
	setInterval(function(){
		var date = new Date();
		$('#head-year').text(date.getFullYear());
		$('#head-month').text(date.getMonth()+1);
		$('#head-day').text(date.getDate());
		$('#head-hour').text(date.getHours());
		$('#head-min').text(date.getMinutes());
	},1000);
});

function trim(str){
	return str.replace(new RegExp(' ','g'),'');
}

function getJSON(vo,param){
	var parentVo = vo;
	var nextVo = vo.nextVo;
	$.getJSON(parentVo.url,param).done(function(response){
		var results = response;
		var bodyDiv = $('.'+parentVo.id + '-container');
		if(!bodyDiv[0]){
			bodyDiv = document.createElement("div");
			$(bodyDiv).addClass( parentVo.id + '-container' );
			$(bodyDiv).css('margin-top','5px');
			$('#main_Home').append(bodyDiv);
		}
		$(bodyDiv).hide();
		$(bodyDiv).html('');
		for(var i = 0 ; i < results.length ; i ++){
			var result = results[i];
			var div = document.createElement("div");
			$(bodyDiv).append(div);
			var spans = new Array();
			for(var key in parentVo){
				if(key == 'id'){
					var span = document.createElement("span");
					$(span).text( eval('parentVo.'+key));
					$(span).css('color','blue');
					$(span).css('font-weight','bold');
					spans.push(span);
				}
				if( key != 'seq' && key != 'url' && key != 'isChild' && key != 'id' && key != 'nextVo' ){
					var span = document.createElement("span");
					$(span).html( eval('parentVo.'+key) + ' : <font>' + eval('result.'+key) + '</font>' );
					spans.push(span);
				}
			}
			var br = document.createElement("br");
			$(div).html(spans[0].outerHTML + br.outerHTML + spans[1].outerHTML + br.outerHTML + spans[2].outerHTML + br.outerHTML + spans[3].outerHTML + br.outerHTML + spans[4].outerHTML);
			$(div).addClass( parentVo.id + ' navbar' );
			$(div).css('cursor','pointer');
			$(div).css('width','97%');
			$(div).css('line-height','18px');
			$(div).attr('seq',result.seq);
			$(div).attr('clicked',"false");
			$(div).attr('name',result.name);
			$(div).attr('email',result.email);
			$(div).css('opacity','0.5').mouseover(function(){
				$(this).css('opacity','1');
			}).mouseout(function(){
				$(this).css('opacity','0.5');
			}).click(function(){
				var $obj = $(this);
				if($obj.attr('clicked') == "false"){
					$obj.addClass('clicked');
					$obj.addClass('clicked notify-red');
					$obj.attr('clicked','true');
					$('.'+parentVo.id).each(function(){
						if(!$(this).is($obj)){
							$(this).hide();
						}
					});
					$obj.animate({width:'385px'},800,function(){
						if(nextVo){
							getJSON(nextVo,{url: '<c:url value="/home/userArticle.do"/>', seq:$obj.attr('seq')});							
						}else{
							refrashRow(userArticle, {param:{page : 1, email : $obj.attr('email')}, url: '<c:url value="/home/userArticle.do"/>'});
							$('#work-name').text($obj.attr('name'));
							$('#work-email').attr('href','mailto:'+$obj.attr('email'));
							$('#selectUser').val($obj.attr('email'));
							$('.user-work').slideDown('slow');
						}
					});
				}else{
					$obj.attr('clicked','false');
					while(nextVo){
						$('.'+nextVo.id + '-container').html('');
						nextVo = nextVo.nextVo;
					}
					nextVo = parentVo.nextVo;
					$('.user-work').slideUp('slow',function(){
						$obj.animate({width:'97%'},800,function(){
							$('.'+parentVo.id).each(function(){
								$(this).removeClass('clicked');
								$(this).removeClass('notify-red');
								$(this).show();
							});
						});
					});
				}
			});
		}
		$(bodyDiv).slideDown('slow');
	}).fail(function(jqxhr, textStatus, error){
		 var err = textStatus + ", " + error;
		 console.log( "Request Failed: " + err );
		 location.href='<c:url value="/common/error.do?code="/>'+textStatus;
	});	
}

function contentsUpdate(){
	modal.hide();
	var contents = scheduleParam.contents;
	$('#title').val(scheduleParam.title);
	$('#contents').val(contents.replace(new RegExp('<br>','g'),'\n'));
	var sdate = new Date(scheduleParam.starttime);
	var edate = new Date(scheduleParam.endtime);
	spicker.select(sdate.getFullYear(),sdate.getMonth()+1,sdate.getDate());
	epicker.select(edate.getFullYear(),edate.getMonth()+1,edate.getDate());
	writeModal.show();
}

function contentsDelete(seq){
	if(confirm('삭제 하시겠습니까?')){
		$.post('<c:url value="/home/scheduleDelete.do"/>', {seq:seq}, function(data, textStatus) {
			var result = data;
			if(result.resultCnt > 0){
				alert('삭제 완료');
				refrashRow(scheduleArticle,{url: '<c:url value="/home/scheduleArticle.do"/>', param:{page : 1,today : datepicker.getFormat()}});
				modal.hide();
			}else{
				alert('삭제 실패');
			}
		},"json").fail(function(jqxhr, textStatus, error){
			 var err = textStatus + ", " + error;
			 console.log( "Request Failed: " + err );
			 location.href='<c:url value="/common/error.do?code="/>'+textStatus;
		});
	}
}

function changePage(pNo){
	refrashRow(scheduleArticle, {url: '<c:url value="/home/scheduleArticle.do"/>', param:{page : pNo, today : datepicker.getFormat()}});
}

function refrashRow(table, param){
	$.getJSON(param.url,param.param,function(response){
		var articles = response.articles;
		var article = [];

		//schedulePaging.reload(response.count);
		var pageRow = 15;
		var pageCol = 15;
		var totalCnt = response.count;
		var page = response.page == null ? 1 : response.page;
		pageRow = totalCnt<pageRow?totalCnt:pageRow;
		var totalCol = parseInt(totalCnt%pageRow)>0?(totalCnt/pageRow)+1:totalCnt/pageRow;
		 
		var startIdx = (page-1)*pageRow;
		
		var startCol = parseInt(((startIdx / pageCol)/pageRow))*pageCol;
		var endCol = startCol+pageCol < totalCol ? startCol+pageCol : totalCol;
		
		var html = '';
		if((startCol+1) > pageCol){
			html += '<a href="#" class="prev" onclick="changePage('+(parseInt(startCol) - 1 - parseInt(pageCol))+')">Previous</a>';				
		}else{
			html += '<a href="#" class="prev">Previous</a>';
		}
		html += '<div class="list">';
		for(var i = (parseInt(startCol)) ; i < parseInt(endCol) ; i ++){
			if((i+1) == page){
				html += '<a href="#" class="page active">'+(i+1)+'</a>';
			}else{
				html += '<a href="#" class="page" onclick="changePage('+(i+1)+')">'+(i+1)+'</a>';
			}
		}
		html += '</div>';
		if(totalCol > endCol){
			html += '<a href="#" class="next" onclick="changePage('+(parseInt(startCol) + 1 + parseInt(pageCol))+')">Next</a>';				
		}else{
			html += '<a href="#" class="next">Next</a>';
		}
		
		
		$('#schedulePaging').html(html);
		
		for(var i = 0; i < articles.length; i++) {
			var obj = articles[i];
			
			if(userArticle.selector == table.selector){
				var date = new Date(obj.regtime);
				var regtime = date.getFullYear() + "년 " + (date.getMonth()+1) + "월 " + date.getDate() + "일";
				article.push({ seq: obj.seq, title: obj.title, writer: obj.writer, regtime: regtime });
			}else if(scheduleArticle.selector == table.selector){
				var sdate = new Date(obj.starttime);
				var sregtime = sdate.getFullYear() + "년 " + (sdate.getMonth()+1) + "월 " + sdate.getDate() + "일";
				var edate = new Date(obj.endtime);
				var endtime = edate.getFullYear() + "년 " + (edate.getMonth()+1) + "월 " + edate.getDate() + "일";
				article.push({ seq: obj.seq, title: obj.title, writer: obj.writer, starttime: sregtime, endtime : endtime });
			}
		}
		
		table.update(article);
		
		var list = table.list(); 
		
		for(var i = 0 ; i < list.length ; i++){
			$(list[i].element).attr('seq',list[i].data.seq).mouseover(function(){
				$(this).find('td').css('color','red');
			}).mouseout(function(){
				$(this).find('td').css('color','#000000');
			}).click(function(){
				var url = "";
				if(userArticle.selector == table.selector){
					url = "<c:url value="/home/getArticle.do"/>";
				}else if(scheduleArticle.selector == table.selector){
					url = "<c:url value="/home/getSchedule.do"/>";
				}
				$.getJSON(url,{seq:$(this).attr('seq')},function(response){
					var article = response;
					var sdate = new Date(article.starttime);
					var stime = sdate.getFullYear() + "년 " + (sdate.getMonth()+1) + "월 " + sdate.getDate() + "일";
					var edate = new Date(article.endtime);
					var etime = edate.getFullYear() + "년 " + (edate.getMonth()+1) + "월 " + edate.getDate() + "일";
					$("#modal-title").html(article.title + '<span style="float:right;">'+article.writer+'</span>');
					$("#modal-contents").html('<div class="label label-red" style="min-width:300px;">' + stime + ' ~ ' + etime + '</div><br><div class="notify contents-view" style="margin-top:5px;">' + article.contents + '</div>');
					if(article.isWriter == 'true'){
						scheduleParam = {seq : article.seq,title : article.title, contents : article.contents, starttime : article.starttime, endtime : article.endtime};
						var updateBtn = $('<a/>', {
										    href: '#',
										    name: 'updateBtn',
										    id: 'updateBtn',
										    html: '수정',
										    addClass : 'btn btn-gray btn-small',
										    onclick: 'javascript:contentsUpdate();'
										});
						var deleteBtn = $('<a/>', {
										    href: '#',
										    name: 'deleteBtn',
										    id: 'deleteBtn',
										    html: '삭제',
										    addClass : 'btn btn-gray btn-small',
										    onclick: 'javascript:contentsDelete('+article.seq+');'
										});
						var closeBtn = $('<a/>', {
										    href: '#',
										    name: 'closeBtn',
										    id: 'closeBtn',
										    html: 'Close',
										    addClass : 'btn btn-gray btn-small',
										    onclick: 'javascript:modal.hide();'
										});
						
						$('#contentsBtn').html( updateBtn[0].outerHTML +  deleteBtn[0].outerHTML +  closeBtn[0].outerHTML);
					}else{
						var closeBtn = $('<a/>', {
						    href: '#',
						    name: 'closeBtn',
						    id: 'closeBtn',
						    html: 'Close',
						    addClass : 'btn btn-gray btn-small',
						    onclick: 'javascript:modal.hide();'
						});
		
						$('#contentsBtn').html( closeBtn[0].outerHTML);
					}
					modal.show();
				}).fail(function(jqxhr, textStatus, error){
					 var err = textStatus + ", " + error;
					 console.log( "Request Failed: " + err );
					 location.href='<c:url value="/common/error.do?code="/>'+textStatus;
				});	;
			});
		}
		
		if(articles.length == 0){
			$('#schedulebody').html("<tr><td colspan='5'>해당일의 일정이 없습니다.</td></tr>");
		}
		
	}).fail(function(jqxhr, textStatus, error){
		 var err = textStatus + ", " + error;
		 console.log( "Request Failed: " + err );
		 location.href='<c:url value="/common/error.do?code="/>'+textStatus;
	});	
	
}
</script>
</body>
</html>