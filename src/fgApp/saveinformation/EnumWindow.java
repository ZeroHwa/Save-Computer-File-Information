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
		IntByReference i=new IntByReference();//放PID
		user32.EnumWindows(new User32.WNDENUMPROC() {
			//h为应用句柄，p为回调的数据
			public boolean callback(HWND h, Pointer p) {
				//此函数检索创建指定窗口的线程的标识符，并可选地检索创建窗口的进程的标识符。
				//h为应用句柄,i将指针返回
				user32.GetWindowThreadProcessId(h, i);//获取窗口的PID
				
				//如果句柄为h的应用是可视化的能动的窗口，则将i的值返回到set中
				if(user32.IsWindow(h)&&user32.IsWindowEnabled(h)&&user32.IsWindowVisible(h)){
					set.add(i.getValue());
				}
				return true;
			}
		}, null);
		return set;
	}
}