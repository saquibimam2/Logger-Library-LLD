package main.lld.loggerLibrary.util;

import main.lld.loggerLibrary.repo.Logger;
import main.lld.loggerLibrary.service.LogProcessor;

public class SinkHelper {
	public static LogProcessor convertStringToSink(String sink, Logger logger) {
		if(sink==null) return null;
		switch (sink) {
			case "FILE":
				return logger.getFileProcessor();
			case "CONSOLE":
				return logger.getConsoleProcessor();
		}
		return null;
	}
}
