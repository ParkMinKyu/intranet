var modal;
var sviewModal;
var writeModal;
var passwdModal;
var gameModal;
var mailModal;
var userModal;
var fileModal;
var chartModal;
var searchCombo;
var userArticle;
var userRadio;
var fileArticle;
var datepicker;
var spicker;
var epicker;
var userPaging;
var userTree;

/**
 * JUI 영역
 */
jui.ready(function(ui, uix, _) {
	
	userTree = uix.tree(".tree", {
		root: { title: "CC_LIST" },
		event: {
			select: function(node) {
				this.select(node.index); // 선택 효과
				addCC(node.data);
			}
		},
		tpl: {
			node: $("#tpl_tree").html()
		}
	});
	
	chartModal = ui.modal("#chartModal", {
		color: "black",
		target: ".main-container"
	});

	userModal = ui.modal("#userModal", {
		color: "black",
		target: ".main-container"
	});

	sviewModal = ui.modal("#sviewModal", {
		color: "black",
		target: ".main-container"
	});
	
	modal = ui.modal("#modal", {
		color: "black",
		target: ".main-container"
	});
	
	
	passwdModal = ui.modal("#passwdModal", {
		color: "black",
		target: ".main-container"
	});

	gameModal = ui.modal("#gameModal", {
		color: "black"
	});
	
	gameModal.callAfter("hide",function(){
		$('iframe[id!=scheduleFrame]').remove();
	});
	
	fileModal = ui.modal("#fileModal", {
		color: "black",
		target: ".main-container"
	});
	
	writeModal = ui.modal("#writeModal", {
		color: "black",
		target: ".main-container"
	});
	
	mailModal = ui.modal("#mailModal", {
		color: "black",
		target: ".main-container"
	});
	
	searchCombo = ui.combo("#searchCombo", {
		width: 200,
		position: "bottom"
	});

	var userRadio_options2 = {
		type: "radio"
	};
	
	userRadio = ui.button("#userRadio", userRadio_options2);
	
	datepicker = ui.datepicker("#datepicker", {
	    titleFormat: "yyyy년 MM월",
	    format: "yyyy/MM/dd",
	    event: {
	       select: function(date, e) {
	    	   refrashRow(scheduleArticle,{url: getContextPath()+'/home/scheduleArticle.do', param:{page : 1, today : date}});
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
    		   spicker.select(sdate.getFullYear(),sdate.getMonth()+1,sdate.getDate());
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
	
	fileArticle = uix.table("#fileArticle", {
    	animate: true
    });
	
});