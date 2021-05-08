package com.KingIsDdServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameProcessing {
	public static ArrayList<String> CardsPlayedByPlayerInfo = new ArrayList<String>();

	public static void initializeFile(String filePath) throws InterruptedException {

		Random random = new Random();

		StringBuilder message = new StringBuilder(Constant.MESSAAGE_02).append(":");
		message.append(Constant.MORAY_CHAR).append(Constant.COMMA).append(Constant.FOUR).append(Constant.COMMA)
				.append(Constant.ZERO).append(Constant.COMMA).append(Constant.ZERO).append(Constant.COMMA)
				.append(Constant.GWYNEDD_CHAR).append(Constant.COMMA).append(Constant.ZERO).append(Constant.COMMA)
				.append(Constant.FOUR).append(Constant.COMMA).append(Constant.ZERO).append(Constant.COMMA)
				.append(Constant.ESSEX_CHAR).append(Constant.COMMA).append(Constant.ZERO).append(Constant.COMMA)
				.append(Constant.ZERO).append(Constant.COMMA).append(Constant.FOUR);

		ArrayList<String> loca = new ArrayList<String>();
		loca.add(Constant.STRATHCLYDE_CHAR);
		loca.add(Constant.LANCASTER_CHAR);
		loca.add(Constant.NORTHUMBRIA_CHAR);
		loca.add(Constant.WARWICK_CHAR);
		loca.add(Constant.DEVON_CHAR);

		for (int i = 0; i < 5; i++) {

			int n1 = random.nextInt(4);
			int n2 = random.nextInt(4 - n1);
			int n3 = 4 - n1 - n2;
			message.append(Constant.COMMA).append(loca.get(i)).append(Constant.COMMA).append(n1).append(Constant.COMMA)
					.append(n2).append(Constant.COMMA).append(n3);
		}
		System.out.println("02 Message " + message.toString());
		for (int i=1;i<=3;i++)
		Utility.writeFile(filePath+"P"+i, message.toString());

	}

	// 03 message
	public static void distributeFoll(String filePath) throws InterruptedException {
		Random random = new Random();

		StringBuilder message = new StringBuilder(Constant.MESSAAGE_03).append(":");
		for (int i = 1; i <= 3; i++) {

			int n1 = random.nextInt(3);
			int n2 = random.nextInt(3 - n1);
			int n3 = 3 - n1 - n2;
			String playerName = "P" + i;
			if (i < 3)
				message.append(playerName).append(Constant.COMMA).append(n1).append(Constant.COMMA).append(n2)
						.append(Constant.COMMA).append(n3).append(Constant.COMMA);
			else
				message.append(playerName).append(Constant.COMMA).append(n1).append(Constant.COMMA).append(n2)
						.append(Constant.COMMA).append(n3);
		}
		System.out.println("03 Message " + message.toString());
		for (int i=1;i<=3;i++)
		Utility.writeFile(filePath+"P"+i, message.toString());

	}

	// 04 message
	public static void distributeReg(String filePath) throws InterruptedException {
		Random random = new Random();

		StringBuilder message = new StringBuilder(Constant.MESSAAGE_04).append(":");

		ArrayList<String> loca = new ArrayList<String>();
		loca.add(Constant.MORAY_CHAR);
		loca.add(Constant.GWYNEDD_CHAR);
		loca.add(Constant.ESSEX_CHAR);
		loca.add(Constant.STRATHCLYDE_CHAR);
		loca.add(Constant.LANCASTER_CHAR);
		loca.add(Constant.NORTHUMBRIA_CHAR);
		loca.add(Constant.WARWICK_CHAR);
		loca.add(Constant.DEVON_CHAR);

		for (int i = 7; i >= 0; i--) {

			if (i > 0) {
				int n1 = random.nextInt(i);
				message.append(loca.get(n1)).append(Constant.COMMA);
				loca.remove(n1);
			} else
				message.append(loca.get(0));

		}
		System.out.println("04 Message " + message.toString());
		for (int i=1;i<=3;i++)
		Utility.writeFile(filePath+"P"+i, message.toString());

	}

	
	public static ArrayList<String> PlayerTurn( String filePath) throws InterruptedException {
		
		//for player selection to start with
				Random random = new Random();
				
				ArrayList<String> playerList = new ArrayList<String> ();
				ArrayList<String> playerSeq = new ArrayList<String> ();
				playerList .add("P1");
				playerList.add("P2");
				playerList.add("P3");
				Collections.shuffle(playerList);
				playerSeq = playerList;
				System.out.println("Play Order- "+ playerSeq);
		
		StringBuilder message = new StringBuilder(Constant.MESSAAGE_06).append(":");
		message.append(playerSeq.get(0));
		for(int i = 0 ; i <= 3 ; i ++)
			Utility.writeFile(filePath + "P"+i , message.toString());
		
		return playerSeq;
	}
	
	//message 15
	public static void pass(String messageNumber, List<String> messageDetailsList) {
		// TODO Auto-generated method stub
		System.out.println("Player has passed the turn");
		Main.passCount ++;
	}

	public static void addForCard(String messageNumber, List<String> messageDetailsList) {
		// TODO Auto-generated method stub
		for (int i = 0; i < messageDetailsList.size(); i++) {
			
			CardsPlayedByPlayerInfo.add(messageDetailsList.get(i));
			
		}
	}

	//message 12
	//message 13 Eg : 13: P1; [A, ES, R, NO, Y, MO, B]; [ES.B]

	public static void SupporterDraw(String messageNumber, List<String> messageDetailsList,String playernam) throws InterruptedException {
	
		//report round status to all players
		StringBuilder message = new StringBuilder(Constant.MESSAAGE_13).append(":");
		
		message.append(playernam).append(Constant.SEMICOLLEN);
		
		for (int i = 0; i < CardsPlayedByPlayerInfo.size(); i++) {
			
			if(i == 0)
				message.append( CardsPlayedByPlayerInfo.get(i) );
			message.append(Constant.COMMA).append( CardsPlayedByPlayerInfo.get(i) );
			 
		}
		
		message.append(Constant.SEMICOLLEN);
	    for (int i = 0; i < messageDetailsList.size(); i++) {
			
	    		if(i == 0)
				message.append( CardsPlayedByPlayerInfo.get(i) );
			message.append(Constant.COMMA).append( CardsPlayedByPlayerInfo.get(i) );			 
		}
	    
	    for(int i = 1 ; i <= 3 ; i ++)
	    		Utility.writeFile(Utility.getInstance().getFileWritePath()+"P"+i , message.toString());	   	
	   		CardsPlayedByPlayerInfo.clear();
	}

	public static void powerStruggle() {
		// TODO Auto-generated method stub
		
		
	}

	public static void winnerMessage() {
		// TODO Auto-generated method stub
		
	}

	


}
