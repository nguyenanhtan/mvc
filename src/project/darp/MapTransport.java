package project.darp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class MapTransport {

	private float coordinate[][];
	protected int numdepot;
	private Map<Integer,Set<Integer>> map;
	private final int MAXNUMBER = 9999999;
	private float[][][] paths;
	MapTransport()
	{
		numdepot = 5;
		coordinate = new float[numdepot][2];
		readCoordinate("testmap.drp");
		Debug.display(coordinate);
		map = new HashMap<Integer,Set<Integer>>();
		simulateMap();
		Debug.display(map);
		paths = new float[numdepot][numdepot][2];
		for(int i = 0;i < numdepot;i++)
		{
			float[][] sp = null;
			sp = dijkstraAl(i);
			paths[i] = sp;
		}
		
		
	}
	MapTransport(boolean x)
	{
		System.out.println(x);
	}
	MapTransport(String fileMap)
	{
		map = new HashMap<Integer,Set<Integer>>();
		readMap(fileMap);
		coordinate = new float[numdepot][2];
		readCoordinate("testmap.drp");
		paths = new float[numdepot][numdepot][2];
		for(int i = 0;i < numdepot;i++)
		{
			float[][] sp = null;
			sp = dijkstraAl(i);
			paths[i] = sp;
		}
		println(paths);
	}
	public void println(float[][][] x)
	{
		System.out.println();
		for(float y[][]:x)
		{
			for(float z[]:y)
			{
				for(float t:z)
				{
					System.out.print(t+"\t");
				}
				System.out.println();
			}
			System.out.println("------------------");
		}
	}
	public int getT(int i,int j)
	{
		return (int)paths[i][1][j]/Vehicle.v;
	}
	public float getFar(int i,int j)
	{
		return paths[i][j][1];
	}
	public float[][] getCoordinate()
	{
		return coordinate;
	}
	private void readCoordinate(String nameFile)
	{
		BufferedReader br = null;
		try 
		{
 
			String sCurrentLine;
			br = new BufferedReader(new FileReader(nameFile));
			int i = 0;
			while ((sCurrentLine = br.readLine()) != null) 
			{
				String[] s = sCurrentLine.split(" ");
				//System.out.println(s[1] + " --- " + s[2]);
				coordinate[i][0] = Float.parseFloat(s[1]);
				coordinate[i][1] = Float.parseFloat(s[2]);
				i++;
				
			}
 
		} catch (IOException e) 
		{
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	private void readMap(String file)
	{
		BufferedReader br = null;
		try 
		{
 
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file));
			int i = 0;
			while ((sCurrentLine = br.readLine()) != null) 
			{
				System.out.println("sCurrent = "+sCurrentLine);
				if(sCurrentLine == "")
				{
					break;
				}
				sCurrentLine = sCurrentLine.trim();
				String[] s = sCurrentLine.split(" ");
				Set<Integer> hs = new HashSet<Integer>();
				for(int j = 1;j < s.length;j++)
				{
					hs.add(Integer.parseInt(s[j]));
				}
				map.put(Integer.parseInt(s[0]), hs);
				i++;
				
			}
			numdepot = i;
 
		} catch (IOException e) 
		{
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	private void simulateMap()
	{
		//Khoi tao do thi lien thong ban dau
		ArrayList<Integer> AI = new ArrayList<Integer>();
		for(int i = 1;i < numdepot;i++)
		{
			AI.add(i);
		}
		int vCurrent = 0;
		int index,ip;
		while(!AI.isEmpty())
		{
			index = (new Random()).nextInt(AI.size());
			ip = AI.get(index);
			System.out.println(ip);
			Set<Integer> hs = new HashSet<Integer>();
			hs.add(ip);
			map.put(vCurrent,hs);
			//map.put(vCurrent,hs);
			
			vCurrent = ip;
			AI.remove(index);
		}
		Set<Integer> hs = new HashSet<Integer>();
		map.put(vCurrent,hs);
		//Add bo sung cac canh
		Set setEntry = map.entrySet();
		Iterator it = setEntry.iterator();
		while(it.hasNext())
		{
			Map.Entry<Integer, Set<Integer>> me = (Map.Entry<Integer, Set<Integer>>)it.next();
			int numIt = (new Random()).nextInt(numdepot - 1);
			for(int i = 0;i < numIt;i++)
			{
				int v = (new Random()).nextInt(numdepot);
				
				if(v != me.getKey())
				{
					map.get(me.getKey()).add(v);
				}
			}
		}
		it = setEntry.iterator();
		while(it.hasNext())
		{
			Map.Entry<Integer, Set<Integer>> me = (Map.Entry<Integer, Set<Integer>>)it.next();
			Iterator<Integer> sit = me.getValue().iterator();
			while(sit.hasNext())
			{
				int adj = sit.next();				
				map.get(adj).add(me.getKey());
				System.out.println(map.get(adj).size());

			}	
		}
		
	}
	private float getDistance(int x,int y)
	{
		if(map.get(x).contains(y)||x==y)
		{
			return (float)Math.sqrt((coordinate[x][0] - coordinate[y][0])*(coordinate[x][0] - coordinate[y][0]) + (coordinate[x][1] - coordinate[y][1])*(coordinate[x][1] - coordinate[y][1]));
		}
		else
		{
			return MAXNUMBER;
		}
	}
	private float [][] dijkstraAl(int start)//Hàng 0 --> p; Hàng 1 --> d
	{
		float [][] rval;
		rval = new float [2][numdepot];
		float [] d = new float[numdepot];
		int [] p = new int[numdepot];
		//Init label
		//rval[0][i] = p[i]
		//rval[1][i] = d[i]
		for(int i = 0;i < numdepot;i++)
		{
			p[i] = start;
			if(map.get(start).contains(i)||i == start)
			{
				d[i] = getDistance(start, i);
			}
			else
			{
				d[i] = MAXNUMBER;
			}
		}
		//End Init
		Set<Integer> S = new HashSet<Integer>();
		Set<Integer> T = new HashSet<Integer>();
		for(int i = 0;i < numdepot;i++)
		{
			T.add(i);
		}
		T.remove(start);
		S.add(start);
		
		while(!T.isEmpty())
		{
			int currU = -1,currV = -1;
			float dMin = MAXNUMBER;
			Iterator<Integer> it = T.iterator();
			while(it.hasNext())
			{
				currV = it.next();
				if(d[currV] < dMin)
				{
					currU = currV;
					dMin = d[currV];
				}
			}
			S.add(currU);
			T.remove(currU);
			it = T.iterator();
			while(it.hasNext())
			{
				int v = it.next();
				if(d[v] > d[currU] + getDistance(v, currU))
				{
					d[v] = d[currU] + getDistance(v, currU);
					p[v] = currU;
					System.out.println("p["+v+"] = "+currU);
				}
			}
		}
		for(int i = 0; i < numdepot;i++)
		{
			rval[0][i] = p[i];
			rval[1][i] = d[i];
			
		}
		return rval;
	}
	
	public static void main(String[] args) {
		MapTransport SD = new MapTransport();
	}
}
