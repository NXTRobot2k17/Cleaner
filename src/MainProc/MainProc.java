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
		values[0] = Sensors.get(SensorEnum.Light);
		values[1] = Sensors.get(SensorEnum.SonicWave);
		hbTimer=new CTimer(100);
	}
	
	public void run()
	{
		if(Sensors.get(SensorEnum.LeftBumper)==1 || Sensors.get(SensorEnum.RightBumper)==1)
		{
			
		}
		lightTmp = Sensors.get(SensorEnum.Light);
		sonicTmp = Sensors.get(SensorEnum.SonicWave);
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
