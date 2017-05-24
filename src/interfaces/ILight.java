package interfaces;

public interface ILight extends ISensor {

	public int readNormalizedValue();
	public int readValue();
	public void calibrateLow();
	public void calibrateHigh();
}
