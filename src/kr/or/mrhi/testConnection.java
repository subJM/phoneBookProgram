package kr.or.mrhi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class testConnection {

	// mysql Driver & URL ����
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost/sqldb";
	public static final String userID = "root";
	public static final String userPassword = "123456";
	public static final Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		// 1. jdbc Driver loader
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Loader Fail");
		}
		// 2. Mysql Database Connection
		Connection con = null;
		// outputStream = prepareStatement
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(URL, userID, userPassword);
			System.out.println("DB connection success!!");
			System.out.print("������ ������>>");
			String name = scan.nextLine();
			String dml = "delete from usertbl where name = ?";
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name); // delete from usertbl where name = '������';
			int result = pstmt.executeUpdate(); // mysql���� ���� ���� ���ڿ� ��Ʈ���� �����ؼ� ������� ���Ϲ޴� ��
			if (result == 1) {
				System.out.println(name + "���� �����Ǿ����ϴ�.");
			} else {
				System.out.println(name + "���� ������ �����߽��ϴ�.");
			}
		} catch (SQLException e) {
			System.out.println("DB connection Fail");
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
				if (con != null && con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		System.out.println("goodbye");
	}// end of main

}
