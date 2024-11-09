package com.email.sender.bulkemailsender.data;

import com.email.sender.bulkemailsender.entity.EmailMessagePriority;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class EmailMessageDto {
    private String from;
    private String to;
    private String subject;
    private String body;
    private EmailMessagePriority priority;
    private Timestamp createdOn;
    private boolean followUp;
}
