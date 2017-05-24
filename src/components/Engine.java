package components;

import interfaces.IMotor;
import lejos.robotics.RegulatedMotor;
import lejos.nxt.NXTRegulatedMotor;
public class Engine implements IMotor {

	private RegulatedMotor Engine;
	
	public Engine(NXTRegulatedMotor m)
	{
		Engine = m;  //Motor.A, Motor.B, Motor.C
	}
	@Override
	public void forwards() {
		Engine.backward();
	}

	@Override
	public void backwards() {
		Engine.forward();
	}

	@Override
	public void setSpeed(int speed) {
		Engine.setSpeed(speed);
	}

	@Override
	public void getspeed() {
		Engine.getSpeed();
	}

	@Override
	public void stop() {
		Engine.stop();
	}
}
