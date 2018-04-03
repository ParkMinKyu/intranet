## Simple Intranet

## URL접속 후 guest/123456 으로 테스트 가능

### server start시 argument에 -Dniee.mode=real 추가<br>
### 암호화 모듈 사용시 argument에 -Dniee.security=password 추가<br>
### properties value 지정시 ENC(암호화된 String)<br>
### properties 암호화될 String 생성 방법 <br>
```java
public static void main(String[] args) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword("niee.security value");
		String password = encryptor.encrypt("test");
		System.out.println(password);
		System.out.println(encryptor.decrypt(password));
	}
```
### /src/main/resources/config/ 폴더에 각종 설정파일들 수정<br>
### /src/main/resources/sqlmap/E-R/ 폴더에 있는 mysql용 ERD를 이용하여 디비생성<br>
### [설치방법](http://niees.tistory.com/66)

V2.0.0 완료일 : 2017-11-30(오래도 걸렸다 ;;)<br>
 - 추가해야지 해야지 하고 미루던 spring security 추가 ( 3.1.0 버전 인게 함정)  
 - remember me 기능 추가

V1.04 완료일 : 2017-02-08<br>
 - 각 서비스별 controller, service 나누기 완료

V1.03 완료일 : 2017-01-18(오래도 걸렸다 ;;)<br>
 - 스케줄 수정시 파일 추가 등록 시 등록한 파일이 제대로 다운로드 되지 않는 현상 수정
 - 스케줄 파일 삭제시 다른 파일이 같이 삭제 되는 현상 수정

V1.02 완료일 : 2015-09-08<br>
 - 스케줄 리스트 불러올때 쿼리에서 Content 칼럼 불러오는 부분 제거
 - HomeServiceImpl에서 리스트 불러올때 Content html태그 변경 부분 제거
 - intranet_JQUERY 에서 fullcalendar randaring할 때 이름으로 색상 변경하던 부분 제거

V1.01 완료일 : 2015-09-05<br>
- ERD EMPLOYEE - regdate default NOW() 제거
- javascript JSON.parse 미흡부분 추가
- 사용자로그인이나 등록시에 자동으로 붙여놨던 @도메인. 제거
- java1.6 -> 1.7 변경
- servlet3.0변경 및 tomcat7사용시 url에 jsessionid붙는 현상 제거

V1.0 완료일 : 2015-09-03<br>
개발 시작일 : 2014-04-01<br>

사용된 라이브러리<br>
Spring 3.2.14<br>
JUI<br>
jquery-1.8.3<br>
jackson 1.9.11<br>
등등.......
