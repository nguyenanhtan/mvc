package com.javatpoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import project.darp.MapGoogle;


public class ControllerServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String s = request.getParameter("matrix_distances");
			String weight = request.getParameter("weight");
			System.out.println("weight: "+weight);
			parserWeight(weight);
		}catch(Exception e)
		{
			
		}
		response.getWriter().write("sd");
		System.out.println("*********");
//		Map<String, String[]> prmMap = request.getParameterMap();
//		for (Map.Entry<String, String[]> entry : prmMap.entrySet()) {
//            String key = entry.getKey();         // parameter name
//            String[] value = entry.getValue();   // parameter values as array of String
//            String sVal = "";
//            for(String s:value)
//            {
//            	sVal += (s + ";  ");
//            }
//            System.out.println(key+" --> "+sVal);
//		}
		//System.out.println("r: "+request.getParameterMap() );
		/*response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		
		LoginBean bean=new LoginBean();
		bean.setName(name);
		bean.setPassword(password);
		request.setAttribute("bean",bean);
		
		boolean status=bean.validate();
		
		if(status){
			RequestDispatcher rd=request.getRequestDispatcher("login-success.jsp");
			rd.forward(request, response);
		}
		else{
			RequestDispatcher rd=request.getRequestDispatcher("login-error.jsp");
			rd.forward(request, response);
		}
		*/
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	public int[] parserWeight(String json)
	{
		int[] weight = null;
		JSONParser parser=new JSONParser();
		try{
			JSONArray jsow = (JSONArray)parser.parse(json);
			Iterator<JSONObject> it = jsow.iterator();
			weight = new int[jsow.size()];
			int i = 0;
			while(it.hasNext())
			{
				weight[i] = Integer.parseInt(it.next().toString());
				i++;
				System.out.print(weight[i]+" --- > ");
			}
		}catch(Exception e)
		{
			System.err.println(e.toString());
		}
		System.out.println();
		for(int x:weight)
		{
			System.out.print(x+"\t");
		}
		System.out.println();
		return weight;
	}
	
}
