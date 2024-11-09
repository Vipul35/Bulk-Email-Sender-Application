package com.email.sender.bulkemailsender.service;

import com.email.sender.bulkemailsender.entity.EmailMessageEntity;
import com.email.sender.bulkemailsender.entity.EmailMessageStatus;
import com.email.sender.bulkemailsender.repository.EmailMessageRepository;
import com.email.sender.bulkemailsender.data.EmailMessageDto;
import com.email.sender.bulkemailsender.mapper.EmailMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

@Service
@RequiredArgsConstructor
public class EmailMessageService {
    private final EmailMessageRepository repository;
    private final EmailMessageMapper mapper;
    private final KafkaTemplate<Long, Long> kafkaTemplate;

    public void create(EmailMessageDto emailMessageDto){
        EmailMessageEntity entity = mapper.to(emailMessageDto);
        entity.setStatus(EmailMessageStatus.PENDING);

        repository.save(entity);

        kafkaTemplate.send("emailMessageTopic", entity.getId());
    }
}
