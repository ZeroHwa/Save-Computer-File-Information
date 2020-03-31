package computer.saveinformation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.filechooser.FileSystemView;

public class SaveInText {

	FileSystemView fsv = FileSystemView.getFileSystemView();
	static SaveInMysql sim = new SaveInMysql();
	String deleteSql = "TRUNCATE TABLE computerInformation;";

	public static void main(String[] args) {
		// 从根目录记录
		// Node root = new Node();
		// 测试F盘
		Node root = new Node(new File("F:\\"));
		StringBuffer information = new StringBuffer("(当前文件名)(子文件名)(当前文件绝对路径)(类型)(大小)");
		try {
			// 如果为 true，则将数据写入文件末尾处，而不是写入文件开始处。
			// 此处为false原因是每次新运行，都重新写一遍文件，而不是写在原文件后面
			FileWriter fw = new FileWriter("D:\\Java_learn\\Bishe\\src\\TestForF.txt", false);
			// \r\n是换行符号
			fw.write(information + "\r\n");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sim.newTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SaveInText sit = new SaveInText();
		sit.recursion(root);
		
		
		// String information;
		// String name;
		// String[] childrenName;
		// String path;
		// long size;
		// String StringOfSize;
	}

	public void recursion(Node node) {
		// System.out.println("start of recursion");
		// System.out.println("node节点："+node.toString());
		StringBuffer information = new StringBuffer("(当前文件名)(子文件名)(当前文件绝对路径)(大小)");
		String name;
		File[] files;
		String path;
		// 用来存MySQL里
		long size2;
		String size;
		
		//如果是文件的话
		if (node.file.isFile()) {
			size2 = getFileSize(node.file);
			size = getSize(size2);
			name = getName(node);
			path = getPath(node);
			information = information.delete(0, information.length());
			information.append("(" + name + ")");
			information.append("(无子文件)");
			information.append("(" + path + ")");
			information.append("(文件)");
			information.append("(" + size + ")");
			// System.out.println(information.toString());

			try {
				writeTxt(information.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//写入数据库中
			 String[] childrenName = {"无子文件"};
			 //将转义字符前面在加个反斜杠，避免在MySQL里转义
			 path = path.replaceAll("\\\\", "\\\\\\\\");
			sim.insertInfo(name, childrenName, path, size2, true);
			
			//如果是文件夹的话
		} else if (node.file.isDirectory()) {
			// 原来用的node.file.isDirectory()
			// 或用!node.file.isFile()

			/**
			 * getDirectorySize会因为时间过长出错？
			 */
			// size = getSize(getDirectorySize(node.file));

			/**
			 * 这里改用file.toString直接输出
			 */

			// childrenName = getChildrenName(node);
			// for (String string : childrenName) {
			// System.out.println("chName:"+string);
			// }
			// childrenName = node.file.list();

			files = getChildren(node);
			// for (File file : getChildren(node) ) {
			// System.out.println(file);
			// }

			name = getName(node);
			path = getPath(node);
			information = information.delete(0, information.length());
			information.append("(" + name + ")");
			information.append("(");

			/**
			 * 这里改用file.toString直接输出
			 */
			// for (String cn : childrenName) {
			// information.append(cn + " ");
			// }
			 String[] childrenName = new String[files.length];
			 int i = 0;
			for (File file : files) {
				information.append(file.getName() + " ");
				childrenName[i++] = file.getName();
			}

			information.append(")");
			information.append("(" + path + ")");
			information.append("(文件夹)");
			information.append("(文件夹不显示大小)");
			// information.append("("+size+")");
			// System.out.println(information.toString());
			try {
				writeTxt(information.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			path = path.replaceAll("\\\\", "\\\\\\\\");
			sim.insertInfo(name, childrenName, path, 0, false);
			
			/**
			 * 直接用File
			 */
			// for (String string : childrenName) {
			// System.out.println(string);
			// recursion(new Node(new File(node.getPath() + "\\" + string)));
			// }
			for (File file : files) {
				recursion(new Node(file));
			}
		} else {
			System.out.println("judgement of file is error ");
		}
	}

	/**
	 * @param information
	 *            要输出的一行文件信息
	 * @throws IOException
	 *             抛出IO异常
	 */
	public void writeTxt(String information) throws IOException {
		// 如果为 true，则将数据写入文件末尾处，而不是写入文件开始处。

		// FileWriter fw = new
		// FileWriter("D:\\Java_learn\\Bishe\\src\\computerInformation.txt", true);

		// F盘测试
		FileWriter fw = new FileWriter("D:\\\\Java_learn\\\\Bishe\\\\src\\\\TestForF.txt", true);

		// \r\n是换行符号
		fw.write(information + "\r\n");
		fw.flush();
		fw.close();
	}

	public String getName(Node node) {
		return node.toString();
	}

	/**
	 * 本来用的返回String的，但是发现在计算机下一层get不了各个磁盘的名字 所以直接用了getFiles,在 Node里面换成了
	 * file.listFiles
	 * 
	 * @param node
	 * @return
	 */
	public File[] getChildren(Node node) {
		File[] files = node.getFiles();
		// String[] childrenName = new String[files.length];
		// int i = 0;
		// for (File file : files) {
		// childrenName[i] = file.getName();
		// System.out.println("childrenName[i]"+childrenName[i]);
		// i++;
		// System.out.println(file);
		// }
		return files;
	}

	public String getPath(Node node) {
		return node.getPath();
	}

	public long getDirectorySize(File f) throws NullPointerException {
		long DirectorySize = 0;
		File flie[] = f.listFiles();
		for (int i = 0; i < flie.length; i++) {
			if (flie[i].isDirectory()) {
				DirectorySize = DirectorySize + getDirectorySize(flie[i]);
			} else {
				DirectorySize = DirectorySize + flie[i].length();
			}
		}
		return DirectorySize;
	}

	public long getFileSize(File f) {
		return f.length();
	}

	public String getSize(long size) {
		if (size < 1024) {
			return size + "B";
		} else if (size < 1024 * 1024) {
			return ((float) size / 1024) + "K";
		} else if (size < 1024 * 1024 * 1024) {
			return ((float) size / (1024 * 1024)) + "M";
		} else if (size < 1024 * 1024 * 1024 * 1024) {
			return ((float) size / (1024 * 1024 * 1024)) + "G";
		}
		return "Error";
	}

}
