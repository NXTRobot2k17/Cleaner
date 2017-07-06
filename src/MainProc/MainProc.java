package MainProc;

import Framework.*;
import components.*;
import lejos.nxt.Button;
import lejos.nxt.Sound;

public class MainProc {
	CTimer heartbeatTimer, shutdownTimer, errorTimer, standbyTimer, waitTimer;
	Hardware cHw = new Hardware();
	int lightTmp=0, sonicTmp=0;
	int errorCode;
	int lightMin = -20, lightMax=100, lightDelta;
	int[] light;
	int lightCounter;
	int[] sonic;
	int sonicCounter;
	int sonicMin = 25;
	int sonicValue=0;
	int sonicTmpValue=0;
	int sonicPreErrorValue=255;
	int yellowZone=90, redZone=35;
	int turnCounter=0;
	double sonicDelta;
	boolean inStandby = false;
	boolean invertRotation=false;
	boolean goBack=false;
	boolean wait;
	boolean dodge=false;
	boolean userConfirm=false;
	Zone zone;
	
	enum Zone{red, yellow, green}
	
	public void init()
	{
		if(cHw.isAlive() != 0)
		{
			standby();
			return;
		}
		Sound.beep();
		heartbeatTimer = new CTimer(10);
		shutdownTimer = new CTimer(600000);
		errorTimer=new CTimer(3000);
		standbyTimer=new CTimer(1000);
		waitTimer = new CTimer(100);
		errorCode=0;
		light=new int[32];
		lightCounter=0;
		sonic=new int[32];
		sonicCounter=0;
		wait=false;
		zone = Zone.red;
	}
	
	public void run()
	{
		if(inStandby)
		{
			standby();
			return;
		}else		
			shutdownTimer.reset();

		if(Button.ENTER.isDown() && cHw.sonic.getDistance()<yellowZone && sonicTmp==0 || userConfirm)
		{
			userConfirm=true;
		}
		else if(cHw.sonic.getDistance()<yellowZone && sonicTmp==0)
		{
			return;
		}
		
		if(sonicTmp==0 && sonicCounter==0 && !dodge)
			Sound.beepSequence();

		if(sonicTmp==0 && sonicCounter==1 && !dodge)
			Sound.beepSequenceUp();
		
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
		
		
		int value = cHw.light.readValue();
		if(value >= 130)
			if(cHw.isAlive()!=0)
			{
				inStandby=true;
				return;
			}
		if(value>50)
		{
			errorCode=3;
			errorTimer.reset();
		}
		else
		{
			light[lightCounter]=value;
			lightCounter++;
			lightCounter=lightCounter%32;
			if(lightCounter>=32)
			{
				int lightValue=0;
				for(int i=0; i<32; i++)
					lightValue+=light[i];
				lightValue/=32;
				if(lightValue<lightMin || lightValue>lightMax)
					errorCode=3;
				else
					lightTmp=lightValue;
			}
		}

		sonic[sonicCounter] = cHw.sonic.getDistance();
		sonicCounter++;
		sonicCounter = sonicCounter%32;
		if(sonic[31]!=0)
		{
			dodge=false;
			sonicValue=0;
			for(int i=0; i<32; i++)
				sonicValue+=sonic[i];
			sonicValue/=32;
			if(
					(sonic[(sonicCounter+32-2)%32]/2) > redZone 
					&& sonic[(sonicCounter+32-1)%32] < (sonic[(sonicCounter+32-2)%32]/2) 
					&& sonicTmp!=0 
					&& !wait
				)
			{
				Sound.beepSequence();
				zone = Zone.yellow;
				if(wait==false)
					waitTimer.reset();
				wait=true;
				errorCode=2;
				sonicPreErrorValue=sonic[(sonicCounter+32-2)%32];
				return;
			}
			if(sonicValue<sonicMin && errorCode == 0)
				errorCode=2;
			else
				sonicTmp=sonicValue;
		}
		
		if(cHw.touchLeft.isPressed() || cHw.touchRight.isPressed())
			errorCode=1;
		if(sonicTmp==0)
			return;

		cHw.engine.forwards();
		
		if(sonicTmp<redZone)
		{
			cHw.engine.setspeed(100);
			zone=Zone.red;
		}
		else if(sonicTmp<yellowZone)
		{
			cHw.engine.setspeed(200);
			if(zone == Zone.green)
			{
				wait=true;
				Sound.twoBeeps();
			}
			zone=Zone.yellow;
		}
		else
		{
			cHw.engine.setspeed(300);
			zone=Zone.green;
		}
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
		if(zone==Zone.yellow && wait)
		{
			if(waitTimer.count())
			{
				
				wait=false;
				int egress=cHw.sonic.getDistance();
				if(egress>yellowZone)
				{
					Sound.beepSequenceUp();
					sonicPreErrorValue=255;
					errorCode=0;
					return;
				}
			}
			else
			{
				cHw.engine.stop();
				return;
			}
			
		}
		if(errorTimer.count())
		{
			errorCode=0;
			return;
		}
		else if(errorTimer.get()<2900)
		{
			if(errorTimer.get()==2900)
			{
				sonicTmpValue=cHw.sonic.getDistance();
				if(sonicTmpValue>sonicTmp) 
				{
					errorCode=0;
					return;
				}
			}else
			{
				dodge();
			}
		}
		else if(errorTimer.get()==2999)
		{
			Sound.twoBeeps();
		}
		else
		{
			cHw.engine.stop();
		}
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
		dodge=true;
		if(errorTimer.get()%20==0)
			Sound.beep();
		if(cHw.touchLeft.isPressed() || cHw.touchRight.isPressed())
		{
			if(invertRotation)
				goBack=true;
			else
				goBack=false;
			invertRotation=!invertRotation;
			errorTimer.reset();
			turnCounter=0;
		}
		cHw.engine.setspeed(100);
		if(goBack)
		{
			cHw.engine.backwards();
			turnCounter++;
			if(turnCounter==100)
			{
				turnCounter=0;
				goBack=false;
				errorTimer.reset();
				invertRotation=false;
			}
		}else
		{
			if(invertRotation)
				cHw.engine.rotateLeft();
			else
				cHw.engine.rotateRight();
			turnCounter+=1;
		}
		if(turnCounter==75)
		{
			turnCounter=0;
			cHw.engine.stop();
			errorTimer.reset();
			errorCode=0;
			sonicCounter=0;
			sonicTmp=0;
			sonic[31]=0;
		}
	}

	private void standby()
	{
		userConfirm=false;
		cHw.engine.stop();
		if(Button.ENTER.isDown() && cHw.isAlive()==0)
		{
			errorCode=0;
			sonicCounter=0;
			sonicTmp=0;
			sonic[31]=0;
			inStandby=false;
			return;
		}
		if(standbyTimer.count())
			Sound.twoBeeps();
		if(shutdownTimer.count())
			Main.keepAlive = false;
	}
}
