<%@ page language="java"  contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>지렁이게임</title>
<script type="text/javascript" src="<c:url value="/resources/lib/jquery-1.8.3.min.js"/>"></script>
<script type="text/javascript">
var lastTime = 0;
var vendors = ['ms', 'moz', 'webkit', 'o'];
for(var x = 0; x < vendors.length && !window.requestAnimationFrame; ++x) {
    window.requestAnimationFrame = window[vendors[x]+'RequestAnimationFrame'];
    window.cancelAnimationFrame = window[vendors[x]+'CancelAnimationFrame'] 
                               || window[vendors[x]+'CancelRequestAnimationFrame'];
}

if (!window.requestAnimationFrame)
    window.requestAnimationFrame = function(callback, element) {
        var currTime = new Date().getTime();
        var timeToCall = Math.max(0, 16 - (currTime - lastTime));
        var id = window.setTimeout(function() { callback(currTime + timeToCall); }, 
          timeToCall);
        lastTime = currTime + timeToCall;
        return id;
    };

if (!window.cancelAnimationFrame)
    window.cancelAnimationFrame = function(id) {
        clearTimeout(id);
    };
</script>
</head>
<body>
<center>
	<canvas id="map" height="340" width="240" style="border: 1px solid;"></canvas><br>
	속도:<span id="speed"></span>&nbsp;&nbsp;길이:<span id="length"></span>&nbsp;&nbsp;점수:<span id="score"></span>
</center>
<script type="text/javascript">
var c = document.getElementById("map"); //canvas객체
var ctx = c.getContext('2d'); // context객체

//맵
var map = [
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0],
   		[0,0,0,0,0,0,0,0,0,0,0,0]
   		];

var moveCode = 0;//현재이동 코드
var bMoveCode = 0;//이전이동 코드
var snakeLen = 4;//처음길이
//필드 이미지
var field = getImage("<c:url value="/resources/images/field.png"/>");
//먹이이미지
var eat = getImage("<c:url value="/resources/images/eat.png"/>");
//머리 이미지
var head = [getImage("<c:url value="/resources/images/head_up.png"/>"),getImage("<c:url value="/resources/images/head_left.png"/>"),getImage("<c:url value="/resources/images/head_down.png"/>"),getImage("<c:url value="/resources/images/head_right.png"/>")];
//몸 이미지
var body = [getImage("<c:url value="/resources/images/body_ud.png"/>"),getImage("<c:url value="/resources/images/body_lr.png"/>"),getImage("<c:url value="/resources/images/body_ul.png"/>"),getImage("<c:url value="/resources/images/body_ur.png"/>"),getImage("<c:url value="/resources/images/body_dl.png"/>"),getImage("<c:url value="/resources/images/body_dr.png"/>")];
//꼬리 이미지
var tail = [getImage("<c:url value="/resources/images/tail_up.png"/>"),getImage("<c:url value="/resources/images/tail_left.png"/>"),getImage("<c:url value="/resources/images/tail_down.png"/>"),getImage("<c:url value="/resources/images/tail_right.png"/>")];
var snakeNum = 0; //뱀 머리꼬리찾기위한
var firstX = parseInt(map[0].length/2); //초기 x
var firstY = parseInt(map.length/2); //초기 Y
var speed = 500; // 속도
var snake = new Array();
var score = 0;
function getImage(src){
	var img = new Image();
	img.src = src;
	return img;
}

