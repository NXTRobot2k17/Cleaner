package Framework;

import interfaces.*;
import components.*;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;

public class Drivetrain {
	
	static IMotor[] motor = 
		{new Engine(new NXTRegulatedMotor(MotorPort.A))
		,new Engine(new NXTRegulatedMotor(MotorPort.B))}
	;
	
	public static void forward()
	{
		motor[0].forwards();
		motor[1].forwards();
	}
	
	public static void backward()
	{
		motor[0].backwards();
		motor[1].backwards();
	}
	
	public static void setSpeed(int speed)
	{
		motor[0].setSpeed(speed);
		motor[1].setSpeed(speed);
	}
	
	public static void turn(double angle)
	{
		
	}
	
	public static void turn(double angle, double radius)
	{
		
	}
	
	public static void Stop()
	{
		motor[0].stop();
		motor[1].stop();
	}
	
	public static boolean heartbeat()
	{
		return false;
	}
	
}
