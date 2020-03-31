package fgApp.saveinformation;

import java.util.HashSet;
import java.util.Set;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.IntByReference;

public class EnumWindow {
	public static Set<Integer> getTaskPID() {
		User32 user32 = User32.INSTANCE;
		Set<Integer> set=new HashSet<Integer>();
		IntByReference i=new IntByReference();//��PID
		user32.EnumWindows(new User32.WNDENUMPROC() {
			//hΪӦ�þ����pΪ�ص�������
			public boolean callback(HWND h, Pointer p) {
				//�˺�����������ָ�����ڵ��̵߳ı�ʶ��������ѡ�ؼ����������ڵĽ��̵ı�ʶ����
				//hΪӦ�þ��,i��ָ�뷵��
				user32.GetWindowThreadProcessId(h, i);//��ȡ���ڵ�PID
				
				//������Ϊh��Ӧ���ǿ��ӻ����ܶ��Ĵ��ڣ���i��ֵ���ص�set��
				if(user32.IsWindow(h)&&user32.IsWindowEnabled(h)&&user32.IsWindowVisible(h)){
					set.add(i.getValue());
				}
				return true;
			}
		}, null);
		return set;
	}
}