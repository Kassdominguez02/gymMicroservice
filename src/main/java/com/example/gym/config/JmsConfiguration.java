package com.example.gym.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;



@Configuration
@EnableJms
public class JmsConfiguration {
	
	 @Bean
	    public ActiveMQConnectionFactory connectionFactory() {
	        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
	        connectionFactory.setBrokerURL("tcp://localhost:61616");
	        connectionFactory.setUserName("admin");
	        connectionFactory.setPassword("admin");
	        return connectionFactory;
	    }

	    @Bean
	    public JmsTemplate jmsTemplate() {
	        return new JmsTemplate(connectionFactory());
	    }
	
	

}
