package io.actionpay.queuer;

import com.rabbitmq.client.*;
import io.actionpay.config.AMQPConfig;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

/**
 * Create connection to rabbitMQ and allow to recieve messages from our queue and process them by processor
 *
 * @author Artur Khakimov <djion@ya.ru>
 */
public class QueueReader implements Runnable {

	QueueingConsumer consumer;
	Channel channel;
	Connection connection;

	public QueueReader(QueueProcessor processor, AMQPConfig config, int qosCount) throws IOException, TimeoutException {
		this.processor = processor;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(config.getHost());
		factory.setVirtualHost(config.getVhost());
		factory.setUsername(config.getUsername());
		factory.setPassword(config.getPassword());

		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(config.getName(), true, false, false, null);
		channel.basicQos(qosCount);
		consumer = new QueueingConsumer(channel);
		channel.basicConsume(config.getName(), false, consumer);
	}

	QueueProcessor processor;

	public void processQueue() throws InterruptedException, SQLException, IOException, ClassNotFoundException {
		QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		if (processor.process(delivery))
			consumer.handleConsumeOk(consumer.getConsumerTag());
		else
			consumer.handleCancel(consumer.getConsumerTag());
		channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	}

	public void handleLoopException(Exception exception) {
		exception.printStackTrace();
	}

	public void handleChannelCloseException(Exception exception) {
		exception.printStackTrace();
	}

	public void handleConnectionCloseException(Exception exception) {
		exception.printStackTrace();
	}

	public void run() {
		try {
			while (true) {
				try {
					processQueue();
				} catch (Exception e) {
					handleLoopException(e);
				}
			}
		} finally {
			try {
				channel.close();
			} catch (Exception e) {
				handleChannelCloseException(e);
			}
			try {
				connection.close();
			} catch (IOException e) {
				handleConnectionCloseException(e);
			}
		}
	}
}
