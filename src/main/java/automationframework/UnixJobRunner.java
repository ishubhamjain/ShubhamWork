package automationframework;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;

public class UnixJobRunner {

	public static void runMe(String command, String host, String user, String password) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, 22);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			;
			session.setPassword(password);
			System.out.println("user==" + user + "\n host==" + host);
			session.connect();
			System.out.println("connected to host ====" + host);
			String sudo_pass = "demo123";

			Channel channel = session.openChannel("exec");

			System.out.println("cd command");

			/*
			 * ((ChannelExec) channel).setCommand(
			 * "cd ~demouser/bin;ls; ( echo 'echo Automation' && echo 'command' )  | pbrun democommand"
			 * )
			 */;
			((ChannelExec) channel).setCommand(command);
			((ChannelExec) channel).setPty(true);

			InputStream in = channel.getInputStream();
			OutputStream out = channel.getOutputStream();
			((ChannelExec) channel).setErrStream(System.err);

			channel.connect();

			out.write((sudo_pass + "\n").getBytes());
			out.write(("\n").getBytes());

			out.flush();

			byte[] tmp = new byte[102400];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 102400);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
					ee.printStackTrace();
					System.out.println(ee.getMessage());
				}
			}
			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void sftpFileUpload(String localFilePath, String remoteFilePath, String host, String user,
			String password) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, 22);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();

			sftpChannel.put(localFilePath, remoteFilePath);
			sftpChannel.disconnect();
			session.disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void sftpFileDownload(String localFilePath, String remoteFilePath, String host, String user,
			String password) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, 22);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();

			sftpChannel.get(remoteFilePath, localFilePath);
			sftpChannel.disconnect();
			session.disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void writeFile(String str_Host, String str_Username, String str_Password, String str_FileDirectory,
			String str_FileName, String str_Content) {
		JSch obj_JSch = new JSch();
		Session obj_Session = null;
		try {
			obj_Session = obj_JSch.getSession(str_Username, str_Host);
			// obj_Session.setPort(int_Port);
			obj_Session.setPassword(str_Password);
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