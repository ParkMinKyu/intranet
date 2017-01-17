# Simple Intranet
=====
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

개발 시작일 : 2014-04-01<br>
V1.0 완료일 : 2015-09-03<br>
V1.01 완료일 : 2015-09-05<br>
- ERD EMPLOYEE - regdate default NOW() 제거
- javascript JSON.parse 미흡부분 추가
- 사용자로그인이나 등록시에 자동으로 붙여놨던 @도메인. 제거
- java1.6 -> 1.7 변경
- servlet3.0변경 및 tomcat7사용시 url에 jsessionid붙는 현상 제거



사용된 라이브러리<br>
Spring 3.2.14<br>
JUI<br>
jquery-1.8.3<br>
jackson 1.9.11<br>
등등.......
