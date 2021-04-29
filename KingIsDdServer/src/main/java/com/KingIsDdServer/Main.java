package com.KingIsDdServer;

import java.io.File;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class Main {
	
	public static Boolean ExitGame = false;
	public static void main(String[] args) throws IOException, InterruptedException {
	
        for (int i = 1 ; i <=2 ;i ++) {
        	String playerName = "P" + i;
        	String readPath = "/tmp/From" + playerName;
        	String writePath = "/tmp/To" + playerName;
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
        	OutputStream readFile = Files.newOutputStream(readFileName.toPath(), CREATE_NEW);
       

        	OutputStream writeFile = Files.newOutputStream(writeFileName.toPath(), CREATE_NEW);

        		String input = "Hello " + playerName;
        		writeFile.write(input.getBytes());
        		writeFile.close();
        }
       
        	System.out.println("in");
        	Utility.readFile(ExitGame);      
	}

}
