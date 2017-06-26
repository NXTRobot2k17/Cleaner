package MainProc;
import Framework.*;
import components.*;
import lejos.nxt.Sound;

public class MainProc {

	CTimer heartbeatTimer, shutdownTimer, errorTimer;
	int lightTmp, sonicTmp;
	int errorCode=0;
	boolean inStandby = false;
	Hardware cHw = new Hardware();
	
	public void init()
	{
		if(cHw.isAlive() != 0)
		{
			standby();
			return;
		}
		lightTmp=cHw.light.readValue();
		sonicTmp=cHw.sonic.getDistance();
		heartbeatTimer = new CTimer(100);
		shutdownTimer = new CTimer(300000);
		errorTimer=new CTimer(1000);
	}
	
	public void run()
	{
		if(inStandby)
		{
			standby();
			return;
		}
		shutdownTimer.reset();
		if(heartbeatTimer.count())
		{
			if(cHw.isAlive()!=0)
			{
				inStandby=true;
				return; 				
			}
		}
		switch(errorCode)
		{
		case 0:
			break;
		case 1:
			bumperError();
			return;
		case 2:
			sonicError();
			return;
		case 3:
			lightError();
			return;
		}
		errorTimer.reset();
	}
	
	private void bumperError()
	{
		if(errorTimer.count())
			Sound.twoBeeps();
	}
	
	private void sonicError()
	{
		if(errorTimer.count())
			errorCode=0;
		cHw.engine.setspeed(20);
		cHw.engine.rotateLeft();
	}
	
	private void lightError()
	{
		if(errorTimer.get()==1000)
			Sound.beep();
		if(errorTimer.count())
			errorCode=0;
		cHw.engine.setspeed(20);
		if(errorTimer.get()>900)
			cHw.engine.backwards();
		cHw.engine.rotateLeft();
	}
	
	private void standby()
	{
		if(cHw.isAlive() == 0)
		{
			inStandby=false;
			return;
		}
		if(shutdownTimer.count())
			Main.keepAlive = false;
	}
}
