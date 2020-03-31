package fgApp.saveinformation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SaveInfo2csv {
	//输出的文件，写成静态方便存入Mysql时读取
	static File OUTPUT = new File("C:\\Users\\Administrator\\Desktop\\csv2.csv");
	public void saveInfo() throws IOException, InterruptedException
	{
		Set<Integer> pids = EnumWindow.getTaskPID();
		/**
		 * 命令 "tasklist /v /fi \"PID eq " + pid + "\" /FO \"csv\" > csv3.csv"
		 */
		// 相当于cmd的一个中介，可以写入command然后start
		ProcessBuilder pb = new ProcessBuilder();
		// 输出的文件
//		File output = new File("C:\\Users\\Administrator\\Desktop\\csv2.csv");
		// 错误日志文件
		File errorMsg = new File("C:\\Users\\Administrator\\Desktop\\error.txt");
		// 用来放进 写 的程序的中间，增长每次写的时间，避免乱序。
		int time = 100;
		List<String> command = new ArrayList<>();
		// 如果是第一次的话就覆盖重写，如果不是的话就续写
		boolean isFirst = true;

		/**
		 * 说一下tasklist命令,参考：https://jingyan.baidu.com/article/09ea3ede1371fec0aede3997.html
		 * tasklist 查看本机进程 /v是指定 要显示详细信息 /fi 是过滤，然后接着条件 "pid eq ...，就是pid 等于多少的，显示出来 fo
		 * 就是输出格式， 然后 > 是输出到文件【重写】， >>是续写 这里有redirectOutput()方法，就可以
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
			//便于数据库，全部都不写标题，如果要便于读的话，就注释掉
			command.add("/NH");
			// 覆写
			if (isFirst) {
				pb.command(command);
				pb.redirectError(errorMsg);
				pb.redirectOutput(OUTPUT);
				isFirst = false;
			} else {
				// 不用开头标题
//				command.add("/NH");
				pb.command(command);
				// 续写
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
