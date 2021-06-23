## 캠핑카 대여/관리 프로그램

</br>
> ## Description

- ### 개발목적

데이터베이스 교과목에서 JAVA언어와 MySQL DB를 연동하여 프로그래밍 언어로 DB의 모든 데이터를 조작하는 것과 SQL문 사용에  능숙해지기 위해 진행한 과제


- ### 개발기간

2020.04 ~ 2020.06


- ### 사용기술

JAVA(swing GUI), MySQL

</br>

---

> ## Function

- ### 관리자

  1. #### 캠핑카 리스트 데이터 초기화

  <이미지 450 400>1

  - '초기화' 버튼으로 동작하며, 데이터 초기화를 수행하는 기능
  - 초기에는 15개의 대여가능 캠핑카들이 존재

  </br>

  2. #### 캠핑카 데이터 삽입/삭제/변경

  <이미지>2

  - 'data 삽입/삭제/변경 및 반환' 버튼으로 동작하며, 데이터를 조작하는 기능

  - 생성된 창에 '수행할 작업' 과 '변경할 테이블' 선택 후 '작업 선택 및 테이블 선택' 버튼으로 어떤 데이터를 기입해야 하는지 양식이 바뀐다

  <이미지>3

  - 데이터 기입 후 '수행' 버튼으로 실제 데이터 변경이 실행된다.

  - 데이터 변경은 총 5개 (대여회사, 캠핑카, 고객, 정비소, 정비정보)의 테이블 내에서 이뤄진다

  </br>

  3. #### 검색기능

     <이미지>4

     관리자의 검색기능은 3가지가 존재한다

     - 대여고객 및 대여차량 테이블 검색 기능
     - 수리가 필요한 차량 테이블 검색 기능
     - 정비소별 정비내역 테이블 검색 기능

  </br></br>

- ### 고객

  1. #### 대여가능한 캠핑카 검색

     <이미지>5

     - '대여가능한 캠핑카 목록 검색' 버튼으로 동작하며, 현재 대여가 가능한 캠핑카의 목록을 검색하는 기능

  2. #### 캠핑카 대여 신청

     <이미지>6

     - 알맞은 양식 기입 후 '신청' 버튼으로 대여 신청이 실행된다.

  </br></br>

---

> ## Development

1. ### MySQL 연동

   - 먼저 sql 라이브러리를 import 선언해준다.

   ```java
   import java.sql.*;
   ```

   

   - Class.forName() 으로 드라이버를 로드하고, getConnection()으로 connection 인스턴스를 생성하여 DB와 연결하는 과정

   ```java
   public void conDB() {
         try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //드라이버 로드
            System.out.println("드라이버 로드 성공");
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }
         
         try { /* 데이터베이스를 연결하는 과정 */
             System.out.println("데이터베이스 연결 준비...");
             con = DriverManager.getConnection(url, userid, pwd); 
             	//사전에 설정된 url, userid, pwd로 connection 인스턴스 생성
             System.out.println("데이터베이스 연결 성공");
          } catch (SQLException e1) {
             e1.printStackTrace();
          }
      }
   ```

   </br></br>

2. ### 데이터 생성

   총 8 개의 테이블을 생성하였다.

   <테이블 명>

   - campingcarlist (모든 캠핑카 테이블)
   - availablecampingcarlist (대여가 가능한 캠핑카 테이블)
   - campingcustomer (고객 테이블)
   - carcenterlist (정비소 테이블)
   - carcheck (반환 후 수리가 필요한 차량 테이블)
   - companylist (대여회사 테이블)
   - fixinformation (정비소별 정비내역 테이블)
   - presentrentcampingcarlist (현재 대여중인 캠핑카 테이블)

