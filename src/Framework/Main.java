package Framework;

import MainProc.*;

public class Main {
	static MainProc proc=new MainProc();
	public static boolean keepAlive=true;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		proc.init();
		while(keepAlive)
			proc.run();
		//use timer for 1kHz rate
	}

}
