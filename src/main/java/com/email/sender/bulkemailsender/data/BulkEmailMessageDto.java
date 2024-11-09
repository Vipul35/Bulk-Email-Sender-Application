package com.email.sender.bulkemailsender.data;

import com.email.sender.bulkemailsender.entity.EmailMessagePriority;
import lombok.Data;

import java.util.List;

@Data
public class BulkEmailMessageDto {
    private String from;
    private List<RecipientDetailsDto> recipientDetails;
    private String subject;
    private String body;
    private EmailMessagePriority priority;
    private String path;
    private boolean googleSheet;
    private String prompt;
}
