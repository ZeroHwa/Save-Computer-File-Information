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
		// �Ӹ�Ŀ¼��¼
		// Node root = new Node();
		// ����F��
		Node root = new Node(new File("F:\\"));
		StringBuffer information = new StringBuffer("(��ǰ�ļ���)(���ļ���)(��ǰ�ļ�����·��)(����)(��С)");
		try {
			// ���Ϊ true��������д���ļ�ĩβ����������д���ļ���ʼ����
			// �˴�Ϊfalseԭ����ÿ�������У�������дһ���ļ���������д��ԭ�ļ�����
			FileWriter fw = new FileWriter("D:\\Java_learn\\Bishe\\src\\TestForF.txt", false);
			// \r\n�ǻ��з���
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
		// System.out.println("node�ڵ㣺"+node.toString());
		StringBuffer information = new StringBuffer("(��ǰ�ļ���)(���ļ���)(��ǰ�ļ�����·��)(��С)");
		String name;
		File[] files;
		String path;
		// ������MySQL��
		long size2;
		String size;
		
		//������ļ��Ļ�
		if (node.file.isFile()) {
			size2 = getFileSize(node.file);
			size = getSize(size2);
			name = getName(node);
			path = getPath(node);
			information = information.delete(0, information.length());
			information.append("(" + name + ")");
			information.append("(�����ļ�)");
			information.append("(" + path + ")");
			information.append("(�ļ�)");
			information.append("(" + size + ")");
			// System.out.println(information.toString());

			try {
				writeTxt(information.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//д�����ݿ���
			 String[] childrenName = {"�����ļ�"};
			 //��ת���ַ�ǰ���ڼӸ���б�ܣ�������MySQL��ת��
			 path = path.replaceAll("\\\\", "\\\\\\\\");
			sim.insertInfo(name, childrenName, path, size2, true);
			
			//������ļ��еĻ�
		} else if (node.file.isDirectory()) {
			// ԭ���õ�node.file.isDirectory()
			// ����!node.file.isFile()

			/**
			 * getDirectorySize����Ϊʱ���������
			 */
			// size = getSize(getDirectorySize(node.file));

			/**
			 * �������file.toStringֱ�����
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
			 * �������file.toStringֱ�����
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
			information.append("(�ļ���)");
			information.append("(�ļ��в���ʾ��С)");
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
			 * ֱ����File
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
	 *            Ҫ�����һ���ļ���Ϣ
	 * @throws IOException
	 *             �׳�IO�쳣
	 */
	public void writeTxt(String information) throws IOException {
		// ���Ϊ true��������д���ļ�ĩβ����������д���ļ���ʼ����

		// FileWriter fw = new
		// FileWriter("D:\\Java_learn\\Bishe\\src\\computerInformation.txt", true);

		// F�̲���
		FileWriter fw = new FileWriter("D:\\\\Java_learn\\\\Bishe\\\\src\\\\TestForF.txt", true);

		// \r\n�ǻ��з���
		fw.write(information + "\r\n");
		fw.flush();
		fw.close();
	}

	public String getName(Node node) {
		return node.toString();
	}

	/**
	 * �����õķ���String�ģ����Ƿ����ڼ������һ��get���˸������̵����� ����ֱ������getFiles,�� Node���滻����
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
