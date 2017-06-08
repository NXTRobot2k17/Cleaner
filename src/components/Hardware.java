package components;

import interfaces.iHardware;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class Hardware  implements iHardware{
	private DisplayPackage dp;
	public Light light;
	public Touch touchLeft;
	public Touch touchRight;
	public Ultrasonic sonic;
	public Engine engine;
	public Display lcd = new Display();
	
	public Hardware(SensorPort lp, SensorPort t1p, SensorPort t2p, SensorPort sp, NXTRegulatedMotor left, NXTRegulatedMotor right) {
		light = new Light(lp);
		touchLeft = new Touch(t1p);
		touchRight = new Touch(t2p);
		sonic = new Ultrasonic(sp);
		engine = new Engine(left, right);
	}
	/* (non-Javadoc)
	 * @see interfaces.iHardware#isAlive()
	 */
	@Override
	public int isAlive() {
		if(light.isAlive() && sonic.isAlive() == false)
		{
			dp.sonarInfo = "Sonar: failed";
			dp.sonicInfo = "Sonic: failed";
			lcd.update(dp);
			engine.stop();
			return 3;
		}
		if(light.isAlive() == false)
		{
			dp.sonarInfo = "Sonar: failed";
			lcd.update(dp);
			engine.stop();
			return 1;
		}
		if(sonic.isAlive() == false)
		{
			dp.sonicInfo = "Sonic: failed";
			lcd.update(dp);
			engine.stop();
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
