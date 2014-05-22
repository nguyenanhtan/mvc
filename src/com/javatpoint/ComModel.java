package com.javatpoint;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Properties;

public class ComModel {

	public static final int PICKUP = 0;
	public static final int DELIVER = 1;
	public static final int DURATION_PICKUP = 2;
	public static final int DURATION_DELIVER = 3;
	public static final int WEIGHT = 4;
	public static final int EP = 5;
	public static final int LP = 6;
	public static final int ED = 7;
	public static final int LD = 8;
	public static final int MATRIX_DISTANCE = 9;
	public static final int NUMBER_VEHICLE = 10;
	public static final int CAPACITY = 11;
	public static final int DEPOT = 12;
	public static final String TABLE_SESSION = "SESSIONS";
	public static final String TABLE_REQUEST = "REQUEST";
	public static final String TABLE_POSITIONS = "POSITIONS";
	public static final String TABLE_ROUTCOST = "ROUTCOST";
	Connection conn = null;
	//config a session
	String[] pickup;
	String[] deliver;
	int[] durationP;
	int[] durationD;
	int[] weight;
	int[] Ep;
	int[] Lp;
	int[] Ed;
	int[] Ld;
	int[][] matrixDistance;
	int[][] matrixDuration;
	int numVehicle;
	int capacity;
	String depot;
	
	public void insert() throws SQLException
	{
		int idDepot = insertMarker(depot, 0);
		int idSession = insertSession(idDepot, numVehicle, capacity);
		int[] arrIdPickup = new int[pickup.length];
		int[] arrIdDeliver = new int[pickup.length];
		int m = pickup.length;
		for(int i = 0;i < m;i++)
		{
			int idPickup = insertMarker(pickup[i], durationP[i]);
			int idDeliver = insertMarker(deliver[i], durationD[i]);
			arrIdPickup[i] = idPickup;
			arrIdDeliver[i] = idDeliver;
			insertRequest(idPickup, idDeliver, weight[i], Ep[i], Lp[i], Ed[i], Ld[i], idSession);
			
			insertRoutCost(idDepot, idPickup, matrixDistance[0][i+1], matrixDuration[0][i+1]);
			insertRoutCost(idDepot, idDeliver, matrixDistance[0][i+1+m], matrixDuration[0][i+1+m]);
			insertRoutCost(idPickup, idDeliver, matrixDistance[i+1][i+1+m], matrixDuration[i+1][i+1+m]);
			for(int j = 0;j < i;j++)
			{
				insertRoutCost(arrIdPickup[j], idPickup, matrixDistance[j+1][i+1], matrixDuration[j+1][i+1]);
				insertRoutCost(arrIdDeliver[j], idPickup, matrixDistance[j+1+m][i+1], matrixDuration[j+1+m][i+1]);
				insertRoutCost(arrIdPickup[j], idDeliver, matrixDistance[j+1][i+1+m], matrixDuration[j+1][i+1+m]);
				insertRoutCost(arrIdDeliver[j], idDeliver, matrixDistance[j+1+m][i+1+m], matrixDuration[j+1+m][i+1+m]);
				
			}
		}
	}
	public boolean set(String[] tmp,int prm)
	{
		switch(prm)
		{
		case PICKUP:
			pickup = tmp;
			return true;
		case DELIVER:
			deliver = tmp;
			return true;
		default:
			return false;
		}
	}
	public boolean set(int[] tmp,int prm)
	{
		switch(prm)
		{
		case DURATION_PICKUP:
			durationP = tmp;
			return true;
		case DURATION_DELIVER:
			durationD = tmp;
			return true;
		case WEIGHT:
			weight = tmp;
			return true;
		case EP:
			Ep = tmp;
			return true;
		case LP:
			Lp = tmp;
			return true;
		case ED:
			Ed = tmp;
		case LD:
			Ld = tmp;
		default:
			return false;
			
		}
	}
	public void set(String dp)
	{
		depot = dp;
	}
	public void set(int[][] mt,int[][] du)
	{
		matrixDistance = mt;
		matrixDuration = du;
	}
	public boolean set(int x,int prm)
	{
		if(prm == NUMBER_VEHICLE)
		{
			numVehicle = x;
		}else
		if(prm == CAPACITY)
		{
			capacity = x;
		}
		else
		{
			return false;
		}
		return true;
	}
	/* public Connection createConnection() throws IOException, ClassNotFoundException, SQLException {
		 
	        Connection connection;
	         
	        Properties prop = new Properties();
	        System.out.println("test");
	        prop.load(new FileInputStream(System.getProperty("user.home") + "/mydb.cfg"));
	        System.out.println("user.home: "+System.getProperty("user.home"));
	        String host = prop.getProperty("host").toString();
	        String username = prop.getProperty("username").toString();
	        String password = prop.getProperty("password").toString();
	        String driver = prop.getProperty("driver").toString();
	 
	        System.out.println("host: " + host + "username: " + username + "password: " + password + "\ndriver: " + driver);
	 
	        Class.forName(driver);
	        System.out.println("--------------------------");
	        System.out.println("DRIVER: " + driver);
	        connection = DriverManager.getConnection(host, username, password);
	        System.out.println("CONNECTION: " + connection);
	 
	        return connection;
	    }
	 ComModel()
	 {
		 try
		 {
			 conn = createConnection();
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }*/
	
