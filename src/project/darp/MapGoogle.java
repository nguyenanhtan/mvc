package project.darp;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class MapGoogle extends MapTransport {

	private float[][] matrixDistance;
	public MapGoogle(String JMatrix) {
		// TODO Auto-generated constructor stub
		super(true);
		JSONParser parser=new JSONParser();
		try
		{
			JSONArray jso = (JSONArray)parser.parse(JMatrix);		
			Iterator<JSONObject> it = jso.iterator();
			int i = 0;
			this.numdepot = jso.size();
			matrixDistance = new float[this.numdepot][this.numdepot];
			while(it.hasNext())
			{
				//System.out.println("$$: "+it.next());
				JSONArray elements = (JSONArray)it.next().get("elements");
				Iterator<JSONObject> itEle = elements.iterator();
				System.out.println("$$$");
				int j = 0;
				while(itEle.hasNext())
				{
					JSONObject obj = itEle.next();
					JSONObject distance = (JSONObject)obj.get("distance");
					float value = Float.parseFloat(distance.get("value").toString());
					System.out.println(value);
					matrixDistance[i][j] = value;
					j++;
				}
				i++;
			}
			System.out.println("jso = "+jso.size());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
}
