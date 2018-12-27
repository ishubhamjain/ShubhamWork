package DataBases;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import Utilities.YmlReader;

public class DataBaseSource {

	private static final String SYBASE="SYBASE";
	private static final String ORACLE="ORACLE";
	private static DataBaseSource instance;
	private String drivername = "net.sf.log4jdbc.sql.jdbcapi.DriverSpy";
	private Map<String, DataSource> dataSource;

	private DataBaseSource(){
		YmlReader.readYmlProperty();
		initDataSource();
	}

	public static DataBaseSource getInstance() {
		if (instance == null) {
			instance = new DataBaseSource();
		}
		return instance;
	}

	public DataSource setupDataSource(String url, String username, String password) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(drivername);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}

	public void initDataSource() {
		if (dataSource == null) {
			dataSource = new HashMap<>();
			Set<Entry<String, FunctionalId>> setEntry = YmlReader.functionId.entrySet();
			for (Entry<String, FunctionalId> entry : setEntry) {
				FunctionalId id = entry.getValue();
				switch (id.getDbType()) {
				case SYBASE:
					System.out.println("i am in Sybase section");
			//		dataSource.put(SYBASE,setupDataSource("jdbc:log4jdbc:sybase:Tds:"+id.getHost()+":"+id.getPort()+"?CHARSET=utf8&ENCRYPT_PASSWORD=true&JCE_PROVIDER_CLASS=org.bouncycastle.jce.provider.BouncyCastleProvider",id.getUsername(),decryptPassword(id.getPassword(),id.isUseFunctionalId())));
			//		dataSource.put(SYBASE,setupDataSource("jdbc:log4jdbc:sybase:Tds:"+id.getHost()+":"+id.getPort()+"?CHARSET=utf8&ENCRYPT_PASSWORD=true&JCE_PROVIDER_CLASS=org.bouncycastle.jce.provider.BouncyCastleProvider",id.getUsername()),"");
					break;
				case ORACLE:
					System.out.println("i am in oralce section");
					dataSource.put(ORACLE,setupDataSource("jdbc:log4jdbc:oracle:thin:@"+id.getHost()+":"+id.getPort()+":"+entry.getKey(),id.getUsername(),id.getPassword()));
					break;
				default:
					break;
				}
			}
		}
	}

	/*private String decryptPassword(String password, boolean useFunctionalId) {
		String decryptpass = null ;
		PasswordUtil passutil = new PasswordUtil();
		passutil.setSecurityFile(new File(getAbsolutePath(TestingConstants.SECURITY_FILE)));
		try {
			if(useFunctionalId) {
				decryptpass = passutil.getDecryptedPassword(password);
			}else {
				decryptpass = password;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptpass;
	}*/
	
	public String getAbsolutePath(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		String path = new File(classLoader.getResource(fileName).getFile()).getAbsolutePath();
		System.out.println(path);		
		return path;
	}

	public Connection getSybaseConnection() throws SQLException {
		if(dataSource.get(SYBASE) == null) {
			throw new SQLException("Sybase datasource not configured");
		}
		return dataSource.get(SYBASE).getConnection();
	}

	public Connection getOracleDatabaseConnection() throws SQLException {
		if(dataSource.get(ORACLE) == null) {
			throw new SQLException("Oracle datasource not configured");
		}
		return dataSource.get(ORACLE).getConnection();
	}

	public void destroyDataSource() throws SQLException {
		if(dataSource != null) {
			Set<Entry<String,DataSource>> destorySource =  dataSource.entrySet();
			for(Entry<String,DataSource> entry : destorySource) {
				BasicDataSource bds = (BasicDataSource) entry.getValue();
	    	    bds.close();
			}
		}
		
	}

}
