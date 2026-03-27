package main.lld.loggerLibrary.service;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import main.lld.loggerLibrary.enums.SinkType;
import main.lld.loggerLibrary.util.MessageObject;

public class MessagesQueueBroker {

	private Map<SinkType, BlockingQueue<MessageObject>> queueBroker = new ConcurrentHashMap<>();

	private boolean running = true;

	public void publish(SinkType type, MessageObject msgObject) {
		BlockingQueue<MessageObject> queue =
                queueBroker.computeIfAbsent(type, k -> new LinkedBlockingQueue<>());
        queue.offer(msgObject);
	}

	public MessageObject subscribe(SinkType type) throws InterruptedException {
		BlockingQueue<MessageObject> queue =
                queueBroker.computeIfAbsent(type, k -> new LinkedBlockingQueue<>());
        return queue.poll(1, TimeUnit.SECONDS);

	}

	public void stop() {
		running = false;
	}

	public boolean getIsRunning() {
		return running;
	}

}