function game(options){
	var fn = {};
	fn.context = options.context;
	fn.update = function(){
		console.log(moveCode)
		switch(moveCode){
		case 1:
			if(firstY>=0 && bMoveCode != 3){
				firstY--;
				if(firstY == -1){
					alert('게임오버 점수 : '+ score);
					location.reload();
				}
				var obj = map[firstY][firstX];
				if(obj.snakeNum != 0){
					alert('게임오버 점수 : '+ score);
					location.reload();
				}
				obj.snakeNum = ++snakeNum;
				obj.moveCode = 1;
				snake.push(obj.snakeNum);
				map[firstY][firstX] = obj;
				bMoveCode = 1;
			}else{
				moveCode = 3;
			}
			break;
		case 2:
			if(firstX>=0 && bMoveCode != 4){
				firstX--;
				if(firstX == -1 ){
					alert('게임오버 점수 : '+ score);
					location.reload();
				}
				var obj = map[firstY][firstX];
				if(obj.snakeNum != 0){
					alert('게임오버 점수 : '+ score);
					location.reload();
				}
				obj.snakeNum = ++snakeNum;
				obj.moveCode = 2;
				snake.push(obj.snakeNum);
				map[firstY][firstX] = obj;
				bMoveCode = 2;
			}else{
				moveCode = 4;
			}
			break;
		case 3:
			if(firstY < map.length && bMoveCode != 1){
				firstY++;
				if(firstY == map.length){
					alert('게임오버 점수 : '+ score);
					location.reload();
				}
				var obj = map[firstY][firstX];
				if(obj.snakeNum != 0){
					alert('게임오버 점수 : '+ score);
					location.reload();
				}
				obj.snakeNum = ++snakeNum;
				obj.moveCode = 3;
				snake.push(obj.snakeNum);
				map[firstY][firstX] = obj;
				bMoveCode = 3;
			}else{
				moveCode = 1;
			}
			break;
		case 4:
			if(firstX < map[firstY].length && bMoveCode != 2){
				firstX++;
				if(firstX == map[firstY].length){
					alert('게임오버 점수 : '+ score);
					location.reload();
				}
				var obj = map[firstY][firstX];
				if(obj.snakeNum != 0){
					alert('게임오버 점수 : '+ score);
					location.reload();
				}
				obj.snakeNum = ++snakeNum;
				obj.moveCode = 4;
				snake.push(obj.snakeNum);
				map[firstY][firstX] = obj;
				bMoveCode = 4;
			}else{
				moveCode = 2;
			}
			break;
		}
		startTime = (new Date()).getTime();
	}
	
	fn.render = function(){
		snake = maxsort(snake);
		var tailPosition = 0;
		var headObj = null;
		var bodyArray = new Array();
		var tailObj = null;
		var tailBeObj = null;
		for(var i = 0 ; i < map.length ; i ++){
			for(var k = 0 ; k < map[i].length ; k++){
				var obj = map[i][k];
				for(var j = snakeLen ; j < snake.length ; j++ ){
					if(obj.snakeNum == snake[j]){
						obj.snakeNum = 0;
					}
				}
				for(var j = 1 ; j < snakeLen-1 ; j++ ){
					if(obj.snakeNum == snake[j]){
						bodyArray.push(obj);
					}
				}
				if(obj.snakeNum == 0){
					fn.context.drawImage(field,obj.positionX,obj.positionY);
				}else if(obj.snakeNum == snake[0] ){
					headObj = obj;
				}else if(obj.snakeNum ==  snake[snakeLen-2]){
					tailBeObj = obj;
				}else if((obj.snakeNum ==  snake[snakeLen-1]) || (snake.length == 2 && obj.snakeNum ==  snake[1])){
					tailObj = obj;
				}
				
				if(obj.isEat){
					fn.context.drawImage(eat,obj.positionX,obj.positionY);
				}
			}
		}
		
		var beforeMove = 0;
		//머리그리기
		if(headObj != null){
			if(headObj.isEat){
				score += 100;
				headObj.isEat = false;
				isEat = false;
				snakeLen++;
				if(speed > 100){
					speed-=10;
				}
			}
			$('#score').text(score);
			$('#length').text(snakeLen);
			$('#speed').text(speed);
			beforeMove = headObj.moveCode;
			fn.context.drawImage(head[headObj.moveCode==0?0:headObj.moveCode-1],headObj.positionX,headObj.positionY);			
		}
		//몸통그리기
		bodyArray = bodySort(bodyArray);
		for(var i = 0 ; i < bodyArray.length ; i ++){
			var bodyObj = bodyArray[i];
			switch(bodyObj.moveCode){
			case 1:
				if(beforeMove == 2){
					fn.context.drawImage(body[2],bodyObj.positionX,bodyObj.positionY);
				}else if(beforeMove == 4){
					fn.context.drawImage(body[3],bodyObj.positionX,bodyObj.positionY);
				}else{
					fn.context.drawImage(body[0],bodyObj.positionX,bodyObj.positionY);
				}
				break;
			case 2:
				if(beforeMove == 1){
					fn.context.drawImage(body[5],bodyObj.positionX,bodyObj.positionY);
				}else if(beforeMove == 3){
					fn.context.drawImage(body[3],bodyObj.positionX,bodyObj.positionY);
				}else {
					fn.context.drawImage(body[1],bodyObj.positionX,bodyObj.positionY);
				}
				break;
			case 3:
				if(beforeMove == 2){
					fn.context.drawImage(body[4],bodyObj.positionX,bodyObj.positionY);
				}else if(beforeMove == 4){
					fn.context.drawImage(body[5],bodyObj.positionX,bodyObj.positionY);
				}else {
					fn.context.drawImage(body[0],bodyObj.positionX,bodyObj.positionY);
				}
				break;
			case 4:
				if(beforeMove == 1){
					fn.context.drawImage(body[4],bodyObj.positionX,bodyObj.positionY);
				}else if(beforeMove == 3){
					fn.context.drawImage(body[2],bodyObj.positionX,bodyObj.positionY);
				}else {
					fn.context.drawImage(body[1],bodyObj.positionX,bodyObj.positionY);
				}
				break;
			}
			
			beforeMove = bodyObj.moveCode;
		}
		//꼬리그리기
		if(tailObj !=null && tailBeObj != null){
			fn.context.drawImage(tail[tailBeObj.moveCode==0?0:tailBeObj.moveCode-1],tailObj.positionX,tailObj.positionY);
		}else if(tailObj !=null && tailBeObj == null){
			console.log(beforeMove);
			fn.context.drawImage(tail[headObj.moveCode==0?0:headObj.moveCode-1],tailObj.positionX,tailObj.positionY);
		}
	}
	
	//먹이생성
	fn.createEat = function(){
		var isEat = false;
		if(!isEat){
			for(var i = 0 ; i < map.length ; i ++){
				for(var k = 0 ; k < map[i].length ; k++){
					var obj = map[i][k];
					if(obj.isEat){
						isEat = true;
						break;
					}
				}
				if(isEat){
					break;
				}
			}
			
			if(!isEat){
				while(true){
					var eatY = parseInt(Math.random()*map.length);
					var eatX = parseInt(Math.random()*map[eatY].length);
					var obj = map[eatY][eatX];
					if(obj.snakeNum == 0){
						obj.isEat = true;
						map[eatY][eatX] = obj;
						break;
					}
				}
			}
		}
	}
	
	return fn;
}

