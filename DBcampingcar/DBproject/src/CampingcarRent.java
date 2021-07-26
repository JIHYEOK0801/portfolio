import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;


class MyModalDialog extends JDialog{
	private JTextField tfselect = new JTextField("");
	private JTextField tftableselect = new JTextField("");
	private JTextField tf1 = new JTextField("");
	private JTextField tf2 = new JTextField("");
	private JTextField tf3 = new JTextField("");
	private JTextField tf4 = new JTextField("");
	private JTextField tf5 = new JTextField("");
	private JTextField tf6 = new JTextField("");
	private JTextField tf7 = new JTextField("");
	private JTextField tf8 = new JTextField("");
	private JTextField tf9 = new JTextField("");
	private JTextField tf10 = new JTextField("");
	
	private JButton okButton = new JButton("작업 선택 및 테이블 선택");
	private JButton insertButton = new JButton("수행");
	
	private JLabel labelselect= new JLabel("번호를 골라 입력하세요. (1 : 입력 / 2 : 삭제 / 3 : 변경/ 4 : 반환)");
	private JLabel labeltableselect = new JLabel("테이블을 고르고 버튼을 눌러주세요.('반환'의경우 빈칸으로 남겨둠)  1 : 대여회사 / 2 : 캠핑카 / 3 : 고객 / 4 : 정비소 / 5 : 정비정보");
	private JLabel labeltableselect2 = new JLabel("");
	private JLabel label1 = new JLabel("데이터1 :");
	private JLabel label2 = new JLabel("데이터2 :");
	private JLabel label3 = new JLabel("데이터3 :");
	private JLabel label4 = new JLabel("데이터4 :");
	private JLabel label5 = new JLabel("데이터5 :");
	private JLabel label6 = new JLabel("데이터6 :");
	private JLabel label7 = new JLabel("데이터7 :");
	private JLabel label8 = new JLabel("데이터8 :");
	private JLabel label9 = new JLabel("데이터9 :");
	private JLabel label10 = new JLabel("데이터10 :");
	
