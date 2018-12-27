package Utilities;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import MainRunner.TestingConstants;

public class JobRunner {

	private String userName;
	private String password;
	private String host;
	public static String commandResults;
	//public String commandResults;
	
	public JobRunner() {
		loadDefaultCredentials();
	}
	private void loadDefaultCredentials() {
		this.userName = "shu";
		this.password = "mypass";
		this.host = "muhost";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public void runMe(String maincommand) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(getUserName(), getHost(), 22); // Creating SSH session
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(getPassword()); // Setting password for SSH connection
			AutomationLogCustom.info("user==" + getUserName() + "\n host==" + getHost());
			session.connect(); // Connecting to the session for SSH server
			AutomationLogCustom.info("connected to host ====" + getHost());
			String sudo_pass = password;
			Channel channel = session.openChannel("exec"); // opening a exec type SSH channel
			AutomationLogCustom.info("cd command");
			((ChannelExec) channel).setCommand(maincommand);
			((ChannelExec) channel).setPty(true);
			InputStream in = channel.getInputStream();
			OutputStream out = channel.getOutputStream();
			((ChannelExec) channel).setErrStream(System.err);
			channel.connect(); // Connecting to the channel for SSH server
			out.write((sudo_pass + "\n").getBytes());
			out.write(("\n").getBytes());
			out.flush();
			byte[] tmp = new byte[102400];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 102400);
					if (i < 0)
						break;
					commandResults = new String(tmp, 0, i);
					AutomationLogCustom.info(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					AutomationLogCustom.info("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
					AutomationLog.error(ee.getMessage(),ee);
				}
			}
			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			AutomationLog.error(e.getMessage(),e);
		}
	}
	
	public void sftpsriptUpload(String localfilepath, String Remotefilepath) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(getUserName(), getHost());
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(getPassword());
			session.connect();
			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			sftpChannel.put(TestingConstants.DIRECTORY + localfilepath, Remotefilepath);
			sftpChannel.disconnect();
			session.disconnect();
		} catch (Exception ex) {
			AutomationLog.error(ex.getMessage(),ex);
		}
	}
	
	public void sftpsriptDownload(String localfilepath, String Remotefilepath) {
		try {
			if(localfilepath == null) {
				localfilepath = TestingConstants.DIRECTORY;
			}
			JSch jsch = new JSch();
			Session session = jsch.getSession(getUserName(), getHost());
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(getPassword());
			session.connect();
			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			sftpChannel.get(Remotefilepath, localfilepath);
			sftpChannel.disconnect();
			session.disconnect();
		} catch (Exception ex) {
			AutomationLog.error(ex.getMessage(),ex);
		}
	}
	
	public void writeFileToLinux(String str_Content, String str_FileDirectory, String str_FileName) {
		JSch obj_JSch = new JSch();
		Session obj_Session = null;
		try {
			obj_Session = obj_JSch.getSession(getUserName(), getHost());
			// obj_Session.setPort(int_Port);
			obj_Session.setPassword(getPassword());
			Properties obj_Properties = new Properties();
			obj_Properties.put("StrictHostKeyChecking", "no");
			obj_Session.setConfig(obj_Properties);
			obj_Session.connect();
			Channel obj_Channel = obj_Session.openChannel("sftp");
			obj_Channel.connect();
			ChannelSftp obj_SFTPChannel = (ChannelSftp) obj_Channel;
			obj_SFTPChannel.cd(str_FileDirectory);
			InputStream obj_InputStream = new ByteArrayInputStream(str_Content.getBytes());
			obj_SFTPChannel.put(obj_InputStream, str_FileDirectory + str_FileName);
			obj_SFTPChannel.exit();
			obj_InputStream.close();
			obj_Channel.disconnect();
			obj_Session.disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
