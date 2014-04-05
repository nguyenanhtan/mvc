package com.javatpoint;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ComModel {

	Connection conn = null;
	ComModel()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
//			System.out.println("OK");
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		try {
			
		    conn =
		       DriverManager.getConnection("jdbc:mysql://localhost/darp?" +
		                                   "user=root&password=1234567890");
		    if(!conn.isClosed())
		    {
                System.out.println("Successfully connected to " + "MySQL server using TCP/IP...");
		    }else
		    {
		    	System.out.println("Fail");
		    }
            conn.close();
		    // Do something with the Connection
	
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	public static void main(String[] args) {
		ComModel cm = new ComModel();
	}
}
