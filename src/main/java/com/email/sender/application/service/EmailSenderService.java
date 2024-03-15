package com.email.sender.application.service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class EmailSenderService {
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
	private String subject="Your Custom Subject";  //could be anything from job finding to cold email campaigns
	private static int cnt=0;

	
	@Autowired
	private ApplicationContext applicationContext;
	  
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailSenderHelper emailSenderHelper;
	
//	@Autowired 
//	private SheetsQuickstart sheets;
	
	@PostConstruct
    public void sendEmailOnStartup() throws IOException, GeneralSecurityException, InterruptedException {
		List<Customer> custList=emailSenderHelper.readExcelFile();
		Scanner sc=new Scanner(System.in);
		System.out.print("Now we are going to start sending bulk emails. Do you want to send any attachment file. Type yes if you want to");
		String attachment=sc.nextLine();
		if(attachment.equals("yes"))
		{
		for(Customer client:custList)
		sendEmail("Sender Email ID", client.getEmail(), subject+client.getCompany() , "Email Body", "Resume of Portfolio path");
		}
    }

    private void sendEmail(String fromEmail, String toEmail, String subject, String body, String attachment) throws InterruptedException {
        try {
            // Your email sending logic here
        	cnt++;
        	Thread.sleep(8000);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setText(body);
            mimeMessageHelper.setSubject(subject);

            FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

            mailSender.send(mimeMessage);
            System.out.println("Mail with attachment sent successfully.."  + formatter.format(LocalDateTime.now())+" -> "+ cnt);
        } catch (MessagingException e) {
            // Handle messaging exception
            e.printStackTrace();
        }
    }
}