	public MyModalDialog(JFrame frame, String title) {
		super(frame,title,true);
		setLayout(new GridLayout(14,2,5,5));
		
		add(labelselect);	add(tfselect);
		add(labeltableselect);	add(tftableselect);
		add(labeltableselect2);		add(okButton);
		add(label1);		add(tf1);
		add(label2);		add(tf2);
		add(label3);		add(tf3);
		add(label4);		add(tf4);
		add(label5);		add(tf5);
		add(label6);		add(tf6);
		add(label7);		add(tf7);
		add(label8);		add(tf8);
		add(label9);		add(tf9);
		add(label10);		add(tf10);
		add(new JLabel("수행버튼을 누르세요.")); add(insertButton);
		setSize(1400,800);
		
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String performnumber = tfselect.getText();
				String tablenumber = tftableselect.getText();
				if(performnumber.compareTo("1")==0 || performnumber.compareTo("3")==0)
					{
					if(tablenumber.compareTo("1")==0)
					{
						label1.setText("캠핑카대여회사ID :");//companyid
						label2.setText("회사명 :");//companyname
						label3.setText("주소 :");//companyaddress
						label4.setText("전화번호 :");//companyphone
						label5.setText("담당자 이름 :");//managername
						label6.setText("담당자 이메일 :");//manageremail
						label7.setText("기입 X");
						label8.setText("기입 X");
						label9.setText("기입 X");
						label10.setText("기입 X");
					}
					else if(tablenumber.compareTo("2")==0)
					{
						label1.setText("캠핑카등록ID :");//carid
						label2.setText("캠핑카이름 :");//carname
						label3.setText("캠핑카 차량번호 :");//carnumber
						label4.setText("캠핑카승차 인원수 :");//carmax
						label5.setText("제조회사 :");//carbrand
						label6.setText("제조연도(YYYY-MM-DD) :");//birthday
						label7.setText("누적주행거리 :");//distance
						label8.setText("캠핑카대여비용 :");//rentprice
						label9.setText("캠핑카대여회사ID :");//companyid
						label10.setText("캠핑카등록일자(YYYY-MM-DD) :");//enrolldate
					}
					else if(tablenumber.compareTo("3")==0)
					{
						label1.setText("운전면허증번호 :");//licenseid
						label2.setText("고객명 :");//customername
						label3.setText("고객주소 :");//customeraddress
						label4.setText("고객전화번호 :");//customerphone
						label5.setText("고객이메일정보 :");//customermail
						label6.setText("기입 X");
						label7.setText("기입 X");
						label8.setText("기입 X");
						label9.setText("기입 X");
						label10.setText("기입 X");
					}
					else if(tablenumber.compareTo("4")==0)
					{
						label1.setText("정비소ID :");//carcenterid
						label2.setText("정비소명 :");//carcentername
						label3.setText("정비소주소 :");//carcenteraddress
						label4.setText("정비소전화번호 :");//carcenterphone
						label5.setText("담당자이름 :");//centermanagername
						label6.setText("이메일 정보");//centermanageremail
						label7.setText("기입 X");
						label8.setText("기입 X");
						label9.setText("기입 X");
						label10.setText("기입 X");
					}
					else if(tablenumber.compareTo("5")==0)
					{
						label1.setText("고유정비번호 :");//fixid
						label2.setText("캠핑카등록ID :");//carid
						label3.setText("캠핑카정비소ID :");//carcenterid
						label4.setText("캠핑카대여회사ID :");//companyid
						label5.setText("고객 운전면허증번호 :");//licenseid
						label6.setText("정비 내역 :");//fix
						label7.setText("수리 날짜(YYYY-MM-DD) :");//fixdate
						label8.setText("수리 비용 :");//fixprice
						label9.setText("납입 기한(YYYY-MM-DD) :");//depositdate
						label10.setText("기타정비내역 :");//etcfix
					}
					else
					{
						label1.setText("데이터1 :");
						label2.setText("데이터2 :");
						label3.setText("데이터3 :");
						label4.setText("데이터4 :");
						label5.setText("데이터5 :");
						label6.setText("데이터6 :");
						label7.setText("데이터7 :");
						label8.setText("데이터8 :");
						label9.setText("데이터9 :");
						label10.setText("데이터10 :");
					}
					}
				else if(performnumber.compareTo("2")==0)
				{
					if(tablenumber.compareTo("1")==0)
					{
						label1.setText("삭제할 캠핑카대여회사ID :");//companyid
						label2.setText("기입 X");//companyname
						label3.setText("기입 X");//companyaddress
						label4.setText("기입 X");//companyphone
						label5.setText("기입 X");//managername
						label6.setText("기입 X");//manageremail
						label7.setText("기입 X");
						label8.setText("기입 X");
						label9.setText("기입 X");
						label10.setText("기입 X");
					}
					else if(tablenumber.compareTo("2")==0)
					{
						label1.setText("삭제할 캠핑카등록ID :");//carid
						label2.setText("기입 X");//carname
						label3.setText("기입 X");//carnumber
						label4.setText("기입 X");//carmax
						label5.setText("기입 X");//carbrand
						label6.setText("기입 X");//birthday
						label7.setText("기입 X");//distance
						label8.setText("기입 X");//rentprice
						label9.setText("기입 X");//companyid
						label10.setText("기입 X");//enrolldate
					}
					else if(tablenumber.compareTo("3")==0)
					{
						label1.setText("삭제할 운전면허증번호 :");//licenseid
						label2.setText("기입 X");//carname
						label3.setText("기입 X");//carnumber
						label4.setText("기입 X");//carmax
						label5.setText("기입 X");//carbrand
						label6.setText("기입 X");//birthday
						label7.setText("기입 X");//distance
						label8.setText("기입 X");//rentprice
						label9.setText("기입 X");//companyid
						label10.setText("기입 X");//enrolldate
					}
					else if(tablenumber.compareTo("4")==0)
					{
						label1.setText("삭제할 정비소ID :");//carcenterid
						label2.setText("기입 X");//carname
						label3.setText("기입 X");//carnumber
						label4.setText("기입 X");//carmax
						label5.setText("기입 X");//carbrand
						label6.setText("기입 X");//birthday
						label7.setText("기입 X");//distance
						label8.setText("기입 X");//rentprice
						label9.setText("기입 X");//companyid
						label10.setText("기입 X");//enrolldate
					}
					else if(tablenumber.compareTo("5")==0)
					{
						label1.setText("삭제할 고유정비번호 :");//fixid
						label2.setText("기입 X");//carname
						label3.setText("기입 X");//carnumber
						label4.setText("기입 X");//carmax
						label5.setText("기입 X");//carbrand
						label6.setText("기입 X");//birthday
						label7.setText("기입 X");//distance
						label8.setText("기입 X");//rentprice
						label9.setText("기입 X");//companyid
						label10.setText("기입 X");//enrolldate
					}
					else
					{
						label1.setText("데이터1 :");
						label2.setText("데이터2 :");
						label3.setText("데이터3 :");
						label4.setText("데이터4 :");
						label5.setText("데이터5 :");
						label6.setText("데이터6 :");
						label7.setText("데이터7 :");
						label8.setText("데이터8 :");
						label9.setText("데이터9 :");
						label10.setText("데이터10 :");
					}
				}
				else if(performnumber.compareTo("4")==0)
				{
					label1.setText("고유대여번호 :");//companyid
					label2.setText("캠핑카등록ID :");//companyname
					label3.setText("앞부분설명 :");//companyaddress
					label4.setText("왼쪽설명 :");//companyphone
					label5.setText("오른쪽설명 :");//managername
					label6.setText("뒤쪽설명 :");//manageremail
					label7.setText("수리필요여부 (수리필요시 1 아니면 0) :");
					label8.setText("운전면허증번호");
					label9.setText("기입 X");
					label10.setText("기입 X");
				}
				
			}});
		
		insertButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}});
	}
	void resetString() {
		label1.setText("데이터1 :");
		label2.setText("데이터2 :");
		label3.setText("데이터3 :");
		label4.setText("데이터4 :");
		label5.setText("데이터5 :");
		label6.setText("데이터6 :");
		label7.setText("데이터7 :");
		label8.setText("데이터8 :");
		label9.setText("데이터9 :");
		label10.setText("데이터10 :");
		tfselect.setText("");
		tftableselect.setText("");
		tf1.setText("");
		tf2.setText("");
		tf3.setText("");
		tf4.setText("");
		tf5.setText("");
		tf6.setText("");
		tf7.setText("");
		tf8.setText("");
		tf9.setText("");
		tf10.setText("");
	}
	String getInputSelect() {
		if(tfselect.getText().length()==0) return null;
		else return tfselect.getText();
	}
	String getInputTableSelect() {
		if(tftableselect.getText().length()==0) return null;
		else return tftableselect.getText();
	}
	String getInput1() {
		if(tf1.getText().length()==0) return null;
		else return tf1.getText();
	}
	String getInput2() {
		if(tf2.getText().length()==0) return null;
		else return tf2.getText();
	}
	String getInput3() {
		if(tf3.getText().length()==0) return null;
		else return tf3.getText();
	}
	String getInput4() {
		if(tf4.getText().length()==0) return null;
		else return tf4.getText();
	}
	String getInput5() {
		if(tf5.getText().length()==0) return null;
		else return tf5.getText();
	}
	String getInput6() {
		if(tf6.getText().length()==0) return null;
		else return tf6.getText();
	}
	String getInput7() {
		if(tf7.getText().length()==0) return null;
		else return tf7.getText();
	}
	String getInput8() {
		if(tf8.getText().length()==0) return null;
		else return tf8.getText();
	}
	String getInput9() {
		if(tf9.getText().length()==0) return null;
		else return tf9.getText();
	}
	String getInput10() {
		if(tf10.getText().length()==0) return null;
		else return tf10.getText();
	}
	
}
public class CampingcarRent extends JFrame implements ActionListener {
   JButton btnRent, btnReset, UbtnSearch1, btnExecute, AbtnSearch1, AbtnSearch2, AbtnSearch3, AbtnSearch4;
   JTextArea txtResult,txtResult2;
   JTextField txtField1,txtField2,txtField3,txtField4,txtField5;
   
