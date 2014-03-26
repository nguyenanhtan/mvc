package project.darp;
import java.util.Map;
import java.util.TreeMap;


public class Require {
	private Map<String, Time> timewindow;
	private int duarationPickup;
	private int duarationDeliver;
	private int weight;
	private int pickup;
	private int deliver;
	private int Ep;
	private int Lp;
	private int Ed;
	private int Ld;
	
	Require()
	{
		timewindow = new TreeMap<String, Time>();
		setTimewindow(new Time(), new Time());
		setDuarationPickup(0);
		setDuarationDeliver(0);
		setWeight(0);
		setPickup(0);
		setDeliver(0);
	}
	Require(Map<String, Time> timewindow,int duarationPickup,int duarationDeliver,int loaded, int pickup, int deliver)
	{
		this.timewindow.putAll(timewindow);
		this.duarationPickup = duarationPickup;
		this.duarationDeliver = duarationDeliver;
		this.weight = loaded;
		this.pickup = pickup;
		this.deliver = deliver;
	}
	public void setEp(int ep)
	{
		this.Ep = ep;
	}
	public void setEd(int ep)
	{
		this.Ed = ep;
	}
	public void setLp(int ep)
	{
		this.Lp = ep;
	}
	public void setLd(int ep)
	{
		this.Ld = ep;
	}
	
	public int getEd()
	{
		return this.Ed;
	}
	public int getEp()
	{
		return this.Ep;
	}
	public int getLd()
	{
		return this.Ld;
	}
	public int getLp()
	{
		return this.Lp;
	}
	
	public void setTimewindow(Time e, Time l)
	{
		timewindow.put("e", e);
		timewindow.put("l", l);
	}
	public Map<String, Time> getTimewindow()
	{
		return timewindow;
	}
	public void setDuarationPickup(int duaration)
	{
		this.duarationPickup = duaration;
	}
	public int getDuarationPickup()
	{
		return this.duarationPickup; 
	}
	public void setDuarationDeliver(int duaration)
	{
		this.duarationDeliver = duaration;
	}
	public int getDuarationDeliver()
	{
		return this.duarationDeliver; 
	}
	public void setWeight(int loaded)
	{
		this.weight = loaded;
	}
	public int getWeight()
	{
		return this.weight;
	}
	public void setPickup(int p)
	{
		this.pickup = p;
	}
	public int getPickup()
	{
		return this.pickup;
	}
	public void setDeliver(int d)
	{
		this.deliver = d;
	}
	public int getDeliver()
	{
		return this.deliver;
	}
}
