package computer.saveinformation;

//import java.sql.ResultSet;
import java.sql.SQLException;

public class TextForMysql {
	public static void main(String[] args) {
		String deleteSql = "TRUNCATE TABLE computerInformation;";
		String querySql = "select * from computerInformation";
		String insertSql = "insert into computerInformation "
				+ "value(,'aa','aaa aab bcc asd','path',1024,2);";
		SaveInMysql sim = new SaveInMysql();
		boolean check;
		try {
			check = sim.newTable();
			System.out.println("new Table:"+check);
//			ResultSet rs = Connector.QueryConnect(querySql);
			System.out.println("is there data in the sql:"+Connector.QueryConnect(querySql).next());
			Connector.ExecuteConnect(insertSql);
			System.out.println("is there data in the sql:"+Connector.QueryConnect(querySql).next());
			Connector.ExecuteConnect(deleteSql);
			System.out.println("is there data in the sql:"+Connector.QueryConnect(querySql).next());
			String name = "name";
			String[] childName = {"aaa","bbb"};
			String path = "aa";
			long size = 0;
			boolean isFile = false;
			sim.insertInfo(name, childName, path, size, isFile);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
