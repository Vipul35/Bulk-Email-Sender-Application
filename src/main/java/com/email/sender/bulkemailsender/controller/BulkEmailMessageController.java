package com.email.sender.bulkemailsender.controller;

import com.email.sender.bulkemailsender.service.BulkEmailMessageService;
import com.email.sender.bulkemailsender.data.BulkEmailMessageDto;
import com.email.sender.bulkemailsender.service.EmailFollowUpService;
import com.email.sender.bulkemailsender.service.EmailReplyTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.ollama.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RequiredArgsConstructor
@Service
@RestController
@RequestMapping("/api/v1/bulk-email-messages")
public class BulkEmailMessageController {

    private final BulkEmailMessageService emailMessageService;
    private final EmailReplyTrackerService replyTrackerService;
    private final OllamaChatModel chatModel;
    private final EmailFollowUpService emailFollowUpService;


    @PostMapping
    public void create(@RequestBody BulkEmailMessageDto emailMessageDto) throws GeneralSecurityException, IOException {
        ChatResponse response = chatModel.call(
                new Prompt(emailMessageDto.getPrompt(),
                        OllamaOptions.create()
                                .withModel(OllamaModel.MISTRAL)
                ));
        String message=response.getResult().getOutput().getContent();
        emailMessageDto.setBody(message);
        emailMessageService.create(emailMessageDto);
    }

}
