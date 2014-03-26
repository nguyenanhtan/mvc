package project.darp;

public class Problem {

	//Output var
	private int [] successor;//s
	private float[] load;//l -- states the vehicle load just after performing the pickup or delivery at vertex i
	private int[] servertex;//v --  = the vehicle serving vertex i
	private Time[] timeserved;//t -- 
	
	//Input var
	private Require []require;
	private Vehicle[] vehicle;
	private MapTransport maptransport;
	Problem(Require []require,Vehicle[] vehicle,MapTransport maptransport)
	{
		this.require = require;
		this.vehicle = vehicle;
		this.maptransport = maptransport;
	}
	public int [] getSuccessor()
	{
		return successor;
	}
	public float[] getLoad()//states the vehicle load just after performing the pickup or delivery at vertex i
	{
		return load;
	}
	public int[] getServertex()// = the vehicle serving vertex i
	{
		return servertex;
	}
	public Time[] getTimeserved()
	{
		return timeserved;
	}
}
