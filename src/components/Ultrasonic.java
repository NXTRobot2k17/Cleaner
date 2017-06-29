package components;

import interfaces.IUltrasonic;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class Ultrasonic implements IUltrasonic {

	/*
	 * max 170 cm
	 * bei keinem echo gibt getDistance 255 zurück
	 */
	private UltrasonicSensor sonar;
	public Ultrasonic(SensorPort port) {
		sonar = new UltrasonicSensor(port);
	}
	@Override
	public int getDistance() {
		return sonar.getDistance();
	}
	@Override
	public boolean isAlive() {
		if(sonar.getActualMode() == -3)
		{
			return false;
		}
		return true;
	}
}
