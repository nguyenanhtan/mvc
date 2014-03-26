package project.darp;

public class Location {

	private int local;
	Location()
	{
		this.local = 0;
	}
	Location(int local)
	{
		this.local = local;
	}
	public void setLocal(int local)
	{
		this.local = local;
	}
	public int getLocal()
	{
		return this.local;
	}
}
