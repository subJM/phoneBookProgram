package kr.or.mrhi.sixclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//����Ÿ���̽��� ���õ� ������ ���⿡�� �ְ��Ѵ�. (�Է�)->(������)->(�������� ���Ϲ޾Ƽ� UI�о߿��� �����ش�.)
public class DBController {

	//1.��ȭ��ȣ�� �Է�
	public static int phoneBookInsertTBL(PhoneBook phoneBook) {
		// ����Ÿ���̽��� ����
		Connection con = DBUtility.getConnection();
		String insertQuery = "insert into phonebook.phonebookTBL values(?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int count = 0;
		try {
			preparedStatement = con.prepareStatement(insertQuery);
			preparedStatement.setString(1, phoneBook.getPhoneNumber());
			preparedStatement.setString(2, phoneBook.getName());
			preparedStatement.setString(3, phoneBook.getGender());
			preparedStatement.setString(4, phoneBook.getJob());
			preparedStatement.setString(5, phoneBook.getBirthday());
			preparedStatement.setInt(6, phoneBook.getAge());

			count = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}

			} catch (SQLException e) {
			}
		}
		return count;
	}
	//2.��ȭ��ȣ�� �˻�
	public static List<PhoneBook> phoneBookSearchTBL(String searchData, int searchNumber) {
		List<PhoneBook> list = new ArrayList<>();

		Connection con = DBUtility.getConnection();
		String searchQuery = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		

	try	{
		switch (searchNumber) {
		case 1:	searchQuery = "select * from phonebooktbl where phoneNumber like ?";break;
		case 2:	searchQuery = "select * from phonebooktbl where name like ?";  		break;
		case 3:	searchQuery = "select * from phonebooktbl where gender like ?";		break;
		default:
			System.out.println("�˻� ������ ������ϴ�. �ٽ��Է����ּ���.");
			return list;
		}
	
		preparedStatement = con.prepareStatement(searchQuery);
		searchData = "%" + searchData + "%";
		preparedStatement.setString(1, searchData);
		resultSet = preparedStatement.executeQuery();



		while (resultSet.next()) {
			String phoneNumber = resultSet.getString(1);
			String name = resultSet.getString(2);
			String gender = resultSet.getString(3);
			String job = resultSet.getString(4);
			String birthday = resultSet.getString(5);
			int age = resultSet.getInt(6);

			PhoneBook phoneBook = new PhoneBook(phoneNumber, name, gender, job, birthday, age);
			list.add(phoneBook);
		}

	}catch(
	SQLException e)
	{
		e.printStackTrace();
	}finally
	{
		try {
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
			if (con != null && !con.isClosed()) {
				con.close();
			}

		} catch (SQLException e) {
		}
	}
	return list;

}

	//3.��ȭ��ȣ�� ����
	public static int phoneBookDeleteTBL(String name) {
		//�����ͺ��̽��� ����
		Connection con = DBUtility.getConnection();
		String deletQuery = "delete from phonebooktbl where name like ?";
		PreparedStatement preparedStatement = null;
		int count = 0;

		try {
			preparedStatement = con.prepareStatement(deletQuery);
			String strName = "%" + name + "%";
			preparedStatement.setString(1, strName);
			count = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}

			} catch (SQLException e) {	}
		}
		return count;
		
	}
	//4.��ȭ��ȣ�� ����
	public static int phoneBookUpdateTBL(String phoneNumber, String name) {
		Connection con = DBUtility.getConnection();
		String updateQuery = "update phonebooktbl set name = ? where phoneNumber = ?";
		PreparedStatement preparedStatement = null;
		int count = 0;

		try {
			preparedStatement = con.prepareStatement(updateQuery);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, phoneNumber);
			count = preparedStatement.executeUpdate();

			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}

			} catch (SQLException e) {
			}
		}
		return count;
	}
	//5.��ȭ��ȣ�� ���
	public static List<PhoneBook> phoneBookSelectTBL() {
		List<PhoneBook> list = new ArrayList<>();
		
		Connection con = DBUtility.getConnection();
		String selectQuery = "select * from phonebooktbl";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = con.prepareStatement(selectQuery);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String phoneNumber = resultSet.getString(1);
				String name = resultSet.getString(2);
				String gender = resultSet.getString(3);
				String job = resultSet.getString(4);
				String birthday = resultSet.getString(5);
				int age = resultSet.getInt(6);

				PhoneBook phoneBook = new PhoneBook(phoneNumber, name, gender, job, birthday, age);
				list.add(phoneBook);
				

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}

			} catch (SQLException e) {	}
		}
		return list;
	}
	
}
