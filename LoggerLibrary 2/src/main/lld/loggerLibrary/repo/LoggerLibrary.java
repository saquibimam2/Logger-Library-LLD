package main.lld.loggerLibrary.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.lld.loggerLibrary.enums.LogLevel;
import main.lld.loggerLibrary.util.SinkHelper;

public class LoggerLibrary {
	private static LoggerLibrary instance = null;
	
	// map of all loggers created by client
	// no 2 loggers with same name cant be created
	Map<String, Logger> nameToLogger = new HashMap<>();
	
	private LoggerLibrary() {
		
	}
	
	public static LoggerLibrary getInstance() {
		if(instance==null) {
			synchronized (LoggerLibrary.class) {
				if(instance==null) {
					instance = new LoggerLibrary();
				}
			}
		}
		return instance;
	}
	
	public Logger createLogger(String loggerName) {
		if(!nameToLogger.containsKey(loggerName)) {
			Logger logger = new Logger();
			nameToLogger.put(loggerName,logger);			
		}
		return nameToLogger.get(loggerName);
	}
	
	public void setSinkLevels(String loggerName, String sink, List<LogLevel> levels) {
		Logger logger = nameToLogger.get(loggerName);
		logger.setSinkLevels(sink, levels);
	}
	
	public void log(String loggerName, String msg, String level, String sink, String tsFormat, String sinkLocation) {
		Logger logger = nameToLogger.get(loggerName);
		logger.log(msg, level, SinkHelper.convertStringToSink(sink, logger), tsFormat, sinkLocation);
	}
	
	public void log(String loggerName, String msg, String level, String tsFormat) {
		Logger logger = nameToLogger.get(loggerName);
		logger.log(msg, level, tsFormat);
	}
}
