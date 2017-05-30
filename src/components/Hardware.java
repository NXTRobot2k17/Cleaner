package components;

import interfaces.iHardware;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class Hardware  implements iHardware{

	public Light light;
	public Touch touch;
	public Ultrasonic sonic;
	public Engine engine;
	
	public Hardware(SensorPort lp, SensorPort tp, SensorPort sp, NXTRegulatedMotor left, NXTRegulatedMotor right) {
		light = new Light(lp);
		touch = new Touch(tp);
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
			engine.stop();
			return 3;
		}
		if(light.isAlive() == false)
		{
			engine.stop();

			return 1;
		}
		if(sonic.isAlive() == false)
		{
			engine.stop();
			return 2;
		}
		return 0;
	}

}
