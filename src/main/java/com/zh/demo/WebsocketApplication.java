package com.zh.demo;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import sun.rmi.runtime.Log;

@SpringBootApplication
@EnableWebSocket
public class WebsocketApplication {

	private static final Logger logger = Logger.getLogger(WebsocketApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class, args);
		logger.info("Chapter19启动!");
	}

	/**
	 * 会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
	 * 要注意，如果使用独立的servlet容器，
	 * 而不是直接使用springboot的内置容器，
	 * 就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理。
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
