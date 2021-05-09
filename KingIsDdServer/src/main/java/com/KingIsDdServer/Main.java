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
		
    	
		
		ArrayList<String> playerSeq = GameProcessing.PlayerTurn();
		
		
		String result = "C";
		ExitGame = false;
		System.out.println("after");
		
		while(ExitGame == false) {
		
			for(int i = 1 ; i<=8 ; i ++) {
			passCount = 0;
				while(passCount < 3) {
					
					Boolean canBreak = false;
					playerTurn(playerSeq.get(0));
					System.out.println("Player " + playerSeq.get(0) + "Turn");
					Utility.readFile(readFile, canBreak, playerSeq.get(0));
					
					
					
					if(canBreak.equals(true)) {
						canBreak = false;
						playerTurn(playerSeq.get(1));
						System.out.println("Player " + playerSeq.get(1) + "Turn");
						Utility.readFile(readFile, canBreak, playerSeq.get(1));
					}
					
					if(canBreak.equals(true)) {
						canBreak = false;
						playerTurn(playerSeq.get(2));
						System.out.println("Player " + playerSeq.get(2) + "Turn");
						Utility.readFile(readFile, canBreak, playerSeq.get(2));
					}
				}
				GameProcessing.powerStruggle();
		
			}
			GameProcessing.winnerMessage(result);
		}
	}

	private static void playerTurn(String playerName) {
		// TODO Auto-generated method stub
		StringBuilder message = new StringBuilder(Constant.MESSAAGE_06).append(":");
		message.append(playerName);
		for(int i = 1 ; i <= 3 ; i ++)
			Utility.writeFile(Utility.getInstance().getFileWritePath() + "P"+i , message.toString());
		
	}
}
