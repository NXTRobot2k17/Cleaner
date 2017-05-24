package Framework;

import lejos.util.*;
import MainProc.*;

public class HwTimer implements TimerListener
{
	MainProc proc;
	
	public HwTimer(MainProc proc)
	{
		this.proc = proc;
	}
	
	@Override
	public void timedOut() {
		// TODO Auto-generated method stub
		proc.run();
	}

}