//몸통정렬
function bodySort(bodyArray){
	for(var i = 0 ; i < bodyArray.length ; i ++){
		for(var k = 0 ; k < bodyArray.length-1 ; k ++){
			if(parseInt(bodyArray[k].snakeNum)<parseInt(bodyArray[k+1].snakeNum)){
				var a = bodyArray[k];
				bodyArray[k] = bodyArray[k+1];
				bodyArray[k+1] = a;
			}
		}	
	}
	return bodyArray;
}

//머리찾기위한 내림차순 정렬
function maxsort(snake){
	for(var i = 0 ; i < snake.length ; i ++){
		for(var k = 0 ; k < snake.length-1 ; k ++){
			if(parseInt(snake[k])<parseInt(snake[k+1])){
				var a = snake[k];
				snake[k] = snake[k+1];
				snake[k+1] = a;
			}
		}	
	}
	return snake;
}

var user = game({
	context : ctx
});

//맵 셋팅 
function createMap(){
	for(var i = 0 ; i < map.length ; i ++){
		for(var k = 0 ; k < map[i].length ; k++){
			var obj = {
					width : 20,
					height : 20,
					positionX : 0,
					positionY : 0,
					isEat : false,
					moveCode : moveCode,
					snakeNum : snakeNum
			};
			obj.positionX = k * 20;
			obj.positionY = i * 20;
			map[i][k] = obj;
		}
	}
}

//초기 지렁이 위치
function createSnake(){
		var obj = map[firstY][firstX];
		obj.snakeNum = ++snakeNum;
		snake.push(obj.snakeNum);
		map[firstY][firstX] = obj;	
}

//게임실행
var startTime = (new Date()).getTime();
function startGame(){
	window.requestAnimationFrame(startGame);
	var thisTime = ((new Date()).getTime() - startTime);
	if(thisTime > speed){
		user.createEat();
		user.update();
		user.render();
	}
}

function call_move(code){
	switch(code){
	//left
	case 37:
		moveCode = 2;
		break;
	//up	
	case 38:
		moveCode = 1;
		break;
	//right
	case 39:
		moveCode = 4;
		break;
	//down
	case 40:
		moveCode = 3;
		break;
	}	
}

createMap();//맵그리기
createSnake();//지렁이그리기
startGame();//게임시작

$(function(){
	$(document).keydown(function(event){
		switch(event.keyCode){
		//left
		case 37:
			moveCode = 2;
			break;
		//up	
		case 38:
			moveCode = 1;
			break;
		//right
		case 39:
			moveCode = 4;
			break;
		//down
		case 40:
			moveCode = 3;
			break;
	}			
	});
});
</script>
</body>
</html>