package com.KingIsDdServer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static Boolean ExitGame;

	public static int passCount;

	public static void main(String[] args) throws IOException, InterruptedException {

		String readFile = "/tmp/allfrom";
		String writeFile = "/tmp/allto";
		Utility.getInstance().setFileWritePath(writeFile);
		Utility.getInstance().setReadfilepath(readFile);
		HashMap<String, Integer>  followerMap = new HashMap<String, Integer> () ;
		followerMap.put("B", 18);
		followerMap.put("R", 18);
		followerMap.put("Y", 18);
		
		GameParameter.getInstance().setFollowerMap(followerMap);
//		GameParameter.getInstance().setRedFollower(18);
//		GameParameter.getInstance().setBlueFollower(18);
//		GameParameter.getInstance().setYellowFollower(18);
	
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

		System.out.println("Board has been initialised.");
		//Utility.readAllFile(ExitGame, readFile, writeFile);
		
		System.out.println("Selecting player to start with!!");
		
    	
		
		ArrayList<String> playerSeq = GameProcessing.PlayerTurn();
		
		
		String result = "C";
		//need to check exit logic
		ExitGame = false;
		System.out.println("after");
		
		while(ExitGame == false) {
		
			for(int i = 1 ; i<=8 ; i ++) {
			//need to check pass logic
			passCount = 0;
				while(true) {
					// System.out.println("PAssCount 1:" + passCount);

					Boolean canBreak = false;
					playerTurn(playerSeq.get(0));
					System.out.println("------Player " + playerSeq.get(0) + " Turn------");
					Utility.readFile(readFile, canBreak, playerSeq.get(0));
					 if( checkPassCount()) {
						 break;
					 }
					// System.out.println("PAssCount 2:" + passCount);
						canBreak = false;
						playerTurn(playerSeq.get(1));
						System.out.println("------Player " + playerSeq.get(1) + " Turn------");
						Utility.readFile(readFile, canBreak, playerSeq.get(1));

						 if( checkPassCount()) {
							 break;
						 }
						 
						// System.out.println("PAssCount 3:" + passCount);

						canBreak = false;
						playerTurn(playerSeq.get(2));
						System.out.println("------Player " + playerSeq.get(2) + "Turn------");
						Utility.readFile(readFile, canBreak, playerSeq.get(2));
					
						 if( checkPassCount()) {
							 break;
						 }
		
			}

				ExitGame = GameProcessing.powerStruggle();

		}
			GameProcessing.winnerMessage(result);

	}
		
}
	private static void playerTurn(String playerName) {
		// TODO Auto-generated method stub
		StringBuilder message = new StringBuilder(Constant.MESSAAGE_06).append(":");
		message.append(playerName);
		for (int i = 1; i <= 3; i++)
			Utility.writeFile(Utility.getInstance().getFileWritePath() + "P" + i, message.toString());

	}

	private static Boolean checkPassCount() {
		//System.out.println("IN PAssCount :" + passCount);
		if (Main.passCount >= 3) {
			return true;
		}
		return false;
	}
}
