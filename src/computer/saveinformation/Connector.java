package computer.saveinformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {
	//查询语句 静态方法	
	public static ResultSet QueryConnect(String sql) {
		String driver = "com.mysql.cj.jdbc.Driver";
		// URL指向要访问的数据库名test1
		String url = "jdbc:mysql://localhost:3306/db_bishe?serverTimezone=GMT";
		// MySQL配置时的用户名
		String user = "root";
		// Java连接MySQL配置时的密码
		String password = "chh1612.";
		ResultSet rs = null;
		try {
			// 1 加载驱动程序
			Class.forName(driver);
		} catch (Exception e) {
			System.out.println("wrong in driver");
		}
		try {
			// 2 连接数据库
			Connection conn = DriverManager.getConnection(url, user, password);
			// 3 用来执行SQL语句
			Statement statement = conn.createStatement();
			// 要执行的SQL语句
			rs = statement.executeQuery(sql);
			//如果关闭了conn的话，就会导致导出的ResultSet无法用next方法。
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
		// URL指向要访问的数据库名test1
		String url = "jdbc:mysql://localhost:3306/db_bishe?serverTimezone=GMT";
		// MySQL配置时的用户名
		String user = "root";
		// Java连接MySQL配置时的密码
		String password = "chh1612.";
//		ResultSet rs = null;
		boolean excuteOk;
		try {
			// 1 加载驱动程序
			Class.forName(driver);
		} catch (Exception e) {
			System.out.println("wrong in driver");
		}
		try {
			// 2 连接数据库
			Connection conn = DriverManager.getConnection(url, user, password);
			// 3 用来执行SQL语句
//			Statement statement = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement(sql);
			// 要执行的SQL语句
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
