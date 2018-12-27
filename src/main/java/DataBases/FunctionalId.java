package DataBases;

public class FunctionalId {

	private String id;
	private String dbType;
	private String host;
	private String port;
	private String username;
	private String password;
	private boolean useFunctionalId;
	private String encryptPassword;
	
	
	public boolean isUseFunctionalId() {
		return useFunctionalId;
	}
	public void setUseFunctionalId(boolean useFunctionalId) {
		this.useFunctionalId = useFunctionalId;
	}
	public String getEncryptPassword() {
		return encryptPassword;
	}
	public void setEncryptPassword(String encryptPassword) {
		this.encryptPassword = encryptPassword;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		if(isUseFunctionalId()) {
			setPassword(getEncryptPassword());
		}
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
