package components;

import interfaces.iHardware;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class Hardware  implements iHardware{
	private DisplayPackage dp = new DisplayPackage();
	public Light light;
	public Touch touchLeft;
	public Touch touchRight;
	public Ultrasonic sonic;
	public Engine engine;
	public Display lcd = new Display();
	
	public Hardware() {
		light = new Light(SensorPort.S3);
		touchLeft = new Touch(SensorPort.S1);
		touchRight = new Touch(SensorPort.S2);
		sonic = new Ultrasonic(SensorPort.S4);
		engine = new Engine(Motor.A, Motor.C);
	}
	/* (non-Javadoc)
	 * @see interfaces.iHardware#isAlive()
	 */
	@Override
	public int isAlive() {
		if((!light.isAlive() && !sonic.isAlive()) == true)
		{
			dp.sonarInfo = "Sonar: failed";
			dp.sonicInfo = "Sonic: failed";
			lcd.update(dp);
			//engine.stop();
			return 3;
		}
		if(light.isAlive() == false)
		{
			dp.sonarInfo = "Sonar: failed";
			lcd.update(dp);
			//engine.stop();
			return 1;
		}
		if(sonic.isAlive() == false)
		{
			dp.sonicInfo = "Sonic: failed";
			lcd.update(dp);
			//engine.stop();
			return 2;
		}
		dp.sonarInfo = "Sonar: OK";
		dp.sonicInfo = "Sonic: OK";
		dp.MotorLeft = "Motor: OK";
		dp.Motorright = "Motor: OK";
		lcd.update(dp);
		return 0;
	}

}
