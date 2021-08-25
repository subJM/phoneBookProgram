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
	//DB mySQL server 장치. 자바에서 디비 인식을 할려면 자바 mySQL 드라이버가 설치되어 있어야한다.
		//mySQL Drivaer & URL 선언
		public static final String DRIVER = "com.mysql.jdbc.Driver";//정해져있음
		public static final String URL = "jdbc:mysql://localhost/sqldb";
		public static final String userID = "root";
		public static final String userPassword = "123456";
		public static final Scanner scan = new Scanner(System.in);
		
		
		public static void main(String[] args) throws IOException {
			//collection Framework properties 사용해서 드라이버 URL ID PASSWORD를 가져온다
			Properties properties = new Properties();
			String filePath = DatabaseTest.class.getResource("db.properties").getPath();
			filePath = URLDecoder.decode(filePath, "utf-8");
			
//			File file = new File(filePath);
//			FileReader filereader = new FileReader(file); // 이것을 한문장으로 properties.load(new FileReader(filePath));
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
			Connection con = null; // 소켓
			PreparedStatement pstmt = null;
			
			try {
				con=DriverManager.getConnection(URL, userID, userPassword);
				System.out.println("DB connection succcess");
				
				System.out.print("누구를 지울까요 >> ");
				String name = scan.nextLine();
				
				String dml = "delete from usertbl where name = ?";
				
				//outputStream = prepareStatement
				
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, name); // delete from usertbl where name = '은지원'
				int result = pstmt.executeUpdate();    // mysql 에서 내가 보낸 문자열 스트링을 실행해서 결과값을 리턴받는다.
				
				if(result == 1) {
					System.out.println(name+ "님이 삭제되었습니다");
				}else {
					System.out.println("삭제실패");
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
