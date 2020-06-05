var aHoliday;
var yy = 0;
/*
===============================================================================
== 함수 목록....공통함수들은 여기에 작성한다.
===============================================================================
● gfnToday : 해당 PC의 오늘 날짜를 가져온다.
● gfnTodayTime : 해당 PC의 오늘 날짜+시간를 가져온다.
● gfnAddDate : 입력된 날자에 OffSet 으로 지정된 만큼의 일을 더한다.
● gfnAddMonth : 입력된 날자에 OffSet 으로 지정된 만큼의 달을 더한다.
● gfnDatetime : MiPlatform에서 사용하던 Datetime형식으로 변환
● gfnGetDiffDay : 2개의 날짜간의 Day count
● gfnDateCheck : 날짜에 대한 형식 체크
● gfnGetDay : 입력된 날자로부터 요일을 구함
● gfnStrToDate : 반자를 전자로 변환하는 함수
● gfnGetDayName : 입력된 날자로부터 요일명을 구함
● gfnIsLeapYear : 윤년여부 확인
● gfnLastDateNum : 해당월의 마지막 날짜를 숫자로 구하기
● gfnLastDate : 해당월의 마지막 날짜를 yyyyMMdd형태로 구하기
● gfnSolar2Lunar : 양력을 음력으로 변환해주는 함수 (처리가능 기간  1841 - 2043년)
● gfnLunar2Solar : 음력을 양력으로 변환해주는 함수 (처리가능 기간  1841 - 2043년)
● gfnGetHolidays : 양력 nYear에 해당하는 년도의 법정 공휴일(양력) List 모두 구하기
● _SolarBase : 각 월별 음력 기준 정보를 처리하는 함수(처리가능 기간  1841 - 2043년) 단, 내부에서 사용하는 함수임
*/
/*******************************************************************************
 * Function명 : gfnToday
 * 설명          : 해당 PC의 오늘 날짜를 가져온다.
 * parameter  : None
 * return        : string
******************************************************************************/ 
function gfnToday()
{
    var strToday = "";
    var objDate   = new Date();
    var strToday  = objDate.getFullYear() + "";
    strToday += gfnRight("0" + (objDate.getMonth() + 1), 2);
    strToday += gfnRight("0" + objDate.getDate(), 2);
    return strToday;
}   
/*******************************************************************************
 * Function명 : gfnTodayTime
 * 설명       : 해당 PC의 오늘 날짜+시간를 가져온다.
 * parameter : None
 * return         : string
******************************************************************************/ 
function gfnTodayTime()
{
    var strToday = "";
    var objDate = new Date();
    var strToday  = objDate.getFullYear() + "";
    strToday += gfnRight("0" + (objDate.getMonth() + 1), 2);
    strToday += gfnRight("0" + objDate.getDate(), 2);
    strToday += gfnRight("0" + objDate.getHours(), 2);
    strToday += gfnRight("0" + objDate.getMinutes(), 2);
    strToday += gfnRight("0" + objDate.getSeconds(), 2);
    return strToday;
}
/*******************************************************************************
 * Function명: gfnCurrentTime
 * 설명      : 해당 PC의 현재시간를 가져온다.
 * parameter : None
 * return    : string
******************************************************************************/ 
function gfnCurrentTime()
{
    var strCurrentTime = "";
    var objDate = new Date();
    strCurrentTime += gfnRight("0" + objDate.getHours(), 2);
    strCurrentTime += gfnRight("0" + objDate.getMinutes(), 2);
    strCurrentTime += gfnRight("0" + objDate.getSeconds(), 2);
    return strCurrentTime;
}
/**********************************************************************************
 * 함수명      : gfnAddDate
 * 설명        : 입력된 날자에 OffSet 으로 지정된 만큼의 일을 더한다.
 *               Date Type을 String으로 변환
 * argument    : date ('yyyyMMdd' 형태로 표현된 날자)
 *               nOffSet (날짜로부터 증가 감소값. 지정하지 않으면 Default Value = 1 로 적용됩니다)
 * return Type : String
 * return 내용 : Date에 nOffset이 더해진 결과를 'yyyyMMdd'로 표현된 날자.
**********************************************************************************/
function gfnAddDate(date, nOffSet)
{
    var nYear  = parseInt(date.substr(0, 4));
    var nMonth = parseInt(date.substr(5, 2));
    var nDate  = parseInt(date.substr(7, 2)) + nOffSet;
    return nYear +"/"+ nMonth +"/"+ nDate;
}
/**********************************************************************************
 * 함수명      : gfnAddMonth
 * 설명        : 입력된 날자에 OffSet 으로 지정된 만큼의 달을 더한다.
 *               Date Type을 String으로 변환
 * argument    : date ('yyyyMMdd' 형태로 표현된 날자)
 *               nOffSet (날짜로부터 증가 감소값. 지정하지 않으면 Default Value = 1 로 적용됩니다)
 * return Type : String
 * return 내용 : Date에 nOffset이 더해진 결과를 'yyyyMMdd'로 표현된 날자.
**********************************************************************************/
function gfnAddMonth(date, nOffSet)
{
    var nYear  = parseInt(date.substr(0, 4));
    var nMonth = parseInt(date.substr(4, 2)) + nOffSet;
    var nDate  = parseInt(date.substr(6, 2));
    return gfnDatetime(nYear, nMonth, nDate);
}
/**********************************************************************************
 * 함수명      : gfnDatetime
 * 설명        : MiPlatform에서 사용하던 Datetime형식으로 변환
 *               Date Type을 String으로 변환
 * argument    : nYear (년도)
 *               nMonth (월)
 *               nDate (일)
 * return Type : String
 * return 내용 : 조합한 날짜를 리턴
**********************************************************************************/

