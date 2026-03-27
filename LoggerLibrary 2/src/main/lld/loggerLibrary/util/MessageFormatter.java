package main.lld.loggerLibrary.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import main.lld.loggerLibrary.enums.LogLevel;

public class MessageFormatter {
	public static String format(LogLevel level, Date date, String timeFormat, String message) {
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		String formattedDate = sdf.format(date);
		String formatterMessage = "[" + level.toString() + "] " + formattedDate + " " + message;
		return formatterMessage;
	}
}
