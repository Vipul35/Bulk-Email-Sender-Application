package com.email.sender.bulkemailsender.service;

import com.email.sender.bulkemailsender.data.EmailMessageDto;
import com.email.sender.bulkemailsender.entity.EmailMessageEntity;
import com.email.sender.bulkemailsender.entity.EmailMessageStatus;
import com.email.sender.bulkemailsender.mapper.EmailMessageMapper;
import com.email.sender.bulkemailsender.repository.EmailMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//@Service
@RequiredArgsConstructor
@Log4j
public class EmailMessageSchedulerService {
    private final EmailMessageRepository repository;
    private final EmailMessageSenderService emailMessageSenderService;
    private final EmailMessageMapper mapper;
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);
    private final Semaphore semaphore = new Semaphore(1000);

    @Scheduled(fixedDelay = 1000)
    public void schedule() {
        try {
            List<EmailMessageEntity> messageEntities = repository.findByStatus(EmailMessageStatus.PENDING);

            for (EmailMessageEntity entity : messageEntities) {
                entity.setStatus(EmailMessageStatus.SENDING);
                repository.save(entity);
            }

            for (EmailMessageEntity entity : messageEntities) {
                entity.setStatus(EmailMessageStatus.SENDING);
                repository.save(entity);

                EmailMessageDto dto = mapper.from(entity);

                semaphore.acquire();
                executorService.submit(() -> {
                    try {
                        emailMessageSenderService.sendEmail(dto);
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
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
