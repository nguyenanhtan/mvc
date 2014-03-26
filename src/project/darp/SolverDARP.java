package project.darp;
import org.w3c.dom.*;

import javax.xml.parsers.*;

import java.io.File;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
public class SolverDARP {

	private int m,n;
	private int[] s, v, t, l,location;
	private MapGoogle map;
	private Require[] requires;
	private HashSet<Integer> setUnassignRequest;
	public static int Q = 10;
	public static int L = 100;
	
	public static void main(String[] args) {
		SolverDARP S = new SolverDARP();
		Solution Sol = S.LNSFFPA(100, 120);
		S.println("Rout cost: "+Sol.getRoutCost());
		S.println(Sol.getS());
	}
	public Solution LNSFFPA(int numiter, int timeLimt)
	{
		this.TreeSearch();
		Solution bestS = new Solution(this.s, this.v, this.t, getRoutingCost());
		Solution currentS = new Solution(this.s, this.v, this.t, getRoutingCost());
//		println("Rout cost: "+currentS.getRoutCost());
//		println(currentS.getS());
//		println("Rout cost: "+bestS.getRoutCost());
//		println(bestS.getS());
//		printfError("");
		Date startTime = new Date();
		boolean bol;
		for(int i = this.n;i > 1;i--)
		{
			for(int k = 0;k < numiter;k++)
			{
				relaxedSolution(i);
				bol = TreeSearch();
				if(bol == false)
				{
					continue;
				}
				int rCost = getRoutingCost();
				if(rCost < currentS.getRoutCost())
				{
					currentS.save(s, v, t, rCost);
					if(currentS.getRoutCost() < bestS.getRoutCost())
					{
						bestS.save(currentS.getS(), currentS.getV(), currentS.getT(), currentS.getRoutCost());
					}
				}
				if(isTimeOut(startTime, timeLimt))
				{
					return bestS;
				}
			}
		}
		println("Rout cost: "+currentS.getRoutCost());
		println(currentS.getS());
		println("Rout cost: "+bestS.getRoutCost());
		println(bestS.getS());
		return bestS;
	}
	private boolean isTimeOut(Date start, int maxSecond)
	{
		Date now = new Date();
		int nNow = now.getHours()*3600 + now.getMinutes()*60 + now.getSeconds();
		int nStart = start.getHours()*3600 + start.getMinutes()*60 + start.getSeconds();
		if(nNow - nStart < maxSecond)
		{
			return false;
		}
		return true;
	}
	private void relaxedSolution(int numR)
	{
		Random rand = new Random();
		int x;
		for(int i = 0; i < numR;i++)
		{
			x = rand.nextInt(n);
			if(!setUnassignRequest.contains(x))
			{
				removeRequire(x, getNodeBeforePickup(x), getNodeBeforeDeliver(x));
			}
		}
	}
	public SolverDARP(String Jmatrix) {
		// TODO Auto-generated constructor stub
//		map = new MapTransport("fileMap.drp");
		map = new MapGoogle(Jmatrix);
		readProblem();
		s = new int[2*(m+n)];
		v = new int[2*(m+n)];
		t = new int[2*(m+n)];
		l = new int[2*(m+n)];
		location = new int[2*(m+n)];
		setUnassignRequest = new HashSet<Integer>();
		for(int i = 0;i < n;i++)
		{
			setUnassignRequest.add(new Integer(i));
		}
		for(int i = 0;i < m;i++)
		{
			t[i] = 0;
			l[i] = 0;
			v[i] = i;
			s[i] = i + 2*n + m;
			location[i] = 0;
		}
		for(int i = 2*n+m;i < 2*(n+m);i++)
		{
			s[i] = i - 2*n - m;
			v[i] = i - 2*n - m;
			location[i] = 0;
			t[i] = 0;
			l[i] = 0;
		}
		for(int i = m;i < m+n;i++)
		{
			s[i] = -1;
			s[i+n] = -1;
			
			v[i] = -1;
			v[i+n] = -1;
			
			t[i] = -1;
			t[i+n] = -1;
			
			l[i] = -1;
			l[i+n] = -1;
			
			location[i] = requires[i-m].getPickup();
			location[i+n] = requires[i-m].getDeliver();			
		}
		println(s);
	}
	public SolverDARP() {
		// TODO Auto-generated constructor stub
//		map = new MapTransport("fileMap.drp");
//		map = new MapGoogle("fileMap.drp");
		readProblem();
		s = new int[2*(m+n)];
		v = new int[2*(m+n)];
		t = new int[2*(m+n)];
		l = new int[2*(m+n)];
		location = new int[2*(m+n)];
		setUnassignRequest = new HashSet<Integer>();
		for(int i = 0;i < n;i++)
		{
			setUnassignRequest.add(new Integer(i));
		}
		for(int i = 0;i < m;i++)
		{
			t[i] = 0;
			l[i] = 0;
			v[i] = i;
			s[i] = i + 2*n + m;
			location[i] = 0;
		}
		for(int i = 2*n+m;i < 2*(n+m);i++)
		{
			s[i] = i - 2*n - m;
			v[i] = i - 2*n - m;
			location[i] = 0;
			t[i] = 0;
			l[i] = 0;
		}
		for(int i = m;i < m+n;i++)
		{
			s[i] = -1;
			s[i+n] = -1;
			
			v[i] = -1;
			v[i+n] = -1;
			
			t[i] = -1;
			t[i+n] = -1;
			
			l[i] = -1;
			l[i+n] = -1;
			
			location[i] = requires[i-m].getPickup();
			location[i+n] = requires[i-m].getDeliver();			
		}
		println(s);
	}
	//Update after insert a unsigned request
	private void updateTimeservice(int request,int pickupAfterNode, int deliverAfterNode, boolean isInsert)
	{
		println(">>updateTimeservice<<");
		if(request > n || pickupAfterNode > m + 2*n || deliverAfterNode > m + 2 * n)
		{
			printfError("ERROR IN FUNCTION updateTimeservice: Value invalid");
		}
		if(s[request + m] < 0)
		{
			printfError("ERROR IN FUNCTION updateTimeservice: Please update s array before update time");
		}
		int deltaP = (int)getDeltaTimeService(request, pickupAfterNode, true);
		if(isInsert)
		{
			//Update after insert
			println("pickupAfterNode = "+pickupAfterNode);
			println("deliverNodeAfter = "+deliverAfterNode);
			println("m = "+m);
			println("request = "+request);
			if(pickupAfterNode < m)
			{
				t[request + m] = t[pickupAfterNode] + map.getT(location[pickupAfterNode], location[request + m]);
			}
			else
			if(pickupAfterNode < m + n)
			{
				t[request + m] = t[pickupAfterNode] + requires[pickupAfterNode - m].getDuarationPickup() + map.getT(location[pickupAfterNode], location[request + m]);
			}
			else
			{
				t[request + m] = t[pickupAfterNode] + requires[pickupAfterNode - m - n].getDuarationDeliver() + map.getT(location[pickupAfterNode], location[request + m]);
			}
			
			if(deliverAfterNode < m + n)
			{
				println(location[deliverAfterNode]+";"+ location[request + m + n]);
				t[request + m + n] = t[deliverAfterNode] + requires[deliverAfterNode - m].getDuarationPickup() + map.getT(location[deliverAfterNode], location[request + m + n]);
			}
			else
			{
				t[request + m + n] = t[deliverAfterNode] + requires[deliverAfterNode - m - n].getDuarationDeliver() + map.getT(location[deliverAfterNode], location[request + m + n]);
			}
		}
		else
		{
			//Update after remove
			t[request + m] = -1;
			t[request + m + n] = -1;
		}
		int currentNode = s[request + m];
		if(!isInsert)
		{
			deltaP = -deltaP;
		}
		while(currentNode != request + m + n)
		{
			t[currentNode] += deltaP;
			currentNode = s[currentNode];
		}
		int deltaD = (int)getDeltaTimeService(request, deliverAfterNode, false);
		if(!isInsert)
		{
			deltaD = -deltaD;
		}
		currentNode = s[request + m + n];
		while(currentNode < m + 2*n)
		{
			t[currentNode] += (deltaP + deltaD);
			currentNode = s[currentNode];
		}
		t[currentNode] += (deltaP + deltaD);
		
		
	}
	private void updateLoaded(int request,int pickupAfterNode, int deliverAfterNode)
	{
		println(">>updateLoaded<<");
		l[request + m] = l[pickupAfterNode] + requires[request].getWeight();
		int currentNode = request + m;
		int beforCurrent = 0;
		while(currentNode != request + m + n)
		{
			l[s[currentNode]] = l[currentNode] + requires[request].getWeight();
			beforCurrent = currentNode;
			currentNode = s[currentNode];
		}
		l[request + m + n] = l[beforCurrent] - requires[request].getWeight();
	}
	private float getDeltaTimeService(int request,int afterNode,boolean isPickup)//request < n
	{		
		println(">>getDeltaTimeService<<");
		if(request >= n || afterNode >= m + 2*n)
		{
			printfError("Error in getDeltaTimeService: Please check value variable!");
			return 0;
		}
		if(isPickup)
		{
			return 
				map.getT(location[afterNode], location[request + m])+
				map.getT(location[request + m], location[s[afterNode]])+
				requires[request].getDuarationPickup()-
				map.getT(location[afterNode], location[s[afterNode]]);
		}else
		{
			return 
					map.getT(location[afterNode], location[request + m + n])+
					map.getT(location[request + m + n], location[s[afterNode]])+
					requires[request].getDuarationDeliver()-
					map.getT(location[afterNode], location[s[afterNode]]);
		}
		
	}
	//Update after remove a unsigned request
	private void updateTimeservice(int request)
	{
		
	}
	private void updateLoaded(int request)
	{
		println(">>updateLoaded<<");
		if(s[request + m] < 0)
		{
			printfError("ERROR: Please update before modify value s[request + m]");
		}
		int currentNode = s[request + m];
		
		while(currentNode != request + m + n)
		{
			l[currentNode] = l[currentNode] - requires[request].getWeight();	
			currentNode = s[currentNode];
		}
	}
	
