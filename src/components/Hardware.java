package components;

import interfaces.iHardware;
import lejos.nxt.SensorPort;

public class Hardware  implements iHardware{

	public Light light;
	public Touch touch;
	public Ultrasonic sonic;
	
	public Hardware(SensorPort lp, SensorPort tp, SensorPort sp) {
		light = new Light(lp);
		touch = new Touch(tp);
		sonic = new Ultrasonic(sp);
	}
	/* (non-Javadoc)
	 * @see interfaces.iHardware#isAlive()
	 */
	@Override
	public int isAlive() {
		if(light.isAlive() && sonic.isAlive() == false)
		{
			return 3;
		}
		if(light.isAlive() == false)
		{
			return 1;
		}
		if(sonic.isAlive() == false)
		{
			return 2;
		}
		return 0;
	}

}
