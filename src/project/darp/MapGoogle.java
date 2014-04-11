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
				JSONArray elements = (JSONArray)it.next().get("elements");
				Iterator<JSONObject> itEle = elements.iterator();				
				int j = 0;
				while(itEle.hasNext())
				{
					JSONObject obj = itEle.next();
					JSONObject distance = (JSONObject)obj.get("distance");
					float value = Float.parseFloat(distance.get("value").toString());
					matrixDistance[i][j] = value;
					matrixDistance[j][i] = value;
					
					j++;
				}
				i++;
			}
			//System.out.println("jso = "+jso.size());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	public float[][] getMatrixDistance()
	{
		return matrixDistance;
	}
	@Override
	public int getT(int i,int j)
	{
		return (int)matrixDistance[i][j];
	}
	
}