function gfnDatetime(nYear, nMonth, nDate)
{
    if (nYear.toString().trim().length >= 5) {
        var sDate    = new String(nYear);
        var nYear    = sDate.substr(0,4);
        var nMonth   = sDate.substr(4,2);
        var nDate    = ((sDate.substr(6,2) == "") ? 1 : sDate.substr(6,2));
        var nHours   = ((sDate.substr(8,2) == "") ? 0 : sDate.substr(8,2));
        var nMinutes = ((sDate.substr(10,2) == "") ? 0 : sDate.substr(10,2));
        var nSeconds = ((sDate.substr(12,2) == "") ? 0 : sDate.substr(12,2));
        
        var objDate = new Date(parseInt(nYear), parseInt(nMonth)-1, parseInt(nDate), parseInt(nHours), parseInt(nMinutes), parseInt(nSeconds));
    } else {
        var objDate = new Date(parseInt(nYear), parseInt(nMonth)-1, parseInt(((nDate == null) ? 1 : nDate)));
    }
    var strYear  = objDate.getYear().toString();
    var strMonth = (objDate.getMonth() + 1).toString();
    var strDate  = objDate.getDate().toString();
    if (strMonth.length == 1)  strMonth  = "0" + strMonth;
    if (strDate.length == 1)   strDate   = "0" + strDate;
    return strYear + strMonth + strDate;
}
/******************************************************************************
 * Function명 : gfnGetDiffDay
 * 사용법 : gfnGetDiffDay("20090808", "20091001")
 * 설명       : 2개의 날짜간의 Day count
 * Params     : sFdate   시작일자
 *              sTdate   종료일자
 * Return     : 양 일자간의 Day count
 ******************************************************************************/
 function gfnGetDiffDay(sFdate, sTdate) {
 sFdate = new String(sFdate);
    sFdate = sFdate.replace(" ", "").replace("-", "").replace("/", "");
    sTdate = new String(sTdate);
    sTdate = sTdate.replace(" ", "").replace("-", "").replace("/", "");
    sFdate = gfnLeft(sFdate, 8);
    sTdate = gfnLeft(sTdate, 8);
    
    if (sFdate.length != 8 || sTdate.length != 8 
         || isNumeric(sFdate) == false || isNumeric(sTdate) == false
         || gfnGetDay(sFdate) == -1 || gfnGetDay(sTdate) == -1) {
        return null;
    }
    var nDiffDate = gfnStrToDate(sTdate) - gfnStrToDate(sFdate);
    var nDay      = 1000*60*60*24;
    
    nDiffDate = parseInt(nDiffDate/nDay);
    if (nDiffDate < 0) {
        nDiffDate = nDiffDate - 1;
    } else {
        nDiffDate = nDiffDate + 1;
    }
    return nDiffDate;
}
/******************************************************************************
 * Function명 : gfnDateCheck
 * 설명       : 날짜에 대한 형식 체크
 * Params     : sFdate   검사일자
 * Return     : 유효성반환 (날짜형식이 아닐경우 FLASE)
 ******************************************************************************/
 function gfnDateCheck(sDate) {
 sDate = sDate.replace(" ", "").replace("-", "").replace("/", "");
 if (isNumeric(sDate) == false || gfnGetDay(sDate) == -1 /*|| datetime(sDate) == datetime("00000101")*/) {
  return false;
 }
 return true;
 }
 
 /******************************************************************************
 * Function명 : gfnGetDay
 * 설명       : 입력된 날자로부터 요일을 구함
 * Params     : sDate  8자리 형식으로된 날짜. yyyyMMdd의 형식으로 입력됩니다.
 * Return     : 요일에 따른 숫자.
 *              0 = 일요일 ~ 6 = 토요일 로 대응됩니다.
 *              오류가 발생할 경우 -1이 Return됩니다.
 ******************************************************************************/
function gfnGetDay(sDate) {
 var objDate = gfnStrToDate(sDate);
 return objDate.getDay();
}
/*******************************************************************************
 * 함수명      : gfnStrToDate
 * 설명        : 반자를 전자로 변환하는 함수
 * argument    : sDate 8자리 형식으로된 날짜. yyyyMMdd의 형식으로 입력됩니다.
 * Return      : Date
*******************************************************************************/
function gfnStrToDate(sDate) {
    var nYear  = parseInt(sDate.substr(0, 4));
    var nMonth = parseInt(sDate.substr(4, 2)) - 1;
    var nDate  = parseInt(sDate.substr(6, 2));
 return new Date(nYear, nMonth, nDate);
}
 /******************************************************************************
 * 2010.05.11. 권세준 추가 시작
 ******************************************************************************/
 
  /******************************************************************************
 * Function명 : gfnGetDayName
 * 설명       : 입력된 날자로부터 요일명을 구함
 * Params     : sDate  8자리 형식으로된 날짜. yyyyMMdd의 형식으로 입력됩니다.
 * Return     : 요일명
 ******************************************************************************/
function gfnGetDayName(sDate) 
{
 var objDayName = new Array("일요일", "월요일", "화요일","수요일","목요일", "금요일","토요일");
 var objDate = gfnGetDay(sDate);
 
 return objDayName[objDate];
}
 /******************************************************************************
 * Function명 : gfnIsLeapYear
 * 설명       : 윤년여부 확인
 * Params     : sDate : yyyyMMdd형태의 날짜 ( 예 : "20121122" )
 * Return     : 
 *               - sDate가 윤년인 경우 = true
 *     - sDate가 윤년이 아닌 경우 = false
 *       - sDate가 입력되지 않은 경우 = false
 ******************************************************************************/
