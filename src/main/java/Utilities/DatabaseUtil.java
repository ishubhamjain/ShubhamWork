package Utilities;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DataBases.DataBaseSource;



public class DatabaseUtil {
	
	private DatabaseUtil() {
		throw new IllegalStateException("Hiding public constructor");
	}
	/**
	 * 
	 * @return connection for Sybase Database
	 */
	public static Connection getDatabaseConnection(){
		Connection conn = null;
		try {
			conn = DataBaseSource.getInstance().getSybaseConnection();
		} catch (SQLException e) {
			AutomationLog.error(e.getMessage(),e);
		}
		return conn;
	}
	/**
	 * 
	 * @return connection for Oracle Database
	 */
	public static Connection getOracleDatabaseConnection(){
		Connection conn = null;
		try {
			conn = DataBaseSource.getInstance().getOracleDatabaseConnection();
		} catch (SQLException e) {
			AutomationLog.error(e.getMessage(),e);
		}
		return conn;
	}

	public static void close(Connection conn, Statement ps, ResultSet rs) {
		try {
			if (conn != null)
				conn.close();
			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			AutomationLog.error(e.getMessage(),e);
		}
	}
	
	public static void close(Connection conn, Statement ps) {
		try {
			conn.close();
			ps.close();
		} catch (SQLException e) {
			AutomationLog.error(e.getMessage(),e);
		}
	}

	public static void close(PreparedStatement ps) {
		try {
			ps.close();
		} catch (SQLException e) {
			AutomationLog.error(e.getMessage(),e);
		}
	}
}

