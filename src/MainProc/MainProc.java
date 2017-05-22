package MainProc;
import Framework.*;

public class MainProc {

	CTimer heartbeatTimer, shutdownTimer;
	int[] values;
	int lightTmp, sonicTmp;
	boolean inStandby=false;
	
	public void init()
	{
		if(!checkComponents())
		{
			standby();
			return;
		}
		inStandby = false;
		values = new int[2];
		values[0] = Sensors.get(Port.Light);
		values[1] = Sensors.get(Port.SonicWave);
		heartbeatTimer = new CTimer(100);
		shutdownTimer = new CTimer(300000);
	}
	
	public void run()
	{
		if(inStandby)
		{
			init();
			return;
		}
		

		if(heartbeatTimer.get())
		{
			if(!(Drivetrain.heartbeat() && Sensors.heartbeat()))
			{
				standby();
			}
		}
	}
	
	private boolean checkComponents()
	{
		return false;
	}
	
	private void standby()
	{
		inStandby = true;
		Drivetrain.Stop();
		if(shutdownTimer.get())
			Main.keepAlive = false;
	}
}
