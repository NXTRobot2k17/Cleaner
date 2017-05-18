package Framework;

import lejos.nxt.SensorPort;
import MainProc.*;
import interfaces.*;

public class Main {
	static MainProc proc=new MainProc();
	public static boolean keepAlive=true;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		proc.init();
		while(keepAlive)
			proc.run();
	}

}
