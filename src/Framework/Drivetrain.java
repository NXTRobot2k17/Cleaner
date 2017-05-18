package Framework;

import interfaces.*;
import components.*;

public class Drivetrain {
	
	static IMotor[] motor={new Motor(0), new Motor(1)};
	
	public static void forward(int speed)
	{
		
	}
	
	public static void backward(int speed)
	{
		
	}
	
	public static void turn(double angle)
	{
		
	}
	
	public static void Stop()
	{
		
	}
	
	public static boolean heartbeat()
	{
		return false;
	}
	
	public static boolean check()
	{
		forward(1);
		int x=0;
		while(x<10)x++;
		backward(1);
		while(x>0)x--;
		Stop();
		return false;
	}
}
