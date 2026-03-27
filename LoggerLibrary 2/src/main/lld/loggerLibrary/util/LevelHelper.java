package main.lld.loggerLibrary.util;

import main.lld.loggerLibrary.enums.LogLevel;

public class LevelHelper {
	public static LogLevel convertStringToLevel(String level) {
		switch (level) {
		case "DEBUG":
			return LogLevel.DEBUG;
		case "INFO":
			return LogLevel.INFO;
		case "WARN":
			return LogLevel.WARN;
		case "ERROR":
			return LogLevel.ERROR;
		case "FATAL":
			return LogLevel.FATAL;
		}
		return null;
	}
}
