package MainProc;
import Framework.*;
import components.*;

public class MainProc {

	CTimer heartbeatTimer, shutdownTimer;
	int[] values;
	int lightTmp, sonicTmp;
	boolean inStandby = false;
	Hardware cHw = new Hardware();
	
	public void init()
	{
		if(cHw.isAlive() != 0)
		{
			standby();
			return;
		}
		values = new int[2];
		heartbeatTimer = new CTimer(100);
		shutdownTimer = new CTimer(300000);
	}
	
	public void run()
	{
		if(inStandby)
		{
			standby();
			return;
		}
		if(heartbeatTimer.get())
		{
			if(cHw.isAlive()!=0)
			{
				inStandby=true;
				return; 				
			}
		}
		sonicTmp=cHw.sonic.getDistance();
		if(sonicTmp < (Sensors.sonicMin + Sensors.sonicDelta))
		{
			cHw.engine.stop();
		}
		lightTmp=cHw.light.readValue();
		if(lightTmp>Sensors.lightMax)
		{
			cHw.engine.stop();
		}
	}
	
	private void standby()
	{
		if(cHw.isAlive() == 0)
		{
			inStandby=false;
			return;
		}
		if(shutdownTimer.get())
			Main.keepAlive = false;
	}
}
