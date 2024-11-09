package com.email.sender.bulkemailsender.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class BulkEmailMessageEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "\"from\"")
    private String from;

    @ElementCollection(targetClass = RecipientDetails.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "\"to\"", joinColumns = @JoinColumn(name = "bulk_email_message_id"))
    @Column(name = "\"to\"")
    private List<RecipientDetails> recipientDetails;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    private EmailMessagePriority priority;
}
