package com.javatpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PreProcess {
	public static int NUM_RANGE = 10;
	public static int MAX_RANGE = 0;
	public static String MATRIX_DURATION = "duration";
	public static String MATRIX_DISTANCE = "distance";
	
	public static int[][] getMatrix(String[] range, String nameMatrix)
	{
		int [][] mat = new int[range.length][range.length];
		NUM_RANGE = 10;
		MAX_RANGE = range.length/NUM_RANGE + Math.min(range.length%NUM_RANGE, 1);
		System.out.println("MAX_RANE: "+MAX_RANGE);
		try
		{
			for(int i = 0;i < MAX_RANGE;i++)
			{
				for(int j = 0;j < MAX_RANGE;j++)
				{
					String link = getURLRequest(range, i, j, NUM_RANGE);
					String s = getResponseMatrix(link);
//					System.out.println(s);
					updateMatrix(mat, s, i, j, nameMatrix);					
					//break;
				}
				//break;
			}
			return mat;
		}catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
//		System.out.println("_______getMatrixDisAndDur_______");
	}
	private static void updateMatrix(int[][] mat, String json, int grow, int gcol, String nameMatrix)
	{
		int[][] jtm = JSONToMatrix(json, nameMatrix);
		for(int i = 0;i < jtm.length;i++)
		{
			for(int j = 0;j < jtm[0].length;j++)
			{
				mat[NUM_RANGE*grow+i][NUM_RANGE*gcol+j] = jtm[i][j];
			}
		}
	}
	private static int[][] JSONToMatrix(String json, String nameMatrix)
	{
		int[][] data = null;
		JSONParser parser=new JSONParser();
		try
		{
			JSONObject jsonObj = (JSONObject)parser.parse(json);
			if(!jsonObj.get("status").equals("OK"))
			{
				System.out.println("Can't get matrix distance");
				return null;
			}
			JSONArray row = (JSONArray)jsonObj.get("rows");
			Iterator<JSONObject> it = row.iterator();
			int i = 0;
			int j = 0;
			data = new int[row.size()][];
//			SolverDARP.println("row_size: "+row.size()+"");
			while(it.hasNext())
			{
				JSONArray elements = (JSONArray)it.next().get("elements");
				Iterator<JSONObject> ite = elements.iterator();
				data[i] = new int[elements.size()];
//				SolverDARP.println("elements_size: "+elements.size()+"");
				j = 0;
				while(ite.hasNext())
				{
					JSONObject job = ite.next();
					JSONObject dur = (JSONObject)job.get(nameMatrix);
					data[i][j] = Integer.parseInt(dur.get("value")+"");
					j++;
				}
				i++;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return data;
	}
    public static String[] parserRange(String json)
    {
    	String[] data = null;
		JSONParser parser=new JSONParser();
		try{
			JSONArray jsow = (JSONArray)parser.parse(json);
//			JSONArray jsow = (JSONArray)job;
			Iterator<JSONObject> it = jsow.iterator();
			data = new String[jsow.size()];
			int i = 0;
//			System.out.println("jsow: "+jsow);
			while(it.hasNext())
			{
				JSONObject tg = it.next();
				data[i] = tg.get("k")+","+tg.get("A");
				//System.out.println(data[i]);
				i++;
			}
		}catch(Exception e)
		{
			System.err.println("parse: "+e.toString());
//			System.exit(0);
		}
		return data;
    }
	private static String getURLRequest(String[] range,int grow, int gcol, int NUM)
	{
		String s = "https://maps.googleapis.com/maps/api/distancematrix/json?";
		String [] org = getRangeByGroup(range, grow, NUM);
		String [] des = getRangeByGroup(range, gcol, NUM);
		String sorg = "";
		String sdes = "";
		for(int i = 0;i < org.length;i++)
		{
			sorg+= org[i]+"|";
		}
		for(int i = 0;i < des.length;i++)
		{
			sdes+= des[i]+"|";
		}
		sorg =  sorg.substring(0, sorg.length()-1);
		sdes = sdes.substring(0, sdes.length()-1);
		s += "origins="+sorg;
		s += "&destinations="+sdes;
		s += "&key=AIzaSyDgaadJHrg37wH05ZRMDmpe_oC2DwL8iyg&sensor=true&avoid=highways";
		return s;
	}
	private static String[] getRangeByGroup(String[] r, int g,int NUM)
	{
		int MAXFOR = Math.min(NUM, r.length-g*NUM);
		String [] rt = new String[MAXFOR];
		for(int i = 0;i < MAXFOR;i++)
		{
			rt[i] = r[g*NUM+i];
		}
		return rt;
	}
	private static String getResponseMatrix(String link) throws IOException
	{
		 URL oracle = new URL(link);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));
	        String data = "";
	        String inputLine = "";
	        while ((inputLine = in.readLine()) != null)
	        {
	        	data+=inputLine;
	        }	        
	        in.close();
	        return data;
	}
}
