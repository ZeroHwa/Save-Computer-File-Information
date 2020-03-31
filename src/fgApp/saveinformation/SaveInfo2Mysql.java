package fgApp.saveinformation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import computer.saveinformation.Connector;

public class SaveInfo2Mysql {
	public void SaveInfo() throws IOException {
		// ������
		String createTable = "create table if not exists fgappInfo(" + "ӳ������ varchar(2000)," + "pid int," + "�Ự�� char(200),"
				+ "�Ự int," + "�ڴ�ʹ�� varchar(2000)," + "״̬ char(200)," + "�û��� varchar(2000)," + "CPUʱ�� time," + "���ڱ��� text);";
		Connector.ExecuteConnect(createTable);

		// BufferedReader br = new BufferedReader(new FileReader(SaveInfo2csv.OUTPUT));
		// String firstLine = br.readLine();
		// br.close();
		// BufferedWriter bw = new BufferedWriter(new FileWriter(SaveInfo2csv.OUTPUT));

		//��մ˱�
		String deleteSql = "TRUNCATE TABLE fgappInfo;";
		Connector.ExecuteConnect(deleteSql);
		// System.out.println(SaveInfo2csv.OUTPUT.toString());
		// �� \ �滻�� \\ ��
		String loadFile = SaveInfo2csv.OUTPUT.toString();
		loadFile = loadFile.replaceAll("\\\\", "\\\\\\\\");
		String loadSql = "load data infile \'" + loadFile + "\' " + "into table fgappInfo character set gbk "
				+ "fields terminated by \',\' optionally enclosed by \'\"\' escaped by \'\"\' "
				+ "lines terminated by \'\\r\\n\';";
		Connector.ExecuteConnect(loadSql);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		SaveInfo2Mysql si2m = new SaveInfo2Mysql();
		SaveInfo2csv si2s = new SaveInfo2csv();
		si2s.saveInfo();
		si2m.SaveInfo();

	}

}
