package components;

import interfaces.IMotor;
import lejos.robotics.RegulatedMotor;
import lejos.nxt.NXTRegulatedMotor;
public class Engine implements IMotor {

	private RegulatedMotor EngineLeft;
	private RegulatedMotor EngineRight;
	
	public Engine(NXTRegulatedMotor m1, NXTRegulatedMotor m2)
	{
		EngineLeft = m1;  //Motor.A, Motor.B, Motor.C
		EngineRight = m2; //Motor.A, Motor.B, Motor.C
	}
	@Override
	public void forwards() {
		EngineLeft.backward();
		EngineRight.backward();
	}
	@Override
	public void backwards() {
		EngineLeft.forward();
		EngineRight.forward();
	}
	@Override
	public void stop() {
		EngineLeft.stop();
		EngineRight.stop();
	}
	@Override
	public int getSpeedEngineRight() {
		return EngineRight.getSpeed();
	}
	@Override
	public int getSpeedEngineLeft() {
		return EngineLeft.getSpeed();
	}
	@Override
	public void setSpeedLeft(int speed) {
		EngineLeft.setSpeed(speed);
	}
	@Override
	public void setSpeedRight(int speed) {
		EngineRight.setSpeed(speed);
	}
	@Override
	public void leftForwards() {
		EngineRight.backward();
	}
	@Override
	public void rightForwards() {
		EngineLeft.backward();
	}
	@Override
	public void leftBackwards() {
		EngineRight.forward();
	}
	@Override
	public void rightBackwards() {
		EngineLeft.forward();
	}
}
