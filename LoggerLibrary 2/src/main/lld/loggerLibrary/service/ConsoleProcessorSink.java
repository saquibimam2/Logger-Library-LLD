package main.lld.loggerLibrary.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import main.lld.loggerLibrary.enums.LogLevel;
import main.lld.loggerLibrary.enums.SinkType;
import main.lld.loggerLibrary.util.MessageFormatter;
import main.lld.loggerLibrary.util.MessageObject;

public class ConsoleProcessorSink extends LogProcessor {

	public ConsoleProcessorSink(LogProcessor nextLogProcessor,  MessagesQueueBroker broker) {
		super(nextLogProcessor, broker);
	}
	
	private SinkType type = SinkType.CONSOLE;
	
	//default accepted levels which can be changed using setter
	public List<LogLevel> acceptedLevels = Arrays.asList(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARN);

	public List<LogLevel> getAcceptedLevels() {
		return acceptedLevels;
	}

	public void setAcceptedLevels(List<LogLevel> acceptedLevels) {
		this.acceptedLevels = acceptedLevels;
	}
	
	public void log(MessageObject message) {
		broker.publish(SinkType.CONSOLE, message);
	}

	public void subscribe() throws InterruptedException {
		while(broker.getIsRunning()) {
			MessageObject message = broker.subscribe(SinkType.CONSOLE);
			String msg = message.getMessage();
			LogLevel level = message.getLevel();
			Date date = message.getDate();
			String timeFormat = message.getTs_format();
			if(acceptedLevels.contains(level)) {
				System.out.println(MessageFormatter.format(level, date, timeFormat, msg));
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
