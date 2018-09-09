package DataBases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcMYSQLServerConnection {
	
	static Connection con;
	static ResultSet rs;
	static Statement stmt;
	
	static String mySqlURL ="localhost:3306";
	static String databaseName ="seleniumdemodb";
	static String databaseUsername = "root";
	static String databasepassword = "";
	
	private static ResultSet connectionSetup(String Query) throws SQLException, ClassNotFoundException {

    	 	con = DriverManager.getConnection(
		  "jdbc:mysql://"+mySqlURL+"/"+databaseName+"", databaseUsername, databasepassword);
		
		 stmt = con.createStatement();
		 rs = stmt.executeQuery(Query);
		 return rs;
	 } 

	public ResultSet dataBaseQuery(String Query) throws ClassNotFoundException, SQLException {
		ResultSet rs =connectionSetup(Query);
		return rs;
	}

	public void closeConnection() throws SQLException {
		rs.close();
		stmt.close();
		con.close();
	}
}