function gfnIsLeapYear(sDate)
{
    var ret;
    var nY;
    
    if( gfnIsEmpty(sDate) )  return false;
    
    nY = parseInt(sDate.substring(0,4), 10);
    if ((nY % 4) == 0) 
    {
        if ((nY % 100) != 0 || (nY % 400) == 0) 
            ret = true;
        else 
            ret = false;
    } 
    else 
        ret = false;
  
    return ret;
}
 /******************************************************************************
 * Function명 : gfnLastDateNum
 * 설명       : 해당월의 마지막 날짜를 숫자로 구하기
 * Params     : sDate : yyyyMMdd형태의 날짜 ( 예 : "20121122" )
 * Return     : 
 *               - 성공 = 마지막 날짜 숫자값 ( 예 : 30 )
 *     - 실패 = -1
 ******************************************************************************/
function gfnLastDateNum(sDate)
{
    var nMonth, nLastDate;
 if( gfnIsEmpty(sDate) )  return -1;
 
 nMonth = parseInt(sDate.substr(4,2), 10);
    if( nMonth == 1 || nMonth == 3 || nMonth == 5 || nMonth == 7  || nMonth == 8 || nMonth == 10 || nMonth == 12 )
  nLastDate = 31;
    else if( nMonth == 2 )  
    {
        if( gfnIsLeapYear(sDate) == true )
   nLastDate = 29;
        else
   nLastDate = 28;
    } 
    else 
  nLastDate = 30;
  
 return nLastDate;
}
 /******************************************************************************
 * Function명 : gfnLastDate
 * 설명       : 해당월의 마지막 날짜를 yyyyMMdd형태로 구하기 
 * Params     : sDate : yyyyMMdd형태의 날짜 ( 예 : "20121122" )
 * Return     : 
 *               - 성공 = yyyyMMdd형태의 마지막 날짜 ( 예 : "20121130" )
 *     - 실패 = ""
 ******************************************************************************/
function gfnLastDate(sDate)
{
 if( gfnIsEmpty(sDate) )  return "";
 
 var nLastDate = gfnLastDateNum(sDate);
 
 return sDate.substr(0,6) + nLastDate.toString();
}
 /******************************************************************************
 * Function명 : gfnSolar2Lunar
 * 설명       : 양력을 음력으로 변환해주는 함수 (처리가능 기간  1841 - 2043년)
 * Params     : sDate : yyyyMMdd형태의 양력일자 ( 예 : "20121122" )
 * Return     : return값이 8자리가 아니고 9자리임에 주의
 *               - 성공 = Flag(1 Byte) + (yyyyMMdd형태의 음력일자)
 *        ( Flag : 평달 = "0", 윤달 = "1" )
 *       - 실패 = "" ( 1841 ~ 2043 범위 오류시 )
 ******************************************************************************/
function gfnSolar2Lunar(sDate)
{
 var sMd = "31,0,31,30,31,30,31,31,30,31,30,31";
 var aMd = new Array();
 var aBaseInfo = new Array();
 var aDt = new Array();  // 매년의 음력일수를 저장할 배열 변수
 var td;     // 음력일을 계산하기 위해 양력일과의 차이를 저장할 변수
 var td1;     // 1840년까지의 날수
 var td2;     // 현재까지의 날수
 var mm;     // 임시변수
 var nLy, nLm, nLd;   // 계산된 음력 년, 월, 일을 저장할 변수
 var sLyoon;     // 현재월이 윤달임을 표시
 if( gfnIsEmpty(sDate) )  return "";
 
 sY = parseInt(sDate.substr(0,4), 10);
 sM = parseInt(sDate.substr(4,2), 10);
 sD = parseInt(sDate.substr(6,2), 10);
 if( sY < 1841 || sY > 2043 ) return "";
 aBaseInfo = _SolarBase();
 aMd = sMd.split(",");
 if( gfnIsLeapYear(sDate) == true )     
  aMd[1] = 29;
 else
  aMd[1] = 28; 
 
 td1 = 672069;      // 672069 = 1840 * 365 + 1840/4 - 1840/100 + 1840/400 + 23  //1840년까지 날수
  
 // 1841년부터 작년까지의 날수
 td2 = (sY - 1) * 365 + parseInt((sY - 1)/4) - parseInt((sY - 1)/100) + parseInt((sY - 1)/400);
  
 // 전월까지의 날수를 더함
 for( i = 0 ; i <= sM - 2 ; i++ )
  td2 = td2 + parseInt(aMd[i]);
 // 현재일까지의 날수를 더함
 td2 = td2 + sD;
 // 양력현재일과 음력 1840년까지의 날수의 차이
 td = td2 - td1 + 1;
 
 // 1841년부터 음력날수를 계산
 for( i = 0 ; i <= sY - 1841 ; i++ )
 {
  aDt[i] = 0;
  for( j = 0 ; j <= 11 ; j++ )
  {
   switch( parseInt(aBaseInfo[i*12 + j]) )
   {
    case 1 : mm = 29;
      break;
    case 2 : mm = 30;
      break;    
    case 3 : mm = 58; // 29 + 29
      break;    
    case 4 : mm = 59; // 29 + 30
      break;    
    case 5 : mm = 59; // 30 + 29
      break;    
    case 6 : mm = 60; // 30 + 30
      break;    
   }
   aDt[i] = aDt[i] + mm;
  }
 }
  
 // 1840년 이후의 년도를 계산 - 현재까지의 일수에서 위에서 계산된 1841년부터의 매년 음력일수를 빼가면수 년도를 계산
 nLy = 0;
 do
 {
  td = td - aDt[nLy];
  nLy = nLy + 1;
 }
 while(td > aDt[nLy]);
 
 nLm = 0;
 sLyoon = "0";   // 현재월이 윤달임을 표시할 변수 - 기본값 평달
 do
 {
  if( parseInt(aBaseInfo[nLy*12 + nLm]) <= 2 )
  {
   mm = parseInt(aBaseInfo[nLy*12 + nLm]) + 28;
   if( td > mm )
   {
    td = td - mm;
    nLm = nLm + 1;
   }
   else
    break;
  }
  else
  {
   switch( parseInt(aBaseInfo[nLy*12 + nLm]) )
   {
    case 3 :
     m1 = 29;
     m2 = 29;
     break;
    case 4 : 
     m1 = 29;
     m2 = 30;
     break;     
    case 5 : 
     m1 = 30;
     m2 = 29;
     break;     
    case 6 : 
     m1 = 30;
     m2 = 30;
     break;     
   }
   if( td > m1 )
   {
    td = td - m1;
    if( td > m2 )
    {
     td = td - m2;
     nLm = nLm + 1;
    }
    else
    {
     sLyoon = "1";
    }
   }
   else
   {
    break;
   }
  }
 }
 while(1);
 
 nLy = nLy + 1841;
 nLm = nLm + 1;
 nLd = td;
 return sLyoon+nLy+gfnRight("0" + nLm, 2)+gfnRight("0" + nLd, 2);
}
 /******************************************************************************
 * Function명 : gfnLunar2Solar
 * 설명       : 음력을 양력으로 변환해주는 함수 (처리가능 기간  1841 - 2043년)
 * Params     : sDate : Flag(1 Byte)+yyyyMMdd형태의 음력일자 ( 예 : "020121122" ) ( Flag : 평달 = "0", 윤달 = "1" )
 * Return     : 
 *               - 성공 = yyyyMMdd형태의 양력일자
 *        ( Flag : 평달 = "0", 윤달 = "1" )
 *       - 실패 = null 
 *           - 1841 ~ 2043 범위 오류의 경우
 *           - sDate가 9자리가 아닐경우
 *           - sDate의 첫자리 Flag가 "0"도 아니고 "1"도 아닌 경우
 ******************************************************************************/