   JPanel pn1,pn2,pn3,pn4;

   private MyModalDialog dialog;//
   
   static Connection con;
   Statement stmt;
   ResultSet rs;
   String Driver = "";
   String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
   String userid = "madang";
   String pwd = "madang";
   int rentid=0;
   
   
    
   public CampingcarRent() {
      super("camping car");
      layInit();
      conDB();
      setVisible(true);
      setBounds(100, 30, 1280, 720); //가로위치,세로위치,가로길이,세로길이
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      dialog=new MyModalDialog(this,"작업창");
      
   }

   public void layInit() {
      btnRent = new JButton("신청");
      btnReset = new JButton("초기화");
      UbtnSearch1 = new JButton("대여가능한 캠핑카목록 검색");
      btnExecute = new JButton("data 삽입/삭제/변경 및 반환");
      AbtnSearch1 = new JButton("대여고객 및 대여차량 검색");
      AbtnSearch2 = new JButton("수리가 필요한 차량 검색");
      AbtnSearch3 = new JButton("정비소별 정비내역 검색");
      AbtnSearch4 = new JButton("고객별 청구할 수리비용 검색");
      txtResult = new JTextArea();
      txtResult2 = new JTextArea();
      txtField1 = new JTextField("");
      txtField2 = new JTextField("");
      txtField3 = new JTextField("");
      txtField4 = new JTextField("");
      txtField5 = new JTextField("");
      
      pn1 = new JPanel();
      pn2 = new JPanel();
      pn3 = new JPanel();
      pn4 = new JPanel();
    
     
      
      
      
      pn2.setLayout(new GridLayout(6,2));
      	pn2.add(new JLabel("대여가능 차량 검색"));
      	pn2.add(UbtnSearch1);
      	pn2.add(new JLabel("운전면허증 번호 입력(LICENSE ID)"));
      	pn2.add(txtField1);
      	pn2.add(new JLabel("대여할 차량 ID 입력"));
      	pn2.add(txtField2);
      	pn2.add(new JLabel("대여시작일 입력(YYYY-MM-DD):"));
      	pn2.add(txtField3);
      	pn2.add(new JLabel("대여기간 입력(일):"));
      	pn2.add(txtField4);
      	pn2.add(new JLabel("대여신청"));
      	pn2.add(btnRent);
      
      pn4.setLayout(new FlowLayout(FlowLayout.CENTER,300, 25));
      pn4.add(btnReset);//초기화버튼
      pn4.add(btnExecute);
      pn4.add(AbtnSearch1);
      pn4.add(AbtnSearch2);
      pn4.add(AbtnSearch3);
      pn4.add(AbtnSearch4);
      
      txtResult.setEditable(false);
      
      GridLayout grid = new GridLayout(2,2,5,5);
      Container c = getContentPane();
      c.setLayout(grid);
      
      JScrollPane scrollPane = new JScrollPane(txtResult);
      JScrollPane scrollPane2 = new JScrollPane(txtResult2);

      //c.add(pn1);c.add(pn3);
      c.add(scrollPane);c.add(scrollPane2);
      c.add(pn2);c.add(pn4);
      

      btnRent.addActionListener(this);
      btnReset.addActionListener(this);
      UbtnSearch1.addActionListener(this);
      btnExecute.addActionListener(this);
      AbtnSearch1.addActionListener(this);
      AbtnSearch2.addActionListener(this);
      AbtnSearch3.addActionListener(this);
      AbtnSearch4.addActionListener(this);

      txtField1.addActionListener(this);
      txtField2.addActionListener(this);
      txtField3.addActionListener(this);
      txtField4.addActionListener(this);
      txtField5.addActionListener(this);
   }
   
