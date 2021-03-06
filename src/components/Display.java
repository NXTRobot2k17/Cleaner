package components;

import interfaces.IDisplay;
import lejos.nxt.LCD;

public class Display implements IDisplay {
	

	@Override
	public void update(DisplayPackage p) {
		LCD.clearDisplay();
		LCD.drawString(p.sonicInfo, 0, 0);
		LCD.drawString(p.sonarInfo, 0, 1);
		LCD.drawString(p.otherinfo, 0, 2);
		LCD.drawString(p.errorcode, 0, 3);
	}

}
