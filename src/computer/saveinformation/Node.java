package computer.saveinformation;

import java.io.File;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

/**
 * 
 * @author Zero_Hwa
 *
 */
public class Node {
	
	File file;
	File[] files;
	int count;
//	Node[] children;
	String path;
	FileSystemView fsv = FileSystemView.getFileSystemView();
	
	File root = fsv.getFiles(fsv.getHomeDirectory(), true)[0];
	                                                                                                                       
	public Node() {
		this.file = root;
		/**
		 * ������fsv.getFiels������get���������̣�ԭ������ֱ����files.listFiles()���ԡ�
		 */
//		files = fsv.getFiles(file, true);
		files = file.listFiles();
		count = files.length;
		path = this.file.getAbsolutePath();
	}
	public File[] getFiles() {
		return files;
	}
	public int getCount() {
		return count;
	}
	public String getPath() {
		return path;
	}
	public Node(File file) {
		this.file = file;
		files = fsv.getFiles(file, true);
		count = files.length;
		path = file.getAbsolutePath();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return fsv.getSystemDisplayName(file);
	}
	//��ϵͳ�ļ����������ʾ���ļ���Ŀ¼���ļ��е�ͼ�ꡣ
	public Icon getIcon() {						
        return fsv.getSystemIcon(file);
    }
	
}