function gfnLunar2Solar(sDate)
{
 var sMd = "31,0,31,30,31,30,31,31,30,31,30,31";
 var aMd = new Array(); 
 var aBaseInfo = new Array(); 
 
 var nLy, nLm, nLd, sLflag;  // 전해온 음력 인자값을 저장할 년, 월, 일, 윤달여부 임시변수
 var nSy, nSm, nSd;    // 계산된 양력 년, 월, 일을 저장할 변수
 var y1, m1, i, j, y2, y3; // 임시변수 
 var leap;
 if( gfnIsEmpty(sDate) )   return "";
 if( sDate.length != 9 )  return "";
 
 sLflag = sDate.substr(0,1);
 nLy = parseInt(sDate.substr(1,4), 10);
 nLm = parseInt(sDate.substr(5,2), 10);
 nLd = parseInt(sDate.substr(7,2), 10);
 if( nLy < 1841 || nLy > 2043 )   return "";
 if( sLflag != "0" && sLflag != "1" ) return "";
  
 aBaseInfo = _SolarBase();
 aMd = sMd.split(",");
 if( gfnIsLeapYear(sDate.substr(1,8)) == true )     
  aMd[1] = 29;
 else
  aMd[1] = 28; 
  
 y1 = nLy - 1841;
 m1 = nLm - 1;
 leap = 0;
 if( parseInt(aBaseInfo[y1*12 + m1]) > 2 )
  leap = gfnIsLeapYear(nLy+"0101");
 
 if( leap == 1 )
 {
  switch( parseInt(aBaseInfo[y1*12 + m1]) )
  {
   case 3 : mm = 29;
     break;
   case 4 : mm = 30;
     break;   
   case 5 : mm = 29;
     break;   
   case 6 : mm = 30;
     break;
  }
 }
 else
 {
  switch( parseInt(aBaseInfo[y1*12 + m1]) )
  {
   case 1 : mm = 29;
     break;   
   case 2 : mm = 30;
     break;   
   case 3 : mm = 29;
     break;   
   case 4 : mm = 29;
     break;   
   case 5 : mm = 30;
     break;   
   case 6 : mm = 30;
     break;   
  }
 }
 td = 0;
 for( i = 0 ; i <= y1 - 1 ; i++ )
 {
  for( j = 0 ; j <= 11 ; j++ )
  {
   switch( parseInt(aBaseInfo[i*12 + j]) )
   {
    case 1 : td = td + 29;
      break;
    case 2 : td = td + 30;
      break;    
    case 3 : td = td + 58;
      break;    
    case 4 : td = td + 59;
      break;    
    case 5 : td = td + 59;
      break;    
    case 6 : td = td + 60;
      break;    
   }
  }
 }
 for( j = 0 ; j <= m1 - 1 ; j++ )
 {
  switch( parseInt(aBaseInfo[y1*12 + j]) )
  {
   case 1 : td = td + 29;
     break;   
   case 2 : td = td + 30;
     break;      
   case 3 : td = td + 58;
     break;      
   case 4 : td = td + 59;
     break;      
   case 5 : td = td + 59;
     break;      
   case 6 : td = td + 60;
     break;      
  }
 }
 if( leap == 1 )
 {
  switch( parseInt(aBaseInfo[y1*12 + m1]) )
  {
   case 3 : mm = 29;
     break;      
   case 4 : mm = 29;
     break;      
   case 5 : mm = 30;
     break;      
   case 6 : mm = 30;
     break;      
  }
 }
 
 td = td + nLd + 22;
 
 if( sLflag == "1" )
 {
  switch( parseInt(aBaseInfo[y1*12 + m1]) )
  {
   case 3 : td = td + 29;
     break;      
   case 4 : td = td + 30;
     break;      
   case 5 : td = td + 29;
     break;      
   case 6 : td = td + 30;
     break;      
  }
 }
 
 y1 = 1840;
 do
 {
  y1 = y1 + 1;
  leap = gfnIsLeapYear(y1+"0101");
  if( leap == 1 )
   y2 = 366;
  else
   y2 = 365;
  if( td <= y2 )
   break;
   
  td = td - y2;
 }
 while(1);
 nSy = y1;
 aMd[1] = y2 - 337;
 m1 = 0;
 do
 {
  m1 = m1 + 1;
  if( td <= parseInt(aMd[m1-1]) )
   break;
  td = td - parseInt(aMd[m1-1]);
 }
 while(1);
 
 nSm = m1;
 nSd = td;
 y3 = nSy;
 td = y3 * 365 + parseInt(y3/4) - parseInt(y3/100) + parseInt(y3/400);
 for( i = 0 ; i <= nSm - 1 ; i++ )
  td = td + parseInt(aMd[i]);
 td = td + nSd;
 return y3 + "/" + nSm + "/" + nSd;
}
 /******************************************************************************
 * Function명 : gfnGetHolidays
 * 설명       : 양력 nYear에 해당하는 년도의 법정 공휴일(양력) List 모두 구하기
 * Params     : nYear : nYear에 해당하는 년도 ( 예 : 2012 )
 * Return     : 
 *               - 성공 = 공휴일 List Array ==> 각 Array[i]="yyyyMMdd공휴일명" 으로 return된다.
 *        ( 예 : Array[0] = "20120101신정" )
 *       - 실패 = 빈 Array
 ******************************************************************************/