   public void conDB() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         System.out.println("드라이버 로드 성공");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
      
      try { /* 데이터베이스를 연결하는 과정 */
          System.out.println("데이터베이스 연결 준비...");
          con = DriverManager.getConnection(url, userid, pwd);
          System.out.println("데이터베이스 연결 성공");
       } catch (SQLException e1) {
          e1.printStackTrace();
       }
   }
   public void createTable() {//테이블 구현 Companylist,Campingcarlist,CampingCustomer,PresentRentCampingcarList,CarCheck,FixInformation,CarcenterList,AvailableCampingcarlist
	  try { 
	   String sql[] = new String[8];
	   StringBuilder sb = new StringBuilder();
	   stmt = con.createStatement();
	   
	   stmt.execute("drop table if exists companylist,campingcarlist,campingcustomer,presentrentcampingcarlist,carcheck,fixinformation,carcenterlist,availablecampingcarlist");
	   
	   
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
   
   
   
   
   @Override
   public void actionPerformed(ActionEvent e) { // 액션담당 함수
    
      try {//일반 사용자용 에러검출
         stmt = con.createStatement();
         String query;
         
         if (e.getSource() == btnRent) { // btnRent를 눌렀을 때
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
         
         
         
         else if(e.getSource() == UbtnSearch1) {//검색1버튼을 눌렀을 때
        	 query = "SELECT * FROM availablecampingcarlist ";
        	txtResult.setText("");
        	txtResult.setText("차량 ID\t캠핑카이름\t차량번호 \t최대승차인원수  제조회사\t제조연도\t누적주행거리\t대여비용(1일)\t\n");
        	rs = stmt.executeQuery(query);
        	while (rs.next()) {
                String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) +"\t" + rs.getInt(7) +"\t" + rs.getInt(8)
                      + "\n";
                txtResult.append(str);
        						}
         }
        
         
         
         
      } catch (Exception e2) {
         System.out.println("쿼리 읽기 실패 :" + e2);
         txtResult.setText("오류 메세지 :" + e2);
         
         
/*      } finally {
         try {
            if (rs != null)
               rs.close();
            if (stmt != null)
               stmt.close();
            if (con != null)
               con.close();
         } catch (Exception e3) {
            // TODO: handle exception
         }
  */
      }
      try {//관리자용 에러 검출
    	  stmt = con.createStatement();
          String query;
          if (e.getSource() == btnReset) {// btnReset을 눌렀을 때
              txtResult2.setText("");//화면의 텍스트들을 지우는 역할
              createTable();
              insertMydata();
              rentid = 0;
              txtResult2.setText("데이터가 초기화 되었습니다!!");
           }
          
          
          
          
          
          else if(e.getSource() == btnExecute) {//execute버튼을 눌렀을 때
         	 dialog.setVisible(true);// 다이얼로그 작동시작
         	 String selectnumber = dialog.getInputSelect();
         	 String tablenumber = dialog.getInputTableSelect();
         	 String data1 = dialog.getInput1();
         	 String data2 = dialog.getInput2();
         	 String data3 = dialog.getInput3();
         	 String data4 = dialog.getInput4();
         	 String data5 = dialog.getInput5();
         	 String data6 = dialog.getInput6();
         	 String data7 = dialog.getInput7();
         	 String data8 = dialog.getInput8();
         	 String data9 = dialog.getInput9();
         	 String data10 = dialog.getInput10();
         	 dialog.resetString();
         	 
         	 if(selectnumber.compareTo("1")==0)//'입력'선택시
         	 {
         		if(tablenumber.compareTo("1")==0)
            	 {
            		 query = "insert into companylist values("+data1+",'"+data2+"','"+data3+"','"+data4+"','"+data5+"','"+data6+"')";
            		 stmt.executeUpdate(query);
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
         	 else if(selectnumber.compareTo("2")==0) //'삭제' 선택시
         	 {
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
         	 else if(selectnumber.compareTo("3")==0)//'변경'선택시
         	 {
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
         	else if(selectnumber.compareTo("4")==0)//'반환'선택시
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
           }
          
          
          
          
          
          
          else if(e.getSource() == AbtnSearch1) {//관리자용 검색1 버튼을 눌렀을 때
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
          
          else if(e.getSource() == AbtnSearch2) {//관리자용 검색2 버튼을 눌렀을 때
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
          else if(e.getSource() == AbtnSearch3) {//관리자용 검색3 버튼을 눌렀을 때
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
          else if(e.getSource() == AbtnSearch4) {//관리자용 검색4 버튼을 눌렀을 때
            	 query = "select campingcustomer.licenseid, campingcustomer.customername, sum(fixinformation.fixprice) " + 
            	 		"from campingcustomer,fixinformation " + 
            	 		"where campingcustomer.licenseid = fixinformation.licenseid " + 
            	 		"Group by campingcustomer.customername " + 
            	 		"order by campingcustomer.customername;";
             	txtResult2.setText("");
             	txtResult2.setText("운전면허증번호\t고객명\t수리비용\n");
             	rs = stmt.executeQuery(query);
             	while (rs.next()) {
                     String str = rs.getInt(1) + "\t"+ rs.getString(2) + "\t"+ rs.getInt(3)
                           + "\n";
                     txtResult2.append(str);
             						}
              }
          
      }
      catch(Exception e2) {
    	  System.out.println("쿼리 읽기 실패 :" + e2);
          txtResult2.setText("오류 메세지 :" + e2);
      }

   }

   public static void main(String[] args) {
      CampingcarRent BLS = new CampingcarRent();
      
      //BLS.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      //BLS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      BLS.addWindowListener(new WindowAdapter() {
    	  public void windowClosing(WindowEvent we) {
    		try {
    			con.close();
    		} catch (Exception e4) { 	}
    		System.out.println("프로그램 완전 종료!");    		
    	    System.exit(0);
    	  }
    	});
  
   }
}

/*
 * connection coon = null;
 * PreparedStatement pstmt = null;
 * ResultSet rs = null;
 * 
 * String mallmanage_url = "";
 * String mallmanage_id = "";
 * String mallmanage_pwd ="";
 * 
 * pstmt = conn.prepareStatement(SQL);
 * pstmt.setString(1,"auction_order_inquiry");
 * 
  */
