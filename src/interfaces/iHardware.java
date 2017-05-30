package interfaces;

public interface iHardware {

	
	/**
	 * @return 0 everything is available, 1 light is dead, 2 sonic is dead, 3 light and sonic is dead
	 */
	public int isAlive();
	
}
