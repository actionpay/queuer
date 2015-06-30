package io.actionpay.config;

/**
 * AMQP queue configuration
 *
 * @author Artur Khakimov <djion@ya.ru>
 */
public class AMQPConfig {
	public static final String EXCHANGE_NAME = System.getProperty("amqp.name.queue", "queue-name");
	public static final String EXCHANGE_HOST = System.getProperty("amqp.host", "localhost");
	public static final String EXCHANGE_VHOST = System.getProperty("amqp.vhost", "vhost");
	public static final String EXCHANGE_USERNAME = System.getProperty("amqp.user", "username");
	public static final String EXCHANGE_PASSWORD = System.getProperty("amqp.password", "password");

	String name;
	String host;
	String vhost;
	String username;
	String password;

	public AMQPConfig() {
		setName(EXCHANGE_NAME);
		setHost(EXCHANGE_HOST);
		setVhost(EXCHANGE_VHOST);
		setUsername(EXCHANGE_USERNAME);
		setPassword(EXCHANGE_PASSWORD);
	}

	public AMQPConfig(String name, String host, String vhost, String username, String password) {
		this.name = name;
		this.host = host;
		this.vhost = vhost;
		this.username = username;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getVhost() {
		return vhost;
	}

	public void setVhost(String vhost) {
		this.vhost = vhost;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
