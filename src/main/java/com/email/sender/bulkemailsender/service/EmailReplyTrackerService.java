package com.email.sender.bulkemailsender.service;

import com.email.sender.bulkemailsender.entity.EmailMessageEntity;
import com.email.sender.bulkemailsender.entity.EmailMessageStatus;
import com.email.sender.bulkemailsender.repository.EmailMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class EmailReplyTrackerService {

    private final EmailMessageRepository repository;

    public void checkForReplies() {
        try {

            Properties properties = new Properties();
            properties.put( "mail.imap.host", "imap.gmail.com" );
            properties.put( "mail.imap.port", "993" );
            properties.put( "mail.imap.ssl.enable", "true" );

            Session session = Session.getInstance( properties );


            Store store = session.getStore("imap" );
            store.connect("imap.gmail.com", "vipulkapoor35@gmail.com", "cdjh ungy dgde srma");

            Folder inbox = store.getFolder( "INBOX" );
            inbox.open( Folder.READ_ONLY );


            Calendar calendar = Calendar.getInstance();
            calendar.add( Calendar.DAY_OF_MONTH, -5 ); // 5 days ago
            Date fiveDaysAgo = calendar.getTime();

            // Search emails received after the specified date
            SearchTerm newerThan = new ReceivedDateTerm( ComparisonTerm.GT, fiveDaysAgo );
            Message[] messages = inbox.search( newerThan );

            for (Message message : messages) {
                // Check if the email is a reply
                String[] inReplyToHeaders = message.getHeader( "In-Reply-To" );
                String inReplyTo = inReplyToHeaders != null && inReplyToHeaders.length > 0 ? inReplyToHeaders[0] : null;
                if ( inReplyTo != null ) {
                    // Extract sender's email
                    Address[] fromAddresses = message.getFrom();
                    if ( fromAddresses != null ) {
                        InternetAddress internetAddress = (InternetAddress) fromAddresses[0];
                        String senderEmail = internetAddress.getAddress();
                        EmailMessageEntity emailMessageEntity = repository.findFirstByToOrderByIdDesc(senderEmail);
                        if(emailMessageEntity!=null) {
                            emailMessageEntity.setStatus(EmailMessageStatus.REPLYRECIEVED);
                            repository.save(emailMessageEntity);
                        }
                        System.out.println("Reply from: " + senderEmail);
                    }
                }
            }

            inbox.close( false );
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

