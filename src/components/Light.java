package components;

import interfaces.ILight;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class Light implements ILight {

	private LightSensor light;
	
	public Light(SensorPort port) {
		light = new LightSensor(port);
	}
	
	@Override
	public int readNormalizedValue() {
		return light.readNormalizedValue();
	}

	@Override
	public int readValue() {
		return light.readValue();
	}

	@Override
	public void calibrateLow() {
		light.calibrateLow();
	}

	@Override
	public void calibrateHigh() {
		light.calibrateHigh();
	}

	@Override
	public boolean isAlive() {
		if(light.readValue() == 0)
		{
			return false;
		}
		return true;
	}

}
