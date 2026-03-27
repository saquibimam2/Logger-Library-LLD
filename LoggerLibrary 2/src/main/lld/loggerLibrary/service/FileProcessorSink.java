package main.lld.loggerLibrary.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import main.lld.loggerLibrary.enums.LogLevel;
import main.lld.loggerLibrary.enums.SinkType;
import main.lld.loggerLibrary.util.FileWriteHelper;
import main.lld.loggerLibrary.util.MessageObject;

public class FileProcessorSink extends LogProcessor{

	public FileProcessorSink(LogProcessor nextLogProcessor, MessagesQueueBroker broker) {
		super(nextLogProcessor, broker);
	}
	
	private SinkType type = SinkType.FILE;

	//default accepted levels which can be changed using setter
	public List<LogLevel> acceptedLevels = Arrays.asList(LogLevel.FATAL, LogLevel.ERROR, LogLevel.WARN);

	public List<LogLevel> getAcceptedLevels() {
		return acceptedLevels;
	}

	public void setAcceptedLevels(List<LogLevel> acceptedLevels) {
		this.acceptedLevels = acceptedLevels;
	}

	public void log(MessageObject message) {
		broker.publish(SinkType.FILE, message);
	}

	public void subscribe() throws InterruptedException {
		while(broker.getIsRunning()) {
			MessageObject message = broker.subscribe(SinkType.FILE);
			String msg = message.getMessage();
			LogLevel level = message.getLevel();
			String path = message.getPath();
			Date date = message.getDate();
			String timeFormat = message.getTs_format();
			if(acceptedLevels.contains(level)) {
				FileWriteHelper.writeOutputToFile(level, date, timeFormat, msg, path);
			}
			if(!message.getSpecificSinkOnly()) {
				super.log(message);			
			}
		}
	}

	public SinkType getType() {
		return type;
	}

	@Override
	public void setLogLevels(List<LogLevel> levels) {
		setAcceptedLevels(levels);		
	}
	
//	public LogProcessor findProcessor(LogLevel level) {
//		if(acceptedLevels.contains(level)) {
//			return this;
//		}
//		return super.findProcessor(level);
//	}
		
}
