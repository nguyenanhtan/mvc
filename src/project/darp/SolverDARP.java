package project.darp;
import org.w3c.dom.*;

import javax.xml.parsers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
public class SolverDARP {

	private int m,n;
	private int[] s, v, t, l,location;
	private MapGoogle map;
	private Require[] requires;
	private HashSet<Integer> setUnassignRequest;
	public static int Q = 10;
	public static int L = 1400;
	public static boolean SHOW_BREAK_POINT = false;
	public static double EPS = 0.00001;
	public static int MAX_INIT = 1000;
	private Date startTime = new Date();
	public static int TIME_LIMIT_TREESEARCH = 5000;
	public static int TIME_LIMIT_LNSFFPA = 35000;
	private Solution tmpSol = null;
	public static void main(String[] args) {
		SolverDARP S = new SolverDARP();
		Solution Sol = S.LNSFFPA(100, 120);
		S.println("Rout cost: "+Sol.getRoutCost());
		S.println(Sol.getS());
	}
	public Solution LNSFFPA(int numiter, int timeLimt)
	{
		for(int i = 0;i < MAX_INIT;i++)
		{
			TreeSearch();
			revert(tmpSol);
			if(isSolution())
			{
				break;
			}
			else
			{
				init();
			}			
		}
		if(!isSolution()){
			printfError("Can't init!");
		}
		status();
		//System.exit(0);
//		if(true)
//		return new Solution(s, v, location, 100);
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
				if(isSolution())
				{
					continue;
				}
//				System.out.println("A Relax");
//				status();
				TreeSearch();
				revert(tmpSol);
				if(!isSolution())
				{
					continue;
				}
				else
				{
					status();
				}
				int rCost = getRoutingCost();
				if(rCost < currentS.getRoutCost())
				{					
					currentS = new Solution(s, v, t, rCost);
					if(currentS.getRoutCost() < bestS.getRoutCost())
					{
						bestS = new Solution(currentS.getS(), currentS.getV(), currentS.getT(), currentS.getRoutCost());
//						println("best: "+currentS.getRoutCost());
//						println(currentS.getS());
//						status();
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
//		status();
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
	public SolverDARP(String Jmatrix, int m, int n, int capacity, int[] weight,int[] Ep,int[] Lp,int[] Ed,int[] Ld,int[] dP,int[] dD) {
		// TODO Auto-generated constructor stub
//		map = new MapTransport("fileMap.drp");
		SolverDARP.Q = capacity;
		map = new MapGoogle(Jmatrix);
//		println(map.getMatrixDistance());
//		System.out.println(m+":"+n);
		readProblem(m,n,capacity,weight,Ep,Lp,Ed,Ld,dP,dD);
		System.out.println(m+":"+n);
		s = new int[2*(m+n)];
		v = new int[2*(m+n)];
		t = new int[2*(m+n)];
		l = new int[2*(m+n)];
		location = new int[2*(m+n)];
		init();
		//status();
	}
	private void init()
	{
		
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
			t[i] = 0;
			l[i] = 0;
			location[i] = 0;
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
	}
	public void status()
	{
		System.out.println("\n---------------STATUS-----------------");
		println("m:"+this.m);
		println("n:"+this.n);
		System.out.print("s:\t");
		println(s);
		System.out.print("v:\t");
		println(v);
		System.out.print("t:\t");
		println(t);
		System.out.print("l:\t");
		println(l);
		System.out.print("location:\t");
		println(location);
		System.out.println("Require:\t");
		println(requires);
		println("Rout cost:");
		//println(getRoutingCost()+"");
		System.out.println("---------------END-----------------");
	}
	public void printBreakPoint(String s)
	{
		if(SHOW_BREAK_POINT)
		{
			println(s);
		}
	}
	public void printBreakPoint(String s,boolean a)
	{
		if(SHOW_BREAK_POINT||a)
		{
			println(s);
		}
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
		init();
	}
	//Update after insert a unsigned request
	private void updateTimeservice(int request,int pickupAfterNode, int deliverAfterNode, boolean isInsert)
	{
		printBreakPoint("--- updateTimeservice ---");
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
//			println("pickupAfterNode = "+pickupAfterNode);
//			println("deliverNodeAfter = "+deliverAfterNode);
//			println("m = "+m);
//			println("request = "+request);
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
//				println(location[deliverAfterNode]+";"+ location[request + m + n]);
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
		printBreakPoint("--- updateLoaded ---");
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
		printBreakPoint("--- getDeltaTimeService ---");
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
		printBreakPoint("--- updateLoaded ---");
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
	
	private void TreeSearch()
	{
		printBreakPoint("--- TreeSearch ---");
		//println("--- TreeSearch ---setUnassignRequest.size() = "+setUnassignRequest.size());
		if(setUnassignRequest.size() == 0)
		{
			
			if(isSolution())
			{
				if(tmpSol == null)
				{
					tmpSol = new Solution(s, v, t, getRoutingCost());
				}
				else
				{
					if(tmpSol.getRoutCost() > getRoutingCost())
					{
						tmpSol = new Solution(s, v, t, getRoutingCost());						
					}
				}
				
//				return true;
			}
			return;
		}
		else
		{
			HashMap<Integer, ArrayList<int[]>> map = getUnassignedRequest();
			if(map == null)
			{
				printfError("ERROR: in Treesearch function");
			}
			Iterator it = map.entrySet().iterator();
			Map.Entry<Integer, ArrayList<int[]>> mapEntry = null;
			if(it.hasNext())
			{
				mapEntry = (Map.Entry<Integer, ArrayList<int[]>>)it.next();			
			}
			for(int[] x:mapEntry.getValue())
			{				
				
				insertRequire(mapEntry.getKey(), x[0], x[1]);
				TreeSearch();				
				removeRequire(mapEntry.getKey(), x[0], x[1]);	
				//status();
			}
			if(isTimeOut(startTime, TIME_LIMIT_TREESEARCH))
			{
				println("TIMEOUT");
				return;
			}

		}
	}
	private void revert(Solution s)
	{
		if(s != null)
		{
			this.s = s.getS();
			this.t = s.getT();
			this.v = s.getV();
		}
		else
		{
			println("Can't revert!");
		}
	}
	private boolean isSolution()
	{
		printBreakPoint("--- isSolution ---");
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
		printBreakPoint("--- getUnassignedRequest ---");
//		println("--- getUnassignedRequest ---");
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
//			println("---setUnassignRequest---");
//			println(setUnassignRequest);
//			println("---setUnassignRequest---");
			ArrayList<int[]> tmpPoint = new ArrayList<int[]>();
			for(int j = 0; j < m;j ++)
			{
				tmpPoint.addAll(getPointInsertIntoRout(i, j));
			}
			map.put(i, tmpPoint);
//			println("*******************"+tmpPoint.size()+"*******"+i+"*****************");
			mapSizeR.put(tmpPoint.size(),i);//to sort by number point available
		}		
		Iterator it = mapSizeR.entrySet().iterator();
		if(it.hasNext())
		{
			Map.Entry<Integer, Integer> mapEntry = (Map.Entry<Integer, Integer>)it.next();			
			HashMap<Integer, ArrayList<int[]>> rt = new HashMap<Integer, ArrayList<int[]>>();//
			rt.put(mapEntry.getValue(),map.get(mapEntry.getValue()));
//			println("--- getUnassignedRequest ---");
//			println(rt);	
			
			return rt;
		}
		else
		{
			printfError("ERROR: getUnAssignRequest");
		}
//		println("--- getUnassignedRequest NULL ---");
		return null;
	}
	private ArrayList<int[]> getPointInsertIntoRout(int r, int rout)//r C# [0, n]
	{
		printBreakPoint("--- getPointInsertIntoRout ---");
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
			
			
			insertRequire(r, point1, r+m);
			if(getViolation(1, 1)< EPS)
			{				
				removeRequire(r, point1, r+m);
				int[] tmp = new int[2];
				tmp[0] = point1;
				tmp[1] = r + m;

				arrInsPt.add(tmp);
			}
			else
			{
				println("***else*****tmp: ");
				removeRequire(r, point1, r+m);
			}
			while(true)
			{
				if(point2 >= m+2*n)
				{
					break;
				}
//				println(">>>>>>>>>>>>>>>>>>>>>>>>");
				insertRequire(r, point1, point2);
//				println(">>>>>>>>>>>>>>>>>>>>>>>>");
				if(getViolation(1, 1) < EPS)
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
//		System.out.print("arrInsPt:");
//		println(arrInsPt);
//		println("");
		return arrInsPt;
	}
	private int getSlackAfterInsertion(int r)
	{
		printBreakPoint("--- getSlackAfterInsertion ---");
		int slack = 0;
		slack = t[s[r+m]] - t[getNodeBeforePickup(r)];
		slack += (t[s[r+m+n]] - t[getNodeBeforeDeliver(r)]);
		
		return slack;
	}
	private int getViolationLoad()
	{
		printBreakPoint("--- getViolationLoad ---");
		int violation = 0;
		for(int i = m;i < m+2*n;i++)
		{
			violation += Math.max(0, l[i] - Q);
		}
		return violation;
	}
	private int getViolationRideTime()
	{
		printBreakPoint("--- getViolationRideTime ---");
		int violation = 0;
		for(int i = m;i < m+n;i++)
		{
			violation += Math.max(0,t[i+n] - t[i] - requires[i-m].getDuarationPickup() - SolverDARP.L);
		}
		//return 0;
		return violation;
	}
	private int getViolation(float factorLoad,float factorRideTime)
	{
		printBreakPoint("--- getViolation ---");
		int violation;
		violation = (int)Math.ceil((factorLoad*getViolationLoad() + factorRideTime*getViolationRideTime())/(factorLoad+factorRideTime));
		//println(getViolationLoad()+" _--_--_"+getViolationRideTime());
		//return Math.ceil((factorLoad*getViolationLoad() + factorRideTime*getViolationRideTime())/(factorLoad+factorRideTime));
		return violation;
	}
	private void insertRequire(int r,int pickupAfterNode,int deliverAfterNode)
	{
		printBreakPoint("--- insertRequire ---",true);
		
//		println("Before InsertRequire s -> ");
//		println(s);
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
//		println("After InsertRequire s -> ");
//		println(s);
	}
	private void removeRequire(int r,int pickupAfterNode,int deliverAfterNode)
	{
		printBreakPoint("--- removeRequire ---",true);
//		println("B Remove");
//		println(s);
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
//		println("A Remove");
//		println(s);
	}
	private int getNodeBeforePickup(int r)
	{	
		printBreakPoint("--- getNodeBeforePickup ---");
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
		printBreakPoint("--- getNodeBeforeDeliver ---");
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
		printBreakPoint("--- getRoutingCost ---");
//		println("--- getRoutingCost ---");
		int routcost = 0;
		for(int i = 0;i < m + 2*n;i++)
		{
			routcost += map.getDistance(location[i], location[s[i]]);
//			println("("+location[i]+";"+location[s[i]]+") -->"+map.getDistance(location[i], location[s[i]]));
		}
//		println("--->"+routcost);
		return routcost;
	}
	public void readProblem(int m,int n,int capacity, int[] weight,int[] Ep,int[] Lp,int[] Ed,int[] Ld,int[] durationP,int[] durationD)
	{
		this.m = m;
		this.n = n;
		this.requires = new Require[this.n];
		if(weight == null)
		{
			this.printfError("weight is null");
		}
		else if(Ep == null)
		{
			this.printfError("Ep is null");
		}
		else if(Lp == null)
		{
			this.printfError("Lp is null");
		}
		else if(Ed == null)
		{
			this.printfError("Ed is null");
		}
		else if(Ld == null)
		{
			this.printfError("Ld is null");
		}
		else
		{
			this.println("Data is valid");
		}
		
		for(int i = 0;i < weight.length;i++)
		{
			this.requires[i] = new Require();
			requires[i].setWeight(weight[i]);
			requires[i].setEp(Ep[i]);
			requires[i].setEd(Ed[i]);
			requires[i].setLp(Lp[i]);
			requires[i].setLd(Ld[i]);
			requires[i].setDuarationPickup(durationP[i]);
			requires[i].setDuarationDeliver(durationD[i]);
			requires[i].setPickup(i+1);
			requires[i].setDeliver(i+n+1);
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
		System.err.println("\n-----ERROR-------");
		System.err.println(msg);
		System.err.println("-----------------");
		System.exit(0);
	}
	public void println(ArrayList<int[]> x)
	{
		for(int[] a:x)
		{
			String s = "";
			for(int y:a)
			{
				s+= y;
				s+= ",";
			}
			s = s.substring(0, s.length() - 1);
			s = "--->["+s+"]";
			println(s);
		}
	}
	public void println(HashMap<Integer, ArrayList<int[]>> x)
	{
		Iterator it = x.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<Integer, ArrayList<int[]>> me = (Map.Entry<Integer, ArrayList<int[]>>)it.next();
			int key = me.getKey();
			ArrayList<int[]> val = me.getValue();
			println("key: "+key);
			for(int[] z:val)
			{
				System.out.print("(--");
				for(int v:z)
				{
					System.out.print(v+"--");
				}
				System.out.print(")\t");
			}
			println("");
			
		}
	}
	public void println(String msg)
	{
		System.out.println(msg);
	}
	public void print(String msg)
	{
		System.out.print(msg);
	}
	private void println(HashSet<Integer> x)
	{
		for(int y:x)
		{
			println(y+"");
		}
	}
	public void println(TreeMap<Integer, Integer> x)
	{
		Iterator it = x.entrySet().iterator();
		String s = "";
		while(it.hasNext())
		{
			Map.Entry<Integer, Integer> me = (Map.Entry<Integer, Integer>)it.next();
			s+="("+me.getKey()+" : "+me.getValue()+")\t";
		}
		println("");
	}
	public void println(int[] x)
	{
		for(int y:x)
		{
			System.out.print(y+"\t");
		}
		println("");
	}
	public void println(float[] x)
	{
		for(float y:x)
		{
			System.out.print(y+"\t");
		}
		println("");
	}
	public void println(Require[] z)
	{
		System.out.println("-------------------------------");
		for(Require x:z)
		{
			System.out.println(x.toString());
			System.out.println("-------------------------------");
		}
	}
	public void println(float[][] x)
	{
		for(float [] y:x)
		{
			println(y);
		}
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
