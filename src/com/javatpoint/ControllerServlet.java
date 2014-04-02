package com.javatpoint;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import project.darp.SolverDARP;
import project.darp.SolverDARP.Solution;


public class ControllerServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		try{
			String matrix = request.getParameter("matrix_distances");
			String weight = request.getParameter("weight");
			String Ep = request.getParameter("Ep");
			String Lp= request.getParameter("Lp");
			String Ed = request.getParameter("Ed");
			String Ld = request.getParameter("Ld");
			String depot = request.getParameter("depot");
			String snVehicle = request.getParameter("num_vehicle");
			
			System.out.println(snVehicle);
			//System.exit(0);
			int[] arrWeight = parser(weight);
			int numVehicle = 0;
			try
			{
				numVehicle = Integer.parseInt(snVehicle.substring(1, snVehicle.length()-1));
				System.out.println("numVehicle: "+numVehicle);
			}catch(Exception e)
			{
				System.out.println(e.toString());
			}
			int numRequest = arrWeight.length;
			
			SolverDARP S = new SolverDARP(matrix,numVehicle,numRequest,arrWeight,parser(Ep),parser(Lp),parser(Ed),parser(Ld));
			System.out.println("S: ");
			Solution Sol = S.LNSFFPA(100, 120);
			S.println("Rout cost: "+Sol.getRoutCost());
			S.println(Sol.getS());
//		}catch(Exception e)
//		{
//			//System.err.println(e.toString());
//		}
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
	
}
