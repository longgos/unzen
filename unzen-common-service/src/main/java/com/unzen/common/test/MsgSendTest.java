package com.unzen.common.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MsgSendTest {

	private static final String QUEUE_NAME = "hello.august";
	private static String message = "hello? thank you !thank you very much!";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFacatory = new ConnectionFactory();
		connectionFacatory.setHost("localhost");
		Connection connection = connectionFacatory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println("send message: " + message);

        channel.close();
        connection.close();
	}
}
