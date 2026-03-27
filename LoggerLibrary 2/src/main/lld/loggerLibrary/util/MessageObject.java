package main.lld.loggerLibrary.util;

import java.util.Date;

import main.lld.loggerLibrary.enums.LogLevel;

public class MessageObject {
	
	String message;
	LogLevel level;
	Date date;
	String ts_format;
	String path;
	boolean specificSinkOnly;

	public MessageObject(String message, LogLevel level, Date time, String path, String ts_format, boolean specificSinkOnly) {
		this.message = message;
		this.level = level;
		this.date = time;
		this.ts_format = ts_format;
		this.path = path;
		this.specificSinkOnly = specificSinkOnly;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public LogLevel getLevel() {
		return level;
	}
	
	public void setLevel(LogLevel level) {
		this.level = level;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getTs_format() {
		return ts_format;
	}

	public void setTs_format(String ts_format) {
		this.ts_format = ts_format;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public boolean getSpecificSinkOnly() {
		return specificSinkOnly;
	}

	public void setSpecificSinkOnly(boolean specificSinkOnly) {
		this.specificSinkOnly = specificSinkOnly;
	}
}
