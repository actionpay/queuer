package io.actionpay.queuer;

import com.rabbitmq.client.QueueingConsumer;

/**
 * Queue Processor allow to do something with received data.
 *
 * @author Artur Khakimov<djion@ya.ru>
 */
public interface QueueProcessor {
	boolean process(QueueingConsumer.Delivery data);
}