</br>

   ```java
   public void createTable() {
   /* 테이블 구현
   Companylist,Campingcarlist,CampingCustomer,PresentRentCampingcarList,
   CarCheck,FixInformation,CarcenterList,AvailableCampingcarlist
   */
   	  try { 
   	   String sql[] = new String[8];
   	   StringBuilder sb = new StringBuilder();
   	   stmt = con.createStatement();
   	   
   	   stmt.execute("drop table if exists companylist,campingcarlist,campingcustomer,presentrentcampingcarlist,carcheck,fixinformation,carcenterlist,availablecampingcarlist");
          // 기존에 존재한 테이블이 있다면 drop	   
   	  // 각 테이블별 스키마 구성
   	   sql[0] = sb.append("create table Companylist(")//table1
                  .append("companyid INTEGER PRIMARY KEY,")//캠핑카대여회사ID
                  .append("companyname varchar(45),")//회사명
                  .append("companyaddress varchar(45),")//주소
                  .append("companyphone varchar(45),")//전화번호
                  .append("managername varchar(45),")//담당자이름
                  .append("manageremail varchar(45)")//담당자이메일
                  .append(");").toString();
   	   	stmt.execute(sql[0]);
   	   	sb.delete(0,sb.length());
   	   
   	   sql[1] = sb.append("create table Campingcarlist(")//table2
                  .append("carid INTEGER PRIMARY KEY,")//캠핑카등록ID
                  .append("carname varchar(45),")//캠핑카 이름
                  .append("carnumber INTEGER,")//캠핑카 차량번호
                  .append("carmax INTEGER,")//캠핑카 인원수
                  .append("carbrand varchar(45),")//차제조회사
                  .append("birthday DATE,")//제조연도
                  .append("distance INTEGER,")//누적주행거리
                  .append("rentprice INTEGER,")//캠핑카대여비용
                  .append("companyid INTEGER,")//캠핑카대여회사ID
                  .append("enrolldate DATE")//캠핑카등록일자
                  .append(");").toString();
   	   	stmt.execute(sql[1]);
   	   	sb.delete(0,sb.length());
   	   			
   	   	sql[2] = sb.append("create table CampingCustomer(")//table3
   	   			.append("licenseid INTEGER PRIMARY KEY,")//운전면허증번호
   	   			.append("customername varchar(45),")//고객명
   	   			.append("customeraddress varchar(45),")//고객주소
   	   			.append("customerphone varchar(45),")//고객전화번호
   	   			.append("customeremail varchar(45)")//고객이메일
   	   			.append(");").toString();
   	   	stmt.execute(sql[2]);
   	   	sb.delete(0, sb.length());
   	   			
   	   	sql[3] = sb.append("create table PresentRentCampingcarList(")//table4
   	   			.append("rentid INTEGER PRIMARY KEY,")//고유대여번호
   	   			.append("carid INTEGER,")//캠핑카등록ID
   	   			.append("licenseid INTEGER,")//운전면허증번호
   	   			.append("companyid INTEGER,")//캠핑카대여회사ID
   	   			.append("startdate DATE,")//대여시작일
   	   			.append("enddate DATE,")//대여기간
   	   			.append("rentprice INTEGER,")//청구요금
   	   			.append("depositdate DATE,")//납입기한
   	   			.append("etc varchar(45),")//기타청구내역
   	   			.append("etcprice INTEGER")//기타청구요금정보
   	   			.append(");").toString();
   	   	stmt.execute(sql[3]);
   	   	sb.delete(0,  sb.length());
   	   			
   	   	sql[4] = sb.append("create table CarCheck(")//table5
   	   			.append("rentid INTEGER PRIMARY KEY,")//고유대여번호
   	   			.append("carid INTEGER,")//캠핑카등록ID
   	   			.append("frontdescription varchar(45),")//앞부분설명
   	   			.append("leftdescription varchar(45),")//왼쪽설명
   	   			.append("rightdescription varchar(45),")//오른쪽설명
   	   			.append("backdescription varchar(45),")//뒤쪽설명
   	   			.append("havetofix INTEGER,")//수리필요여부
   	   			.append("licenseid INTEGER")//운전면허증번호
   	   			.append(");").toString();
   	   	stmt.execute(sql[4]);
   	   	sb.delete(0,  sb.length());
   	   			
   	   	sql[5] = sb.append("create table FixInformation(")//table6
   	   			.append("fixid INTEGER PRIMARY KEY,")//고유정비번호
   	   			.append("carid INTEGER,")//캠핑카등록ID
   	   			.append("carcenterid INTEGER,")//캠핑카정비소ID
   	   			.append("companyid INTEGER,")//캠핑카대여회사ID
   	   			.append("licenseid INTEGER,")//운전면허증번호
   	   			.append("fix varchar(45),")//정비내역
   	   			.append("fixdate DATE,")//수리날짜
   	   			.append("fixprice INTEGER,")//수리비용
   	   			.append("depositdate DATE,")//납입기한
   	   			.append("etcfix varchar(45)")//기타정비내역
   	   			.append(");").toString();
   	   	stmt.execute(sql[5]);
   	   	sb.delete(0,  sb.length());
   	   	
   	   	sql[6] = sb.append("create table CarcenterList(")//table7
   	   			.append("carcenterid INTEGER PRIMARY KEY,")//캠핑카정비소ID
   	   			.append("carcentername varchar(45),")//정비소명
   	   			.append("carcenteraddress varchar(45),")//정비소주소
   	   			.append("carcenterphone varchar(45),")//정비소전화번호
   	   			.append("centermanagername varchar(45),")//담당자이름
   	   			.append("centermanageremail varchar(45)")//이메일정보
   	   			.append(");").toString();
   	   	stmt.execute(sql[6]);
   	   	sb.delete(0,  sb.length());
   	   	
   	   	
   	   	sql[7] = sb.append("create table AvailableCampingcarlist(")//table8
   	   			.append("carid INTEGER PRIMARY KEY,")//캠핑카등록ID
   	               .append("carname varchar(45),")//캠핑카 이름
   	               .append("carnumber INTEGER,")//캠핑카 차량번호
   	               .append("carmax INTEGER,")//캠핑카 인원수
   	               .append("carbrand varchar(45),")//차제조회사
   	               .append("birthday DATE,")//제조연도
   	               .append("distance INTEGER,")//누적주행거리
   	               .append("rentprice INTEGER,")//캠핑카대여비용
   	               .append("companyid INTEGER,")//캠핑카대여회사ID
   	               .append("enrolldate DATE")//캠핑카등록일자
   	               .append(");").toString();
   		   	stmt.execute(sql[7]);
   		   	sb.delete(0,sb.length());	   	
   	   	
   	  }catch(SQLException CreateError)
   		{
   			System.out.println("테이블초기화작업실패"+CreateError);
   		}
   	  
      }
   ```

   <br/>

   실제 초기 데이터를 넣는 과정은 아래와 같이 INSERT문을 통해 실행하였다.

   ```java
      public void insertMydata() { //초기data구현
   	try{
   		String delete[] = new String[6];
   		String insertCompanyList[] = new String[15];
   		String insertCampingcarList[] = new String[15];
   		String insertCampingCustomer[] = new String[15];
   		String insertCarcenterList[] = new String[15];
   		String insertAvailableCampingcarList[] = new String[15];
   		int i;
   	stmt = con.createStatement();
   	
   	delete[0] = "SET foreign_key_checks =0;";
   	delete[1] = "delete from CompanyList ;";
   	delete[2] = "delete from CampingcarList ;";
   	delete[3] = "delete from CampingCustomer ;";
   	delete[4] = "delete from CarcenterList ;";
   	delete[5] = "SET foreign_key_checks =1;";
   	
   //CompanyList data
   	insertCompanyList[0] = "INSERT INTO CompanyList Values (1,'삼성','서울 강남','000-0000-0001','메시','messi@gmail.com')";
   	insertCompanyList[1] = "INSERT INTO CompanyList Values (2,'LG','서울 서초','000-0000-0002','네이마르','neymar@gmail.com')";
   	insertCompanyList[2] = "INSERT INTO CompanyList Values (3,'현대','서울 송파','000-0000-0003','호날두','ronaldo@gmail.com')";
   	insertCompanyList[3] = "INSERT INTO CompanyList Values (4,'SK','성남 분당','000-0000-0004','베일','bale@gmail.com')";
   	insertCompanyList[4] = "INSERT INTO CompanyList Values (5,'롯데','경기 용인','000-0000-0005','오스카','oscar@gmail.com')";
   	insertCompanyList[5] = "INSERT INTO CompanyList Values (6,'포스코','고양 일산','000-0000-0006','포그바','pogba@gmail.com')";
   	insertCompanyList[6] = "INSERT INTO CompanyList Values (7,'한화','서울 목동','000-0000-0007','루니','rooney@gmail.com')";
   	insertCompanyList[7] = "INSERT INTO CompanyList Values (8,'GS','서울 동작','000-0000-0008','실바','silva@gmail.com')";
   	insertCompanyList[8] = "INSERT INTO CompanyList Values (9,'농협','서울 영등포','000-0000-0009','펠레','pele@gmail.com')";
   	insertCompanyList[9] = "INSERT INTO CompanyList Values (10,'신세계','대구 수성','000-0000-0010','헐크','hulk@gmail.com')";
   	insertCompanyList[10] = "INSERT INTO CompanyList Values (11,'CJ','서울 마포','000-0000-0011','디마리아','dimaria@gmail.com')";
   	insertCompanyList[11] = "INSERT INTO CompanyList Values (12,'한진','경기 수원','000-0000-0012','음바페','mbappe@gmail.com')";
   	insertCompanyList[12] = "INSERT INTO CompanyList Values (13,'두산','서울 용산','000-0000-0013','수아레스','suarez@gmail.com')";
   	insertCompanyList[13] = "INSERT INTO CompanyList Values (14,'대림','서울 광진','000-0000-0014','카바니','cavani@gmail.com')";
   	insertCompanyList[14] = "INSERT INTO CompanyList Values (15,'KT','서울 강서','000-0000-0015','피케','pique@gmail.com')";
   	
   //CampingcarList data
   	insertCampingcarList[0] = "INSERT INTO CampingcarList VALUES (1, 'QM3', 1111, 4, '삼성', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 2, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[1] = "INSERT INTO CampingcarList VALUES (2, '엑센트 디젤',1112, 4, '현대', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 5, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[2] = "INSERT INTO CampingcarList VALUES (3, '쏘나타 ', 1113, 4, '현대', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 2, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[3] = "INSERT INTO CampingcarList VALUES (4, 'i30 디젤', 1114, 4, '현대', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 2, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[4] = "INSERT INTO CampingcarList VALUES (5, 'K5 하이브리드', 1115, 4, '기아', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 4, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[5] = "INSERT INTO CampingcarList VALUES (6, 'SM5 디젤', 1116, 6, '삼성', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 6, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[6] = "INSERT INTO CampingcarList VALUES (7, '모닝 CVT', 1117, 6, '기아', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 9, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[7] = "INSERT INTO CampingcarList VALUES (8, 'K3 디젤', 1118, 6, '기아', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 10, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[8] = "INSERT INTO CampingcarList VALUES (9, '아반떼 디젤', 1119, 6, '현대', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 11, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[9] = "INSERT INTO CampingcarList VALUES (10, 'K7 하이브리드', 1120, 8, '기아', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 15, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[10] = "INSERT INTO CampingcarList VALUES (11, '그랜저 ', 1121, 8, '현대', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 12, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[11] = "INSERT INTO CampingcarList VALUES (12, '쉐보레 스파크', 1122, 8, 'GM', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 4, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[12] = "INSERT INTO CampingcarList VALUES (13, '옵티머스프라임', 1123, 8, '오토봇', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 140000, 7, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[13] = "INSERT INTO CampingcarList VALUES (14, '범블비', 1124, 10, '오토봇', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 140000, 8, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertCampingcarList[14] = "INSERT INTO CampingcarList VALUES (15, '벤츠', 1125, 12, '벤츠', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 990000, 3, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	
   //CampingCustomer data
   	insertCampingCustomer[0] = "INSERT INTO CampingCustomer VALUES (1, '조영남', '서울 강남', '000-0000-0001', 'yn@gmail.com')";
   	insertCampingCustomer[1] = "INSERT INTO CampingCustomer VALUES (2, '정지훈', '서울 서초', '000-0000-0002', 'jh@gmail.com')";
   	insertCampingCustomer[2] = "INSERT INTO CampingCustomer VALUES (3, '김남주', '서울 송파', '000-0000-0003', 'nj@gmail.com')";
   	insertCampingCustomer[3] = "INSERT INTO CampingCustomer VALUES (4, '송혜교', '서울 용산', '000-0000-0004', 'hg@gmail.com')";
   	insertCampingCustomer[4] = "INSERT INTO CampingCustomer VALUES (5, '한채영', '서울 양천', '000-0000-0005', 'cy@gmail.com')";
   	insertCampingCustomer[5] = "INSERT INTO CampingCustomer VALUES (6, '박중훈', '서울 마포', '000-0000-0006', 'jh@gmail.com')";
   	insertCampingCustomer[6] = "INSERT INTO CampingCustomer VALUES (7, '최지우', '서울 광진', '000-0000-0007', 'jw@gmail.com')";
   	insertCampingCustomer[7] = "INSERT INTO CampingCustomer VALUES (8, '장근석', '서울 성동', '000-0000-0008', 'gs@gmail.com')";
   	insertCampingCustomer[8] = "INSERT INTO CampingCustomer VALUES (9, '유재석', '서울 강동', '000-0000-0009', 'js@gmail.com')";
   	insertCampingCustomer[9] = "INSERT INTO CampingCustomer VALUES (10, '강호동', '서울 중구', '000-0000-0010', 'hd@gmail.com')";
   	insertCampingCustomer[10] = "INSERT INTO CampingCustomer VALUES (11, '김희애', '서울 영등포', '000-0000-0011', 'ha@gmail.com')";
   	insertCampingCustomer[11] = "INSERT INTO CampingCustomer VALUES (12, '이수만', '서울 동작', '000-0000-0012', 'sm@gmail.com')";
   	insertCampingCustomer[12] = "INSERT INTO CampingCustomer VALUES (13, '이승철', '서울 종로', '000-0000-0013', 'sc@gmail.com')";
   	insertCampingCustomer[13] = "INSERT INTO CampingCustomer VALUES (14, '이효리', '서울 강서', '000-0000-0014', 'hl@gmail.com')";
   	insertCampingCustomer[14] = "INSERT INTO CampingCustomer VALUES (15, '송승헌', '서울 관악', '000-0000-0015', 'sh@gmail.com')";
   
   //CarcenterList data
   	insertCarcenterList[0] = "INSERT INTO CarcenterList Values (1,'세종정비소','서울 강남','000-0000-0011','managerA','sj@gmail.com')";
   	insertCarcenterList[1] = "INSERT INTO CarcenterList Values (2,'한양정비소','서울 서초','000-0000-0021','managerB','hy@gmail.com')";
   	insertCarcenterList[2] = "INSERT INTO CarcenterList Values (3,'건국정비소','서울 송파','000-0000-0031','managerC','gg@gmail.com')";
   	insertCarcenterList[3] = "INSERT INTO CarcenterList Values (4,'서강정비소','서울 용산','000-0000-0041','managerD','sg@gmail.com')";
   	insertCarcenterList[4] = "INSERT INTO CarcenterList Values (5,'성균관정비소','서울 용산','000-0000-0051','managerE','ssg@gmail.com')";
   	insertCarcenterList[5] = "INSERT INTO CarcenterList Values (6,'연세정비소','서울 서초','000-0000-0061','managerF','ys@gmail.com')";
   	insertCarcenterList[6] = "INSERT INTO CarcenterList Values (7,'고려정비소','서울 강남','000-0000-0071','managerG','kr@gmail.com')";
   	insertCarcenterList[7] = "INSERT INTO CarcenterList Values (8,'숭실정비소','서울 송파','000-0000-0081','managerH','ss@gmail.com')";
   	insertCarcenterList[8] = "INSERT INTO CarcenterList Values (9,'중앙정비소','서울 용산','000-0000-0091','managerI','ca@gmail.com')";
   	insertCarcenterList[9] = "INSERT INTO CarcenterList Values (10,'동국정비소','서울 송파','000-0000-0101','managerJ','dk@gmail.com')";
   	insertCarcenterList[10] = "INSERT INTO CarcenterList Values (11,'홍익정비소','서울 서초','000-0000-0201','managerK','hi@gmail.com')";
   	insertCarcenterList[11] = "INSERT INTO CarcenterList Values (12,'국민정비소','서울 용산','000-0000-0301','managerL','km@gmail.com')";
   	insertCarcenterList[12] = "INSERT INTO CarcenterList Values (13,'광운정비소','서울 송파','000-0000-0401','managerM','gw@gmail.com')";
   	insertCarcenterList[13] = "INSERT INTO CarcenterList Values (14,'서울정비소','서울 강남','000-0000-0501','managerN','sl@gmail.com')";
   	insertCarcenterList[14] = "INSERT INTO CarcenterList Values (15,'경희정비소','서울 강남','000-0000-0601','managerO','kh@gmail.com')";
   	
           
   //AvailableCampingcarList data
   	insertAvailableCampingcarList[0] = "INSERT INTO AvailableCampingcarList VALUES (1, 'QM3', 1111, 4, '삼성', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 2, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[1] = "INSERT INTO AvailableCampingcarList VALUES (2, '엑센트 디젤',1112, 4, '현대', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 5, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[2] = "INSERT INTO AvailableCampingcarList VALUES (3, '쏘나타 ', 1113, 4, '현대', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 2, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[3] = "INSERT INTO AvailableCampingcarList VALUES (4, 'i30 디젤', 1114, 4, '현대', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 2, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[4] = "INSERT INTO AvailableCampingcarList VALUES (5, 'K5 하이브리드', 1115, 4, '기아', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 4, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[5] = "INSERT INTO AvailableCampingcarList VALUES (6, 'SM5 디젤', 1116, 6, '삼성', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 6, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[6] = "INSERT INTO AvailableCampingcarList VALUES (7, '모닝 CVT', 1117, 6, '기아', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 9, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[7] = "INSERT INTO AvailableCampingcarList VALUES (8, 'K3 디젤', 1118, 6, '기아', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 10, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[8] = "INSERT INTO AvailableCampingcarList VALUES (9, '아반떼 디젤', 1119, 6, '현대', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 11, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[9] = "INSERT INTO AvailableCampingcarList VALUES (10, 'K7 하이브리드', 1120, 8, '기아', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 15, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[10] = "INSERT INTO AvailableCampingcarList VALUES (11, '그랜저 ', 1121, 8, '현대', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 12, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[11] = "INSERT INTO AvailableCampingcarList VALUES (12, '쉐보레 스파크', 1122, 8, 'GM', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 4, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[12] = "INSERT INTO AvailableCampingcarList VALUES (13, '옵티머스프라임', 1123, 8, '오토봇', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 140000, 7, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[13] = "INSERT INTO AvailableCampingcarList VALUES (14, '범블비', 1124, 10, '오토봇', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 140000, 8, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	insertAvailableCampingcarList[14] = "INSERT INTO AvailableCampingcarList VALUES (15, '벤츠', 1125, 12, '벤츠', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 990000, 3, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
   	
   	for(i=0;i<5;i++)
   		stmt.executeUpdate(delete[i]);
   	for(i=0;i<15;i++)
   		stmt.executeUpdate(insertCompanyList[i]);
   	for(i=0;i<15;i++)
   		stmt.executeUpdate(insertCampingcarList[i]);
   	for(i=0;i<15;i++)
   		stmt.executeUpdate(insertCampingCustomer[i]);
   	for(i=0;i<15;i++)
   		stmt.executeUpdate(insertCarcenterList[i]);
   	for(i=0;i<15;i++)
   		stmt.executeUpdate(insertAvailableCampingcarList[i]);
   	
   	}catch(SQLException e3)
   	{
   		System.out.println("쿼리초기화작업실패"+e3);
   	}
      }
   ```

   </br></br>