function gfnGetHolidays(nYear)
{
 aHoliday = new Array();
 /////// 음력 체크
 // 구정
 aHoliday[0] = gfnLunar2Solar( "0" + (nYear-1) + "1230" ) + "/설날";
 // 석가탄신일
 aHoliday[1] = gfnLunar2Solar( "0" + nYear + "0408" ) + "/석가탄신일";
 // 추석
 aHoliday[2] = gfnLunar2Solar( "0" + nYear + "0814" ) + "/추석";
 /////// 양력 체크
 aHoliday[3] = nYear+"/01/01" + "/신정";
 aHoliday[4] = nYear+"/03/01" + "/삼일절";
 aHoliday[5] = nYear+"/05/01" + "/근로자의날"; 
 aHoliday[6] = nYear+"/05/05" + "/어린이날"; 
 aHoliday[7] = nYear+"/06/06" + "/현충일";  
 aHoliday[8] = nYear+"/08/15" + "/광복절";   
 aHoliday[9] = nYear+"/10/03" + "/개천절";   
 aHoliday[10] = nYear+"/10/09" + "/한글날";   
 aHoliday[11] = nYear+"/10/16" + "/주희생일";   
 aHoliday[12] = nYear+"/12/25" + "/성탄절";   
 return aHoliday.sort(); 
}
 /******************************************************************************
 * Function명 : _SolarBase
 * 설명       : 각 월별 음력 기준 정보를 처리하는 함수(처리가능 기간  1841 - 2043년) 단, 내부에서 사용하는 함수임
 * Params     :  없음
 * Return     : 
 *               - 성공 = 음력 기준정보
 ******************************************************************************/
