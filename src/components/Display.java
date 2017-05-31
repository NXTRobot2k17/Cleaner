package components;

import interfaces.IDisplay;
import lejos.nxt.LCD;

public class Display implements IDisplay {
	

	@Override
	public void update(DisplayPackage p) {
		LCD.drawString("Sonic " + p.sonicInfo, 0, 0);
		LCD.drawString("Sonar" + p.sonarInfo, 0, 1);
		LCD.drawString("MotorLeft " + p.MotorLeft, 0, 2);
		LCD.drawString("MotorRight " + p.Motorright, 0, 3);		
	}

}
