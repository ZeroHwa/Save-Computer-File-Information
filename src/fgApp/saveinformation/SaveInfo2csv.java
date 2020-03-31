package fgApp.saveinformation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SaveInfo2csv {
	//������ļ���д�ɾ�̬�������Mysqlʱ��ȡ
	static File OUTPUT = new File("C:\\Users\\Administrator\\Desktop\\csv2.csv");
	public void saveInfo() throws IOException, InterruptedException
	{
		Set<Integer> pids = EnumWindow.getTaskPID();
		/**
		 * ���� "tasklist /v /fi \"PID eq " + pid + "\" /FO \"csv\" > csv3.csv"
		 */
		// �൱��cmd��һ���н飬����д��commandȻ��start
		ProcessBuilder pb = new ProcessBuilder();
		// ������ļ�
//		File output = new File("C:\\Users\\Administrator\\Desktop\\csv2.csv");
		// ������־�ļ�
		File errorMsg = new File("C:\\Users\\Administrator\\Desktop\\error.txt");
		// �����Ž� д �ĳ�����м䣬����ÿ��д��ʱ�䣬��������
		int time = 100;
		List<String> command = new ArrayList<>();
		// ����ǵ�һ�εĻ��͸�����д��������ǵĻ�����д
		boolean isFirst = true;

		/**
		 * ˵һ��tasklist����,�ο���https://jingyan.baidu.com/article/09ea3ede1371fec0aede3997.html
		 * tasklist �鿴�������� /v��ָ�� Ҫ��ʾ��ϸ��Ϣ /fi �ǹ��ˣ�Ȼ��������� "pid eq ...������pid ���ڶ��ٵģ���ʾ���� fo
		 * ���������ʽ�� Ȼ�� > ��������ļ�����д���� >>����д ������redirectOutput()�������Ϳ���
		 */

		for (Integer pid : pids) {
			String fiPid = "\"PID eq " + pid + "\"";
			command.clear();
			command.add("tasklist");
			command.add("/v");
			command.add("/fi");
			command.add(fiPid);
			command.add("/FO");
			command.add("\"csv\"");
			//�������ݿ⣬ȫ������д���⣬���Ҫ���ڶ��Ļ�����ע�͵�
			command.add("/NH");
			// ��д
			if (isFirst) {
				pb.command(command);
				pb.redirectError(errorMsg);
				pb.redirectOutput(OUTPUT);
				isFirst = false;
			} else {
				// ���ÿ�ͷ����
//				command.add("/NH");
				pb.command(command);
				// ��д
				pb.redirectError(ProcessBuilder.Redirect.appendTo(errorMsg));
				pb.redirectOutput(ProcessBuilder.Redirect.appendTo(OUTPUT));
			}
			pb.start();
			delay(time);
		}
	}

	void delay(int time) throws InterruptedException {
		Thread.sleep(time);
	}

}
