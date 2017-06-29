package MainProc;
import Framework.*;
import components.*;
import lejos.nxt.Sound;

public class MainProc {

	CTimer heartbeatTimer, shutdownTimer, errorTimer, standbyTimer;
	int lightTmp, sonicTmp;
	int errorCode=0;
	boolean inStandby = false;
	Hardware cHw = new Hardware();
	int lightMin = 40, lightMax=80, lightDelta;
	int sonicMin = 12, sonicDelta;
	int yellowZone=21, redZone=16;
	boolean invertRotation=false;
	boolean goBack=false;
	
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
		shutdownTimer = new CTimer(600000);
		errorTimer=new CTimer(3000);
		standbyTimer=new CTimer(1000);
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
		
		calcDelta();
		
		int light=cHw.light.readNormalizedValue();
		if(light<lightMin || light>lightMax || light<lightTmp-lightDelta)
			errorCode=3;
		else
			lightTmp=light;

		int sonic=cHw.sonic.getDistance();
		if(sonic<sonicMin || sonic<sonicTmp-sonicDelta)
			errorCode=2;
		else
			sonicTmp=sonic;

		if(cHw.touchLeft.isPressed() || cHw.touchRight.isPressed())
			errorCode=1;
		
		if(sonicTmp<redZone)
		{
			cHw.engine.setspeed(200);
		}
		else if(sonicTmp<yellowZone)
		{
			cHw.engine.setspeed(400);
		}
		else
		{
			cHw.engine.setspeed(800);
		}
		cHw.engine.forwards();
	}
	
	private void calcDelta()
	{
		double rpm =((double)cHw.engine.getSpeedEngineLeft()/(double)Integer.MAX_VALUE)*170.0;
		double speed=(2*Math.PI*0.028)*(rpm/60);
		sonicDelta=(int)((speed/1000)*1.05); //delta is 105% of way in T=1/1000s
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
		if(errorTimer.get()<2500)
			dodge();
		else if(errorTimer.get()==2999)
			Sound.twoBeeps();
	}
	
	private void lightError()
	{
		if(errorTimer.count())
			errorCode=0;
		dodge();
	}
	
	private void dodge()
	{
		if(errorTimer.get()%500==0)
			Sound.beep();
		if(cHw.touchLeft.isPressed())
			invertRotation=true;
		if(cHw.touchRight.isPressed() && invertRotation)
		{
			goBack=true;
			errorTimer.reset();
		}
		else if(cHw.touchRight.isPressed())
		{
			invertRotation=false;
			errorTimer.reset();
		}
		cHw.engine.setspeed(20);
		if(goBack)
		{
			if(errorTimer.get()<800)
			{
				goBack=false;
				cHw.engine.stop();
			}
			cHw.engine.backwards();
		}else
		{
			if(!invertRotation)
				cHw.engine.rotateLeft();
			else
				cHw.engine.rotateRight();
		}
	}
	
	private void standby()
	{
		cHw.engine.stop();
		if(cHw.isAlive() == 0)
		{
			inStandby=false;
			return;
		}
		if(standbyTimer.count())
			Sound.twoBeeps();
		if(shutdownTimer.count())
			Main.keepAlive = false;
	}
}
