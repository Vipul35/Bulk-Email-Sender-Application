package com.email.sender.application.main;

import javax.mail.MessagingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.email.sender.application.service.EmailSenderService;

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.email.sender.application"})
public class EmailSenderApplication {
	public static void main(String[] args) throws MessagingException {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(EmailSenderApplication.class, args);
		EmailSenderService emailSenderService = applicationContext.getBean(EmailSenderService.class);
	}

}