function _SolarBase()
{
 var kk;
 
 //1841
 kk = "1,2,4,1,1,2,1,2,1,2,2,1,";
 kk += "2,2,1,2,1,1,2,1,2,1,2,1,";
 kk += "2,2,2,1,2,1,4,1,2,1,2,1,";
 kk += "2,2,1,2,1,2,1,2,1,2,1,2,";
 kk += "1,2,1,2,2,1,2,1,2,1,2,1,";
 kk += "2,1,2,1,5,2,1,2,2,1,2,1,";
 kk += "2,1,1,2,1,2,1,2,2,2,1,2,";
 kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
 kk += "2,1,2,3,2,1,2,1,2,1,2,2,";
 kk += "2,1,2,1,1,2,1,1,2,2,1,2,";
 //1851
 kk += "2,2,1,2,1,1,2,1,2,1,5,2,";
 kk += "2,1,2,2,1,1,2,1,2,1,1,2,";
 kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
 kk += "1,2,1,2,1,2,5,2,1,2,1,2,";
 kk += "1,1,2,1,2,2,1,2,2,1,2,1,";
 kk += "2,1,1,2,1,2,1,2,2,2,1,2,";
 kk += "1,2,1,1,5,2,1,2,1,2,2,2,";
 kk += "1,2,1,1,2,1,1,2,2,1,2,2,";
 kk += "2,1,2,1,1,2,1,1,2,1,2,2,";
 kk += "2,1,6,1,1,2,1,1,2,1,2,2,";
 //1861
 kk += "1,2,2,1,2,1,2,1,2,1,1,2,";
 kk += "2,1,2,1,2,2,1,2,2,3,1,2,";
 kk += "1,2,2,1,2,1,2,2,1,2,1,2,";
 kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
 kk += "2,1,1,2,4,1,2,2,1,2,2,1,";
 kk += "2,1,1,2,1,1,2,2,1,2,2,2,";
 kk += "1,2,1,1,2,1,1,2,1,2,2,2,";
 kk += "1,2,2,3,2,1,1,2,1,2,2,1,";
 kk += "2,2,2,1,1,2,1,1,2,1,2,1,";
 kk += "2,2,2,1,2,1,2,1,1,5,2,1,";
 //1871
 kk += "2,2,1,2,2,1,2,1,2,1,1,2,";
 kk += "1,2,1,2,2,1,2,1,2,2,1,2,";
 kk += "1,1,2,1,2,4,2,1,2,2,1,2,";
 kk += "1,1,2,1,2,1,2,1,2,2,2,1,";
 kk += "2,1,1,2,1,1,2,1,2,2,2,1,";
 kk += "2,2,1,1,5,1,2,1,2,2,1,2,";
 kk += "2,2,1,1,2,1,1,2,1,2,1,2,";
 kk += "2,2,1,2,1,2,1,1,2,1,2,1,";
 kk += "2,2,4,2,1,2,1,1,2,1,2,1,";
 kk += "2,1,2,2,1,2,2,1,2,1,1,2,";
 //1881
 kk += "1,2,1,2,1,2,5,2,2,1,2,1,";
 kk += "1,2,1,2,1,2,1,2,2,1,2,2,";
 kk += "1,1,2,1,1,2,1,2,2,2,1,2,";
 kk += "2,1,1,2,3,2,1,2,2,1,2,2,";
 kk += "2,1,1,2,1,1,2,1,2,1,2,2,";
 kk += "2,1,2,1,2,1,1,2,1,2,1,2,";
 kk += "2,2,1,5,2,1,1,2,1,2,1,2,";
 kk += "2,1,2,2,1,2,1,1,2,1,2,1,";
 kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
 kk += "1,5,2,1,2,2,1,2,1,2,1,2,";
 //1891
 kk += "1,2,1,2,1,2,1,2,2,1,2,2,";
 kk += "1,1,2,1,1,5,2,2,1,2,2,2,";
 kk += "1,1,2,1,1,2,1,2,1,2,2,2,";
 kk += "1,2,1,2,1,1,2,1,2,1,2,2,";
 kk += "2,1,2,1,5,1,2,1,2,1,2,1,";
 kk += "2,2,2,1,2,1,1,2,1,2,1,2,";
 kk += "1,2,2,1,2,1,2,1,2,1,2,1,";
 kk += "2,1,5,2,2,1,2,1,2,1,2,1,";
 kk += "2,1,2,1,2,1,2,2,1,2,1,2,";
 kk += "1,2,1,1,2,1,2,5,2,2,1,2,";
 //1901
 kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
 kk += "2,1,2,1,1,2,1,2,1,2,2,2,";
 kk += "1,2,1,2,3,2,1,1,2,2,1,2,";
 kk += "2,2,1,2,1,1,2,1,1,2,2,1,";
 kk += "2,2,1,2,2,1,1,2,1,2,1,2,";
 kk += "1,2,2,4,1,2,1,2,1,2,1,2,";
 kk += "1,2,1,2,1,2,2,1,2,1,2,1,";
 kk += "2,1,1,2,2,1,2,1,2,2,1,2,";
 kk += "1,5,1,2,1,2,1,2,2,2,1,2,";
 kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
 //1911
 kk += "2,1,2,1,1,5,1,2,2,1,2,2,";
 kk += "2,1,2,1,1,2,1,1,2,2,1,2,";
 kk += "2,2,1,2,1,1,2,1,1,2,1,2,";
 kk += "2,2,1,2,5,1,2,1,2,1,1,2,";
 kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
 kk += "1,2,1,2,1,2,2,1,2,1,2,1,";
 kk += "2,3,2,1,2,2,1,2,2,1,2,1,";
 kk += "2,1,1,2,1,2,1,2,2,2,1,2,";
 kk += "1,2,1,1,2,1,5,2,2,1,2,2,";
 kk += "1,2,1,1,2,1,1,2,2,1,2,2,";
 //1921
 kk += "2,1,2,1,1,2,1,1,2,1,2,2,";
 kk += "2,1,2,2,3,2,1,1,2,1,2,2,";
 kk += "1,2,2,1,2,1,2,1,2,1,1,2,";
 kk += "2,1,2,1,2,2,1,2,1,2,1,1,";
 kk += "2,1,2,5,2,1,2,2,1,2,1,2,";
 kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
 kk += "2,1,1,2,1,2,1,2,2,1,2,2,";
 kk += "1,5,1,2,1,1,2,2,1,2,2,2,";
 kk += "1,2,1,1,2,1,1,2,1,2,2,2,";
 kk += "1,2,2,1,1,5,1,2,1,2,2,1,";
 //1931
 kk += "2,2,2,1,1,2,1,1,2,1,2,1,";
 kk += "2,2,2,1,2,1,2,1,1,2,1,2,";
 kk += "1,2,2,1,6,1,2,1,2,1,1,2,";
 kk += "1,2,1,2,2,1,2,2,1,2,1,2,";
 kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
 kk += "2,1,4,1,2,1,2,1,2,2,2,1,";
 kk += "2,1,1,2,1,1,2,1,2,2,2,1,";
 kk += "2,2,1,1,2,1,4,1,2,2,1,2,";
 kk += "2,2,1,1,2,1,1,2,1,2,1,2,";
 kk += "2,2,1,2,1,2,1,1,2,1,2,1,";
 //1941
 kk += "2,2,1,2,2,4,1,1,2,1,2,1,";
 kk += "2,1,2,2,1,2,2,1,2,1,1,2,";
 kk += "1,2,1,2,1,2,2,1,2,2,1,2,";
 kk += "1,1,2,4,1,2,1,2,2,1,2,2,";
 kk += "1,1,2,1,1,2,1,2,2,2,1,2,";
 kk += "2,1,1,2,1,1,2,1,2,2,1,2,";
 kk += "2,5,1,2,1,1,2,1,2,1,2,2,";
 kk += "2,1,2,1,2,1,1,2,1,2,1,2,";
 kk += "2,2,1,2,1,2,3,2,1,2,1,2,";
 kk += "2,1,2,2,1,2,1,1,2,1,2,1,";
 //1951
 kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
 kk += "1,2,1,2,4,2,1,2,1,2,1,2,";
 kk += "1,2,1,1,2,2,1,2,2,1,2,2,";
 kk += "1,1,2,1,1,2,1,2,2,1,2,2,";
 kk += "2,1,4,1,1,2,1,2,1,2,2,2,";
 kk += "1,2,1,2,1,1,2,1,2,1,2,2,";
 kk += "2,1,2,1,2,1,1,5,2,1,2,2,";
 kk += "1,2,2,1,2,1,1,2,1,2,1,2,";
 kk += "1,2,2,1,2,1,2,1,2,1,2,1,";
 kk += "2,1,2,1,2,5,2,1,2,1,2,1,";
 //1961
 kk += "2,1,2,1,2,1,2,2,1,2,1,2,";
 kk += "1,2,1,1,2,1,2,2,1,2,2,1,";
 kk += "2,1,2,3,2,1,2,1,2,2,2,1,";
 kk += "2,1,2,1,1,2,1,2,1,2,2,2,";
 kk += "1,2,1,2,1,1,2,1,1,2,2,1,";
 kk += "2,2,5,2,1,1,2,1,1,2,2,1,";
 kk += "2,2,1,2,2,1,1,2,1,2,1,2,";
 kk += "1,2,2,1,2,1,5,2,1,2,1,2,";
 kk += "1,2,1,2,1,2,2,1,2,1,2,1,";
 kk += "2,1,1,2,2,1,2,1,2,2,1,2,";
 //1971
 kk += "1,2,1,1,5,2,1,2,2,2,1,2,";
 kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
 kk += "2,1,2,1,1,2,1,1,2,2,2,1,";
 kk += "2,2,1,5,1,2,1,1,2,2,1,2,";
 kk += "2,2,1,2,1,1,2,1,1,2,1,2,";
 kk += "2,2,1,2,1,2,1,5,2,1,1,2,";
 kk += "2,1,2,2,1,2,1,2,1,2,1,1,";
 kk += "2,2,1,2,1,2,2,1,2,1,2,1,";
 kk += "2,1,1,2,1,6,1,2,2,1,2,1,";
 kk += "2,1,1,2,1,2,1,2,2,1,2,2,";
 //1981
 kk += "1,2,1,1,2,1,1,2,2,1,2,2,";
 kk += "2,1,2,3,2,1,1,2,2,1,2,2,";
 kk += "2,1,2,1,1,2,1,1,2,1,2,2,";
 kk += "2,1,2,2,1,1,2,1,1,5,2,2,";
 kk += "1,2,2,1,2,1,2,1,1,2,1,2,";
 kk += "1,2,2,1,2,2,1,2,1,2,1,1,";
 kk += "2,1,2,2,1,5,2,2,1,2,1,2,";
 kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
 kk += "2,1,1,2,1,2,1,2,2,1,2,2,";
 kk += "1,2,1,1,5,1,2,1,2,2,2,2,";
 //1991
 kk += "1,2,1,1,2,1,1,2,1,2,2,2,";
 kk += "1,2,2,1,1,2,1,1,2,1,2,2,";
 kk += "1,2,5,2,1,2,1,1,2,1,2,1,";
 kk += "2,2,2,1,2,1,2,1,1,2,1,2,";
 kk += "1,2,2,1,2,2,1,5,2,1,1,2,";
 kk += "1,2,1,2,2,1,2,1,2,2,1,2,";
 kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
 kk += "2,1,1,2,3,2,2,1,2,2,2,1,";
 kk += "2,1,1,2,1,1,2,1,2,2,2,1,";
 kk += "2,2,1,1,2,1,1,2,1,2,2,1,";
 //2001
 kk += "2,2,2,3,2,1,1,2,1,2,1,2,";
 kk += "2,2,1,2,1,2,1,1,2,1,2,1,";
 kk += "2,2,1,2,2,1,2,1,1,2,1,2,";
 kk += "1,5,2,2,1,2,1,2,2,1,1,2,";
 kk += "1,2,1,2,1,2,2,1,2,2,1,2,";
 kk += "1,1,2,1,2,1,5,2,2,1,2,2,";
 kk += "1,1,2,1,1,2,1,2,2,2,1,2,";
 kk += "2,1,1,2,1,1,2,1,2,2,1,2,";
 kk += "2,2,1,1,5,1,2,1,2,1,2,2,";
 kk += "2,1,2,1,2,1,1,2,1,2,1,2,";
 //2011
 kk += "2,1,2,2,1,2,1,1,2,1,2,1,";
 kk += "2,1,6,2,1,2,1,1,2,1,2,1,";
 kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
 kk += "1,2,1,2,1,2,1,2,5,2,1,2,";
 kk += "1,2,1,1,2,1,2,2,2,1,2,2,";
 kk += "1,1,2,1,1,2,1,2,2,1,2,2,";
 kk += "2,1,1,2,3,2,1,2,1,2,2,2,";
 kk += "1,2,1,2,1,1,2,1,2,1,2,2,";
 kk += "2,1,2,1,2,1,1,2,1,2,1,2,";
 kk += "2,1,2,5,2,1,1,2,1,2,1,2,";
 //2021
 kk += "1,2,2,1,2,1,2,1,2,1,2,1,";
 kk += "2,1,2,1,2,2,1,2,1,2,1,2,";
 kk += "1,5,2,1,2,1,2,2,1,2,1,2,";
 kk += "1,2,1,1,2,1,2,2,1,2,2,1,";
 kk += "2,1,2,1,1,5,2,1,2,2,2,1,";
 kk += "2,1,2,1,1,2,1,2,1,2,2,2,";
 kk += "1,2,1,2,1,1,2,1,1,2,2,2,";
 kk += "1,2,2,1,5,1,2,1,1,2,2,1,";
 kk += "2,2,1,2,2,1,1,2,1,1,2,2,";
 kk += "1,2,1,2,2,1,2,1,2,1,2,1,";
 //2031
 kk += "2,1,5,2,1,2,2,1,2,1,2,1,";
 kk += "2,1,1,2,1,2,2,1,2,2,1,2,";
 kk += "1,2,1,1,2,1,5,2,2,2,1,2,";
 kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
 kk += "2,1,2,1,1,2,1,1,2,2,1,2,";
 kk += "2,2,1,2,1,4,1,1,2,1,2,2,";
 kk += "2,2,1,2,1,1,2,1,1,2,1,2,";
 kk += "2,2,1,2,1,2,1,2,1,1,2,1,";
 kk += "2,2,1,2,5,2,1,2,1,2,1,1,";
 kk += "2,1,2,2,1,2,2,1,2,1,2,1,";
 //2041
 kk += "2,1,1,2,1,2,2,1,2,2,1,2,";
 kk += "1,5,1,2,1,2,1,2,2,2,1,2,";
 kk += "1,2,1,1,2,1,1,2,2,1,2,2";
 
 var arr = new Array();
 arr = kk.split(",");
 
 return arr;
}
//현재월에서 +-월의 마지막날을 찾는 function
function gfnNowLastDay(intNum)
{
 var objDate2 = new Date();
 objDate2.addMonth(intNum+1);
 objDate2.setDate(1);
 objDate2.addDate(-1);
 var last_date = objDate2.toFormatString("%Y%m%d");
 
 return last_date;
}
//현재월에서 +-월의 첫날을 찾는 function
function gfnNowFirstDay(intNum)
{
 var objDate2 = new Date();
 objDate2.addMonth(intNum);
 objDate2.setDate(1);
 objDate2.addDate(0);
 var first_date = objDate2.toFormatString("%Y%m%d");
 
 return first_date;
}
/*******************************************************************************
 * Function명 : gfnTodayDiv
 * 설명          : 해당 PC의 오늘 날짜를 가져온다. (년/월/일 사이에 구분자를 원하는 형태로)
 * parameter  : strDel   구분자
 * return        : string
******************************************************************************/ 
function gfnTodayDiv(strDel)
{
    var strToday = "";
    var objDate   = new Date();
    var strToday  = objDate.getFullYear();
    strToday = strToday + strDel + gfnRight("0" + (objDate.getMonth() + 1), 2);
    strToday = strToday + strDel + gfnRight("0" + objDate.getDate(), 2);
    return strToday;
}    

