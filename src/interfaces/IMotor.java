package interfaces;

public interface IMotor {
	public void forwards();
	public void backwards();
	public void setSpeed(int speed);
	public void getspeed();
	public void stop();
	public void setport();
	public boolean isAlive();
}
