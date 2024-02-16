package com.example.gym.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import com.example.gym.dto.DeleteTrainingRequestDTO;

import jakarta.jms.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;



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
	    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
	        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
	        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
	        return jmsTemplate;
	    }
	
	    @Bean
	    public MessageConverter jacksonJmsMessageConverter() {
	        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	        converter.setTargetType(MessageType.TEXT);
	        converter.setTypeIdPropertyName("_type");
	        return converter;
	    }

	    
	    @Bean
	    public JmsListenerContainerFactory wareHouseFactory(ConnectionFactory factory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
	        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
	        configurer.configure(containerFactory, factory);
	        return containerFactory;
	    }
	    
	    @Bean
	    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
	        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	        factory.setConnectionFactory(connectionFactory());
	        return factory;
	    }

}
