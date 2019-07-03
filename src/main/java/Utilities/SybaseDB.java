package Utilities;

/**
 * @author Shubham Jain
 * */
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

//import net.sourceforge.jtds.jdbc.*;
public class SybaseDB {

    private static Connection conn;

    public static int deleteComponent(String commond)
    throws ClassNotFoundException, SQLException, InterruptedException
    {
		int result = 0;
    	try {
        connect();
        AutomationLog.info("Got connected OK");
        Statement stmt = conn.createStatement();
        stmt.execute(commond);
        AutomationLog.info("delete query executed successfully");
       // rs.close();
		stmt.close();
			return result;
    	}
    	catch(Exception ex) {
    		AutomationLog.error(ex.getMessage());
    		ex.printStackTrace();
    		return 0;
    	}
    }
    
    public static int updateComponent(String commond)throws ClassNotFoundException, SQLException, InterruptedException
    	    {
    			int result = 0;
    	    	try {
    	        connect();
    	        AutomationLog.info("Got connected OK");
    	        Statement stmt = conn.createStatement();
    	        stmt.execute(commond);
    	        AutomationLog.info("updation query executed successfully");
    			stmt.close();
    			result=1;
    			return result;
    	    	}
    	    	catch(Exception ex) {
    	    		AutomationLog.error(ex.getMessage());
    	    		ex.printStackTrace();
    	    		return 0;
    	    	}
    	    }
    
    public static int insertComponent(String commond)throws ClassNotFoundException, SQLException, InterruptedException
    {
		int result = 0;
    	try {
        connect();
        AutomationLog.info("Got connected OK");
        Statement stmt = conn.createStatement();
        stmt.execute(commond);
        AutomationLog.info("Inseration query executed successfully");
		stmt.close();
		result=1;
		return result;
    	}
    	catch(Exception ex) {
    		AutomationLog.error(ex.getMessage());
    		ex.printStackTrace();
    		return 0;
    	}
    }
    
    public static ResultSet QueryComponent(String commond)
    	    throws ClassNotFoundException, SQLException, InterruptedException
    	    {
    	connect();
		AutomationLog.info("Got connected OK");
		Statement stmt = conn.createStatement();
		ResultSet rset =  stmt.executeQuery(commond);
    	    	try {
 
    	    		/////////////////////////Function////////////////////////////
    	    	/*	// Check the column count
    	    	      ResultSetMetaData md = rset.getMetaData();
    	    	     // System.out.println("Resultset has " + md.getColumnCount() + " cols.");
    	    	      int columnCount = md.getColumnCount();

    	    		while (rset.next())
    	    		{
    	    			//use below if you want full raw data
    	    			for(int i=1;i<=columnCount;i++) {
    	    				AutomationLog.info("\t" + rset.getString(i));
    	    			}
    	    			//use below if you want full raw data key wise
    	    			System.out.println("yes get the keys = "+rset.getInt("Deal"));
    	    		}*/
    	    		
    	    		/////////////////
    	    		
    	    		
    	        	AutomationLog.info("query executed successfully");
    	        	return rset;
    	    	}
    	    	catch(Exception ex) {
    	    		AutomationLog.error(ex.getMessage());
    	    	}
    	    	finally {
    	    		/*rset.close();
    	    		stmt.close();*/
    	    		//SybaseDB.close(conn, stmt, rset);
    	    	}
				return null;
    	    }

    public static void connect()
    throws ClassNotFoundException, SQLException
    {
/*        DriverManager.registerDriver
            (new net.sourceforge.jtds.jdbc.Driver());*/
        
        /*String hostAndport = YmlReader.getProperty("hostName")+":"+YmlReader.getProperty("port");
        String url = "jdbc:sybase:Tds:"+hostAndport+"?CHARSET=utf8&ENCRYPT_PASSWORD=true&JCE_PROVIDER_CLASS=org.bouncycastle.jce.provider.BouncyCastleProvider";
        conn = DriverManager.getConnection(url,YmlReader.getProperty("userName"),YmlReader.getProperty("password"));*/
    }
    
    /**
     * This method is used to read data from result set and store in to HashMap.
     * The Key of the Map is column name and value is ArrayList of that column values.
     * @param rs SQl result set
     * @return HashMap containing the table data.
     * @throws SQLException
     */
	public static HashMap<String, ArrayList<String>> getDataMap(ResultSet rs) throws SQLException {
		HashMap<String, ArrayList<String>> resultMap = new HashMap<String, ArrayList<String>>();
		SQLException ex = null;

		try {
			ResultSetMetaData metadata = rs.getMetaData();
			int columnCount = metadata.getColumnCount();
			if (columnCount > 0) {
				ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
				while (rs.next()) {
					ArrayList<String> row = new ArrayList<String>();
					for (int rowIndex = 1; rowIndex <= metadata.getColumnCount(); ++rowIndex) {
						String value = rs.getObject(rowIndex) == null ? null : rs.getObject(rowIndex).toString();
						row.add(value);
					}
					rows.add(row);
				}
				for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
					ArrayList<String> column = new ArrayList<String>();
					for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
						ArrayList<String> row = rows.get(rowIndex);
						for (int columnIndexInRow = 0; columnIndexInRow < row.size(); columnIndexInRow++) {
							if (columnIndex == columnIndexInRow) {
								column.add(row.get(columnIndexInRow));
								continue;
							}
						}
					}
					String columnName = metadata.getColumnName(columnIndex + 1);
					resultMap.put(columnName, column);
				}
			}
		} catch (SQLException e) {
			AutomationLog.error("Error in select/get queries ="+e.getMessage());
			ex = e;
		} finally {
			rs.close();
		}

		if (ex != null) {
		}

		return resultMap;
	}
	
	public static void checkEODDBProcessDate() {
		Connection connection = null;
		Statement ps = null;
		ResultSet rs = null;
		int currentProcessDate = 0;
		int tokyoCurrent = 0;
		try {
			connection = DatabaseUtil.getDatabaseConnection();
			ps = connection.createStatement();
			rs = ps.executeQuery("select * from EODDB..ProcessDate");
			while(rs.next()) {
				currentProcessDate = rs.getInt("CurrentProcessDate");
				tokyoCurrent = rs.getInt("tokyoCurrent");
			}
			int date = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			if(date!=currentProcessDate || date !=tokyoCurrent) {
				updateEODDBProcessDate();
			}
		}catch (SQLException e) {
			AutomationLog.error(e.getMessage(),e);
		}catch(Exception e){
			AutomationLog.error(e.getMessage(),e);
		}finally {
			DatabaseUtil.close(connection,ps,rs);
		}
	}

	private static void updateEODDBProcessDate() {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int counter = 1;
		try {
			connection = DatabaseUtil.getDatabaseConnection();
			ps = connection.prepareStatement("update EODDB..ProcessDate set CurrentProcessDate = ? ,TokyoCurrent = ?");
			rs = ps.executeQuery();
			while(rs.next()) {
				ps.setInt(counter++, Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date())));
				ps.setInt(counter++, Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date())));
			}
		}catch (SQLException e) {
			AutomationLog.error(e.getMessage(),e);
		}finally {
			DatabaseUtil.close(connection,ps,rs);
		}
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
			AutomationLog.error(e.getMessage());
		}
	}

}

