package MainProc;
import Framework.*;
import components.*;
import lejos.nxt.Sound;

public class MainProc {
	CTimer heartbeatTimer, shutdownTimer, errorTimer, standbyTimer;
	int lightTmp=0, sonicTmp=0;
	int errorCode;
	boolean inStandby = false;
	Hardware cHw = new Hardware();
	int lightMin = -20, lightMax=100, lightDelta;
	int[] light;
	int lightCounter;
	int[] sonic;
	int sonicCounter;
	int sonicMin = 16;
	double sonicDelta;
	int yellowZone=50, redZone=20;
	boolean invertRotation=false;
	boolean goBack=false;
	
	public void init()
	{
		if(cHw.isAlive() != 0)
		{
			standby();
			return;
		}
		heartbeatTimer = new CTimer(100);
		shutdownTimer = new CTimer(600000);
		errorTimer=new CTimer(3000);
		standbyTimer=new CTimer(1000);
		errorCode=0;
		light=new int[64];
		lightCounter=0;
		sonic=new int[32];
		sonicCounter=0;
	}
	
	public void run()
	{
		if(inStandby)
		{
			standby();
			return;
		}else		
			shutdownTimer.reset();
		if(heartbeatTimer.count())
		{
			if(cHw.isAlive()!=0)
			{
				inStandby=true;
				return; 				
			}
		}
		cHw.error(errorCode);
		switch(errorCode)
		{
		case 0:
			errorTimer.reset();
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
		
		
		light[lightCounter%64] = cHw.light.readValue();
		lightCounter++;
		if(lightCounter>=64)
		{
			int lightValue=0;
			for(int i=0; i<64; i++)
				lightValue+=light[i];
			lightValue/=64;
			if(lightValue<lightMin || lightValue>lightMax)
				errorCode=3;
			else
				lightTmp=lightValue;
		}
		
		sonic[sonicCounter%32] = cHw.sonic.getDistance();
		sonicCounter++;
		if(sonicCounter>=32)
		{
			int sonicValue=0;
			for(int i=0; i<32; i++)
				sonicValue+=sonic[i];
			sonicValue/=32;
			if(sonicValue<yellowZone && sonicValue<sonicTmp-25 && sonicTmp!=0)
				errorCode=2;
			else if(sonicValue<sonicMin && errorCode == 0)
				errorCode=2;
			else
				sonicTmp=sonicValue;
		}
		
		if(cHw.touchLeft.isPressed() || cHw.touchRight.isPressed())
			errorCode=1;
		if(sonicTmp==0)
			return;
		if(sonicTmp<redZone)
		{
			cHw.engine.setspeed(100);
		}
		else if(sonicTmp<yellowZone)
		{
			cHw.engine.setspeed(250);
		}
		else
		{
			cHw.engine.setspeed(500);
		}
		cHw.engine.forwards();
	}
	
	private void calcDelta()
	{
		int dps=cHw.engine.getSpeedEngineLeft();
		double rps=dps/360.0;
		sonicDelta=(((2*Math.PI*0.028*rps)/100)*(cHw.engine.getSpeedEngineLeft()/10));
	}
	
	private void bumperError()
	{
		cHw.engine.stop();
		if(errorTimer.count() || errorTimer.get()%500==0)
			Sound.twoBeeps();
	}
	
	private void sonicError()
	{
		cHw.engine.stop();
		if(errorTimer.count())
			errorCode=0;
		else if(errorTimer.get()<2500)
		{
			int sonic=cHw.sonic.getDistance();
			if(sonic>sonicTmp && errorTimer.get()==2499)
			{
				errorCode=0;
				return;
			}
			dodge();
		}
		else if(errorTimer.get()==2999)
			Sound.twoBeeps();
	}
	
	private void lightError()
	{
		if(errorTimer.count())
			errorCode=0;
		else
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
		cHw.engine.setspeed(100);
		if(goBack)
		{
			if(errorTimer.get()<800)
			{
				goBack=false;
				invertRotation=false;
				cHw.engine.stop();
			}
			cHw.engine.backwards();
		}else
		{
			if(!invertRotation)
				cHw.engine.rotateRight();
			else
				cHw.engine.rotateLeft();
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
