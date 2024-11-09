package com.email.sender.bulkemailsender.repository;

import com.email.sender.bulkemailsender.entity.EmailMessageEntity;
import com.email.sender.bulkemailsender.entity.EmailMessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailMessageRepository extends JpaRepository<EmailMessageEntity, Long> {
    List<EmailMessageEntity> findByStatus(EmailMessageStatus emailMessageStatus);

    EmailMessageEntity findFirstByToOrderByIdDesc(String email);

    List<EmailMessageEntity> findByStatusAndFrom(EmailMessageStatus emailMessageStatus, String from);
}
