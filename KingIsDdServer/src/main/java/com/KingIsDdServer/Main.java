package com.KingIsDdServer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static Boolean ExitGame;


	public static int passCount;
	
	public static void main(String[] args) throws IOException, InterruptedException {

		String readFile = "/tmp/From";
		String writeFile = "/tmp/To";
		Utility.getInstance().setFileWritePath(writeFile);
		Utility.getInstance().setReadfilepath(readFile);
		
		GameParameter.getInstance().setRedFollower(18);
		GameParameter.getInstance().setBlueFollower(18);
		GameParameter.getInstance().setYellowFollower(18);
	
		GameParameter.getInstance().setRedControlDisk(8);
		GameParameter.getInstance().setBlueControlDisk(8);
		GameParameter.getInstance().setYellowControlDisk(8);
		
		GameParameter.getInstance().setBlackDisk(3);
		
		
		for (int i = 1; i <= 3; i++) {

			String playerName = "P" + i;
			String readPath = readFile + playerName;
			String writePath = writeFile + playerName;

			File readFileName = new File(readPath);
			readFileName.deleteOnExit();

			File writeFileName = new File(writePath);
			writeFileName.deleteOnExit();

			writeFileName.setReadable(true);
			writeFileName.setWritable(true);
			readFileName.setReadable(true);
			readFileName.setWritable(true);
			readFileName.setExecutable(true);
			writeFileName.setExecutable(true);

			Runtime.getRuntime().exec(new String[] { "mkfifo", "-m", "777", readFileName.toPath().toString() });
			Runtime.getRuntime().exec(new String[] { "mkfifo", "-m", "777", writeFileName.toPath().toString() });

		}

		System.out.println("fisrt");
		GameProcessing.initializeFile(writeFile);

		System.out.println("second");
		GameProcessing.distributeFoll(writeFile);
		System.out.println("third");
		GameProcessing.distributeReg(writeFile);

		System.out.println("All messages processed.");
		//Utility.readAllFile(ExitGame, readFile, writeFile);
		
		System.out.println("Selecting player to start with!!");
		
    	
		
		ArrayList<String> playerSeq = GameProcessing.PlayerTurn(writeFile);
		Boolean canBreak = false;
		
		String result = "C";
		ExitGame = false;
		System.out.println("after");
		
		while(ExitGame == false) {
		
			for(int i = 1 ; i<=8 ; i ++) {
			passCount = 0;
				while(passCount < 3) {
					Utility.readFile(readFile, canBreak, playerSeq.get(0));
					
					if(canBreak.equals(true)) {
						canBreak = false;
						Utility.readFile(readFile, canBreak, playerSeq.get(1));
					}
					
					if(canBreak.equals(true)) {
						canBreak = false;
						Utility.readFile(readFile, canBreak, playerSeq.get(2));
					}
				}
				GameProcessing.powerStruggle();
		
			}
			GameProcessing.winnerMessage(result);
		}
	}
}