	private boolean TreeSearch()
	{
		println(">>TreeSearch<<");
		if(setUnassignRequest.size() == 0)
		{
			if(isSolution())
			{
				return true;
			}else
			{
				return false;
			}
		}
		HashMap<Integer, ArrayList<int[]>> map = getUnassignedRequest();
		if(map == null)
		{
			printfError("ERROR: in Treesearch function");
		}
		Iterator it = map.entrySet().iterator();
		Map.Entry<Integer, ArrayList<int[]>> mapEntry = null;
		if(it.hasNext())
		{
			mapEntry = (Map.Entry)it.next();			
		}
		for(int[] x:mapEntry.getValue())
		{			
			insertRequire(mapEntry.getKey(), x[0], x[1]);
			TreeSearch();
			if(isSolution())
			{
				return true;
			}
			removeRequire(mapEntry.getKey(), x[0], x[1]);
		}
		return false;
	}
	private boolean isSolution()
	{
		println(">>isSolution<<");
		if(getViolation(1, 1) > 0)
		{
			return false;
		}
		for(int i = m;i < m + 2*n;i++)
		{
			if(s[i] < 0)
			{
				return false;
			}
		}
		return true;
	}
	private HashMap<Integer, ArrayList<int[]>> getUnassignedRequest()
	{
		println(">>getUnassignedRequest<<");
		HashMap<Integer, ArrayList<int[]>> map = new HashMap<Integer, ArrayList<int[]>>();
		TreeMap<Integer, Integer> mapSizeR = new TreeMap<Integer, Integer>();
//		if(setUnassignRequest.isEmpty())
//		{
//			println("Un Assigned Request is Empty");
//		}
		HashSet<Integer> SUR = new HashSet<Integer>();
		SUR.addAll(setUnassignRequest);
		for(int i:SUR)
		{
			println(setUnassignRequest);
			ArrayList<int[]> tmpPoint = new ArrayList<int[]>();
			for(int j = 0; j < m;j ++)
			{
				tmpPoint.addAll(getPointInsertIntoRout(i, j));
			}
			map.put(i, tmpPoint);
			mapSizeR.put(tmpPoint.size(),i);
		}
		Iterator it = mapSizeR.entrySet().iterator();
		if(it.hasNext())
		{
			Map.Entry<Integer, Integer> mapEntry = (Map.Entry)it.next();			
			HashMap<Integer, ArrayList<int[]>> rt = new HashMap<Integer, ArrayList<int[]>>();//
			rt.put(mapEntry.getValue(),map.get(mapEntry.getValue()));
			
			return rt;
		}
		else
		{
			printfError("ERROR: getUnAssignRequest");
		}
		return null;
	}
	private ArrayList<int[]> getPointInsertIntoRout(int r, int rout)//r C# [0, n]
	{
		println(">>getPointInsertIntoRout<<");
		if(r < 0 || r > n)
		{
			printfError("ERROR: Value r is invalid!");
		}
		if(rout < 0 || rout>m)
		{
			printfError("ERROR: Value route is invalid!");
		}
		ArrayList<int[]> arrInsPt = new ArrayList<int[]>();
		int point1 = rout;
		int point2 = s[point1];
		while(true)
		{
			if(point1 >= m + 2*n)
			{
				break;
			}
			//println(">>>>>>>>>>>>>>>>>>>>>>>>");
			insertRequire(r, point1, r+m);
			if(getViolation(1, 1) == 0)
			{
				removeRequire(r, point1, r+m);
				int[] tmp = new int[2];
				tmp[0] = point1;
				tmp[1] = r + m;
				arrInsPt.add(tmp);
				//return true;
			}
			else
			{
				removeRequire(r, point1, r+m);
			}
			while(true)
			{
				if(point2 >= m+2*n)
				{
					break;
				}
				println(">>>>>>>>>>>>>>>>>>>>>>>>");
				insertRequire(r, point1, point2);
				println(">>>>>>>>>>>>>>>>>>>>>>>>");
				if(getViolation(1, 1) == 0)
				{
					removeRequire(r, point1, point2);
					int[] tmp = new int[2];
					tmp[0] = point1;
					tmp[1] = point2;
					arrInsPt.add(tmp);
					//return true;
				}
				else
				{
					removeRequire(r, point1, point2);
				}
				point2 = s[point2];	
			}
			point1 = s[point1];
		}
		
		return arrInsPt;
	}
	private int getSlackAfterInsertion(int r)
	{
		println(">>getSlackAfterInsertion<<");
		int slack = 0;
		slack = t[s[r+m]] - t[getNodeBeforePickup(r)];
		slack += (t[s[r+m+n]] - t[getNodeBeforeDeliver(r)]);
		
		return slack;
	}
	private int getViolationLoad()
	{
		println(">>getViolationLoad<<");
		int violation = 0;
		for(int i = m;i < m+2*n;i++)
		{
			violation += Math.max(0, l[i] - Q);
		}
		return violation;
	}
	private int getViolationRideTime()
	{
		println(">>getViolationRideTime<<");
		int violation = 0;
		for(int i = m;i < m+n;i++)
		{
			violation += Math.max(0,t[i+n] - t[i] - requires[i-m].getDuarationPickup() - SolverDARP.L);
		}
		return violation;
	}
	private int getViolation(float factorLoad,float factorRideTime)
	{
		println(">>getViolation<<");
		int violation;
		violation = (int)Math.ceil((factorLoad*getViolationLoad() + factorRideTime*getViolationRideTime())/(factorLoad+factorRideTime));
		return violation;
	}
	private void insertRequire(int r,int pickupAfterNode,int deliverAfterNode)
	{
		println(">>insertRequire<<");
		println(s);
		if(s[r + m] >= 0 || s[r + m + n]>=0)
		{
			println(r+"/"+s[r+m]+"  /   "+s[r+m+n]);
			printfError("IN FUNCTION insertRequire: The require have inserted in a rout");
		}
		if(r > n)
		{
			printfError("ERROR IN FUNCTION insertRequire: invalid value");
		}
		if(!setUnassignRequest.contains(r))
		{
			printfError("ERROR: Require "+r+" inserted");
		}
		else
		{
			setUnassignRequest.remove(r);
		}
		s[r+m] = s[pickupAfterNode];
		s[pickupAfterNode] = r + m;
		v[r+m] = v[pickupAfterNode];
		
		s[r + m + n] = s[deliverAfterNode];
		s[deliverAfterNode] = r + m + n;
		v[r+m+n] = v[pickupAfterNode];
		
		updateTimeservice(r, pickupAfterNode, deliverAfterNode, true);
		updateLoaded(r, pickupAfterNode, deliverAfterNode);
	}
	private void removeRequire(int r,int pickupAfterNode,int deliverAfterNode)
	{
		println(">>removeRequire<<");
		println(s);
		if(setUnassignRequest.contains(r))
		{
			printfError("ERROR: Require "+r+" didn't insert");
		}
		else
		{
			setUnassignRequest.add(r);
		}
		updateTimeservice(r, pickupAfterNode, deliverAfterNode, false);
		
		updateLoaded(r);
		s[deliverAfterNode] = s[r + m + n];
		s[r + m + n] = -1;
		v[r + m + n] = -1;		
		s[pickupAfterNode] = s[r + m];
		s[r + m] = -1;
		v[r + m] = -1;
		
		println(s);
	}
	private int getNodeBeforePickup(int r)
	{	
		println(">>getNodeBeforePickup<<");
		int currentNode = v[r+m];
		
		while(currentNode < m + 2*n)
		{
			if(s[currentNode] == r+m)
			{
				return currentNode;
			}
			else
			{
				currentNode = s[currentNode];
			}
		}
		
		return -1;
	}
	private int getNodeBeforeDeliver(int r)
	{
		println(">>getNodeBeforeDeliver<<");
		int currentNode = r + m;
		if(currentNode < -1)
		{
			printfError("ERROR: Can't find node");
		}
		while(currentNode < m + 2*n)
		{
			if(s[currentNode] == r + m + n)
			{
				return currentNode;
			}
			else
			{
				currentNode = s[currentNode];
			}
		}
		return -1;
	}
	
