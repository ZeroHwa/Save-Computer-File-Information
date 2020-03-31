package computer.saveinformation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveInMysql {
	public boolean newTable() throws SQLException{
		boolean Return;
		String sql = "create table if not exists "
				+ "computerInformation"
				+ "("
				+ "id int unsigned not null auto_increment primary key,"
				+ "name varchar(2000) comment '文件名', "
				+ "childFiles text comment '子文件名，用空格隔开',"
				+ "path text comment '绝对路径',"
				+ "size bigint unsigned comment '大小,文件夹不显示大小',"
				+ "isFile boolean comment 'false为文件夹，true为文件'"
//				+ "parentid int unsigned comment '父文件夹的id'"
				+ ");";
		Connector.ExecuteConnect(sql);
		String sql2 = "select * from information_schema.TABLES "
				+ "where "
				+ "table_schema ='db_bishe' "
				+ "and table_name = 'computerinformation';";
		ResultSet rs  =Connector.QueryConnect(sql2);
		Return = rs.next();
		rs.close();
		return Return;
	}
	public void insertInfo(String name,String[] childName,String path,long size,boolean isFile) {
		String headSql = "insert into computerInformation(name,childFiles,path,size,isFile) value(";
		StringBuffer insertSql = new StringBuffer(300);
		insertSql.append(headSql);
		insertSql.append("'"+name+"','");
		for (String string : childName) {
			insertSql.append(string+"  ");
		}
		insertSql.append("',");
		insertSql.append("'"+path+"',");
		insertSql.append(size+",");
		insertSql.append(isFile);
		insertSql.append(");");
		Connector.ExecuteConnect(insertSql.toString());
	}
	
	
	
//	public boolean deleteData() {
//		String sql = "TRUNCATE TABLE 'computerInformation'";
//		
//	}
}
