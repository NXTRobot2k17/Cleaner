package interfaces;

import lejos.*;

public interface ISensor {
	boolean heartbeat();
	void getData();
	void setPort(lejos.nxt.SensorPort port);
}
