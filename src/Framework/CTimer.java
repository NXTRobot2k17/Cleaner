package Framework;

public class CTimer {
	
	int startValue, value;
	
	public CTimer(int value)
	{
		startValue=value;
		this.value=value;
	}
	
	public boolean count()
	{
		if(value==0)
			value=startValue;
		--value;
		return 0 == value;
	}
	
	public int get()
	{
		return value;
	}
	
	public void reset()
	{
		value=startValue;
	}
}
