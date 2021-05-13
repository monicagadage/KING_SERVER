package com.KingIsDdServer;

import java.util.ArrayList;
import java.util.HashMap;

public class GameParameter {

	
	private ArrayList<String> Initializeloca = new ArrayList<String>();
	private HashMap<String, HashMap<String, Integer>> locationFollower = new HashMap<> ();
	private HashMap<String, String>  powerStruggle = new HashMap<String, String> () ;
	private HashMap<String, HashMap<String, Integer>> Follower = new HashMap<> ();
	private HashMap<String, Integer>  followerMap = new HashMap<String, Integer> () ;
	
	
	private int redControlDisk;
	private int blueControlDisk;
	private int yellowControlDisk;
	private int blackDisk;
	

	private static volatile GameParameter GameParameter = null;

	GameParameter() {
		// private constructor
	}

	// singleton object creation
	public static GameParameter getInstance() {
		if (GameParameter == null) {
			synchronized (Utility.class) {
				if (GameParameter == null) {
					GameParameter = new GameParameter();
				}
			}
		}
		return GameParameter;
	}
	
	public ArrayList<String> getInitializeloca() {
		return Initializeloca;
	}

	public void setInitializeloca(ArrayList<String> initializeloca) {
		Initializeloca = initializeloca;
	}

	public int getRedControlDisk() {
		return redControlDisk;
	}

	public void setRedControlDisk(int redControlDisk) {
		this.redControlDisk = redControlDisk;
	}

	public int getBlueControlDisk() {
		return blueControlDisk;
	}

	public void setBlueControlDisk(int blueControlDisk) {
		this.blueControlDisk = blueControlDisk;
	}

	public int getYellowControlDisk() {
		return yellowControlDisk;
	}

	public void setYellowControlDisk(int yellowControlDisk) {
		this.yellowControlDisk = yellowControlDisk;
	}

	public int getBlackDisk() {
		return blackDisk;
	}

	public void setBlackDisk(int blackDisk) {
		this.blackDisk = blackDisk;
	}

	public HashMap<String, HashMap<String, Integer>> getLocationFollower() {
		return locationFollower;
	}

	public void setLocationFollower(HashMap<String, HashMap<String, Integer>> locationFollower) {
		this.locationFollower = locationFollower;
	}

	public static GameParameter getGameParameter() {
		return GameParameter;
	}

	public static void setGameParameter(GameParameter gameParameter) {
		GameParameter = gameParameter;
	}

	public HashMap<String, String> getPowerStruggle() {
		return powerStruggle;
	}

	public void setPowerStruggle(HashMap<String, String> powerStruggle) {
		this.powerStruggle = powerStruggle;
	}

	public HashMap<String, HashMap<String, Integer>> getFollower() {
		return Follower;
	}

	public void setFollower(HashMap<String, HashMap<String, Integer>> follower) {
		Follower = follower;
	}

	public HashMap<String, Integer> getFollowerMap() {
		return followerMap;
	}

	public void setFollowerMap(HashMap<String, Integer> followerMap) {
		this.followerMap = followerMap;
	}


}
