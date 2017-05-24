package Framework;

import MainProc.*;
import lejos.util.*;

public class Main 
{
	static MainProc proc=new MainProc();
	static TimerListener listener = new HwTimer(proc);
	public static boolean keepAlive=true;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		proc.init();
		Timer timer=new Timer(1, listener);
		timer.start();
		while(keepAlive){}
		timer.stop();
	}

}
