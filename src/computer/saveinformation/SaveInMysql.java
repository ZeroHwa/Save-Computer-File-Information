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
				+ "name varchar(2000) comment '�ļ���', "
				+ "childFiles text comment '���ļ������ÿո����',"
				+ "path text comment '����·��',"
				+ "size bigint unsigned comment '��С,�ļ��в���ʾ��С',"
				+ "isFile boolean comment 'falseΪ�ļ��У�trueΪ�ļ�'"
//				+ "parentid int unsigned comment '���ļ��е�id'"
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
