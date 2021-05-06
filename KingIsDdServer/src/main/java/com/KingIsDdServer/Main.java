package com.KingIsDdServer;

import java.io.File;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class Main {
	
	public static Boolean ExitGame = false;
	public static String readFile = "/tmp/From";
	public static String writeFile = "/tmp/To";
	public static ArrayList<String> playernameslist = new ArrayList<String>();
	public static ArrayList<String> tempplayernameslist = new ArrayList<String>();
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		
		//for player selection to start with
		Random random = new Random();
		
		playernameslist.add("P1");
		playernameslist.add("P2");
		playernameslist.add("P3");
		Collections.shuffle(playernameslist);
		tempplayernameslist=playernameslist;
		System.out.println("Play Order- "+tempplayernameslist);
		
        for (int i = 1 ; i <=3 ;i ++) {
        	
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
	         
	        Files.newOutputStream(readFileName.toPath(), CREATE_NEW);
	      
	        Files.newOutputStream(writeFileName.toPath(), CREATE_NEW);
        }
        
        
        		
        		System.out.println("fisrt");
        		GameProcessing.initializeFile(writeFile);
        		
        		System.out.println("second");
        		GameProcessing.distributeFoll(writeFile);
        		System.out.println("third");
        		GameProcessing.distributeReg(writeFile);
        		
        		System.out.println("Selecting player to start with!!");
        		
        	
        		
        		GameProcessing.PlayerTurn(tempplayernameslist.get(0),writeFile);
        		tempplayernameslist.remove(0);
        	
        
        
       
//        	System.out.println("in");
        	Utility.readAllFile(ExitGame , readFile, writeFile);      
	}

}

