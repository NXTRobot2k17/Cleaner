package MainProc;
import Framework.*;

public class MainProc {

	CTimer hbTimer;
	int[] values;
	int lightTmp, sonicTmp;
	
	public void init()
	{
		Sensors.check();
		Drivetrain.check();
		values=new int[2];
		values[0] = Sensors.get(Port.Light);
		values[1] = Sensors.get(Port.SonicWave);
		hbTimer=new CTimer(100);
	}
	
	public void run()
	{
		if(Sensors.get(Port.LeftBumper)==1 || Sensors.get(Port.RightBumper)==1)
		{
			
		}
		lightTmp = Sensors.get(Port.Light);
		sonicTmp = Sensors.get(Port.SonicWave);
		if((lightTmp-values[0]) > 1)
		{
			
		}
		if((values[1]-sonicTmp) > 1)
		{
			
		}
		values[0] = lightTmp;
		values[1] = sonicTmp;
		if(hbTimer.get())
		{
			if(!(Drivetrain.heartbeat() && Sensors.heartbeat()))
			{
				Drivetrain.Stop();
				Main.keepAlive = false;
			}
		}
	}
}
