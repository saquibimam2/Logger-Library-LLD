package main.lld.loggerLibrary.repo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import main.lld.loggerLibrary.enums.LogLevel;
import main.lld.loggerLibrary.service.ConsoleProcessorSink;
import main.lld.loggerLibrary.service.FileProcessorSink;
import main.lld.loggerLibrary.service.LogProcessor;
import main.lld.loggerLibrary.service.MessagesQueueBroker;
import main.lld.loggerLibrary.util.LevelHelper;
import main.lld.loggerLibrary.util.MessageObject;
import main.lld.loggerLibrary.util.SinkHelper;

public class Logger {

	MessagesQueueBroker broker = null;
	LogProcessor fileProcessor = null;
	LogProcessor consoleProcessor = null;
	LogProcessor parent;
    public ExecutorService EXECUTOR = null;

	public Logger() {
		broker = new MessagesQueueBroker();
		consoleProcessor = new ConsoleProcessorSink(null, broker);
		fileProcessor = new FileProcessorSink(consoleProcessor, broker);
		parent = fileProcessor;
		EXECUTOR = Executors.newFixedThreadPool(5);
		EXECUTOR.submit(() -> {
			try {
				fileProcessor.subscribe();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		EXECUTOR.submit(() -> {
			try {
				consoleProcessor.subscribe();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	public LogProcessor getFileProcessorFromName(String processor) {
		return SinkHelper.convertStringToSink(processor, this);
	}

	public LogProcessor getFileProcessor() {
		return fileProcessor;
	}

	public void setFileProcessor(LogProcessor fileProcessor) {
		this.fileProcessor = fileProcessor;
	}

	public LogProcessor getConsoleProcessor() {
		return consoleProcessor;
	}

	public void setConsoleProcessor(LogProcessor consoleProcessor) {
		this.consoleProcessor = consoleProcessor;
	}

	public void defineSinkPriority(Map<Integer, String> priorityToSinkProcessor) {
		LogProcessor previousProcessor = null;
		for(Entry<Integer, String> entry : priorityToSinkProcessor.entrySet()) {
			LogProcessor currentProcessor = getFileProcessorFromName(entry.getValue());
			if(previousProcessor!=null) {
				previousProcessor.setNextProcessor(currentProcessor);
				currentProcessor.setNextProcessor(null);
			} else {
				this.parent = currentProcessor;
			}
			previousProcessor = currentProcessor;
		}	
	}
	public void setSinkLevels(String sink, List<LogLevel> levels) {
		SinkHelper.convertStringToSink(sink, this).setLogLevels(levels);
	}

	public void log(String msg, String level, LogProcessor sink, String tsFormat, String sinkLocation) {
		MessageObject message = new MessageObject(msg, LevelHelper.convertStringToLevel(level), new Date(),
				sinkLocation, tsFormat, true);
		sink.log(message);
	}

	public void log(String msg, String level, String sink, String tsFormat, String sinkLocation) {
		log(msg, level, getFileProcessorFromName(sink), tsFormat, sinkLocation);
	}

	public void log(String msg, String level, String tsFormat, String path) {
		MessageObject message = new MessageObject(msg, LevelHelper.convertStringToLevel(level), new Date(), path,
				tsFormat, false);
		parent.log(message);
	}
	
	public void log(String msg, String level, String tsFormat) {
		log(msg, level, tsFormat, null);

	}
	
	public void stopBroker() {
		broker.stop();
	}

}
