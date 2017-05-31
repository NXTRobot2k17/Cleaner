package interfaces;

public interface IMotor extends ISensor {
	public void forwards();
	public void backwards();
	public void leftForwards();
	public void rightForwards();
	public void leftBackwards();
	public void rightBackwards();
	public int getSpeedEngineRight();
	public int getSpeedEngineLeft();
	public void stop();
	public void setSpeedLeft(int speed);
	public void setSpeedRight(int speed);
	public void setspeed(int speed);
	public void rotateLeft();
	public void rotateRight();
}
