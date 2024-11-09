package com.email.sender.bulkemailsender.service;

import com.email.sender.bulkemailsender.data.EmailMessageDto;
import com.email.sender.bulkemailsender.entity.EmailMessageEntity;
import com.email.sender.bulkemailsender.entity.EmailMessageStatus;
import com.email.sender.bulkemailsender.mapper.EmailMessageMapper;
import com.email.sender.bulkemailsender.repository.EmailMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailMessageKafkaScheduler {
    private final EmailMessageRepository repository;
    private final EmailMessageSenderService emailMessageSenderService;
    private final EmailMessageMapper mapper;
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);
    private final Semaphore semaphoreLow = new Semaphore(30);
    private final Semaphore semaphoreMiddle = new Semaphore(100);
    private final Semaphore semaphoreHigh = new Semaphore(3000);

    // low      3X
    // middle   10X
    // high     30X

    @KafkaListener(topics = "emailMessageTopic", groupId = "emailMessageTopic")
    public void emailMessageTopicLow(Long messageId) throws InterruptedException {
        semaphoreLow.acquire();
        sendEmailMessage(messageId, semaphoreLow, false);
    }

    @KafkaListener(topics = "emailMessageTopicMedium", groupId = "emailMessageTopic")
    public void emailMessageTopicMedium(Long messageId) throws InterruptedException {
        semaphoreMiddle.acquire();
        sendEmailMessage(messageId, semaphoreMiddle, false);
    }

    @KafkaListener(topics = "emailMessageTopicHigh", groupId = "emailMessageTopic")
    public void emailMessageTopicHigh(Long messageId) throws InterruptedException {
        semaphoreHigh.acquire();
        sendEmailMessage(messageId, semaphoreHigh, false);
    }

    @KafkaListener(topics = "emailMessageFollowUp", groupId = "emailMessageTopic")
    public void emailMessageFollowUp(Long messageId) throws InterruptedException {
        semaphoreHigh.acquire();
        sendEmailMessage(messageId, semaphoreHigh, true);
    }

    private void sendEmailMessage(Long messageId, Semaphore semaphore, boolean check) throws InterruptedException {
        EmailMessageEntity entity = repository.findById(messageId).orElseThrow();
        EmailMessageDto dto = mapper.from(entity);
        executorService.submit(() -> { // potential problem, if task is rejected, we need to either make sure, it is not rejected or we need to release semmaphore
            try {
                emailMessageSenderService.sendEmail(dto);
                if(check)
                    entity.setStatus(EmailMessageStatus.FOLLOW_UP_SENT);
                else
                    entity.setStatus(EmailMessageStatus.SENT);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                entity.setStatus(EmailMessageStatus.FAILED);
            } finally {
                repository.save(entity);
                semaphore.release();
            }
        });
    }
}