function gfnIsEmpty(date){
	if(date == null || date == ''){
		return true;
	}else{
		return false;
	}
}

/*
 *공휴일 여부 
 */
function isHoliday(date){
	var m = date.getMonth();
	var d = date.getDate();
	for(var i = 0 ; i < aHoliday.length ; i ++){
		var temp = aHoliday[i].split("/");
		if((parseInt(temp[1])-1) == m && temp[2] == d){
			return true;
		}
	}
	return false;
}

/*
 * 공휴일 추가
 * */
function addHoliday(date){
	for(var i = 0 ; i < aHoliday.length ; i ++){
		var temp = aHoliday[i].split("/");
		
		var event = {id:new Date(temp[0],(parseInt(temp[1])-1),temp[2]).getTime() , title  : temp[3],start  : new Date(temp[0],(parseInt(temp[1])-1),temp[2]),color:'red', editable : false, allDay: true,  rendering: 'background'};
		
		if(temp[3] == '설날' || temp[3] == '추석' ){
			event.start = new Date(temp[0],(parseInt(temp[1])-1),temp[2]);
			event.end = new Date(temp[0],(parseInt(temp[1])-1),parseInt(temp[2])+3).toISOString(); 
		}
		
		if(calendar.getEventById( new Date(temp[0],(parseInt(temp[1])-1),temp[2]).getTime() ) != null){
			calendar.getEventById( new Date(temp[0],(parseInt(temp[1])-1),temp[2]).getTime() ).remove();
		}
		calendar.addEvent( event );
	}
}

/*
 * 급여날 계산
 */
function getPayDay(){
	var calDate = calendar.getDate();
	var y = calDate.getFullYear();
	var m = calDate.getMonth();
	var d = 25;
	var payday = new Date(y,m,d);
	if(yy != y){
		gfnGetHolidays(y);
		yy = y;
	}
	addHoliday(new Date(y,m,d));
	if(payday.getDay() == 0){
		d = d-2;
	}else if(payday.getDay() == 6){
		d = d-1;
	}
	while(isHoliday(new Date(y,m,d))){
		d = d-1;
	}
	
	if(calendar.getEventById( new Date(y,m,d).getTime() ) != null){
		calendar.getEventById( new Date(y,m,d).getTime() ).remove();
	}
	calendar.addEvent( {id: new Date(y,m,d).getTime(),title  : '월급날',start  : new Date(y,m,d),color:'red', editable : false, allDay: true} );
}