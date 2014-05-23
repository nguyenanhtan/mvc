package com.javatpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import project.darp.MapGoogle;
public class ControllerModel extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("command");
		System.out.println("load Session: "+cmd);
		if(cmd.equals("saveSession"))
		{
			doSaveSession(request,response);
		}
		else if(cmd.equals("loadIdSession"))
		{
			System.out.println("load Id Session");
			doLoadIdSession(request, response);
			System.out.println("load Id Session");
		}
		else if(cmd.equals("loadSession"))
		{
			doLoadSession(request, response);
		}
		else if(cmd.equals("deleteSession"))
		{
			doDeleteSession(request, response);
			doLoadIdSession(request, response);
		}
		//doLoadSession(request, response);
	}
	private void doLoadSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String ids = request.getParameter("ids");
		int[] arrIds = parser(ids);
		ArrayList<TreeMap<String, String>> reqs = new ArrayList<TreeMap<String,String>>();
		TreeMap<String, String> session = new TreeMap<String, String>();
		ComModel cm = new ComModel();
		try
		{
			reqs = cm.getRequestsSession(arrIds);	
			session = cm.getSession(arrIds[0]);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println(encode(reqs, session));
		response.getWriter().write(encode(reqs, session));
	}
	private void doDeleteSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String ids = request.getParameter("ids");
		int[] arrIds = parser(ids);
		ComModel cm = new ComModel();
		try
		{
			cm.deleteSession(arrIds);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	private String encode(ArrayList<TreeMap<String, String>> reqs, TreeMap<String, String> sess)
	{
		JSONObject Job = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(TreeMap<String, String> x:reqs)
		{
			jarr.add(x);
		}
		Job.put("session", sess);
		Job.put("requests", jarr);
		String jsonText = JSONValue.toJSONString(Job);
		return jsonText;
	}
	private String encodeArray(ArrayList<Integer> arr)
	{
		JSONArray arrJSON = new JSONArray();
		for(int x: arr)
		{
			arrJSON.add(x);
		}
		String jsonText = JSONValue.toJSONString(arrJSON);
		return jsonText;
	}
	private void doLoadIdSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComModel cModel = new ComModel();
		try
		{ 
			ArrayList<Integer> idSession = cModel.getSetIdSession();
			response.getWriter().write(encodeArray(idSession));
		}catch(SQLException ex)
		{
			response.getWriter().write("CANT");
			System.err.println("ERROR SQL: "+ex.toString());
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
	}
	private void doSaveSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String matrix = request.getParameter("rangeMatrix");
		String weight = request.getParameter("weight");
		String Ep = request.getParameter("Ep");
		String Lp= request.getParameter("Lp");
		String Ed = request.getParameter("Ed");
		String Ld = request.getParameter("Ld");
		String depot = request.getParameter("depot");
		String snVehicle = request.getParameter("num_vehicle");
		String scapVehicle = request.getParameter("capacity_vehicle");
		snVehicle = snVehicle.substring(1, snVehicle.length()-1);
		scapVehicle = scapVehicle.substring(1, scapVehicle.length()-1);
		String sdP = request.getParameter("duration_pickup");
		String sdD = request.getParameter("duration_deliver");
		String pk = request.getParameter("pickup");
		String delv = request.getParameter("deliver");
		String left = request.getParameter("left");
		String back = request.getParameter("back");
		System.out.println("left-back: "+left+"/"+back);
		String[] arrtg = back.substring(1, back.length()-1).split(":");
		int timeBack = Integer.parseInt(arrtg[0])*3600 + Integer.parseInt(arrtg[1])*60;
		String[] arrtg2 = left.substring(1, left.length()-1).split(":");
		int timeLeft = Integer.parseInt(arrtg2[0])*3600 + Integer.parseInt(arrtg2[1])*60;
		
/*		try
		{*/
			ComModel cModel = new ComModel();		
			cModel.set(parser(weight),ComModel.WEIGHT);
			cModel.set(parser(Ep),ComModel.EP);
			cModel.set(parser(Ed),ComModel.ED);
			cModel.set(parser(Lp),ComModel.LP);
			cModel.set(parser(Ld),ComModel.LD);
			cModel.set(parser(sdP),ComModel.DURATION_PICKUP);
			cModel.set(parser(sdD),ComModel.DURATION_DELIVER);
			cModel.set(timeBack,ComModel.TIME_BACK);
			cModel.set(timeLeft,ComModel.TIME_LEFT);
			cModel.set(parser(pk,true),ComModel.PICKUP);
			cModel.set(parser(delv,true),ComModel.DELIVER);
			
			cModel.set(Integer.parseInt(snVehicle),ComModel.NUMBER_VEHICLE);
			cModel.set(Integer.parseInt(scapVehicle),ComModel.CAPACITY);
			cModel.set(depot);
			System.out.println(depot);
			System.out.println(pk);
			System.out.println(delv);
			MapGoogle mg = new MapGoogle(matrix);
			cModel.set(mg.getMatrixDistance(),mg.getMatrixDuration());
			try
			{
				cModel.insert();
				response.getWriter().write("Inserted");
			}catch(SQLException ex)
			{
				System.err.println("***CAN'T INSERT THE SESSION***");
				System.err.println("ERROR SQL: "+ex.toString());
				System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
				response.getWriter().write("Can't Inserted");
			}
			
			cModel.close();
/*		}catch(SQLException ex)
		{
			System.err.println("ERROR SQL: "+ex.toString());
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		*/
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	public int[] parser(String json)
	{
		int[] data = null;
		JSONParser parser=new JSONParser();
		try{
			JSONArray jsow = (JSONArray)parser.parse(json);
//			JSONArray jsow = (JSONArray)job;
			Iterator<String> it = jsow.iterator();
			data = new int[jsow.size()];
			int i = 0;
//			System.out.println("jsow: "+jsow);
			while(it.hasNext())
			{
				String tg = it.next();
				
				if(tg.contains(":"))
				{
					String[] arrtg = tg.split(":");
					data[i] = Integer.parseInt(arrtg[0])*3600 + Integer.parseInt(arrtg[1])*60;
				}
				else
				{
					//System.out.println("weight");
					data[i] = Integer.parseInt(tg);
				}
				//System.out.println(data[i]);
				i++;
			}
		}catch(Exception e)
		{
			System.err.println("parse1: "+e.toString());
		}

		return data;
	}
	public String[] parser(String json,boolean x)
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
				JSONObject jnxt = it.next();
				data[i] = jnxt.toString();
				i++;
			}
		}catch(Exception e)
		{
			System.err.println("parse2: "+e.toString());
		}

		return data;
	}

}
