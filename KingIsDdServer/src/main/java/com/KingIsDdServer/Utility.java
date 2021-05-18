package com.KingIsDdServer;

import static java.nio.file.StandardOpenOption.WRITE;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Utility {

	private String writefilepath;
	private String readfilepath;
	private static volatile Utility Utility = null;
	int count= 0;

	Utility() {
		// private constructor
	}

	// singleton object creation
	public static Utility getInstance() {
		if (Utility == null) {
			synchronized (Utility.class) {
				if (Utility == null) {
					Utility = new Utility();
				}
			}
		}
		return Utility;
	}

	public String getFileWritePath() {
		return writefilepath;
	}

	public void setFileWritePath(String fileWritePath) {
		this.writefilepath = fileWritePath;
	}

	public static void writeFile(String filePath, String message) {
		
		try {
			System.out.println("[write file ]  message "+message);		
			Path path = Paths.get(filePath);
			OutputStream outputStream = Files.newOutputStream(path, WRITE);
			outputStream.write(message.getBytes());
			BufferedInputStream reader = new BufferedInputStream(new FileInputStream( filePath ) );

			while(reader.available() > 0) {
				
			}
			
			reader.close();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void readFile(String filename, boolean canBreak, String playerName) throws InterruptedException, IOException {
		String line;
		try {
			String fileName = filename + playerName;
			LineNumberReader lnr =new LineNumberReader(new FileReader(fileName));
			while (!canBreak) {
				line = lnr.readLine();
				if (line == null || line.isEmpty()) {				
					Thread.sleep(2400);
					continue;
				}
				canBreak = parseMessage(line,playerName);
				if (canBreak)
					break;
			}
			lnr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Boolean parseMessage(String message,String playerName) throws InterruptedException {
		String[] messageArray = message.split(":");
		String[] messageDetails = messageArray[1].split(",");
		String messageNumber = messageArray[0];
		System.out.println(" out messageNumber" + messageNumber);
		List<String> messageDetailsList = new ArrayList<>(Arrays.asList(messageDetails));
		
		if ("15".equals(messageNumber)) {
			GameProcessing.pass(messageNumber, messageDetailsList);
			return true;
		}
		else if("07".equals(messageNumber) || "08".equals(messageNumber) || "09".equals(messageNumber) || "10".equals(messageNumber) || "11".equals(messageNumber)) {
			GameProcessing.addForCard(messageNumber, messageDetailsList);
			System.out.println("in messageNumber");
			GameProcessing.clearPassCount();
			return false;
		}
		else if("12".equals(messageNumber)) {
			System.out.println(messageDetailsList);			
			GameProcessing.SupporterDraw(messageNumber, messageDetailsList,playerName);
			return true;
		}
		else 
			return false;
			
	}

	public String getReadfilepath() {
		return readfilepath;
	}

	public void setReadfilepath(String readfilepath) {
		this.readfilepath = readfilepath;
	}
}
