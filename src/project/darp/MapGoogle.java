package project.darp;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import com.javatpoint.PreProcess;

public class MapGoogle {

	private int[][] matrixDistance;
	private int[][] matrixDuration;
	public MapGoogle(String JMatrix) {
		// TODO Auto-generated constructor stub		
		matrixDistance = PreProcess.getMatrix(PreProcess.parserRange(JMatrix),PreProcess.MATRIX_DISTANCE);
		matrixDuration = PreProcess.getMatrix(PreProcess.parserRange(JMatrix),PreProcess.MATRIX_DURATION);
	}
	public int[][] getMatrixDistance()
	{
		return matrixDistance;
	}
	public int[][] getMatrixDuration()
	{
		return matrixDuration;
	}
	public int getT(int i, int j)
	{
		return getDuration(i, j);
	}
	public int getDuration(int i,int j)
	{
		return (int)matrixDuration[i][j];
	}
	public int getDistance(int i,int j)
	{
		return (int)matrixDistance[i][j];
	}
	
}
