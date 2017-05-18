package Framework;

import interfaces.*;
import components.*;

public class Sensors {
	
	static ISensor[] sensors=new ISensor[4];
	
	public static void check()
	{
		
	}
	
	public static int get(Port s)
	{
		switch(s)
		{
			case LeftBumper:
				sensors[0].getData();
			case RightBumper:
				sensors[1].getData();
			case Light:
				sensors[2].getData();
			case SonicWave:
				sensors[3].getData();
		}
		return -1;
	}
	
	public static boolean heartbeat()
	{
		return 	sensors[0].heartbeat() && 
				sensors[1].heartbeat() && 
				sensors[2].heartbeat() && 
				sensors[3].heartbeat();
	}
}
