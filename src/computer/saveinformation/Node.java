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
		 * 这里用fsv.getFiels好像不能get到各个磁盘，原因不明，直接用files.listFiles()可以。
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
	//在系统文件浏览器中显示的文件、目录或文件夹的图标。
	public Icon getIcon() {						
        return fsv.getSystemIcon(file);
    }
	
}
