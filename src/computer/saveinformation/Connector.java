package computer.saveinformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {
	//��ѯ��� ��̬����	
	public static ResultSet QueryConnect(String sql) {
		String driver = "com.mysql.cj.jdbc.Driver";
		// URLָ��Ҫ���ʵ����ݿ���test1
		String url = "jdbc:mysql://localhost:3306/db_bishe?serverTimezone=GMT";
		// MySQL����ʱ���û���
		String user = "root";
		// Java����MySQL����ʱ������
		String password = "chh1612.";
		ResultSet rs = null;
		try {
			// 1 ������������
			Class.forName(driver);
		} catch (Exception e) {
			System.out.println("wrong in driver");
		}
		try {
			// 2 �������ݿ�
			Connection conn = DriverManager.getConnection(url, user, password);
			// 3 ����ִ��SQL���
			Statement statement = conn.createStatement();
			// Ҫִ�е�SQL���
			rs = statement.executeQuery(sql);
			//����ر���conn�Ļ����ͻᵼ�µ�����ResultSet�޷���next������
//			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void ExecuteConnect(String sql) {
		String driver = "com.mysql.cj.jdbc.Driver";
		// URLָ��Ҫ���ʵ����ݿ���test1
		String url = "jdbc:mysql://localhost:3306/db_bishe?serverTimezone=GMT";
		// MySQL����ʱ���û���
		String user = "root";
		// Java����MySQL����ʱ������
		String password = "chh1612.";
//		ResultSet rs = null;
		boolean excuteOk;
		try {
			// 1 ������������
			Class.forName(driver);
		} catch (Exception e) {
			System.out.println("wrong in driver");
		}
		try {
			// 2 �������ݿ�
			Connection conn = DriverManager.getConnection(url, user, password);
			// 3 ����ִ��SQL���
//			Statement statement = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement(sql);
			// Ҫִ�е�SQL���
//			rs = statement.executeQuery(sql);
			ps.execute();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
