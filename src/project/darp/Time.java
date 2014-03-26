package project.darp;

public class Time {

	private int day;
	private int hour;
	private int minnute;
	Time()
	{
		day = 0;
		hour = 0;
		minnute = 0;
	}
	Time(int day, int hour, int minute)
	{
		this.day = day;
		this.hour = hour;
		this.minnute = minute;
	}
	public int getDay()
	{
		return day;
	}
	public void setDay(int d)
	{
		this.day = d;
	}
	public int getHour()
	{
		return hour;
	}
	public void setHour(int hour)
	{
		this.hour = hour;
	}
	public int getMinute()
	{
		return minnute;
	}
	public void setMinute(int min)
	{
		this.minnute = min;
	}
}