	private int getRoutingCost()
	{
		println(">>getRoutingCost<<");
		int routcost = 0;
		for(int i = m + 2*n;i < 2*(m+n);i++)
		{
			routcost += t[i];
		}
		return routcost;
	}
	public void readProblem(int m,int n,int[] weight,int[] Ep,int[] Lp,int[] Ed,int[] Ld)
	{
		this.m = m;
		this.n = n;
		this.requires = new Require[this.n];
		for(int i = 0;i < weight.length;i++)
		{
			requires[i].setWeight(weight[i]);
			requires[i].setEp(Ep[i]);
			requires[i].setEd(Ed[i]);
			requires[i].setLp(Lp[i]);
			requires[i].setLd(Ld[i]);
		}
		
	}
	public void readProblem()
	{
		try 
		{
	
			File stocks = new File("problem.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();
	
			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			NodeList nodes = doc.getElementsByTagName("Numvehicle");
			this.m = Integer.parseInt(nodes.item(0).getTextContent());
			NodeList nodesRequire = doc.getElementsByTagName("Require");
			this.n = nodesRequire.getLength();
			this.requires = new Require[this.n];
			for (int i = 0; i < nodesRequire.getLength(); i++) 
			{
				
				this.requires[i] = new Require();
				if (nodesRequire.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nodesRequire.item(i);
					//printfError("item = "+eElement.getElementsByTagName("Dpickup").item(0).getTextContent());
					this.requires[i].setDuarationPickup(Integer.parseInt(eElement.getElementsByTagName("Dpickup").item(0).getTextContent()));
					this.requires[i].setDuarationDeliver(Integer.parseInt(eElement.getElementsByTagName("Ddelivery").item(0).getTextContent()));
					this.requires[i].setWeight(Integer.parseInt(eElement.getElementsByTagName("q").item(0).getTextContent()));
					this.requires[i].setPickup(Integer.parseInt(eElement.getElementsByTagName("pickup").item(0).getTextContent()));
					this.requires[i].setDeliver(Integer.parseInt(eElement.getElementsByTagName("deliver").item(0).getTextContent()));
					this.requires[i].setEp(Integer.parseInt(eElement.getElementsByTagName("Ep").item(0).getTextContent()));
					this.requires[i].setLp(Integer.parseInt(eElement.getElementsByTagName("Lp").item(0).getTextContent()));
					this.requires[i].setEd(Integer.parseInt(eElement.getElementsByTagName("Ed").item(0).getTextContent()));
					this.requires[i].setLd(Integer.parseInt(eElement.getElementsByTagName("Ld").item(0).getTextContent()));
				}
				
			}
		} catch (Exception ex) 
		{
		ex.printStackTrace();
		}
	}
	private void printfError(String msg)
	{
		System.err.println("-----ERROR-------");
		System.err.println(msg);
		System.err.println("-----------------");
		System.exit(0);
	}
	private void println(String msg)
	{
		System.out.println(msg);
	}
	private void println(HashSet<Integer> x)
	{
		for(int y:x)
		{
			println(y+"");
		}
	}
	public void println(int[] x)
	{
		for(int y:x)
		{
			System.out.print(y+"\t");
		}
		println("");
	}
	public class Solution
	{
		int[] s,v,t;
		int routCost;
		Solution(int[] s,int[] v,int[] t, int r)
		{
			setS(s);
			setV(v);
			setT(t);
			routCost = r;
		}
		public void setRoutCost(int r)
		{
			this.routCost = r;
		}
		public int getRoutCost()
		{
			return this.routCost;
		}
		public int [] getS()
		{
			return s;
		}
		public int [] getV()
		{
			return v;
		}
		public int [] getT()
		{
			return t;
		}
		
		public void setS(int[] s)
		{
			if(this.s == null)
			{
				this.s = new int[s.length];
			}
			copy(s,this.s);
		}
		public void setV(int[] v)
		{
			if(this.v == null)
			{
				this.v = new int[v.length];
			}
			copy(v,this.v);
		}
		public void setT(int[] t)
		{
			if(this.t == null)
			{
				this.t = new int[t.length];
			}
			copy(t,this.t);
		}
		public void save(int[] s, int[] v, int[] t, int rc)
		{
			setS(s);
			setV(v);
			setT(t);
			routCost = rc;
		}
		private void copy(int[] source, int[] dest)
		{
			for(int i = 0;i < source.length;i++)
			{
				dest[i] = source[i];
			}
		}
	}
}