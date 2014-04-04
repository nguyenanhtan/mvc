package com.javatpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import project.darp.SolverDARP;
import project.darp.SolverDARP.Solution;

import java.io.StringWriter;

public class ControllerServlet extends HttpServlet {
	int numVehicle = 0;
	int numRequest = 0;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		try{
		String matrix = request.getParameter("matrix_distances");
		String weight = request.getParameter("weight");
		String Ep = request.getParameter("Ep");
		String Lp= request.getParameter("Lp");
		String Ed = request.getParameter("Ed");
		String Ld = request.getParameter("Ld");
		//String depot = request.getParameter("depot");
		String snVehicle = request.getParameter("num_vehicle");
		
		System.out.println(snVehicle);
		//System.exit(0);
		int[] arrWeight = parser(weight);
		
		try
		{
			numVehicle = Integer.parseInt(snVehicle.substring(1, snVehicle.length()-1));
			System.out.println("numVehicle: "+numVehicle);
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		numRequest = arrWeight.length;
		
		SolverDARP S = new SolverDARP(matrix,numVehicle,numRequest,arrWeight,parser(Ep),parser(Lp),parser(Ed),parser(Ld));

		Solution Sol = S.LNSFFPA(1010, 12000);		
		response.getWriter().write(encodeResponse(Sol.getS()));

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
				int re;
				if(tg.contains(":"))
				{
					String[] arrtg = tg.split(":");
					data[i] = Integer.parseInt(arrtg[0])*60 + Integer.parseInt(arrtg[1]);
				}
				else
				{
					System.out.println("weight");
					data[i] = Integer.parseInt(tg);
				}
				//System.out.println(data[i]);
				i++;
			}
		}catch(Exception e)
		{
			System.err.println(e.toString());
		}
		System.out.println("__________________");
		for(int x:data)
		{
			System.out.print(x+"   ");
		}
		System.out.println("\n__________________");
		return data;
	}
	private String encodeResponse(int[] sol)
	{
		JSONObject erp = new JSONObject();
		JSONArray arr = new JSONArray();		
		for(int x:sol)
		{
			arr.add(x);
		}
		erp.put("Solution", arr);
		erp.put("numVehicle", numVehicle);
		erp.put("numRequest", numRequest);
		String jsonText = JSONValue.toJSONString(erp);
		System.out.println("jsonText2: "+jsonText);
		return jsonText;

		
	}
}
