import java.awt.event.*;
import java.awt.*;
//import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;

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
	
	private JButton okButton = new JButton("�۾� ���� �� ���̺� ����");
	private JButton insertButton = new JButton("����");
	
	private JLabel labelselect= new JLabel("��ȣ�� ��� �Է��ϼ���. (1 : �Է� / 2 : ���� / 3 : ����/ 4 : ��ȯ)");
	private JLabel labeltableselect = new JLabel("���̺��� ���� ��ư�� �����ּ���.('��ȯ'�ǰ�� ��ĭ���� ���ܵ�)  1 : �뿩ȸ�� / 2 : ķ��ī / 3 : �� / 4 : ����� / 5 : ��������");
	private JLabel labeltableselect2 = new JLabel("");
	private JLabel label1 = new JLabel("������1 :");
	private JLabel label2 = new JLabel("������2 :");
	private JLabel label3 = new JLabel("������3 :");
	private JLabel label4 = new JLabel("������4 :");
	private JLabel label5 = new JLabel("������5 :");
	private JLabel label6 = new JLabel("������6 :");
	private JLabel label7 = new JLabel("������7 :");
	private JLabel label8 = new JLabel("������8 :");
	private JLabel label9 = new JLabel("������9 :");
	private JLabel label10 = new JLabel("������10 :");
	
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
		add(new JLabel("�����ư�� ��������.")); add(insertButton);
		setSize(1400,800);
		
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String performnumber = tfselect.getText();
				String tablenumber = tftableselect.getText();
				if(performnumber.compareTo("1")==0 || performnumber.compareTo("3")==0)
					{
					if(tablenumber.compareTo("1")==0)
					{
						label1.setText("ķ��ī�뿩ȸ��ID :");//companyid
						label2.setText("ȸ��� :");//companyname
						label3.setText("�ּ� :");//companyaddress
						label4.setText("��ȭ��ȣ :");//companyphone
						label5.setText("����� �̸� :");//managername
						label6.setText("����� �̸��� :");//manageremail
						label7.setText("���� X");
						label8.setText("���� X");
						label9.setText("���� X");
						label10.setText("���� X");
					}
					else if(tablenumber.compareTo("2")==0)
					{
						label1.setText("ķ��ī���ID :");//carid
						label2.setText("ķ��ī�̸� :");//carname
						label3.setText("ķ��ī ������ȣ :");//carnumber
						label4.setText("ķ��ī���� �ο��� :");//carmax
						label5.setText("����ȸ�� :");//carbrand
						label6.setText("��������(YYYY-MM-DD) :");//birthday
						label7.setText("��������Ÿ� :");//distance
						label8.setText("ķ��ī�뿩��� :");//rentprice
						label9.setText("ķ��ī�뿩ȸ��ID :");//companyid
						label10.setText("ķ��ī�������(YYYY-MM-DD) :");//enrolldate
					}
					else if(tablenumber.compareTo("3")==0)
					{
						label1.setText("������������ȣ :");//licenseid
						label2.setText("���� :");//customername
						label3.setText("���ּ� :");//customeraddress
						label4.setText("����ȭ��ȣ :");//customerphone
						label5.setText("���̸������� :");//customermail
						label6.setText("���� X");
						label7.setText("���� X");
						label8.setText("���� X");
						label9.setText("���� X");
						label10.setText("���� X");
					}
					else if(tablenumber.compareTo("4")==0)
					{
						label1.setText("�����ID :");//carcenterid
						label2.setText("����Ҹ� :");//carcentername
						label3.setText("������ּ� :");//carcenteraddress
						label4.setText("�������ȭ��ȣ :");//carcenterphone
						label5.setText("������̸� :");//centermanagername
						label6.setText("�̸��� ����");//centermanageremail
						label7.setText("���� X");
						label8.setText("���� X");
						label9.setText("���� X");
						label10.setText("���� X");
					}
					else if(tablenumber.compareTo("5")==0)
					{
						label1.setText("���������ȣ :");//fixid
						label2.setText("ķ��ī���ID :");//carid
						label3.setText("ķ��ī�����ID :");//carcenterid
						label4.setText("ķ��ī�뿩ȸ��ID :");//companyid
						label5.setText("�� ������������ȣ :");//licenseid
						label6.setText("���� ���� :");//fix
						label7.setText("���� ��¥(YYYY-MM-DD) :");//fixdate
						label8.setText("���� ��� :");//fixprice
						label9.setText("���� ����(YYYY-MM-DD) :");//depositdate
						label10.setText("��Ÿ���񳻿� :");//etcfix
					}
					else
					{
						label1.setText("������1 :");
						label2.setText("������2 :");
						label3.setText("������3 :");
						label4.setText("������4 :");
						label5.setText("������5 :");
						label6.setText("������6 :");
						label7.setText("������7 :");
						label8.setText("������8 :");
						label9.setText("������9 :");
						label10.setText("������10 :");
					}
					}
				else if(performnumber.compareTo("2")==0)
				{
					if(tablenumber.compareTo("1")==0)
					{
						label1.setText("������ ķ��ī�뿩ȸ��ID :");//companyid
						label2.setText("���� X");//companyname
						label3.setText("���� X");//companyaddress
						label4.setText("���� X");//companyphone
						label5.setText("���� X");//managername
						label6.setText("���� X");//manageremail
						label7.setText("���� X");
						label8.setText("���� X");
						label9.setText("���� X");
						label10.setText("���� X");
					}
					else if(tablenumber.compareTo("2")==0)
					{
						label1.setText("������ ķ��ī���ID :");//carid
						label2.setText("���� X");//carname
						label3.setText("���� X");//carnumber
						label4.setText("���� X");//carmax
						label5.setText("���� X");//carbrand
						label6.setText("���� X");//birthday
						label7.setText("���� X");//distance
						label8.setText("���� X");//rentprice
						label9.setText("���� X");//companyid
						label10.setText("���� X");//enrolldate
					}
					else if(tablenumber.compareTo("3")==0)
					{
						label1.setText("������ ������������ȣ :");//licenseid
						label2.setText("���� X");//carname
						label3.setText("���� X");//carnumber
						label4.setText("���� X");//carmax
						label5.setText("���� X");//carbrand
						label6.setText("���� X");//birthday
						label7.setText("���� X");//distance
						label8.setText("���� X");//rentprice
						label9.setText("���� X");//companyid
						label10.setText("���� X");//enrolldate
					}
					else if(tablenumber.compareTo("4")==0)
					{
						label1.setText("������ �����ID :");//carcenterid
						label2.setText("���� X");//carname
						label3.setText("���� X");//carnumber
						label4.setText("���� X");//carmax
						label5.setText("���� X");//carbrand
						label6.setText("���� X");//birthday
						label7.setText("���� X");//distance
						label8.setText("���� X");//rentprice
						label9.setText("���� X");//companyid
						label10.setText("���� X");//enrolldate
					}
					else if(tablenumber.compareTo("5")==0)
					{
						label1.setText("������ ���������ȣ :");//fixid
						label2.setText("���� X");//carname
						label3.setText("���� X");//carnumber
						label4.setText("���� X");//carmax
						label5.setText("���� X");//carbrand
						label6.setText("���� X");//birthday
						label7.setText("���� X");//distance
						label8.setText("���� X");//rentprice
						label9.setText("���� X");//companyid
						label10.setText("���� X");//enrolldate
					}
					else
					{
						label1.setText("������1 :");
						label2.setText("������2 :");
						label3.setText("������3 :");
						label4.setText("������4 :");
						label5.setText("������5 :");
						label6.setText("������6 :");
						label7.setText("������7 :");
						label8.setText("������8 :");
						label9.setText("������9 :");
						label10.setText("������10 :");
					}
				}
				else if(performnumber.compareTo("4")==0)
				{
					label1.setText("�����뿩��ȣ :");//companyid
					label2.setText("ķ��ī���ID :");//companyname
					label3.setText("�պκм��� :");//companyaddress
					label4.setText("���ʼ��� :");//companyphone
					label5.setText("�����ʼ��� :");//managername
					label6.setText("���ʼ��� :");//manageremail
					label7.setText("�����ʿ俩�� (�����ʿ�� 1 �ƴϸ� 0) :");
					label8.setText("������������ȣ");
					label9.setText("���� X");
					label10.setText("���� X");
				}
				
			}});
		
		insertButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}});
	}
	void resetString() {
		label1.setText("������1 :");
		label2.setText("������2 :");
		label3.setText("������3 :");
		label4.setText("������4 :");
		label5.setText("������5 :");
		label6.setText("������6 :");
		label7.setText("������7 :");
		label8.setText("������8 :");
		label9.setText("������9 :");
		label10.setText("������10 :");
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
      setBounds(100, 30, 1280, 720); //������ġ,������ġ,���α���,���α���
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      dialog=new MyModalDialog(this,"�۾�â");
      
   }

   public void layInit() {
      btnRent = new JButton("��û");
      btnReset = new JButton("�ʱ�ȭ");
      UbtnSearch1 = new JButton("�뿩������ ķ��ī��� �˻�");
      btnExecute = new JButton("data ����/����/���� �� ��ȯ");
      AbtnSearch1 = new JButton("�뿩�� �� �뿩���� �˻�");
      AbtnSearch2 = new JButton("������ �ʿ��� ���� �˻�");
      AbtnSearch3 = new JButton("����Һ� ���񳻿� �˻�");
      AbtnSearch4 = new JButton("���� û���� ������� �˻�");
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
      	pn2.add(new JLabel("�Ϲݻ���� �����Դϴ�.��ư�� ���� �뿩������ ������ �˻��ϼ���"));
      	pn2.add(UbtnSearch1);
      	pn2.add(new JLabel("������ ������������ȣ�� �Է����ּ���(LICENSE ID)"));
      	pn2.add(txtField1);
      	pn2.add(new JLabel("����Ͻð� ���� ����ID�� �Է����ּ���."));
      	pn2.add(txtField2);
      	pn2.add(new JLabel("�뿩�������� �Է����ּ���(YYYY-MM-DD):"));
      	pn2.add(txtField3);
      	pn2.add(new JLabel("�뿩�Ⱓ�� �Է����ּ��� (��):"));
      	pn2.add(txtField4);
      	pn2.add(new JLabel("�뿩�� ��û�Ϸ��� ��ư�� ��������"));
      	pn2.add(btnRent);
      
      pn4.setLayout(new FlowLayout(FlowLayout.CENTER,300,40));
      pn4.add(btnReset);//�ʱ�ȭ��ư
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
         System.out.println("����̹� �ε� ����");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
      
      try { /* �����ͺ��̽��� �����ϴ� ���� */
          System.out.println("�����ͺ��̽� ���� �غ�...");
          con = DriverManager.getConnection(url, userid, pwd);
          System.out.println("�����ͺ��̽� ���� ����");
       } catch (SQLException e1) {
          e1.printStackTrace();
       }
   }
   public void createTable() {//���̺� ���� Companylist,Campingcarlist,CampingCustomer,PresentRentCampingcarList,CarCheck,FixInformation,CarcenterList,AvailableCampingcarlist
	  try { 
	   String sql[] = new String[8];
	   StringBuilder sb = new StringBuilder();
	   stmt = con.createStatement();
	   
	   stmt.execute("drop table if exists companylist,campingcarlist,campingcustomer,presentrentcampingcarlist,carcheck,fixinformation,carcenterlist,availablecampingcarlist");
	   
	   
	   sql[0] = sb.append("create table Companylist(")//table1
               .append("companyid INTEGER PRIMARY KEY,")//ķ��ī�뿩ȸ��ID
               .append("companyname varchar(45),")//ȸ���
               .append("companyaddress varchar(45),")//�ּ�
               .append("companyphone varchar(45),")//��ȭ��ȣ
               .append("managername varchar(45),")//������̸�
               .append("manageremail varchar(45)")//������̸���
               .append(");").toString();
	   	stmt.execute(sql[0]);
	   	sb.delete(0,sb.length());
	   
	   sql[1] = sb.append("create table Campingcarlist(")//table2
               .append("carid INTEGER PRIMARY KEY,")//ķ��ī���ID
               .append("carname varchar(45),")//ķ��ī �̸�
               .append("carnumber INTEGER,")//ķ��ī ������ȣ
               .append("carmax INTEGER,")//ķ��ī �ο���
               .append("carbrand varchar(45),")//������ȸ��
               .append("birthday DATE,")//��������
               .append("distance INTEGER,")//��������Ÿ�
               .append("rentprice INTEGER,")//ķ��ī�뿩���
               .append("companyid INTEGER,")//ķ��ī�뿩ȸ��ID
               .append("enrolldate DATE")//ķ��ī�������
               .append(");").toString();
	   	stmt.execute(sql[1]);
	   	sb.delete(0,sb.length());
	   			
	   	sql[2] = sb.append("create table CampingCustomer(")//table3
	   			.append("licenseid INTEGER PRIMARY KEY,")//������������ȣ
	   			.append("customername varchar(45),")//����
	   			.append("customeraddress varchar(45),")//���ּ�
	   			.append("customerphone varchar(45),")//����ȭ��ȣ
	   			.append("customeremail varchar(45)")//���̸���
	   			.append(");").toString();
	   	stmt.execute(sql[2]);
	   	sb.delete(0, sb.length());
	   			
	   	sql[3] = sb.append("create table PresentRentCampingcarList(")//table4
	   			.append("rentid INTEGER PRIMARY KEY,")//�����뿩��ȣ
	   			.append("carid INTEGER,")//ķ��ī���ID
	   			.append("licenseid INTEGER,")//������������ȣ
	   			.append("companyid INTEGER,")//ķ��ī�뿩ȸ��ID
	   			.append("startdate DATE,")//�뿩������
	   			.append("enddate DATE,")//�뿩�Ⱓ
	   			.append("rentprice INTEGER,")//û�����
	   			.append("depositdate DATE,")//���Ա���
	   			.append("etc varchar(45),")//��Ÿû������
	   			.append("etcprice INTEGER")//��Ÿû���������
	   			.append(");").toString();
	   	stmt.execute(sql[3]);
	   	sb.delete(0,  sb.length());
	   			
	   	sql[4] = sb.append("create table CarCheck(")//table5
	   			.append("rentid INTEGER PRIMARY KEY,")//�����뿩��ȣ
	   			.append("carid INTEGER,")//ķ��ī���ID
	   			.append("frontdescription varchar(45),")//�պκм���
	   			.append("leftdescription varchar(45),")//���ʼ���
	   			.append("rightdescription varchar(45),")//�����ʼ���
	   			.append("backdescription varchar(45),")//���ʼ���
	   			.append("havetofix INTEGER,")//�����ʿ俩��
	   			.append("licenseid INTEGER")//������������ȣ
	   			.append(");").toString();
	   	stmt.execute(sql[4]);
	   	sb.delete(0,  sb.length());
	   			
	   	sql[5] = sb.append("create table FixInformation(")//table6
	   			.append("fixid INTEGER PRIMARY KEY,")//���������ȣ
	   			.append("carid INTEGER,")//ķ��ī���ID
	   			.append("carcenterid INTEGER,")//ķ��ī�����ID
	   			.append("companyid INTEGER,")//ķ��ī�뿩ȸ��ID
	   			.append("licenseid INTEGER,")//������������ȣ
	   			.append("fix varchar(45),")//���񳻿�
	   			.append("fixdate DATE,")//������¥
	   			.append("fixprice INTEGER,")//�������
	   			.append("depositdate DATE,")//���Ա���
	   			.append("etcfix varchar(45)")//��Ÿ���񳻿�
	   			.append(");").toString();
	   	stmt.execute(sql[5]);
	   	sb.delete(0,  sb.length());
	   	
	   	sql[6] = sb.append("create table CarcenterList(")//table7
	   			.append("carcenterid INTEGER PRIMARY KEY,")//ķ��ī�����ID
	   			.append("carcentername varchar(45),")//����Ҹ�
	   			.append("carcenteraddress varchar(45),")//������ּ�
	   			.append("carcenterphone varchar(45),")//�������ȭ��ȣ
	   			.append("centermanagername varchar(45),")//������̸�
	   			.append("centermanageremail varchar(45)")//�̸�������
	   			.append(");").toString();
	   	stmt.execute(sql[6]);
	   	sb.delete(0,  sb.length());
	   	
	   	
	   	sql[7] = sb.append("create table AvailableCampingcarlist(")//table8
	   			.append("carid INTEGER PRIMARY KEY,")//ķ��ī���ID
	               .append("carname varchar(45),")//ķ��ī �̸�
	               .append("carnumber INTEGER,")//ķ��ī ������ȣ
	               .append("carmax INTEGER,")//ķ��ī �ο���
	               .append("carbrand varchar(45),")//������ȸ��
	               .append("birthday DATE,")//��������
	               .append("distance INTEGER,")//��������Ÿ�
	               .append("rentprice INTEGER,")//ķ��ī�뿩���
	               .append("companyid INTEGER,")//ķ��ī�뿩ȸ��ID
	               .append("enrolldate DATE")//ķ��ī�������
	               .append(");").toString();
		   	stmt.execute(sql[7]);
		   	sb.delete(0,sb.length());	   	
	   	
	  }catch(SQLException CreateError)
		{
			System.out.println("���̺��ʱ�ȭ�۾�����"+CreateError);
		}
	  
   }
   public void insertMydata() { //�ʱ�data����
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
	
	insertCompanyList[0] = "INSERT INTO CompanyList Values (1,'�Ｚ','���� ����','000-0000-0001','�޽�','messi@gmail.com')";
	insertCompanyList[1] = "INSERT INTO CompanyList Values (2,'LG','���� ����','000-0000-0002','���̸���','neymar@gmail.com')";
	insertCompanyList[2] = "INSERT INTO CompanyList Values (3,'����','���� ����','000-0000-0003','ȣ����','ronaldo@gmail.com')";
	insertCompanyList[3] = "INSERT INTO CompanyList Values (4,'SK','���� �д�','000-0000-0004','����','bale@gmail.com')";
	insertCompanyList[4] = "INSERT INTO CompanyList Values (5,'�Ե�','��� ����','000-0000-0005','����ī','oscar@gmail.com')";
	insertCompanyList[5] = "INSERT INTO CompanyList Values (6,'������','��� �ϻ�','000-0000-0006','���׹�','pogba@gmail.com')";
	insertCompanyList[6] = "INSERT INTO CompanyList Values (7,'��ȭ','���� ��','000-0000-0007','���','rooney@gmail.com')";
	insertCompanyList[7] = "INSERT INTO CompanyList Values (8,'GS','���� ����','000-0000-0008','�ǹ�','silva@gmail.com')";
	insertCompanyList[8] = "INSERT INTO CompanyList Values (9,'����','���� ������','000-0000-0009','�緹','pele@gmail.com')";
	insertCompanyList[9] = "INSERT INTO CompanyList Values (10,'�ż���','�뱸 ����','000-0000-0010','��ũ','hulk@gmail.com')";
	insertCompanyList[10] = "INSERT INTO CompanyList Values (11,'CJ','���� ����','000-0000-0011','�𸶸���','dimaria@gmail.com')";
	insertCompanyList[11] = "INSERT INTO CompanyList Values (12,'����','��� ����','000-0000-0012','������','mbappe@gmail.com')";
	insertCompanyList[12] = "INSERT INTO CompanyList Values (13,'�λ�','���� ���','000-0000-0013','���Ʒ���','suarez@gmail.com')";
	insertCompanyList[13] = "INSERT INTO CompanyList Values (14,'�븲','���� ����','000-0000-0014','ī�ٴ�','cavani@gmail.com')";
	insertCompanyList[14] = "INSERT INTO CompanyList Values (15,'KT','���� ����','000-0000-0015','����','pique@gmail.com')";
	
	
	insertCampingcarList[0] = "INSERT INTO CampingcarList VALUES (1, 'QM3', 1111, 4, '�Ｚ', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 2, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[1] = "INSERT INTO CampingcarList VALUES (2, '����Ʈ ����',1112, 4, '����', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 5, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[2] = "INSERT INTO CampingcarList VALUES (3, '�Ÿ ', 1113, 4, '����', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 2, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[3] = "INSERT INTO CampingcarList VALUES (4, 'i30 ����', 1114, 4, '����', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 2, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[4] = "INSERT INTO CampingcarList VALUES (5, 'K5 ���̺긮��', 1115, 4, '���', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 4, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[5] = "INSERT INTO CampingcarList VALUES (6, 'SM5 ����', 1116, 6, '�Ｚ', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 6, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[6] = "INSERT INTO CampingcarList VALUES (7, '��� CVT', 1117, 6, '���', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 9, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[7] = "INSERT INTO CampingcarList VALUES (8, 'K3 ����', 1118, 6, '���', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 10, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[8] = "INSERT INTO CampingcarList VALUES (9, '�ƹݶ� ����', 1119, 6, '����', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 11, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[9] = "INSERT INTO CampingcarList VALUES (10, 'K7 ���̺긮��', 1120, 8, '���', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 15, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[10] = "INSERT INTO CampingcarList VALUES (11, '�׷��� ', 1121, 8, '����', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 12, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[11] = "INSERT INTO CampingcarList VALUES (12, '������ ����ũ', 1122, 8, 'GM', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 4, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[12] = "INSERT INTO CampingcarList VALUES (13, '��Ƽ�ӽ�������', 1123, 8, '���亿', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 140000, 7, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[13] = "INSERT INTO CampingcarList VALUES (14, '�����', 1124, 10, '���亿', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 140000, 8, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertCampingcarList[14] = "INSERT INTO CampingcarList VALUES (15, '����', 1125, 12, '����', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 990000, 3, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	
	insertCampingCustomer[0] = "INSERT INTO CampingCustomer VALUES (1, '������', '���� ����', '000-0000-0001', 'yn@gmail.com')";
	insertCampingCustomer[1] = "INSERT INTO CampingCustomer VALUES (2, '������', '���� ����', '000-0000-0002', 'jh@gmail.com')";
	insertCampingCustomer[2] = "INSERT INTO CampingCustomer VALUES (3, '�賲��', '���� ����', '000-0000-0003', 'nj@gmail.com')";
	insertCampingCustomer[3] = "INSERT INTO CampingCustomer VALUES (4, '������', '���� ���', '000-0000-0004', 'hg@gmail.com')";
	insertCampingCustomer[4] = "INSERT INTO CampingCustomer VALUES (5, '��ä��', '���� ��õ', '000-0000-0005', 'cy@gmail.com')";
	insertCampingCustomer[5] = "INSERT INTO CampingCustomer VALUES (6, '������', '���� ����', '000-0000-0006', 'jh@gmail.com')";
	insertCampingCustomer[6] = "INSERT INTO CampingCustomer VALUES (7, '������', '���� ����', '000-0000-0007', 'jw@gmail.com')";
	insertCampingCustomer[7] = "INSERT INTO CampingCustomer VALUES (8, '��ټ�', '���� ����', '000-0000-0008', 'gs@gmail.com')";
	insertCampingCustomer[8] = "INSERT INTO CampingCustomer VALUES (9, '���缮', '���� ����', '000-0000-0009', 'js@gmail.com')";
	insertCampingCustomer[9] = "INSERT INTO CampingCustomer VALUES (10, '��ȣ��', '���� �߱�', '000-0000-0010', 'hd@gmail.com')";
	insertCampingCustomer[10] = "INSERT INTO CampingCustomer VALUES (11, '�����', '���� ������', '000-0000-0011', 'ha@gmail.com')";
	insertCampingCustomer[11] = "INSERT INTO CampingCustomer VALUES (12, '�̼���', '���� ����', '000-0000-0012', 'sm@gmail.com')";
	insertCampingCustomer[12] = "INSERT INTO CampingCustomer VALUES (13, '�̽�ö', '���� ����', '000-0000-0013', 'sc@gmail.com')";
	insertCampingCustomer[13] = "INSERT INTO CampingCustomer VALUES (14, '��ȿ��', '���� ����', '000-0000-0014', 'hl@gmail.com')";
	insertCampingCustomer[14] = "INSERT INTO CampingCustomer VALUES (15, '�۽���', '���� ����', '000-0000-0015', 'sh@gmail.com')";
	
	insertCarcenterList[0] = "INSERT INTO CarcenterList Values (1,'���������','���� ����','000-0000-0011','managerA','sj@gmail.com')";
	insertCarcenterList[1] = "INSERT INTO CarcenterList Values (2,'�Ѿ������','���� ����','000-0000-0021','managerB','hy@gmail.com')";
	insertCarcenterList[2] = "INSERT INTO CarcenterList Values (3,'�Ǳ������','���� ����','000-0000-0031','managerC','gg@gmail.com')";
	insertCarcenterList[3] = "INSERT INTO CarcenterList Values (4,'���������','���� ���','000-0000-0041','managerD','sg@gmail.com')";
	insertCarcenterList[4] = "INSERT INTO CarcenterList Values (5,'���հ������','���� ���','000-0000-0051','managerE','ssg@gmail.com')";
	insertCarcenterList[5] = "INSERT INTO CarcenterList Values (6,'���������','���� ����','000-0000-0061','managerF','ys@gmail.com')";
	insertCarcenterList[6] = "INSERT INTO CarcenterList Values (7,'��������','���� ����','000-0000-0071','managerG','kr@gmail.com')";
	insertCarcenterList[7] = "INSERT INTO CarcenterList Values (8,'���������','���� ����','000-0000-0081','managerH','ss@gmail.com')";
	insertCarcenterList[8] = "INSERT INTO CarcenterList Values (9,'�߾������','���� ���','000-0000-0091','managerI','ca@gmail.com')";
	insertCarcenterList[9] = "INSERT INTO CarcenterList Values (10,'���������','���� ����','000-0000-0101','managerJ','dk@gmail.com')";
	insertCarcenterList[10] = "INSERT INTO CarcenterList Values (11,'ȫ�������','���� ����','000-0000-0201','managerK','hi@gmail.com')";
	insertCarcenterList[11] = "INSERT INTO CarcenterList Values (12,'���������','���� ���','000-0000-0301','managerL','km@gmail.com')";
	insertCarcenterList[12] = "INSERT INTO CarcenterList Values (13,'���������','���� ����','000-0000-0401','managerM','gw@gmail.com')";
	insertCarcenterList[13] = "INSERT INTO CarcenterList Values (14,'���������','���� ����','000-0000-0501','managerN','sl@gmail.com')";
	insertCarcenterList[14] = "INSERT INTO CarcenterList Values (15,'���������','���� ����','000-0000-0601','managerO','kh@gmail.com')";
	
	insertAvailableCampingcarList[0] = "INSERT INTO AvailableCampingcarList VALUES (1, 'QM3', 1111, 4, '�Ｚ', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 2, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[1] = "INSERT INTO AvailableCampingcarList VALUES (2, '����Ʈ ����',1112, 4, '����', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 5, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[2] = "INSERT INTO AvailableCampingcarList VALUES (3, '�Ÿ ', 1113, 4, '����', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 2, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[3] = "INSERT INTO AvailableCampingcarList VALUES (4, 'i30 ����', 1114, 4, '����', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 2, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[4] = "INSERT INTO AvailableCampingcarList VALUES (5, 'K5 ���̺긮��', 1115, 4, '���', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 4, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[5] = "INSERT INTO AvailableCampingcarList VALUES (6, 'SM5 ����', 1116, 6, '�Ｚ', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 100000, 6, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[6] = "INSERT INTO AvailableCampingcarList VALUES (7, '��� CVT', 1117, 6, '���', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 9, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[7] = "INSERT INTO AvailableCampingcarList VALUES (8, 'K3 ����', 1118, 6, '���', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 10, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[8] = "INSERT INTO AvailableCampingcarList VALUES (9, '�ƹݶ� ����', 1119, 6, '����', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 11, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[9] = "INSERT INTO AvailableCampingcarList VALUES (10, 'K7 ���̺긮��', 1120, 8, '���', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 15, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[10] = "INSERT INTO AvailableCampingcarList VALUES (11, '�׷��� ', 1121, 8, '����', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 12, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[11] = "INSERT INTO AvailableCampingcarList VALUES (12, '������ ����ũ', 1122, 8, 'GM', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 120000, 4, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[12] = "INSERT INTO AvailableCampingcarList VALUES (13, '��Ƽ�ӽ�������', 1123, 8, '���亿', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 140000, 7, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[13] = "INSERT INTO AvailableCampingcarList VALUES (14, '�����', 1124, 10, '���亿', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 140000, 8, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	insertAvailableCampingcarList[14] = "INSERT INTO AvailableCampingcarList VALUES (15, '����', 1125, 12, '����', STR_TO_DATE('2000-07-13','%Y-%m-%d'), 100, 990000, 3, STR_TO_DATE('2014-07-13','%Y-%m-%d'))";
	
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
		System.out.println("�����ʱ�ȭ�۾�����"+e3);
	}
   }
   
   
   
   
   @Override
   public void actionPerformed(ActionEvent e) { // �׼Ǵ�� �Լ�
    
      try {//�Ϲ� ����ڿ� ��������
         stmt = con.createStatement();
         String query;
         
         if (e.getSource() == btnRent) { // btnRent�� ������ ��
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
        	 txtResult.setText("��Ʈ�Ϸ�");
        	 
            }
         
         
         
         else if(e.getSource() == UbtnSearch1) {//�˻�1��ư�� ������ ��
        	 query = "SELECT * FROM availablecampingcarlist ";
        	txtResult.setText("");
        	txtResult.setText("���� ID\tķ��ī�̸�\t������ȣ \t�ִ�����ο���  ����ȸ��\t��������\t��������Ÿ�\t�뿩���(1��)\t\n");
        	rs = stmt.executeQuery(query);
        	while (rs.next()) {
                String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) +"\t" + rs.getInt(7) +"\t" + rs.getInt(8)
                      + "\n";
                txtResult.append(str);
        						}
         }
        
         
         
         
      } catch (Exception e2) {
         System.out.println("���� �б� ���� :" + e2);
         txtResult.setText("���� �޼��� :" + e2);
         
         
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
      try {//�����ڿ� ���� ����
    	  stmt = con.createStatement();
          String query;
          if (e.getSource() == btnReset) {// btnReset�� ������ ��
              txtResult2.setText("");//ȭ���� �ؽ�Ʈ���� ����� ����
              createTable();
              insertMydata();
              rentid = 0;
              txtResult2.setText("�����Ͱ� �ʱ�ȭ �Ǿ����ϴ�!!");
           }
          
          
          
          
          
          else if(e.getSource() == btnExecute) {//execute��ư�� ������ ��
         	 dialog.setVisible(true);// ���̾�α� �۵�����
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
         	 
         	 if(selectnumber.compareTo("1")==0)//'�Է�'���ý�
         	 {
         		if(tablenumber.compareTo("1")==0)
            	 {
            		 query = "insert into companylist values("+data1+",'"+data2+"','"+data3+"','"+data4+"','"+data5+"','"+data6+"')";
            		 stmt.executeUpdate(query);
            		 txtResult2.setText("ķ��ī �뿩ȸ�� ���̺� ķ��ī�뿩ȸ��ID�� "+data1+"�� data�� �ԷµǾ����ϴ�.");
            	 }
            	 else if(tablenumber.compareTo("2")==0)
            	 {
            		 query = "insert into campingcarlist values("+data1+",'"+data2+"',"+data3+","+data4+",'"+data5+"',STR_TO_DATE('"+data6+"','%Y-%m-%d'),"+data7+","+data8+","+data9+",STR_TO_DATE('"+data10+"','%Y-%m-%d'))";
            		 stmt.executeUpdate(query);
            		 query = "insert into availablecampingcarlist values("+data1+",'"+data2+"',"+data3+","+data4+",'"+data5+"',STR_TO_DATE('"+data6+"','%Y-%m-%d'),"+data7+","+data8+","+data9+",STR_TO_DATE('"+data10+"','%Y-%m-%d'))";
            		stmt.executeUpdate(query);
           		 txtResult2.setText("ķ��ī ���̺� ķ��ī���ID�� "+data1+"�� data�� �ԷµǾ����ϴ�.");
            	 }
            	 else if(tablenumber.compareTo("3")==0)
            	 {
            		 query = "insert into campingcustomer values("+data1+",'"+data2+"','"+data3+"','"+data4+"','"+data5+"')";
            		 stmt.executeUpdate(query);
            		 txtResult2.setText("�� ���̺� ������������ȣ�� "+data1+"�� data�� �ԷµǾ����ϴ�.");
            	 }
            	 else if(tablenumber.compareTo("4")==0)
            	 {
            		 query = "insert into carcenterlist values("+data1+",'"+data2+"','"+data3+"','"+data4+"','"+data5+"','"+data6+"')";
            		 stmt.executeUpdate(query);
            		 txtResult2.setText("ķ��ī����� ���̺� ķ��ī�����ID�� "+data1+"�� data�� �ԷµǾ����ϴ�.");
            	 }
            	 else if(tablenumber.compareTo("5")==0)
            	 {
            		 query = "insert into FixInformation values("+data1+","+data2+","+data3+","+data4+","+data5+",'"+data6+"',STR_TO_DATE('"+data7+"','%Y-%m-%d'),"+data8+",STR_TO_DATE('"+data9+"','%Y-%m-%d'),'"+data10+"')";
            		 stmt.executeUpdate(query);
           		 txtResult2.setText("ķ��ī�������� ���̺� ���������ȣ�� "+data1+"�� data�� �ԷµǾ����ϴ�.");
            	 }
         	 }
         	 else if(selectnumber.compareTo("2")==0) //'����' ���ý�
         	 {
         		if(tablenumber.compareTo("1")==0)
           	 {
         		query = "delete from companylist where companyid ="+ data1;
         		stmt.executeUpdate(query);
           		txtResult2.setText("ķ��ī �뿩ȸ�� ���̺��� ķ��ī�뿩ȸ��ID�� "+data1+" �� data�� �����Ǿ����ϴ�.");
           		 
           	 }
           	 else if(tablenumber.compareTo("2")==0)
           	 {
           		query = "delete from campingcarlist where carid ="+ data1;
         		stmt.executeUpdate(query);
         		query = "delete from availablecampingcarlist where carid ="+ data1;
         		stmt.executeUpdate(query);
           		txtResult2.setText("ķ��ī���̺� �� ���� �뿩������ ķ��ī���̺��� ķ��ī���ID�� "+data1+" �� data�� �����Ǿ����ϴ�.");
           	 }
           	 else if(tablenumber.compareTo("3")==0)
           	 {
           		query = "delete from campingcustomer where licenseid ="+ data1;
         		stmt.executeUpdate(query);
           		txtResult2.setText("�� ���̺��� ������������ȣ�� "+data1+" �� data�� �����Ǿ����ϴ�.");
           	 }
           	 else if(tablenumber.compareTo("4")==0)
           	 {
           		query = "delete from carcenterlist where carcenterid ="+ data1;
         		stmt.executeUpdate(query);
           		txtResult2.setText("ķ��ī�����  ���̺��� ķ��ī�����ID�� "+data1+" �� data�� �����Ǿ����ϴ�.");
           	 }
           	 else if(tablenumber.compareTo("5")==0)
           	 {
           		query = "delete from fixinformation where fixid ="+ data1;
         		stmt.executeUpdate(query);
           		txtResult2.setText("�������� ���̺��� ���������ȣ�� "+data1+" �� data�� �����Ǿ����ϴ�.");
           	 }
         	 }
         	 else if(selectnumber.compareTo("3")==0)//'����'���ý�
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
         			txtResult2.setText("ķ��ī �뿩ȸ�� ���̺��� ķ��ī�뿩ȸ��ID�� "+data1+" �� data�� ����Ǿ����ϴ�.");
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
         			txtResult2.setText("ķ��ī ���̺��� ķ��ī���ID�� "+data1+" �� data�� ����Ǿ����ϴ�.");
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
         			txtResult2.setText("�� ���̺��� ������������ȣ�� "+data1+" �� data�� ����Ǿ����ϴ�.");
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
         			txtResult2.setText("ķ��ī����� ���̺��� �����ID�� "+data1+" �� data�� ����Ǿ����ϴ�.");
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
         			txtResult2.setText("�������� ���̺��� ���������ȣ�� "+data1+" �� data�� ����Ǿ����ϴ�.");
         		}
         	 }
         	else if(selectnumber.compareTo("4")==0)//'��ȯ'���ý�
         		{
         			String str="";
         		//���˳��� ����
         			query = "insert into CarCheck values("+data1+","+data2+",'"+data3+"','"+data4+"','"+data5+"','"+data6+"',"+data7+","+data8+")";
         			stmt.executeUpdate(query);
         		//���� �뿩��Ͽ��� delete
         				query = "delete from PresentRentCampingcarList where rentid = "+data1;
         				stmt.executeUpdate(query);
         		
         		//������ �ʿ����� ������ �뿩������ ������� �̵�
         			if(data7.compareTo("0")==0)
         			{
         				query = "select * from Campingcarlist where carid = "+data2;
         				rs = stmt.executeQuery(query);
         				while (rs.next()) {
         					str ="insert into availablecampingcarlist values("+rs.getInt(1)+",'"+rs.getString(2)+"',"+rs.getInt(3)+","+rs.getInt(4)+",'"+rs.getString(5)+"',STR_TO_DATE('"+rs.getString(6)+"','%Y-%m-%d'),"+rs.getInt(7)+","+rs.getInt(8)+","+rs.getInt(9)+",STR_TO_DATE('"+rs.getString(10)+"','%Y-%m-%d'))";
         	                
         	        						}
         				stmt.executeUpdate(str);
         				
         			}
         			
         			txtResult2.setText("�����뿩��ȣ�� "+data1+"�� ������ ��ȯ�Ǿ����ϴ�.");
         		}
           }
          
          
          
          
          
          
          else if(e.getSource() == AbtnSearch1) {//�����ڿ� �˻�1 ��ư�� ������ ��
         	 query = "select presentrentcampingcarlist.rentid, campingcarlist.carid, campingcarlist.carname, campingcustomer.licenseid, campingcustomer.customername,  campingcarlist.carnumber " + 
         	 		"from campingcustomer,presentrentcampingcarlist,campingcarlist " + 
         	 		"where presentrentcampingcarlist.licenseid = campingcustomer.licenseid and presentrentcampingcarlist.carid = campingcarlist.carid;";
          	txtResult2.setText("");
          	txtResult2.setText("�����뿩��ȣ\tķ��ī���ID\t������\t������������ȣ\t����\t������ȣ\n");
          	rs = stmt.executeQuery(query);
          	while (rs.next()) {
                  String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4) + "\t" + rs.getString(5) + "\t" +rs.getInt(6)
                        + "\n";
                  txtResult2.append(str);
          						}
           }
          
          else if(e.getSource() == AbtnSearch2) {//�����ڿ� �˻�2 ��ư�� ������ ��
          	 query = "select campingcarlist.carid, campingcarlist.carname, campingcarlist.companyid, campingcustomer.licenseid, campingcustomer.customername, carcheck.frontdescription, carcheck.leftdescription, carcheck.rightdescription, carcheck.backdescription " + 
          	 		"from carcheck,campingcarlist,campingcustomer " + 
          	 		"where carcheck.havetofix=1 and carcheck.carID = campingcarlist.carid and carcheck.licenseid=campingcustomer.licenseid "+
          	 		"order by carid;";
           	txtResult2.setText("");
           	txtResult2.setText("ķ��ī���ID\tķ��ī �̸�\tķ��ī�뿩ȸ��ID    ������������ȣ    ����\t�պκ�\t���ʺκ�\t�����ʺκ�\t�޺κ�\n");
           	rs = stmt.executeQuery(query);
           	while (rs.next()) {
                   String str = rs.getInt(1) + "\t"+ rs.getString(2) + "\t"+ rs.getInt(3) + "\t       " + rs.getInt(4) + "\t          " + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8) + "\t" + rs.getString(9)
                         + "\n";
                   txtResult2.append(str);
           						}
            }
          else if(e.getSource() == AbtnSearch3) {//�����ڿ� �˻�3 ��ư�� ������ ��
           	 query = "select  carcenterlist.carcenterid, carcenterlist.carcentername, fixinformation.fixid, fixinformation.companyid, companylist.companyname, fixinformation.carid, campingcarlist.carname, fixinformation.fixdate " + 
           	 		"from fixinformation,carcenterlist,campingcarlist,companylist " + 
           	 		"where fixinformation.carcenterid = carcenterlist.carcenterid and fixinformation.carid = campingcarlist.carid and fixinformation.companyid = companylist.companyid " + 
           	 		"order by carcenterid;";
            	txtResult2.setText("");
            	txtResult2.setText("�����ID\t����� �̸�\t�����ȣ\t�뿩ȸ��ID\t�뿩ȸ���̸� \tķ��ī���ID\tķ��ī �̸�\t������¥\n");
            	rs = stmt.executeQuery(query);
            	while (rs.next()) {
                    String str = rs.getInt(1) + "\t"+ rs.getString(2) + "\t"+ rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getString(5) + "\t" + rs.getInt(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8)
                          + "\n";
                    txtResult2.append(str);
            						}
             }
          else if(e.getSource() == AbtnSearch4) {//�����ڿ� �˻�4 ��ư�� ������ ��
            	 query = "select campingcustomer.licenseid, campingcustomer.customername, sum(fixinformation.fixprice) " + 
            	 		"from campingcustomer,fixinformation " + 
            	 		"where campingcustomer.licenseid = fixinformation.licenseid " + 
            	 		"Group by campingcustomer.customername " + 
            	 		"order by campingcustomer.customername;";
             	txtResult2.setText("");
             	txtResult2.setText("������������ȣ\t����\t�������\n");
             	rs = stmt.executeQuery(query);
             	while (rs.next()) {
                     String str = rs.getInt(1) + "\t"+ rs.getString(2) + "\t"+ rs.getInt(3)
                           + "\n";
                     txtResult2.append(str);
             						}
              }
          
      }
      catch(Exception e2) {
    	  System.out.println("���� �б� ���� :" + e2);
          txtResult2.setText("���� �޼��� :" + e2);
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
    		System.out.println("���α׷� ���� ����!");    		
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