package com.KingIsDdServer;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class GameProcessing {
	public static ArrayList<String> CardsPlayedByPlayerInfo = new ArrayList<String>();

	public static int blueCount = 0;
	public static int redCount = 0;
	public static int yellowCount = 0;
	public static String lastPowerStrugle = null;
	
	public static String first = null;
	public static String second = null;
	public static String third = null;

	public static void initializeFile(String filePath) throws InterruptedException {

		
		int redFollower;
		int blueFollower;
		int yellowFollower;
		
		HashMap<String, HashMap<String, Integer>> locationFollower = new HashMap<> ();
		HashMap<String, Integer> countFollower = new HashMap<> ();

		StringBuilder message = new StringBuilder(Constant.MESSAAGE_02).append(":");
		message.append(Constant.MORAY_CHAR).append(Constant.COMMA).append(Constant.FOUR).append(Constant.COMMA)
				.append(Constant.ZERO).append(Constant.COMMA).append(Constant.ZERO).append(Constant.COMMA)
				.append(Constant.GWYNEDD_CHAR).append(Constant.COMMA).append(Constant.ZERO).append(Constant.COMMA)
				.append(Constant.FOUR).append(Constant.COMMA).append(Constant.ZERO).append(Constant.COMMA)
				.append(Constant.ESSEX_CHAR).append(Constant.COMMA).append(Constant.ZERO).append(Constant.COMMA)
				.append(Constant.ZERO).append(Constant.COMMA).append(Constant.FOUR);
		
		countFollower.put("B", 4);
		countFollower.put("R", 0);
		countFollower.put("Y", 0);
		locationFollower.put(Constant.MORAY_CHAR, countFollower);
		blueFollower = GameParameter.getInstance().getFollowerMap().get("B");
		GameParameter.getInstance().getFollowerMap().replace("B", blueFollower-4);
		
		countFollower.replace("B", 0);
		countFollower.replace("R", 4);
		countFollower.replace("Y", 0);
		locationFollower.put(Constant.GWYNEDD_CHAR, countFollower);
		redFollower = GameParameter.getInstance().getFollowerMap().get("R");
		GameParameter.getInstance().getFollowerMap().replace("R", redFollower-4);
		
		countFollower.replace("B", 0);
		countFollower.replace("R", 0);
		countFollower.replace("Y", 4);
		locationFollower.put(Constant.ESSEX_CHAR, countFollower);
		yellowFollower = GameParameter.getInstance().getFollowerMap().get("Y");
		GameParameter.getInstance().getFollowerMap().replace("Y", yellowFollower-4);
		
		ArrayList<String> loca = new ArrayList<String>();
		loca.add(Constant.STRATHCLYDE_CHAR);
		loca.add(Constant.LANCASTER_CHAR);
		loca.add(Constant.NORTHUMBRIA_CHAR);
		loca.add(Constant.WARWICK_CHAR);
		loca.add(Constant.DEVON_CHAR);

		
		for (int i = 0; i < 5; i++) {
			
			ArrayList<Integer> follower = produceFollower(4);
			
			HashMap<String, Integer> countFollower1 = new HashMap<> ();

				message.append(Constant.COMMA).append(loca.get(i)).append(Constant.COMMA).append(follower.get(0)).append(Constant.COMMA)
						.append(follower.get(1)).append(Constant.COMMA).append(follower.get(2));
				
				countFollower1.put("B", follower.get(0));
				countFollower1.put("R", follower.get(1));
				countFollower1.put("Y", follower.get(2));
				locationFollower.put(loca.get(i), countFollower1);
			
		}
		GameParameter.getInstance().setLocationFollower(locationFollower);
		System.out.println(GameParameter.getInstance().getLocationFollower().get("NO"));
		System.out.println("02 Message " + message.toString());
		for (int i=1;i<=3;i++)
			Utility.writeFile(filePath+"P"+i, message.toString());

	}

	private static ArrayList<Integer> produceFollower( int number) {
		// TODO Auto-generated method stub
		ArrayList<Integer> follower = new ArrayList<>(); 
		Random random = new Random();
		
		int blueSupply = -1;
		int redSupply = -1;
		int yellowSupply = -1;
		int blue = 0;
		int red = 0;
		int yellow = 0;
		
		while(blueSupply < 0 || redSupply < 0 || yellowSupply < 0) {
			blue = random.nextInt(number);
			red = random.nextInt(number - blue);
			yellow = number - blue - red;
			
			blueSupply = GameParameter.getInstance().getFollowerMap().get("B") - blue;
			redSupply = GameParameter.getInstance().getFollowerMap().get("R") - red;
			yellowSupply = GameParameter.getInstance().getFollowerMap().get("Y") - yellow;
		}
		
			follower.add(blue);
			follower.add(red);
			follower.add(yellow);
			GameParameter.getInstance().getFollowerMap().replace("B", blueSupply);
			GameParameter.getInstance().getFollowerMap().replace("R", redSupply);
			GameParameter.getInstance().getFollowerMap().replace("Y", yellowSupply);

		
		return follower;
	}

	// 03 message
	public static void distributeFoll(String filePath) throws InterruptedException {
		
		HashMap<String, HashMap<String, Integer>> playerFollower = new HashMap<String, HashMap<String, Integer>>();
		 
		

		StringBuilder message = new StringBuilder(Constant.MESSAAGE_03).append(":");
		for (int i = 1; i <= 3; i++) {
			HashMap<String, Integer> followerMap = new HashMap<>();
			ArrayList<Integer> followerList = produceFollower(3);
				
				String playerName = "P" + i;
				if (i < 3)
					message.append(playerName).append(Constant.COMMA).append(followerList.get(0)).append(Constant.COMMA).append(followerList.get(1))
							.append(Constant.COMMA).append(followerList.get(2)).append(Constant.COMMA);
				else
					message.append(playerName).append(Constant.COMMA).append(followerList.get(0)).append(Constant.COMMA).append(followerList.get(1))
							.append(Constant.COMMA).append(followerList.get(2));
				
				followerMap.put("B", followerList.get(0));
				followerMap.put("R", followerList.get(1));
				followerMap.put("Y", followerList.get(2));
				playerFollower.put(playerName, followerMap);
		}
		
		GameParameter.getInstance().setFollower(playerFollower);

	
		System.out.println("03 Message " + message.toString());
		for (int i=1;i<=3;i++) {
			Utility.writeFile(filePath+"P"+i, message.toString());
		}
	}

	// 04 message
	public static void distributeReg(String filePath) throws InterruptedException {
		Random random = new Random();

		StringBuilder message = new StringBuilder(Constant.MESSAAGE_04).append(":");

		ArrayList<String> loca = new ArrayList<String>();
		ArrayList<String> initializeloca = new ArrayList<String>();

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
				initializeloca.add(loca.get(n1));
				loca.remove(n1);
			} else
				message.append(loca.get(0));

		}
		
		GameParameter.getInstance().setInitializeloca(initializeloca);
		System.out.println("04 Message " + message.toString());
		
		for (int i=1;i<=3;i++)
			Utility.writeFile(filePath+"P"+i, message.toString());

	}

	
	public static ArrayList<String> PlayerTurn() throws InterruptedException {
		
		//for player selection to start with
				
				ArrayList<String> playerList = new ArrayList<String> ();
				ArrayList<String> playerSeq = new ArrayList<String> ();
				playerList.add("P1");
				playerList.add("P2");
				playerList.add("P3");
				Collections.shuffle(playerList);
				playerSeq = playerList;
				System.out.println("Play Order- "+ playerSeq);
		
		return playerSeq;
	}
	
	//message 15
	public static void pass(String messageNumber, List<String> messageDetailsList) {
		// TODO Auto-generated method stub
		System.out.println("Player has passed the turn");
		Main.passCount ++;
	}
	
	public static void clearPassCount() {
		System.out.println("Clearing the pass count");
		Main.passCount=0;
	}

	public static void addForCard(String messageNumber, List<String> messageDetailsList) {
		// TODO Auto-generated method stub
		if("07".equals(messageNumber)) 
			CardsPlayedByPlayerInfo.add("S");
		else if("08".equals(messageNumber)) 
			CardsPlayedByPlayerInfo.add("A");
		else if("09".equals(messageNumber)) 
			CardsPlayedByPlayerInfo.add("M");
		else if("10".equals(messageNumber)) 
			CardsPlayedByPlayerInfo.add("O");
		else if("11".equals(messageNumber)) 
			CardsPlayedByPlayerInfo.add("N");
		
		for (int i = 0; i < messageDetailsList.size(); i++) {
			
			CardsPlayedByPlayerInfo.add(messageDetailsList.get(i));
			
			
		}
		System.out.println(CardsPlayedByPlayerInfo);
	}

	//message 12
	//message 13 Eg : 13: P1; [A, ES, R, NO, Y, MO, B]; [ES,B]

	public static void SupporterDraw(String messageNumber, List<String> messageDetailsList,String playerNam) throws InterruptedException {
	
		//report round status to all players
		StringBuilder message = new StringBuilder(Constant.MESSAAGE_13).append(":");
		
		message.append(playerNam).append(Constant.COMMA);
		
		if(CardsPlayedByPlayerInfo.get(0).equals("S"))
			SWEcarsProcess(playerNam);
		if(CardsPlayedByPlayerInfo.get(0).equals("A"))
			AssembleCardProcess(playerNam);
		if(CardsPlayedByPlayerInfo.get(0).equals("M"))
			ManoeuvreCardProcess(playerNam);
		if(CardsPlayedByPlayerInfo.get(0).equals("O"))
			OutManoeuvreCardProcess(playerNam);
		if(CardsPlayedByPlayerInfo.get(0).equals("N"))
			NegotiateCardProcess(playerNam);
		
		System.out.println(CardsPlayedByPlayerInfo + "CardsPlayedByPlayerInfo");
		for (int i = 0; i < CardsPlayedByPlayerInfo.size(); i++) {
			
			if(i == 0)
				message.append( CardsPlayedByPlayerInfo.get(i) );
			else
			message.append(Constant.COMMA).append( CardsPlayedByPlayerInfo.get(i) );
			 
		}
		CardsPlayedByPlayerInfo.clear();
		
		message.append(Constant.COMMA);
		playerFollower(messageDetailsList , playerNam);
	    for (int i = 0; i < messageDetailsList.size(); i++) {
			
	    		if(i == 0)
				message.append( messageDetailsList.get(i) );
	    		else
			message.append(Constant.COMMA).append( messageDetailsList.get(i) );			 
		}
	    
	    System.out.println("13 Message " + message.toString());
	    for(int i = 1 ; i <= 3 ; i ++)
	    		Utility.writeFile(Utility.getInstance().getFileWritePath()+"P"+i , message.toString());	   	
	   	
	}

	
	private static void playerFollower(List<String> messageDetailsList, String playerNam) {
		// TODO Auto-generated method stub
		String location = messageDetailsList.get(0);
		String follower = messageDetailsList.get(1);
		System.out.println(location);
		System.out.println(follower);
		int noFollower = GameParameter.getInstance().getFollower().get(playerNam).get(follower);
		GameParameter.getInstance().getFollower().get(playerNam).replace(follower, noFollower + 1);
		
		int locFollower = GameParameter.getInstance().getLocationFollower().get(location).get(follower);
		GameParameter.getInstance().getLocationFollower().get(location).replace(follower, locFollower - 1);
		
	}

	//messaage 7
	public static void SWEcarsProcess(String playerNam) {
		// TODO Auto-generated method stub
		
		if (CardsPlayedByPlayerInfo.get(1).equals("S")) {
			
			//add the condition for handling if there is only one follower left
			
			addSupporter("B");
			
		}
		
		if (CardsPlayedByPlayerInfo.get(1).equals("W")) {
			
			addSupporter("R");
		}
		
		if (CardsPlayedByPlayerInfo.get(1).equals("E")) {
			
			addSupporter("Y");
			
		}
		
		
	}

	private static void addSupporter( String follower) {
		// TODO Auto-generated method stub
		int followerSupply = GameParameter.getInstance().getFollowerMap().get(follower) - 2;
		if(followerSupply >= 0) {
		GameParameter.getInstance().getFollowerMap().replace(follower , followerSupply);
		HashMap<String, Integer> locationFollower = GameParameter.getInstance().getLocationFollower().get(CardsPlayedByPlayerInfo.get(2));
		int locationFoll = locationFollower.get(follower); 
		locationFollower.replace(follower , locationFoll+2);
		GameParameter.getInstance().getLocationFollower().replace(CardsPlayedByPlayerInfo.get(2), locationFollower);
		}
		else if ( followerSupply == -1) {
			
			GameParameter.getInstance().getFollowerMap().replace(follower , 0);
			HashMap<String, Integer> locationFollower = GameParameter.getInstance().getLocationFollower().get(CardsPlayedByPlayerInfo.get(2));
			int locationFoll = locationFollower.get(follower); 
			locationFollower.replace(follower, locationFoll + 1);
			GameParameter.getInstance().getLocationFollower().replace(CardsPlayedByPlayerInfo.get(2), locationFollower);
		}
	}

	//message 8: ES, R, NO, Y, MO, B
	public static void AssembleCardProcess(String playerNam) {
		
		updateLocation(CardsPlayedByPlayerInfo.get(1) , CardsPlayedByPlayerInfo.get(2));
		updateLocation(CardsPlayedByPlayerInfo.get(3) , CardsPlayedByPlayerInfo.get(4));
		updateLocation(CardsPlayedByPlayerInfo.get(5) , CardsPlayedByPlayerInfo.get(6));
	}

	

	private static void updateLocation(String location, String follower) {
		// TODO Auto-generated method stub
		int foll;
		HashMap<String, Integer> locationFollower = new HashMap<>();
		
		locationFollower = GameParameter.getInstance().getLocationFollower().get(location);
		Boolean result = checkFollower(follower);
		if (result) {
			foll = locationFollower.get(follower) + 1;
			locationFollower.replace(follower, foll);
			GameParameter.getInstance().getLocationFollower().replace(location, locationFollower);
		}
		
		
	}

	private static boolean checkFollower(String follower) {
		// TODO Auto-generated method stub
		//add for all should not substract if we get a 0
			if(GameParameter.getInstance().getFollowerMap().get(follower) > 0) {
				int count = GameParameter.getInstance().getFollowerMap().get(follower);
				GameParameter.getInstance().getFollowerMap().replace(follower, count-1);
				return true;
			}
		return false;
	}

	//message 9 ES, R, NO, Y
	
	public static void ManoeuvreCardProcess(String playerNam) {
		
		//swap the follower change this
		
		String region_one = CardsPlayedByPlayerInfo.get(1);
		String follower_one = CardsPlayedByPlayerInfo.get(2);
		String region_two = CardsPlayedByPlayerInfo.get(1);
		String follower_two = CardsPlayedByPlayerInfo.get(2);
		
		int count_one = GameParameter.getInstance().getLocationFollower().get(region_one).get(follower_one);
		int count_two = GameParameter.getInstance().getLocationFollower().get(region_two).get(follower_two);
		

			
			swapfollower(follower_one , region_one , region_one , count_one);
			
			swapfollower(follower_two , region_two , region_one ,  count_two);
			
	
		
	}
	
	
 
	private static void swapfollower(String follower, String region_sub, String region_add, int count) {
		// TODO Auto-generated method stub
		if (count >= 1) {
			GameParameter.getInstance().getLocationFollower().get(region_sub).replace(follower, count - 1 );
			int tmp = GameParameter.getInstance().getLocationFollower().get(region_add).get(follower);
			GameParameter.getInstance().getLocationFollower().get(region_add).replace(follower, tmp+1);
		}
	}

	//message 10 ES, R,Y, No, B
	public static void OutManoeuvreCardProcess(String playerNam) {
		// TODO Auto-generated method stub
		String region_one = CardsPlayedByPlayerInfo.get(1);
		String follower_one = CardsPlayedByPlayerInfo.get(2);
		String follower_one1 = CardsPlayedByPlayerInfo.get(2);
		String region_two = CardsPlayedByPlayerInfo.get(1);
		String follower_two = CardsPlayedByPlayerInfo.get(2);
		
		int count_one = GameParameter.getInstance().getLocationFollower().get(region_one).get(follower_one);
		int count_one1 = GameParameter.getInstance().getLocationFollower().get(region_one).get(follower_one1);
		int count_two = GameParameter.getInstance().getLocationFollower().get(region_two).get(follower_two);
		
			swapfollower(follower_one , region_one , region_two ,  count_one);

			swapfollower(follower_one1 , region_one , region_two ,  count_one1);
			
			swapfollower(follower_two , region_two , region_one ,  count_two);
			

		
	}

	//message 11 ES,WA

	public static void NegotiateCardProcess(String playerNam) {
		// TODO Auto-generated method stub
		// check once
		int index = GameParameter.getInstance().getInitializeloca().indexOf(CardsPlayedByPlayerInfo.get(1));
		int index2 = GameParameter.getInstance().getInitializeloca().indexOf(CardsPlayedByPlayerInfo.get(2));
		
		//swapping variable is needed
		GameParameter.getInstance().getInitializeloca().set(index2, CardsPlayedByPlayerInfo.get(1));
		GameParameter.getInstance().getInitializeloca().set(index, CardsPlayedByPlayerInfo.get(2));
	}
	
	
	public static void powerStruggle() {
		// TODO Auto-generated method stub
			String location = GameParameter.getInstance().getInitializeloca().get(0);
			HashMap<String, Integer> locationFollower =   GameParameter.getInstance().getLocationFollower().get(location);
			HashMap<String, String> powerStruggle = new HashMap<>();
			
			if (GameParameter.getInstance().getPowerStruggle() != null)
				powerStruggle = GameParameter.getInstance().getPowerStruggle();
			
			int blueFoll = locationFollower.get("B");
			int redFoll = locationFollower.get("R");
			int yollowFoll = locationFollower.get("Y");
			
			//french location 
			if(blueFoll == redFoll || blueFoll ==  yollowFoll || redFoll == yollowFoll ) {
				if (GameParameter.getInstance().getBlackDisk() > 0)
				{
					GameParameter.getInstance().setBlackDisk(GameParameter.getInstance().getBlackDisk() - 1);
						powerStruggle.put(location, "F");
						
					if(GameParameter.getInstance().getBlackDisk() == 0)
						winnerMessage("F");
				}
			}
				
			else {	
			
			if (blueFoll > redFoll && blueFoll > yollowFoll) {
				
					powerStruggle.put(location, "B");
					GameParameter.getInstance().setBlueControlDisk(GameParameter.getInstance().getBlueControlDisk() - 1);
			
			}
			else {
				if (redFoll > yollowFoll) {
					powerStruggle.put(location, "R");
					GameParameter.getInstance().setRedControlDisk(GameParameter.getInstance().getRedControlDisk() - 1);
				}
				else {
					powerStruggle.put(location, "Y");
					GameParameter.getInstance().setYellowControlDisk(GameParameter.getInstance().getYellowControlDisk() - 1);
				}
			}
			
		}
		
		GameParameter.getInstance().setPowerStruggle(powerStruggle);
		if(GameParameter.getInstance().getInitializeloca().size() == 1 )
			lastPowerStrugle = GameParameter.getInstance().getInitializeloca().get(0);
			
		GameParameter.getInstance().getInitializeloca().remove(0);
		
	}

	public static void winnerMessage(String result) {
		// TODO Auto-generated method stub
		
		StringBuilder message = new StringBuilder(Constant.MESSAAGE_16).append(":");
		if (result.equals("F")) {
			int countP1 = getInvasionCount("P1");
			int countP2 = getInvasionCount("P2");
			int countP3 = getInvasionCount("P3");
			
			if (countP1 > countP2 && countP1 > countP3)
				message.append("P1");
			else if(countP2 > countP3)
				message.append("P2");
			else
				message.append("P3");
			
			message.append(Constant.COMMA).append(result);	
				
		}
		if(result.equals("C")) {
			message.append(coronationProcess());	
			
		}
		for(int i = 1 ; i <= 3 ; i ++)
			Utility.writeFile(Utility.getInstance().getFileWritePath()+"P"+i , message.toString());	   
		
		Main.ExitGame = true;
	}
	
	

	private static String coronationProcess() {
		// TODO Auto-generated method stub
		
		GameParameter.getInstance().getPowerStruggle().entrySet().forEach(loca ->{
			
			if(loca.getValue().equals("B"))
				blueCount++;
			if(loca.getValue().equals("R"))
				redCount++;
			if(loca.getValue().equals("Y"))
				yellowCount++;
			
		});
		
		if(blueCount == redCount || blueCount == yellowCount || redCount == yellowCount) {
			equalCount(blueCount, redCount, "B", "R");
			equalCount(blueCount, yellowCount, "B", "Y");
			equalCount(redCount, yellowCount, "R", "Y");
		}
		else {
			if(blueCount > redCount && blueCount > yellowCount) {
				first = "B";
				if(redCount > yellowCount) {
					second = "R";
					third = "Y";
				}
				else {
					second = "Y";
					third = "R";
				}
			}
			else if(redCount > yellowCount) {
				first = "R";
				if(blueCount > yellowCount) {
					second = "B";
					third = "Y";
				}
				else {
					second = "Y";
					third = "B";
				}
			}
			else {
				first = "Y";
				if(blueCount > redCount) {
					second = "B";
					third = "R";
				}
				else {
					second = "R";
					third = "B";
				}
				
			}
		
			
		}
		
		int followerP1 = GameParameter.getInstance().getFollower().get("P1").get(first);
		int followerP2 = GameParameter.getInstance().getFollower().get("P2").get(first);
		int followerP3 = GameParameter.getInstance().getFollower().get("P3").get(first);
		
		if (followerP1 == followerP2 || followerP1 == followerP3 || followerP2 == followerP3) {
			followerP1 = GameParameter.getInstance().getFollower().get("P1").get(second);
			followerP2 = GameParameter.getInstance().getFollower().get("P2").get(second);
			followerP3 = GameParameter.getInstance().getFollower().get("P3").get(second);
			
		}
		
		return largestOfThree(followerP1,followerP2,followerP3);
		
	}

	private static void equalCount(int firstCount, int secondCount, String firstString, String secondString) {
		// TODO Auto-generated method stub
		
		if(firstCount == secondCount) {
			String value = GameParameter.getInstance().getPowerStruggle().get(lastPowerStrugle);
			if (value.equals(firstString)) {
				first = value;
				second = secondString;
			}
			else {
				first = firstString;
				second = secondString;
			}
		}
		
		
	}

	private static String largestOfThree(int followerP1, int followerP2, int followerP3) {
		// TODO Auto-generated method stub
		if(followerP1 > followerP2 && followerP1 > followerP2) {
			return "P1";
		}
		else if (followerP2 > followerP3)
			return "P2";
		else 
			return "P3";
		
	}

	private static int getInvasionCount(String playerName) {
		// TODO Auto-generated method stub
		int count = 0;
		int blue = GameParameter.getInstance().getFollower().get(playerName).get("B");
		int red = GameParameter.getInstance().getFollower().get(playerName).get("R");
		int yellow = GameParameter.getInstance().getFollower().get(playerName).get("Y");
		while(blue < 0 || red < 0 || yellow < 0) {
			count++;
			blue--;
			red --;
			yellow--;
			
		}
		
		return count;
		
	}

	


}
