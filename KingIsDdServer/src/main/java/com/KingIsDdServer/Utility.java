package com.KingIsDdServer;

import static java.nio.file.StandardOpenOption.WRITE;

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

	public static void writeFile(String filePath, String message) {
		try {
			message= message+"\n";
			Path path = Paths.get(filePath);
			OutputStream outputStream = Files.newOutputStream(path, WRITE);

			outputStream.write(message.getBytes());

			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readFile(String filename, boolean canBreak, String playerName) throws InterruptedException, IOException {
		String line;
		try {
			LineNumberReader lnr =new LineNumberReader(new FileReader(filename));
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
		List<String> messageDetailsList = new ArrayList<>(Arrays.asList(messageDetails));
		
		return false;

	}
}
