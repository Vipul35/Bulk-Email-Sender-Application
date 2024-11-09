package com.email.sender.bulkemailsender.service;

import com.email.sender.bulkemailsender.data.EmailMessageDto;
import com.email.sender.bulkemailsender.entity.EmailMessageEntity;
import com.email.sender.bulkemailsender.entity.EmailMessageStatus;
import com.email.sender.bulkemailsender.mapper.EmailMessageMapper;
import com.email.sender.bulkemailsender.repository.EmailMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmailFollowUpService {

    private final EmailMessageRepository emailMessageRepository;
    private final EmailReplyTrackerService emailReplyTrackerService;
    private final EmailMessageRepository repository;
    private final KafkaTemplate<Long, Long> kafkaTemplate;
  //  @Scheduled(cron = "0 0 10 * * ?")
    public void sendFollowUpEmails()
    {
        emailReplyTrackerService.checkForReplies();
        List<EmailMessageEntity> emailsToFollowUp = emailMessageRepository.findByStatusAndFrom(EmailMessageStatus.SENT, "vipulkapoor35@gmail.com");
        for(EmailMessageEntity emailMessageEntity:emailsToFollowUp)
        {
            emailMessageEntity.setStatus(EmailMessageStatus.PENDING);
            repository.save(emailMessageEntity);
            kafkaTemplate.send("emailMessageFollowUp", emailMessageEntity.getId());
        }
    }
}
