package com.mine.sftp.client;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;
import java.util.Date;
import java.util.Vector;

/**
 * Sftp Client.
 * 
 * @author haroon
 */
public class SftpClient {
	private Logger logger = Logger.getInstance();
	private Session session;
	private ChannelSftp channel;

	public void process(){
		logger.info("----------------------- Start Processing -----------------------");

		try {
			this.open();

			// All Files
			Vector<ChannelSftp.LsEntry> ls = channel.ls(".");//TODO if List too long like more than 10,000
			logger.info("Total file found are: "+ls.size());
			int i = 1;
			for (ChannelSftp.LsEntry lsEntry : ls) {
				if(!lsEntry.getAttrs().isDir()) {
					logger.info("Processing file No. ["+i+"] with name ["+lsEntry.getFilename()+"]");
					int mTime = lsEntry.getAttrs().getMTime();
					logger.info("Downloading ["+lsEntry.getFilename()+"]");
					String localFileName = SystemConstant.LOCAL_WORKING_DIR + File.separator + lsEntry.getFilename();
					channel.get(lsEntry.getFilename(), localFileName);
					File downloadedFile = new File(localFileName);
					downloadedFile.setLastModified(((long)mTime)*1000L);
					downloadedFile.lastModified();
					i++;
				}
			}


		}catch (Exception e){
			logger.error(e.getMessage());
			logger.error(Util.getStackTraceAsString(e));
			e.printStackTrace();
			this.close();
		}finally {
			this.close();
		}

		logger.info("-----------------------    Processed     -----------------------");
	}


	private void open(){
		try {

			logger.info("Opening Connection...");
			JSch jsch = new JSch();
			jsch.addIdentity(SystemConstant.PRI_KEY_FILE, SystemConstant.PRI_KEY_PWD);
			session = jsch.getSession(SystemConstant.SFTP_USER,SystemConstant.SFTP_HOST);
			session.setConfig("StrictHostKeyChecking", "no");
//			session.setPassword(SFTP_PWD);
			session.connect();
			channel = (ChannelSftp)session.openChannel("sftp");
			channel.connect();
//			channel.cd(SFTP_WORKING_DIR);
			logger.info("Connection Opened");
		} catch (Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private void close(){
		logger.info("Closing Connection...");
		try {
			channel.disconnect();
			session.disconnect();
		}catch (Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.info("Connection Closed");
	}























	/*public static void main(String[] arg) {

		String SFTPHOST = "N16339";
		int    SFTPPORT = 22;
		String SFTPUSER = "admin";
		String passphrase = "Aa123456";
		String SFTPWORKINGDIR = "remote directory";
		String prikeyfile = "C:\\Open SSH Key.ppk";
		String WORKING_DIR = "C:\\Users\\hpadhyar\\ZSFTP";


		Session     session     = null;
		Channel     channel     = null;
		ChannelSftp channelSftp = null;

		try{
			JSch jsch = new JSch();
//			jsch.addIdentity(prikeyfile, passphrase);
			session = jsch.getSession(SFTPUSER,SFTPHOST);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(passphrase);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp)channel;
//			channelSftp.cd(SFTPWORKINGDIR);
//			byte[] buffer = new byte[1024];
//			BufferedInputStream bis = new BufferedInputStream(channelSftp.get("AHHH.txt"));
//			File newFile = new File("C:\\Users\\hpadhyar\\AHHH_New.txt");
//			OutputStream os = new FileOutputStream(newFile);
//			BufferedOutputStream bos = new BufferedOutputStream(os);
//			int readCount;
//
//			while
//			( (readCount = bis.read(buffer)) > 0) {
//				System.out.println("Writing files to disk: " );
//				bos.write(buffer, 0, readCount);
//			}
//			bis.close();
//			bos.close();

			channelSftp.lcd(WORKING_DIR);
			channelSftp.lcd(WORKING_DIR);
			Vector<ChannelSftp.LsEntry> ls = channelSftp.ls("All Files"); //TODO if List too long like more than 10,000
			for (ChannelSftp.LsEntry l : ls) {
				System.out.println(l.getFilename());
//				System.out.println(l.getAttrs().getATime());
//				System.out.println(l.getAttrs().getAtimeString()+"\n");
//				System.out.println(l.getAttrs().getMTime());
//				System.out.println(l.getAttrs().getMtimeString());
			}

			System.out.println("Downloading AHHH.txt ...");
			channelSftp.get("AHHH.txt","C:\\Users\\hpadhyar\\AHHH_New.txt");
			System.out.println("Downloaded AHHH.txt");
		}catch(Exception ex){
			ex.printStackTrace();

		}

	}*/



}
