package main.lld.loggerLibrary.service;

import java.util.List;

import main.lld.loggerLibrary.enums.LogLevel;
import main.lld.loggerLibrary.enums.SinkType;
//import main.lld.loggerLibrary.enums.LogLevel;
import main.lld.loggerLibrary.util.MessageObject;

public abstract class LogProcessor {
	
	LogProcessor nextLogProcessor;
	MessagesQueueBroker broker;
	
	LogProcessor(LogProcessor nextLogProcessor, MessagesQueueBroker broker) {
		this.nextLogProcessor = nextLogProcessor;
		this.broker = broker;
	}
	
	public void setNextProcessor(LogProcessor nextLogProcessor) {
		this.nextLogProcessor = nextLogProcessor;
	}
	
	public void log(MessageObject message) {
		if(nextLogProcessor!=null) {
			broker.publish(nextLogProcessor.getType(), message);
		}
	}
	
	protected abstract SinkType getType();

	public abstract void setLogLevels(List<LogLevel> levels);

	public abstract void subscribe() throws InterruptedException;
	
//	public LogProcessor findProcessor(LogLevel level) {
//		if(nextLogProcessor!=null) {
//			nextLogProcessor.findProcessor(level);
//		}
//		return null;
//	}
	
}
