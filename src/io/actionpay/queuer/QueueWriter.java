package io.actionpay.queuer;

import com.rabbitmq.client.*;
import io.actionpay.config.AMQPConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Queue writer
 * Create connection to rabbitMQ and allow to send messages to our queue
 *
 * @author Artur Khakmov <djion@ya.ru>
 */
public class QueueWriter {

	Channel channel;
	Connection connection;
	String queueName;

	public void init(AMQPConfig config) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(config.getHost());
		factory.setPassword(config.getPassword());
		factory.setUsername(config.getUsername());
		factory.setVirtualHost(config.getVhost());
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(config.getName(), true, false, false, null);
		queueName = config.getName();
	}

	public void putQueue(byte[] message) throws IOException {
		channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message);
	}

	public void close() throws IOException, TimeoutException {
		channel.close();
		connection.close();
	}


}
