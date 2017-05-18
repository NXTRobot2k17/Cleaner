package Framework;

public class CTimer {
	
	int startValue, value;
	
	public CTimer(int value)
	{
		startValue=value;
		this.value=value;
	}
	
	public boolean get()
	{
		if(value==0)
			value=startValue;
		return 0 == value--;
	}
	
	public void reset()
	{
		value=startValue;
	}
}
