/* 
* 제이쿼리 영역
 */
$(function(){
	getJSON(vo,{seq:1});
	
	$('#eText').keydown(function(e){
		if(e.keyCode == 13){
			$('#employeeSearch').click();
		}
	});
	
	$('#addEmployee').click(function(){
		userModal.show();
	});
	
	$('#addUserBtn').click(function(){
		$.ajax({
			url : getContextPath()+"/user/addEmployee.do",
			type : 'post',
			data : {email : $('#user-email').val(),name : $('#user-name').val(),phone : $('#user-phone').val()},
			success : function(response){
				alert(JSON.parse(response).msg);
				$('#user-email').val('');
				$('#user-name').val('');
				$('#user-phone').val('');
				userModal.hide();
			}
		});
	});
	
	$('#employeeSearch').click(function(){
		var eText = $('#eText').val();
		var sType = searchCombo.getValue();
		if(trim(eText) != ''){
			$('.user-work').hide();
			$('.Employee-container>div').each(function(){
				$(this).css('width','97%');
				$(this).attr('clicked','false');
				if(sType == 3){
					if($(this).attr('name').indexOf(eText) != -1){
						$(this).show();
					}else{
						$(this).hide();
					}
				}else if(sType == 4){
					if($(this).attr('phone').indexOf(eText) != -1){
						$(this).show();
					}else{
						$(this).hide();
					}
				}else if(sType == 5){
					if($(this).attr('email').indexOf(eText) != -1){
						$(this).show();
					}else{
						$(this).hide();
					}
				}
			}); 
		}else{
			$('.user-work').hide();
			$('.Employee-container>div').each(function(){
				$(this).css('width','97%');
				$(this).attr('clicked','false');
				$(this).removeClass('clicked');
				$(this).removeClass('notify-red');
				$(this).show();
			});
		}
	});
	
	$('#etc-chart').click(function(){
		var syear = $('#syear').val();
		var smonth = $('#smonth').val();
		var eyear = $('#eyear').val();
		var emonth = $('#emonth').val();
		$.getJSON(getContextPath()+"/home/getChart.do",{syear:syear,smonth:smonth,eyear:eyear,emonth:emonth},function(response){
			var result = response;
			var array = new Array();
			for(var i = 0 ; i < result.length ; i ++){
				var color1 = '#';
				for(var j = 0 ; j < 6 ; j ++){
					color1 += parseInt(Math.random()*(15)).toString(16);
				}
				var chart = {
						val : result[i].cnt,
						label : result[i].name,
						stColor : color1,
						edColor : 'white',
						textColor : 'black',
						textSize : 12
				};
				array.push(chart);
			}
			var myChart = new nieeChart();

			myChart.setChart({
				array : array,
				id : 'canvas',
				width : 600,
				height: 500,
				isLine : true,
				title : 'niee@naver.com',
				titleSize : 10,
				lineCount : 5,
				isTooltip : false,
				toolStyle : "border:1px solid #000;width:100px;height:50px;background:#FF6600"
			});			
		});

		chartModal.show();
	});
	
	$('#changePasswd').click(function(){
		passwdModal.show();
	});
	
	$('#etc-game').click(function(){
		$('#gameBody').append('<iframe src="'+getContextPath()+'/resources/html/snake.jsp" height="400"></iframe>');
		gameModal.show();
	});
	
	$('#lib-fileadd').click(function(){
		fileModal.show();
	});
	
	$('#sessionBtn').click(function(){
		if(sessionCheker == null){
			sessionCheker = setInterval(function(){
				$.ajax({url : getContextPath()+'/user/session.do',success:function(response){console.log(response.locale);}});
			},(1000*60*5));
			sessionCheker;
			$("#sessionBtn").removeClass('btn-gray');
			$("#sessionBtn").addClass('btn-black');
			$(".icon-gear").addClass('icon-white');
			$('#sessionText').text("세션유지(켜짐)");
		}else{
			clearInterval(sessionCheker);
			sessionCheker = null;
			$("#sessionBtn").removeClass('btn-black');
			$("#sessionBtn").addClass('btn-gray');
			$(".icon-gear").removeClass('icon-white');
			$('#sessionText').text("세션유지(꺼짐)");
		}
	});
	
	$('#starttime').val(spicker.getFormat());
	$('#endtime').val(epicker.getFormat());
	
	$('#btnLogout').click(function(){
		location.href=getContextPath()+"/user/logout.do";
	});
	
	$('#userMailSearch').click(function(){
		var sType = userRadio.getValue();
		var sText = $('#mText').val();
		refrashRow(userArticle, {param:{page : 1, email : $('#selectUser').val(),sType:sType,sText:sText}, url: getContextPath()+'/home/userArticle.do'});
	});
	
	$('#etc-refresh').click(function(){
		getEtc();
	});
	
	$('#lib-refresh').click(function(){
		getFiles();
	});
	
	$('#writeBtn').click(function(){
		oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);
		var title = $('#title').val();
		var contents = $('#contents').val();
		var starttime = $('#starttime').val();
		var endtime = $('#endtime').val();
		
		if(trim(title) == ''){
			alert('제목을 입력하세요');
			$('#title').val('');
			$('#title').focus();
		}
		else if(trim(contents) == '<p>&nbsp;</p>' || trim(contents) == ''){
			alert('내용을 입력하세요');
			$('#contents').val('');
			$('#contents').focus();
		}else{
			var realnames = '';
			var subnames = '';
			$('#schedulefileName').find('span').each(function(){
				var isRealName = ($(this).attr('realname')!='' && $(this).attr('realname')!=null && $(this).attr('realname')!='undefined');
				if( isRealName ){
					realnames += $(this).attr('realname');
					realnames +=',';
				}
				if(isRealName && $(this).attr('subname')!='' && $(this).attr('subname')!=null && $(this).attr('subname')!='undefined'){
					subnames += $(this).attr('subname');
					subnames +=',';
				}
			});
			scheduleParam.title=title;
			scheduleParam.contents=contents;
			scheduleParam.starttime = starttime;
			scheduleParam.endtime = endtime;
			var url = getContextPath()+'/home/scheduleWrite.do';
			if(scheduleParam.seq > 0){
				url = getContextPath()+'/home/scheduleUpdate.do';
			}
			scheduleParam.etcYn = ($('#etcYn').is(':checked')?'N':'Y');
			scheduleParam.type = $('#type').val();
			scheduleParam.realnames = realnames;
			scheduleParam.subnames = subnames;
			$.ajax({
				url : url,
				data : scheduleParam,
				type : 'post',
				success : function(response){
					writeModal.hide();
					$('iframe[id!=scheduleFrame]').remove();
					$('#title').val('');
					$('#contents').val('');
					calendar.refetchEvents()
					$('#schedulefileName').html('');
					var date = new Date();
		    		spicker.select(date.getFullYear(),date.getMonth()+1,date.getDate());
		    		epicker.select(date.getFullYear(),date.getMonth()+1,date.getDate());
				}
			});
		}
	});
	
//	$('#schcalendar').fullCalendar({
//		header: {
//			left: ' ',
//			center: 'prev title next',
//			right: 'today,month,basicWeek,basicDay'
//		},
//		titleFormat: {
//			month: 'yyyy년 MMMM',
//			week: "yyyy년 MMMM d[yyyy]{'일 ~ '[mmm] dd일'}",
//			day: "yyyy년 MMM d dddd"
//		},
//		monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
//		monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
//		dayNames : ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
//		dayNamesShort : ['일','월','화','수','목','금','토'],
//		allDayText : '금일일정',
//		minTime : 9,
//		maxTime : 19,
//		axisFormat : "HH:mm",
//		editable: true,
//		eventDrop : function( event ){
//			eventDragDropUpdate(event)
//		},
//		eventResize : function(event){
//			eventDragDropUpdate(event)
//		},
//		events: function(start, end, callback) {
//	        $.ajax({
//	            url: getContextPath()+"/home/scheduleArticle.do",
//	            dataType: 'json',
//	            data: {
//	            	syear:start.getFullYear(),
//		        	smonth:start.getMonth()+1,
//		        	eyear:end.getFullYear(),
//		        	emonth:end.getMonth()+1
//	            },
//	            success: function(response) {
//	            	getPayDay();
//	                var events = [];
//	                for(var i = 0 ; i < response.length ; i ++){
//	                	var color;
//	                	var textColor;
//	                	var borderColor;
//	                	events.push({
//	                        title: response[i].title,
//	                        start: new Date(response[i].starttime),
//	                        end : new Date(response[i].endtime),
//	                        seq : response[i].seq,
//	                        allDay: (new Date(response[i].starttime).getHours()<8 || new Date(response[i].end).getHours()>19),
//	                        color : (response[i].type == 2 ? '#a125d4' : response[i].type == 3 ? '#5b9a53' : response[i].type == 4 ? '#3a4dad' : color),
//	                        textColor : textColor,
//	                        borderColor:borderColor
//	                    });
//	                }
//	                callback(events);
//	            },
//	            error : function(response,txt){
//	            	location.href = getContextPath()+"/common/error.do?error="+txt;
//	            }
//	        });
//	    },
//	    eventClick: function(calEvent, jsEvent, view) {
//	    	if(calEvent.seq != null){
//	    		
//	    		$.getJSON(getContextPath()+"/home/scheduleFiles.do",{seq:calEvent.seq},function(response){
//	    			var files;
//	    			files = response;
//	    		
//			    	$.getJSON(getContextPath()+"/home/getSchedule.do",{seq:calEvent.seq},function(response){
//			    		var article = response;
//				    	var sdate = new Date(article.starttime);
//						var stime = sdate.getFullYear() + "년 " + (sdate.getMonth()+1) + "월 " + sdate.getDate() + "일";
//						var edate = new Date(article.endtime);
//						var etime = edate.getFullYear() + "년 " + (edate.getMonth()+1) + "월 " + edate.getDate() + "일";
//						var fileHtml = '<div class="notify" style="margin-top:5px;">';
//						for(var i = 0 ; i < files.length ; i ++){
//							fileHtml += '&nbsp;&nbsp;<a href="javascript:scheduleFileDown('+files[i].seq+')">' + files[i].realname +'</a>&nbsp;&nbsp;';
//						}
//						fileHtml += '</div>';
//						$("#modal-contents").html('<div class="label label-red" style="min-width:300px;">' + stime + ' ~ ' + etime + '</div><br><div class="notify contents-view" style="margin-top:5px;">' + article.contents +'</div>'+fileHtml);
//						if(article.isWriter == 'true'){
//							scheduleParam = {seq : article.seq,title : article.title, contents : article.contents, starttime : article.starttime, endtime : article.endtime, etcYn : article.etcYn, type:article.type, files:files};
//							var updateBtn = $('<a/>', {
//											    href: '#',
//											    name: 'updateBtn',
//											    id: 'updateBtn',
//											    html: '수정',
//											    addClass : 'btn btn-gray btn-small',
//											    onclick: 'javascript:contentsUpdate();'
//											});
//							var deleteBtn = $('<a/>', {
//											    href: '#',
//											    name: 'deleteBtn',
//											    id: 'deleteBtn',
//											    html: '삭제',
//											    addClass : 'btn btn-gray btn-small',
//											    onclick: 'javascript:contentsDelete('+article.seq+');'
//											});
//							var closeBtn = $('<a/>', {
//											    href: '#',
//											    name: 'closeBtn',
//											    id: 'closeBtn',
//											    html: 'Close',
//											    addClass : 'btn btn-gray btn-small',
//											    onclick: 'javascript:modal.hide();'
//											});
//							
//							$('#contentsBtn').html( updateBtn[0].outerHTML +  deleteBtn[0].outerHTML +  closeBtn[0].outerHTML);
//							$('#type').val(article.type);
//						}else{
//							var closeBtn = $('<a/>', {
//							    href: '#',
//							    name: 'closeBtn',
//							    id: 'closeBtn',
//							    html: 'Close',
//							    addClass : 'btn btn-gray btn-small',
//							    onclick: 'javascript:modal.hide();'
//							});
//			
//							$('#contentsBtn').html( closeBtn[0].outerHTML);
//						}
//						$("#modal-title").html(article.title + '<span style="float:right;">'+article.writer+'</span>');
//						modal.show();
//			    	}).fail(function(jqxhr, textStatus, error){
//						 var err = textStatus + ", " + error;
//						 console.log( "Request Failed: " + err );
//						 location.href=getContextPath()+'/common/error.do?code='+textStatus;
//					});
//	    		});
//	    	}
//	    },
//	    dayClick: function(date, allDay, jsEvent, view) {
//			scheduleParam = {seq : 0, title : '', contents : '', starttime : date.getTime(), endtime : date.getTime(), writer:'',type:1};
//			$('#title').val(scheduleParam.title);
//			$('#contents').val(scheduleParam.contents);
//			spicker.select(date.getFullYear(),date.getMonth()+1,date.getDate());
//			epicker.select(date.getFullYear(),date.getMonth()+1,date.getDate());
//			$('#etcYn').attr('checked',false);
//			$('#type').val(scheduleParam.type);
//			writeModal.show();
//			editorInit('contents');
//	    }
//	});
	
	$('#mailBtn').click(function(){
		oEditors.getById["mail-contents"].exec("UPDATE_CONTENTS_FIELD", []);
		var title = $('#mail-title').val();
		var contents = $('#mail-contents').val();
		var isSend = $('#mail-yn').is(':checked');
		if(trim(title) == ''){
			alert('제목을 입력하세요');
			$('#mail-title').val('');
			$('#mail-title').focus();
		}
		else if(trim(contents) == '<p>&nbsp;</p>' || trim(contents) == ''){
			alert('내용을 입력하세요');
			$('#mail-contents').val('');
			$('#mail-contents').focus();
		}else{
			var realnames = '';
			var subnames = '';
			var ccs = '';
			$('#fileName').find('span').each(function(){
				if($(this).attr('realname')!='' && $(this).attr('realname')!=null && $(this).attr('realname')!='undefined' ){
					realnames += $(this).attr('realname');
					realnames +=',';
				}
				if($(this).attr('subname')!='' && $(this).attr('subname')!=null && $(this).attr('subname')!='undefined'){
					subnames += $(this).attr('subname');
					subnames +=',';
				}
			});
			$('span[id=cc]').each(function(){
				ccs += $(this).attr('email');
				ccs +=',';
			});
			$.ajax({
				url : getContextPath()+'/home/sendUserMail.do',
				data : {title:title,contents:contents,isSend:isSend,email:$('#selectUser').val(),realnames:realnames,subnames:subnames,ccs:ccs},
				type : 'post',
				success : function(response){
					alert(JSON.parse(response).msg);
					refrashRow(userArticle, {param:{page : 1, email : $('#selectUser').val()}, url: getContextPath()+'/home/userArticle.do'});
					mailModal.hide();
					$('iframe[id!=scheduleFrame]').remove();
					$('#mail-title').val('');
					$('#mail-contents').val('');
					$('#mail-yn').attr('checked',false);
				}
			});
		}
	});
	
	$('#work-refresh').click(function(){
		refrashRow(userArticle, {param:{page : 1, email : $('#selectUser').val()}, url: getContextPath()+'/home/userArticle.do'});
	});

	$('#sview-refresh').click(function(){
		calendar.refetchEvents()
	});
	
	$('#writeClose').click(function(){
		$('#schedulefileName').html('');
		writeModal.hide();
		$('iframe[id!=scheduleFrame]').remove();
	});

	$('#mailClose').click(function(){
		mailModal.hide();
		$('iframe[id!=scheduleFrame]').remove();
	});
	
	$('#contentsUpdate').click(function(){
		alert();
	});
	
	$('#tab_Home,#tab_Schedule,#tab_Etc,#tab_Lib,#tab_View').click(function(){
		var id = $(this).attr('id');
		$('#selectTab').val(id);
		$('.tab').find('li').each(function(){
			var $child = $(this).find('a'); 
			if($child.attr('id') == id){
				$(this).addClass('active');
				$('#'+ $child.attr('title')).show();
				if(id == 'tab_View'){
					calendar.refetchEvents()
				}
			}else{
				$(this).removeClass('active');
				$('#'+ $child.attr('title')).hide();
			}
		});
	});
	getEtc();
	getFiles();
	setInterval(function(){
		var date = new Date();
		$('#head-year').text(date.getFullYear());
		$('#head-month').text(date.getMonth()+1);
		$('#head-day').text(date.getDate());
		$('#head-hour').text(date.getHours());
		$('#head-min').text(date.getMinutes());
	},1000);
	
	$("#etc-print").click(function() {
	    window.print();
	});
	
	$('#mailFileAddBtn').click(function(){
		 $.ajaxFileUpload 
		    ( 
		        { 
		            url:getContextPath()+'/home/mailFileUpload.do', 
		            secureuri:false, 
		            type:'post',
		            fileElementId:'mailfile', 
		            dataType: 'json', 
		            success: function (data, status) 
		            {
		            	if(data.error == '' || data.error == null || data.error == 'undefined'){
		            		alert('등록이 완료되었습니다.');
		            		
		            		var span = document.createElement('span');
		            		$(span).text(data.realname);
		            		$(span).attr('realname',data.realname);
		            		$(span).attr('subname',data.subname);
		            		
		            		var html = $('#fileName').html();
		            		
		            		$('#fileName').html(html + '<span>&nbsp;' +  span.outerHTML + '<i class="icon-trashcan icon-small" style="cursor:pointer;" onclick="mailFileDelete(\''+trim(data.subname)+'\')"></i></span>');
		            	}else{
		            		alert(data.error);
		            	}
		            }, 
		            error: function (data, status, e) 
		            { 
		                alert(e); 
		            } 
		        } 
		    ); 
		     
		    return false;
	});

	$('#scheduleFileAddBtn').click(function(){
		$.ajaxFileUpload 
		( 
				{ 
					url:getContextPath()+'/home/mailFileUpload.do', 
					secureuri:false, 
					type:'post',
					fileElementId:'schedulefile', 
					dataType: 'json', 
					success: function (data, status) 
					{
						if(data.error == '' || data.error == null || data.error == 'undefined'){
							alert('등록이 완료되었습니다.');
							
							var span = document.createElement('span');
							$(span).text(data.realname);
							$(span).attr('realname',data.realname);
							$(span).attr('subname',data.subname);
							
							var html = $('#schedulefileName').html();
							
							$('#schedulefileName').html(html + '<span>&nbsp;' +  span.outerHTML + '<i class="icon-trashcan icon-small" style="cursor:pointer;" onclick="scheduleFileDelete(\''+trim(data.subname)+'\')"></i></span>');
						}else{
							alert(data.error);
						}
					}, 
					error: function (data, status, e) 
					{ 
						alert(e); 
					} 
				} 
		); 
		
		return false;
	});
	
}).on('click','#file-download',function(){
	if(confirm($(this).attr('name') + " 을 다운 받으시겠습니까?")){
		var url = getContextPath()+'/home/filedownload.do';
		var inputs = '<input type="hidden" name="seq" value="'+$(this).attr('seq')+'"/>';
		$('<form action="'+ url +'" method="post">'+inputs+'</form>').appendTo('body').submit().remove();
	}
}).on('click','#file-delete',function(){
	if(confirm($(this).attr('name') + " 을 삭제하시겠습니까?")){
		var seq = $(this).attr('seq');
		$.ajax({
			url : getContextPath()+'/home/filedelete.do',
			type : 'post',
			data:{seq : seq},
			success:function(response){
				var result = JSON.parse(response);
				alert(result.msg);
				$('#lib-refresh').click();
			}
		});
	}
}).on('click','#write-mail',function(){
	$('#mail-contents').val('');
	$('#mail-title').val('');
	$('#mail-yn').attr('checked',false);
	$('#fileName').html('');
	$('#mailfile').val('');
	$('#cclist').html('');
	if($('.root.open').length != 0){
		$('.root>i').click();
	}
	mailModal.show();
	editorInit('mail-contents');
});