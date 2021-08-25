package kr.or.mrhi.sixclass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DatabaseTest {
	public static final Scanner scan = new Scanner(System.in);
	public static final int INSERT = 1, SEARCH = 2, DELETE = 3, UPDATE = 4, SHOWTB = 5, FINISH = 6;

	public static void main(String[] args) throws IOException {
		int selectNumber = 0;
		boolean flag = false;
		int searchNumber =0;
		
		// �޴�����
		while (!flag) {
			// �޴���� �� ��ȣ����
			selectNumber = displayMenu();

			switch (selectNumber) {
			case INSERT:
				phoneBookInsert();
				break; // ��ȭ��ȣ�� �Է��ϱ�
			case SEARCH:
				phoneBookSearch();
				break; // ��ȭ��ȣ�� �˻��ϱ�
			case DELETE:
				phoneBookDelete();
				break; // ��ȭ��ȣ�� �����ϱ�
			case UPDATE:
				phoneBookUpdate();
				break; // ��ȭ��ȣ�� �����ϱ�
			case SHOWTB:
				phoneBookSelect();
				break; // ��ȭ��ȣ�� ����ϱ�
			case FINISH:
				flag = true;
				break;
			default:
				System.out.println("���ڹ����ʰ� �ٽ��Է¿��");
				break;
			}// end of switch
		} // end of while

		System.out.println("���α׷� ����!");
	}// end of main
		// ��ȭ��ȣ�� ����ϱ�

	private static void phoneBookSelect() {
		List<PhoneBook> list = new ArrayList<>();
		
		list = DBController.phoneBookSelectTBL();
		
		if(list.size()<=0) {
			System.out.println("����� �����Ͱ� ���ų� �˻����� ����");
			return;
		}
		
		for(PhoneBook pb : list) {
			System.out.println(pb);
		}
		

	}

	// ��ȭ��ȣ�� �Է��ϱ�
	private static void phoneBookInsert() {
		
		
		String phoneNumber = null;
		String name = null;
		String gender = null;
		String job = null;
		String birthday = null;
		
		int age = 0;

		while (true) {
			System.out.print("��ȭ��ȣ(000-0000-0000) �Է� >>");
			phoneNumber = scan.nextLine();
			if (phoneNumber.length() != 13) {
				System.out.println("��ȭ��ȣ 13�ڸ��� �Է����ּ���");
				continue;
			} 
			boolean checkPhoneNumber = duplicatePhoneNumberCheck(phoneNumber);
			
			if(checkPhoneNumber ==true) {
				System.out.println("�����ϴ� ��ȣ�Դϴ�. �ٽ��Է��ϼ���.");
				continue;
			}
			break;
		} // end of while

		while (true) {
			System.out.print("�̸�(ȫ�浿) �Է� >>");
			name = scan.nextLine();
			if (name.length() < 2 || name.length() > 7) {
				System.out.println("�̸� �ٽ� �Է����ּ���");
				continue;
			} else {
				break;
			}
		} // end of while

		while (true) {
			System.out.print("����(����,����) �Է� >>");
			gender = scan.nextLine();
			if (!(gender.equals("����") || gender.equals("����"))) {
				System.out.println("���� �ٽ� �Է����ּ���");
				continue;
			} else {
				break;
			}
		} // end of while

		while (true) {
			System.out.print("����(20���ڹ̸�) �Է� >>");
			job = scan.nextLine();
			if (job.length() < 1 || job.length() > 20) {
				System.out.println("���� �ٽ� �Է����ּ���");
				continue;
			} else {
				break;
			}
		} // end of while

		while (true) {
			System.out.print("�������(19950830) �Է� >>");
			birthday = scan.nextLine();
			if (birthday.length() != 8) {
				System.out.println("������� �ٽ� �Է����ּ���");
				continue;
			} else {
				int year = Integer.parseInt(birthday.substring(0, 4));
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				age = currentYear - year + 1;
				break;
			}
		} // end of while

		PhoneBook phoneBook = new PhoneBook(phoneNumber, name, gender, job, birthday, age);
		
		//��ȭ��ȣ�� �Է��ϱ�
		int count =DBController.phoneBookInsertTBL(phoneBook);
		
		if (count == 1) {
			System.out.println(phoneBook.getName() + "�� ���Լ���");
		} else {
			System.out.println(phoneBook.getName() + "�� ���Խ���");
		}
	}

	// ��ȭ��ȣ�� �˻��ϱ�
	private static void phoneBookSearch() {
		//�˻��� ���� �Է¹ޱ�(�˻��� ������ �����п� �����ؼ� �ټ� �ִ�.)
		final int PHONE= 1,NAME=2,GENDER=3 , EXIT=4;
		List<PhoneBook> list = new ArrayList<>();

		boolean flag = false;
		String searchData = null;
		int searchNumber = 0;
		
		// ����
		while (!flag) {
			int selectNumber = displaySearchMenu();
			switch (selectNumber) {
			case PHONE:
				System.out.print("��ȭ��ȣ �Է� >> ");
				searchData = scan.nextLine();
				searchNumber =1;
				break;
			case NAME:
				System.out.print("�̸��Է� >> ");
				searchData = scan.nextLine();
				searchNumber=2;
				break;
			case GENDER:
				System.out.print("�����Է� >> ");
				searchData = scan.nextLine();
				searchNumber=3;
				break;
			case EXIT:
				//�Լ�����
				return;
			default:
				System.out.println("�˻� ������ ������ϴ�. �ٽ��Է����ּ���.");
				continue;
			}
			flag= true;
		}
		list = DBController.phoneBookSearchTBL(searchData,searchNumber);
		
		if(list==null) {
			System.out.println("�˻������߻�");
			return;
		}
		
		for(PhoneBook pb:list) {
			System.out.println(pb);
		}
		
	}

	// ��ȭ��ȣ�� �����ϱ�
	private static void phoneBookDelete() {
		System.out.print("������ �̸� �Է�>> ");
		String name = scan.nextLine();
		
		int count = DBController.phoneBookDeleteTBL(name);
		
		if (count != 0) {
			System.out.println(name + "�� ���� �Ϸ�");
		} else {
			System.out.println(name + "�� ���� ����");
		}
	}

	// ��ȭ��ȣ�� �����ϱ�
	private static void phoneBookUpdate() {
		
		
		//��ȭ��ȣ�θ� ������� �ش�� ���ڵ带 �����ش�.
		System.out.print("�����һ�� ��ȭ��ȣ �Է� >> ");
		String phoneNumber = scan.nextLine();
		if(phoneNumber.length()<=12 || phoneNumber.length()>=14 ) {
			System.out.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���");
			return;
		}
		System.out.print("������ �̸� �Է� >> ");
		String name = scan.nextLine();
		if(name.length()<=1 ||name.length()>=5) {
			System.out.println("�̸��� �߸��Ǿ����ϴ�. �ٽ� �Է����ּ���.");
			return;
		}
		
		int count = DBController.phoneBookUpdateTBL(phoneNumber,name);
		
		if (count != 0) {
			System.out.println(phoneNumber + "�� �̸��� ���� �Ϸ�Ǿ����ϴ�.");
		} else {
			System.out.println(phoneNumber + "�� �̸��� ���� �����Ͽ����ϴ�.");
		}
		
	}

	// �޴���� �� ��ȣ����
	private static int displayMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("\n*************************************");
			System.out.println("1.�Է� 2.�˻� 3.���� 4.���� 5.��� 6.����");
			System.out.println("*************************************");
			System.out.print("��ȣ����>>");
			// ��ȣ����
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("���� �Է� ���");
				continue;
			} catch (Exception e) {
				System.out.println("���� �Է� �����߻� ���Է¿��");
				continue;
			}
			break;
		}
		return selectNumber;
	}

	private static int displaySearchMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("\n*************************************************");
			System.out.println("�˻����� 1.��ȭ��ȣ�� �˻� 2.�̸��˻� 3.�����˻� 4.�˻�������");
			System.out.println("*************************************************");
			System.out.print("��ȣ����>>");
			// ��ȣ����
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("���� �Է� ���");
				continue;
			} catch (Exception e) {
				System.out.println("���� �Է� �����߻� ���Է¿��");
				continue;
			}
			break;
		}
		return selectNumber;
	}

	// ��ȭ��ȣ�� �˻��ϱ�
	private static boolean duplicatePhoneNumberCheck(String phoneNumber) {
		//�˻��� ���� �Է¹ޱ�(�˻��� ������ �����п� �����ؼ� �ټ� �ִ�.)
		final int PHONE = 1;
		List<PhoneBook> list = new ArrayList<>();
		int searchNumber = PHONE;
		
		
		list = DBController.phoneBookSearchTBL(phoneNumber,searchNumber);
		
		if(list.size() >=1) {
			return true;
		}
		return false;
	}
		
}