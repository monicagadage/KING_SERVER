package com.KingIsDdServer;

import java.io.File;
import java.io.IOException;

public class Main {

	public static Boolean ExitGame = false;

	public static void main(String[] args) throws IOException, InterruptedException {

		String readFile = "/tmp/From";
		String writeFile = "/tmp/To";

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
	}

}
