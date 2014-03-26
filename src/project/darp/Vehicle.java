package project.darp;

public class Vehicle {

	private int id;
	private int capacity;
	private int loaded;
	public static int Q;//Trọng tải của xe
	public static int v = 20;//vận tốc của xe
	Vehicle()
	{
		this.id = 0;
		this.capacity = 0;
		this.loaded = 0;
	}
	Vehicle(int id, int capacity, int loaded)
	{
		this.id = id;
		this.capacity = capacity;
		this.loaded = loaded;
	}
	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
	}
	public int getCapacity()
	{
		return this.capacity;
	}
	
	public void setLoaded(int loaded)
	{
		this.loaded = loaded;
	}
	public int getLoaded()
	{
		return this.loaded;
	}
}
