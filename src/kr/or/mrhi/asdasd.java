package kr.or.mrhi;

import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
public class asdasd {
	//DB mySQL server ��ġ. �ڹٿ��� ��� �ν��� �ҷ��� �ڹ� mySQL ����̹��� ��ġ�Ǿ� �־���Ѵ�.
		//mySQL Drivaer & URL ����
		public static final String DRIVER = "com.mysql.jdbc.Driver";//����������
		public static final String URL = "jdbc:mysql://localhost/sqldb";
		public static final String userID = "root";
		public static final String userPassword = "123456";
		public static final Scanner scan = new Scanner(System.in);
		
		
		public static void main(String[] args) throws IOException {
			//collection Framework properties ����ؼ� ����̹� URL ID PASSWORD�� �����´�
			Properties properties = new Properties();
			String filePath = DatabaseTest.class.getResource("db.properties").getPath();
			filePath = URLDecoder.decode(filePath, "utf-8");
			
//			File file = new File(filePath);
//			FileReader filereader = new FileReader(file); // �̰��� �ѹ������� properties.load(new FileReader(filePath));
			properties.load(new FileReader(filePath));
			
			String driver = properties.getProperty("DRIVER");
			String url = properties.getProperty("URL");
			String userID = properties.getProperty("userID");
			String userPassword = properties.getProperty("userPassword");
			
			System.out.println(driver);
			
			// 1. jdbc Driver loader
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				System.out.println("Driver Loader Fail");
			}
			
			//2. mySQL Database connection
			Connection con = null; // ����
			PreparedStatement pstmt = null;
			
			try {
				con=DriverManager.getConnection(URL, userID, userPassword);
				System.out.println("DB connection succcess");
				
				System.out.print("������ ������ >> ");
				String name = scan.nextLine();
				
				String dml = "delete from usertbl where name = ?";
				
				//outputStream = prepareStatement
				
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, name); // delete from usertbl where name = '������'
				int result = pstmt.executeUpdate();    // mysql ���� ���� ���� ���ڿ� ��Ʈ���� �����ؼ� ������� ���Ϲ޴´�.
				
				if(result == 1) {
					System.out.println(name+ "���� �����Ǿ����ϴ�");
				}else {
					System.out.println("��������");
				}
				
				
				
			} catch (SQLException e) {
				System.out.println("DB connection Fail");
			}finally {
				try {
					if(pstmt != null&& !pstmt.isClosed()) {
						pstmt.close();
					}
					if(con != null && !con.isClosed()) {
						con.close();
					}
				} catch (SQLException e) {
					
				}
			}
			
			System.out.println("good bye");
			
			
		}//end of main
	}