	ComModel()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance ();
//			System.out.println("OK");
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		try {
			String userName = "root";
	        String password = "1234567890";
	        String url = "jdbc:mysql://127.0.0.1:3306/darp?useUnicode=true&characterEncoding=UTF-8";
	        //String url = "jdbc:mysql://mysql-dial-a-ride.whelastic.net/darp";
	        conn = DriverManager.getConnection (url,userName, password);

		    if(!conn.isClosed())
		    {
                System.out.println("Successfully connected to " + "MySQL server using TCP/IP...");
		    }else
		    {
		    	System.out.println("Fail");
		    }
	
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}

	}
	
	public void deleteSession(int [] x) throws SQLException
	{
		for(int i:x)
		{
			deleteSession(i);
		}
	}
	public void deleteSession(int id) throws SQLException
	{
		
		ResultSet rs = selectTable(TABLE_SESSION, "id = "+id);
		rs.next();
		int depot = rs.getInt("depot");
		System.out.println("depot: "+depot);
//		deleteTable(TABLE_REQUEST, "id_session = "+id);
		rs = selectTable(TABLE_REQUEST, "id_session = "+id);
		while(rs.next())
		{
			int id_p = rs.getInt("id_pickup");
			int id_d = rs.getInt("id_deliver");			
			/*deleteTable(TABLE_ROUTCOST, "id_position_1 = "+id_p);
			deleteTable(TABLE_ROUTCOST, "id_position_2 = "+id_p);
			deleteTable(TABLE_ROUTCOST, "id_position_1 = "+id_d);
			deleteTable(TABLE_ROUTCOST, "id_position_2 = "+id_d);*/
			deleteTable(TABLE_POSITIONS, "id = "+id_p);
			deleteTable(TABLE_POSITIONS, "id = "+id_d);						
		}
		deleteTable(TABLE_REQUEST, "id_session = "+id);
		
//		deleteTable(TABLE_SESSION, "id = "+id);	
		deleteTable(TABLE_POSITIONS, "id = "+depot);
	}
	public TreeMap<String, String> getSession(int id) throws SQLException
	{
		TreeMap<String, String> data = new TreeMap<String, String>();
		data.put("id", id+"");
		ResultSet session = selectTable(TABLE_SESSION, "id = "+id);
		int depot = -1;		
		if(session.next())
		{
			depot = session.getInt("depot");
			data.put("num_vehicle", session.getInt("num_vehicle")+"");
			data.put("capacity", session.getInt("capacity")+"");
		}
		ResultSet rs = selectTable(TABLE_POSITIONS, "id = "+depot);
		if(rs.next())
		{
			data.put("depot", rs.getString("Latlng"));
		}
		return data;
	}
	public ArrayList<TreeMap<String, String>> getRequestsSession(int[] list) throws SQLException
	{
		ArrayList<TreeMap<String, String>> data = new ArrayList<TreeMap<String,String>>();
		for(int ids:list)
		{
			data.addAll(getRequestsSession(ids));
		}
		return data;
	}
	public ArrayList<TreeMap<String, String>> getRequestsSession(int idSession) throws SQLException
	{			
		ResultSet requests = selectTable(TABLE_REQUEST, "id_session = "+idSession);
		
		
		ArrayList<TreeMap<String, String>> data = new ArrayList<TreeMap<String,String>>();
		
		while(requests.next())
		{
			TreeMap<String, String> rq = new TreeMap<String, String>();
			rq.put("id", requests.getInt("id")+"");
			rq.put("weight", requests.getInt("weight")+"");
			rq.put("Ep", requests.getString("Ep"));
			rq.put("Ed", requests.getString("Ed"));
			rq.put("Lp", requests.getString("Lp"));
			rq.put("Ld", requests.getString("Ld"));
			int idP = requests.getInt("id_pickup");
			int idD = requests.getInt("id_deliver");
			ResultSet setP = selectTable(TABLE_POSITIONS, "id = "+idP);
			ResultSet setD = selectTable(TABLE_POSITIONS, "id = "+idD);
			if(setP.next())
			{
				rq.put("pickup", setP.getString("LatLng"));
				rq.put("duration_pickup", setP.getInt("duration")+"");
			}
			if(setD.next())
			{
				rq.put("deliver", setD.getString("LatLng"));
				rq.put("duration_deliver", setD.getInt("duration")+"");
			}
			
			data.add(rq);
		}
		return data;
	}
	public ArrayList<Integer> getSetIdSession() throws SQLException
	{
		ArrayList<Integer> data = new ArrayList<Integer>();
		ResultSet rs = selectTable(TABLE_SESSION);
		int i = 0;
		int length = rs.getRow();
		System.out.println("len: "+length);
		System.out.println("len: "+rs.getFetchSize());
		//data = new int[length];
		while(rs.next())
		{
			//data[i] = rs.getInt("id");
			data.add(rs.getInt("id"));
			i++;
		}
		System.out.println("leni: "+i);
		return data;
	}
	public ResultSet selectTable(String nTable) throws SQLException
	{
		String query = "SELECT * FROM "+nTable;
		PreparedStatement prs = conn.prepareStatement(query);
		return prs.executeQuery();
	}
	public ResultSet selectTable(String nTable,String where) throws SQLException
	{
		String query = "SELECT * FROM "+nTable+" WHERE "+where;
		PreparedStatement prs = conn.prepareStatement(query);
		return prs.executeQuery();
	}
	public void deleteTable(String nTable, String where) throws SQLException
	{
		String query = "DELETE FROM "+nTable+" WHERE "+where;
		PreparedStatement prs = conn.prepareStatement(query);
		prs.execute();
	}
	
	public void close()
	{
	        if (conn != null)
	        {
	            try
	            {
	                conn.close ();
	                System.out.println ("Dong ket noi");
	            }
	            catch (Exception e) { /* bo qua loi luc dong csdl */ }
	        }
	}
	public void insertRoutCost(int idP1, int idP2, float distance, float duration) throws SQLException
	{
		String query = "INSERT INTO ROUTCOST (id_position_1,id_position_2,distance,duration) VALUES (?,?,?,?)";
		PreparedStatement prs = conn.prepareStatement(query);
		prs.setInt(1, idP1);
		prs.setInt(2, idP2);
		prs.setFloat(3, distance);
		prs.setFloat(4, duration);
		prs.execute();
	}
	public int insertMarker(String latlng,int duration) throws SQLException
	{
		String query = "INSERT INTO POSITIONS (LatLng,duration) VALUES (?,?)";
		PreparedStatement prs = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		prs.setString(1, latlng);
		prs.setInt(2, duration);
		prs.execute();
		ResultSet idM = prs.getGeneratedKeys();
		idM.next();
		return idM.getInt(1);
	}
	public int insertSession(int idDepot,int numVehicle, int capacity) throws SQLException
	{		
		String query = "INSERT INTO SESSIONS (depot,num_vehicle,capacity) VALUES (?,?,?)";
		PreparedStatement prs = conn.prepareStatement(query);

		prs.setInt(1, idDepot);
		prs.setInt(2, numVehicle);
		prs.setInt(3, capacity);

		prs.execute();

		ResultSet idS = prs.getGeneratedKeys();
		idS.next();
		return idS.getInt(1);
	}
	public int insertRequest(int idp,int idd, int weight, int Ep,int Lp, int Ed, int Ld, int idsession) throws SQLException
	{
		String query = "INSERT INTO REQUEST (id_pickup,id_deliver,weight,Ep,Lp,Ed,Ld,id_session)"
				+"VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement prs = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		prs.setInt(1, idp);
		prs.setInt(2, idd);
		prs.setInt(3, weight);
		prs.setInt(4, Ep);
		prs.setInt(5, Lp);
		prs.setInt(6, Ed);
		prs.setInt(7, Ld);
		prs.setInt(8, idsession);
		prs.execute();
		ResultSet idS = prs.getGeneratedKeys();
		idS.next();
		return idS.getInt(1);
	}
	public static void main(String[] args) {
		ComModel cm = new ComModel();
	}
}
