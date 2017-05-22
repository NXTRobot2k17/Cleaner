package components;

import interfaces.ITouch;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class Touch implements ITouch {

	private TouchSensor sensor;
	
	public Touch(SensorPort port)
	{
		sensor = new TouchSensor(port);
	}

	@Override
	public boolean isPressed() {
		return sensor.isPressed();
	}

	

}