3. ### 데이터 조작

   "**관리자**"의 데이터 조작에는 **입력, 삭제, 변경, 반환** 총 4가지가 존재한다.

   1. #### 입력

      INSERT문을 사용하여 각 테이블에 원하는 정보를 입력하고 싶을 때 사용하는 기능

      ```java
      if(selectnumber.compareTo("1")==0)//'입력'선택시
               	 {
          //tablenumber = 1: 대여회사  2: 캠핑카  3: 고객  4: 정비소  5. 정비정보
               		if(tablenumber.compareTo("1")==0)
                  	 {
                  		 query = "insert into companylist values("+data1+",'"+data2+"','"+data3+"','"+data4+"','"+data5+"','"+data6+"')";
                  		 stmt.executeUpdate(query); //쿼리 실행
                  		 txtResult2.setText("캠핑카 대여회사 테이블에 캠핑카대여회사ID가 "+data1+"인 data가 입력되었습니다.");
                  	 }
                  	 else if(tablenumber.compareTo("2")==0)
                  	 {
                  		 query = "insert into campingcarlist values("+data1+",'"+data2+"',"+data3+","+data4+",'"+data5+"',STR_TO_DATE('"+data6+"','%Y-%m-%d'),"+data7+","+data8+","+data9+",STR_TO_DATE('"+data10+"','%Y-%m-%d'))";
                  		 stmt.executeUpdate(query);
                           
                  		 query = "insert into availablecampingcarlist values("+data1+",'"+data2+"',"+data3+","+data4+",'"+data5+"',STR_TO_DATE('"+data6+"','%Y-%m-%d'),"+data7+","+data8+","+data9+",STR_TO_DATE('"+data10+"','%Y-%m-%d'))";
                  		stmt.executeUpdate(query);
                 		 txtResult2.setText("캠핑카 테이블에 캠핑카등록ID가 "+data1+"인 data가 입력되었습니다.");
                  	 }
                  	 else if(tablenumber.compareTo("3")==0)
                  	 {
                  		 query = "insert into campingcustomer values("+data1+",'"+data2+"','"+data3+"','"+data4+"','"+data5+"')";
                  		 stmt.executeUpdate(query);
                  		 txtResult2.setText("고객 테이블에 운전면허증번호가 "+data1+"인 data가 입력되었습니다.");
                  	 }
                  	 else if(tablenumber.compareTo("4")==0)
                  	 {
                  		 query = "insert into carcenterlist values("+data1+",'"+data2+"','"+data3+"','"+data4+"','"+data5+"','"+data6+"')";
                  		 stmt.executeUpdate(query);
                  		 txtResult2.setText("캠핑카정비소 테이블에 캠핑카정비소ID가 "+data1+"인 data가 입력되었습니다.");
                  	 }
                  	 else if(tablenumber.compareTo("5")==0)
                  	 {
                  		 query = "insert into FixInformation values("+data1+","+data2+","+data3+","+data4+","+data5+",'"+data6+"',STR_TO_DATE('"+data7+"','%Y-%m-%d'),"+data8+",STR_TO_DATE('"+data9+"','%Y-%m-%d'),'"+data10+"')";
                  		 stmt.executeUpdate(query);
                 		 txtResult2.setText("캠핑카정비정보 테이블에 고유정비번호가 "+data1+"인 data가 입력되었습니다.");
                  	 }
               	 }
      ```

      

   2. #### 삭제

      DELETE문을 사용하여 각 테이블에서 원하는 정보를 삭제하고 싶을 때 사용하는 기능

      ```java
      else if(selectnumber.compareTo("2")==0) //'삭제' 선택시
               	 {
          //tablenumber = 1: 대여회사  2: 캠핑카  3: 고객  4: 정비소  5. 정비정보
               		if(tablenumber.compareTo("1")==0)
                 	 {
               		query = "delete from companylist where companyid ="+ data1;
               		stmt.executeUpdate(query);
                 		txtResult2.setText("캠핑카 대여회사 테이블에서 캠핑카대여회사ID가 "+data1+" 인 data가 삭제되었습니다.");
                 		 
                 	 }
                 	 else if(tablenumber.compareTo("2")==0)
                 	 {
                 		query = "delete from campingcarlist where carid ="+ data1;
               		stmt.executeUpdate(query);
               		query = "delete from availablecampingcarlist where carid ="+ data1;
               		stmt.executeUpdate(query);
                 		txtResult2.setText("캠핑카테이블 및 현재 대여가능한 캠핑카테이블에서 캠핑카등록ID가 "+data1+" 인 data가 삭제되었습니다.");
                 	 }
                 	 else if(tablenumber.compareTo("3")==0)
                 	 {
                 		query = "delete from campingcustomer where licenseid ="+ data1;
               		stmt.executeUpdate(query);
                 		txtResult2.setText("고객 테이블에서 운전면허증번호가 "+data1+" 인 data가 삭제되었습니다.");
                 	 }
                 	 else if(tablenumber.compareTo("4")==0)
                 	 {
                 		query = "delete from carcenterlist where carcenterid ="+ data1;
               		stmt.executeUpdate(query);
                 		txtResult2.setText("캠핑카정비소  테이블에서 캠핑카정비소ID가 "+data1+" 인 data가 삭제되었습니다.");
                 	 }
                 	 else if(tablenumber.compareTo("5")==0)
                 	 {
                 		query = "delete from fixinformation where fixid ="+ data1;
               		stmt.executeUpdate(query);
                 		txtResult2.setText("정비정보 테이블에서 고유정비번호가 "+data1+" 인 data가 삭제되었습니다.");
                 	 }
               	 }
      ```

   3. #### 변경

      UPDATE문을 사용하여 각 테이블에서 원하는 정보를 변경하고 싶을 때 사용하는 기능

      ```java
       else if(selectnumber.compareTo("3")==0)//'변경'선택시
               	 {
               //tablenumber = 1: 대여회사  2: 캠핑카  3: 고객  4: 정비소  5. 정비정보
               		 String s[],d[];
               		if(tablenumber.compareTo("1")==0)
               		{
               			s=new String[] {"companyid","companyname","companyaddress","companyphone","managername","manageremail"};
               			d=new String[] {data1,data2,data3,data4,data5,data6};
               			for(int i=1;i<s.length;i++)
               			{
               				if(d[i]!=null)
               				{
               					query = "update companylist set "+s[i]+" = '"+d[i]+"' where companyid = "+d[0];
               					stmt.executeUpdate(query);
               	           		
               				}
               				else
               					continue;
               				
               			}
               			txtResult2.setText("캠핑카 대여회사 테이블에서 캠핑카대여회사ID가 "+data1+" 인 data가 변경되었습니다.");
      //         			
               		}
               		else if(tablenumber.compareTo("2")==0)
               		{
               			s = new String[] {"carid","carname","carnumber","carmax","carbrand","birthday","distance","rentprice","companyid","enrolldate"};
               			d = new String[] {data1,data2,data3,data4,data5,data6,data7,data8,data9,data10};
               			for(int i=1;i<s.length;i++)
               			{
               				if(d[i]!=null)
               				{	
               					if(i==1 || i==4 )
               						d[i] ="'"+d[i]+"'";
               					else if(i==5 || i==9)
               						d[i] ="STR_TO_DATE('"+d[i]+"','%Y-%m-%d')";
               					query = "update campingcarlist set "+s[i]+" = "+d[i]+" where carid = "+d[0];
               					stmt.executeUpdate(query);
               					query = "update availablecampingcarlist set "+s[i]+" = "+d[i]+" where carid = "+d[0];
               					stmt.executeUpdate(query);
               				}
               				else
               					continue;
               			}
               			txtResult2.setText("캠핑카 테이블에서 캠핑카등록ID가 "+data1+" 인 data가 변경되었습니다.");
               		}
               		else if(tablenumber.compareTo("3")==0)
               		{
               			s = new String[] {"licenseid","customername","customeraddress","customerphone","customeremail"};
               			d = new String[] {data1,data2,data3,data4,data5};
               			for(int i=1;i<s.length;i++)
               			{
               				if(d[i]!=null)
               				{	
             						d[i] ="'"+d[i]+"'";         					
               					query = "update campingcustomer set "+s[i]+" = "+d[i]+" where licenseid = "+d[0];
               					stmt.executeUpdate(query);
               				}
               				else
               					continue;
               			}
               			txtResult2.setText("고객 테이블에서 운전면허증번호가 "+data1+" 인 data가 변경되었습니다.");
               		}
               		else if(tablenumber.compareTo("4")==0)
               		{
               			s = new String[] {"carcenterid","carcentername","carcenteraddress","carcenterphone","centermanagername","centermanageremail"};
               			d = new String[] {data1,data2,data3,data4,data5,data6};
               			for(int i=1;i<s.length;i++)
               			{
               				if(d[i]!=null)
               				{	
             						d[i] ="'"+d[i]+"'";         					
               					query = "update carcenterlist set "+s[i]+" = "+d[i]+" where carcenterid = "+d[0];
               					stmt.executeUpdate(query);
               				}
               				else
               					continue;
               			}
               			txtResult2.setText("캠핑카정비소 테이블에서 정비소ID가 "+data1+" 인 data가 변경되었습니다.");
               		}
               		else if(tablenumber.compareTo("5")==0)
               		{
               			s = new String[] {"fixid","carid","carcenterid","companyid","licenseid","fix","fixdate","fixprice","depositdate","etcfix"};
               			d = new String[] {data1,data2,data3,data4,data5,data6,data7,data8,data9,data10};
               			for(int i=1;i<s.length;i++)
               			{
               				if(d[i]!=null)
               				{	
               					if(i==5 || i==9 )
               						d[i] ="'"+d[i]+"'";
               					else if(i==6 || i==8)
               						d[i] ="STR_TO_DATE('"+d[i]+"','%Y-%m-%d')";
               					query = "update FixInformation set "+s[i]+" = "+d[i]+" where fixid = "+d[0];
               					stmt.executeUpdate(query);
               				}
               				else
               					continue;
               			}
               			txtResult2.setText("정비정보 테이블에서 고유정비번호가 "+data1+" 인 data가 변경되었습니다.");
               		}
               	 }
      ```

      

   4. #### 반환

      DELETE, INSERT문을 사용하여 고객이 캠핑카를 반납할 때 '차의 상태', '수리 여부'를 등록하기 위해 사용하는 기능

      ```java
      else if(selectnumber.compareTo("4")==0)//'반환'선택시
      /*
      '반환'에서 차에 이상이 있을 시 점검내역을 저장해야 하므로
      INSERT문으로 점검내역을 정비정보 테이블에 저장하고
      DELETE문으로 현재 대여중인 캠핑카 테이블에서 해당 캠핑카를 삭제한다.
      */
               		{
               			String str="";
               		//점검내역 저장
               			query = "insert into CarCheck values("+data1+","+data2+",'"+data3+"','"+data4+"','"+data5+"','"+data6+"',"+data7+","+data8+")";
               			stmt.executeUpdate(query);
               		//현재 대여목록에서 delete
               				query = "delete from PresentRentCampingcarList where rentid = "+data1;
               				stmt.executeUpdate(query);
               		
               		//수리가 필요하지 않으면 대여가능한 목록으로 이동
               			if(data7.compareTo("0")==0)
               			{
               				query = "select * from Campingcarlist where carid = "+data2;
               				rs = stmt.executeQuery(query);
               				while (rs.next()) {
               					str ="insert into availablecampingcarlist values("+rs.getInt(1)+",'"+rs.getString(2)+"',"+rs.getInt(3)+","+rs.getInt(4)+",'"+rs.getString(5)+"',STR_TO_DATE('"+rs.getString(6)+"','%Y-%m-%d'),"+rs.getInt(7)+","+rs.getInt(8)+","+rs.getInt(9)+",STR_TO_DATE('"+rs.getString(10)+"','%Y-%m-%d'))";
               	                
               	        						}
               				stmt.executeUpdate(str);
               				
               			}
               			
               			txtResult2.setText("고유대여번호가 "+data1+"인 차량이 반환되었습니다.");
               		}
      ```

      

   </br></br>

   "**고객**"의 데이터 조작에는 **대여 신청** 기능이 있다.

   ```java
    if (e.getSource() == btnRent) { // '대여 신청'을 눌렀을 때
        /*
        현재 대여중 캠핑카 테이블에 INSERT,
        대여가능 캠핑카 테이블에서 DELETE
        */
           	 String licenseid, carid,startdate,rentdays;
           	 int rentprice=0,companyid=0; 
           	 licenseid = txtField1.getText();
           	 carid = txtField2.getText();
           	 startdate = txtField3.getText();
           	 rentdays = txtField4.getText();
           	 
           	 txtField1.setText("");
           	 txtField2.setText("");
           	 txtField3.setText("");
           	 txtField4.setText("");
           	 
           	 query = "select * from Campingcarlist where carid="+carid;
           	 
           	 rs = stmt.executeQuery(query);
           	 	
           	 while(rs.next()) {
           		  companyid = rs.getInt(9);
           		  rentprice = rs.getInt(8);
           	 }
           	 stmt.executeUpdate("INSERT INTO PresentRentCampingcarList VALUES ("+rentid+","+carid+","+licenseid+","+companyid+","+ "STR_TO_DATE('"+startdate+"','%Y-%m-%d'),STR_TO_DATE('"+startdate+"','%Y-%m-%d')-1+"+rentdays+","+rentprice+"*"+rentdays+",STR_TO_DATE('"+startdate+"','%Y-%m-%d')-1,NULL,NULL)");
           	 rentid++;
           	 	stmt.executeUpdate("DELETE from availablecampingcarlist where carid="+carid);
           	 txtResult.setText("렌트완료");
           	 
               }
   ```

   </br>

