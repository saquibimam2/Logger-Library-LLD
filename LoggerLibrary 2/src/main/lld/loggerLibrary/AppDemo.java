package main.lld.loggerLibrary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import main.lld.loggerLibrary.enums.LogLevel;
import main.lld.loggerLibrary.repo.Logger;
import main.lld.loggerLibrary.repo.LoggerLibrary;

public class AppDemo {
	
	// Please change the default file address after creating the output file locally in FileWriteHelper
	public static void main(String[] args) {
		LoggerLibrary lib = LoggerLibrary.getInstance();

		// create a logger with file processor as parent and console processor as child
		Logger fileFirst = lib.createLogger("fileFirst");
		fileFirst.setSinkLevels("FILE", Arrays.asList(LogLevel.FATAL, LogLevel.ERROR));
		fileFirst.setSinkLevels("CONSOLE", Arrays.asList(LogLevel.DEBUG, LogLevel.ERROR, LogLevel.INFO));

		fileFirst.log("The user has ordered an item with id 12345", "INFO", "FILE", "dd:mm:yyyy hh:mm:ss", null);
		fileFirst.log("The user has ordered an item with id 543211111", "ERROR", "FILE", "dd:mm:yyyy hh:mm:ss", null);

		fileFirst.log("The user has ordered an item with id 12345", "INFO", "CONSOLE", "dd:mm:yyyy hh:mm:ss", null);
		fileFirst.log("The user has ordered an item with id 54321", "ERROR", "CONSOLE", "dd:mm:yyyy hh:mm:ss", null);

		// Sink not mentioned then all the sink which accept it should get the log
		fileFirst.log("No-sink mentioned-  The user has purchased an item with id 12345", "INFO",
				"dd:mm:yyyy hh:mm:ss");
		fileFirst.log("No-sink mentioned-  The user has purchased an item with id 543211111", "ERROR",
				"dd:mm:yyyy hh:mm:ss");
		
//		fileFirst.stopBroker();

		// create a logger with console processor as parent and file processor as child
		Logger consoleFirst = lib.createLogger("consoleFirst");
		Map<Integer, String> priorityToSink = new HashMap<>();
		priorityToSink.put(1, "CONSOLE");
		priorityToSink.put(2, "FILE");
		consoleFirst.defineSinkPriority(priorityToSink);

		consoleFirst.setSinkLevels("FILE", Arrays.asList(LogLevel.ERROR, LogLevel.WARN, LogLevel.INFO, LogLevel.DEBUG));
		consoleFirst.setSinkLevels("CONSOLE", Arrays.asList(LogLevel.FATAL, LogLevel.ERROR, LogLevel.WARN));

		consoleFirst.log("Console First - The user has ordered an item with id 12345", "INFO", "FILE",
				"dd:mm:yyyy hh:mm:ss", null);
		consoleFirst.log("Console First - The user has ordered an item with id 12345", "FATAL", "FILE",
				"dd:mm:yyyy hh:mm:ss", null);
		consoleFirst.log("Console First -The user has ordered an item with id 543211111", "ERROR", "FILE",
				"dd:mm:yyyy hh:mm:ss", null);

		consoleFirst.log("Console First -The user has ordered an item with id 12345", "INFO", "CONSOLE",
				"dd:mm:yyyy hh:mm:ss", null);
		consoleFirst.log("Console First -The user has ordered an item with id 54321", "ERROR", "CONSOLE",
				"dd:mm:yyyy hh:mm:ss", null);

		// Sink not mentioned then all the sink which accept it should get the log
		consoleFirst.log("Console First -No-sink mentioned-  The user has purchased an item with id 12345", "INFO",
				"dd:mm:yyyy hh:mm:ss");
		consoleFirst.log("No-sink mentioned-  The user has purchased an item with id 543211111", "ERROR",
				"dd:mm:yyyy hh:mm:ss");
		consoleFirst.log("Console First -No-sink mentioned-  The user has purchased an item with id 12345", "INFO",
				"dd:mm:yyyy hh:mm:ss",
				"/Users/saquibimam/eclipse-workspace/LoggerLibrary/src/main/lld/loggerLibrary/util/outputNew");
		consoleFirst.log("No-sink mentioned-  The user has purchased an item with id 543211111", "ERROR",
				"dd:mm:yyyy hh:mm:ss",
				"/Users/saquibimam/eclipse-workspace/LoggerLibrary/src/main/lld/loggerLibrary/util/outputNew");
		
		
		
		//going to file processor queue first and then half of them gets printed in file as fatal and half of them in console as debug
		for(int i=0;i<10;i++) {
			fileFirst.log("File First -fatal error occured at line number 100", "FATAL",
					"dd:mm:yyyy hh:mm:ss");
			fileFirst.log("File First -debugger stopped at line number 200", "DEBUG",
					"dd:mm:yyyy hh:mm:ss");
			
		}
		
		//auto-rotate
		for(int i=0;i<5000;i++) {
			consoleFirst.log("Console First -The user has ordered an item with id 543211111", "ERROR", "FILE",
					"dd:mm:yyyy hh:mm:ss", null);
			
		}


	}

}
