package com.email.sender.bulkemailsender.repository;

import com.email.sender.bulkemailsender.entity.BulkEmailMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkEmailMessageRepository extends JpaRepository<BulkEmailMessageEntity, Long> {
}