4. ### 데이터 검색

   데이터 검색은 SELECT문을 사용하여 **"고객"**, **"관리자"** 모두 할 수 있는 기능이다.

   - 고객

     ```java
     else if(e.getSource() == UbtnSearch1) {//'대여가능한 캠핑카 목록 검색' 버튼을 눌렀을 때
             	 query = "SELECT * FROM availablecampingcarlist ";
             	txtResult.setText("");
             	txtResult.setText("차량 ID\t캠핑카이름\t차량번호 \t최대승차인원수  제조회사\t제조연도\t누적주행거리\t대여비용(1일)\t\n");
             	rs = stmt.executeQuery(query);
             	while (rs.next()) {
                     String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) +"\t" + rs.getInt(7) +"\t" + rs.getInt(8)
                           + "\n";
                     txtResult.append(str);
             						}
     ```

   - 관리자

     ```java
     else if(e.getSource() == AbtnSearch1) {//'대여고객 및 대여차량 검색' 버튼을 눌렀을 때
              	 query = "select presentrentcampingcarlist.rentid, campingcarlist.carid, campingcarlist.carname, campingcustomer.licenseid, campingcustomer.customername,  campingcarlist.carnumber " + 
              	 		"from campingcustomer,presentrentcampingcarlist,campingcarlist " + 
              	 		"where presentrentcampingcarlist.licenseid = campingcustomer.licenseid and presentrentcampingcarlist.carid = campingcarlist.carid;";
               	txtResult2.setText("");
               	txtResult2.setText("고유대여번호\t캠핑카등록ID\t차량명\t운전면허증번호\t고객명\t차량번호\n");
               	rs = stmt.executeQuery(query);
               	while (rs.next()) {
                       String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4) + "\t" + rs.getString(5) + "\t" +rs.getInt(6)
                             + "\n";
                       txtResult2.append(str);
               						}
                }
               
               else if(e.getSource() == AbtnSearch2) {//'수리가 필요한 차량 검색' 버튼을 눌렀을 때
               	 query = "select campingcarlist.carid, campingcarlist.carname, campingcarlist.companyid, campingcustomer.licenseid, campingcustomer.customername, carcheck.frontdescription, carcheck.leftdescription, carcheck.rightdescription, carcheck.backdescription " + 
               	 		"from carcheck,campingcarlist,campingcustomer " + 
               	 		"where carcheck.havetofix=1 and carcheck.carID = campingcarlist.carid and carcheck.licenseid=campingcustomer.licenseid "+
               	 		"order by carid;";
                	txtResult2.setText("");
                	txtResult2.setText("캠핑카등록ID\t캠핑카 이름\t캠핑카대여회사ID    운전면허증번호    고객명\t앞부분\t왼쪽부분\t오른쪽부분\t뒷부분\n");
                	rs = stmt.executeQuery(query);
                	while (rs.next()) {
                        String str = rs.getInt(1) + "\t"+ rs.getString(2) + "\t"+ rs.getInt(3) + "\t       " + rs.getInt(4) + "\t          " + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8) + "\t" + rs.getString(9)
                              + "\n";
                        txtResult2.append(str);
                						}
                 }
               else if(e.getSource() == AbtnSearch3) {//'정비소별 정비 내역' 버튼을 눌렀을 때
                	 query = "select  carcenterlist.carcenterid, carcenterlist.carcentername, fixinformation.fixid, fixinformation.companyid, companylist.companyname, fixinformation.carid, campingcarlist.carname, fixinformation.fixdate " + 
                	 		"from fixinformation,carcenterlist,campingcarlist,companylist " + 
                	 		"where fixinformation.carcenterid = carcenterlist.carcenterid and fixinformation.carid = campingcarlist.carid and fixinformation.companyid = companylist.companyid " + 
                	 		"order by carcenterid;";
                 	txtResult2.setText("");
                 	txtResult2.setText("정비소ID\t정비소 이름\t정비번호\t대여회사ID\t대여회사이름 \t캠핑카등록ID\t캠핑카 이름\t수리날짜\n");
                 	rs = stmt.executeQuery(query);
                 	while (rs.next()) {
                         String str = rs.getInt(1) + "\t"+ rs.getString(2) + "\t"+ rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getString(5) + "\t" + rs.getInt(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8)
                               + "\n";
                         txtResult2.append(str);
                 						}
                  }       
     ```

5. ### 전체 UI 화면 구성





---

> ## REVIEW

이번 과제를 진행하며 주 내용이었던 **SQL문을 사용하여 데이터베이스를 조작**하는 방법을 학습하고 익숙하게 다루는 방법을 알게 되었다. 또한 부가적인 기능이긴 했지만 JAVA 라이브러리를 임포트하여 사용하는 것과 JAVA의 ActionListener에 대해서도 학습할 수 있었다.

</br>

아쉬운점은 과제평가를 위해 고객과 관리자의 UI를 같은 창 안에 영역만 달리하여 개발하였다는 점, 다중조건을 사용한 쿼리문을 사용해보지 못했다는 점을 꼽을 수 있을 것 같다.
