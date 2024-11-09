package com.email.sender.bulkemailsender.mapper;

import com.email.sender.bulkemailsender.data.EmailMessageDto;
import com.email.sender.bulkemailsender.entity.EmailMessageEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-09T17:53:24+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class EmailMessageMapperImpl implements EmailMessageMapper {

    @Override
    public EmailMessageDto from(EmailMessageEntity entity) {
        if ( entity == null ) {
            return null;
        }

        EmailMessageDto emailMessageDto = new EmailMessageDto();

        emailMessageDto.setFrom( entity.getFrom() );
        emailMessageDto.setTo( entity.getTo() );
        emailMessageDto.setSubject( entity.getSubject() );
        emailMessageDto.setBody( entity.getBody() );
        emailMessageDto.setPriority( entity.getPriority() );
        emailMessageDto.setCreatedOn( entity.getCreatedOn() );

        return emailMessageDto;
    }

    @Override
    public EmailMessageEntity to(EmailMessageDto entity) {
        if ( entity == null ) {
            return null;
        }

        EmailMessageEntity emailMessageEntity = new EmailMessageEntity();

        emailMessageEntity.setFrom( entity.getFrom() );
        emailMessageEntity.setTo( entity.getTo() );
        emailMessageEntity.setSubject( entity.getSubject() );
        emailMessageEntity.setBody( entity.getBody() );
        emailMessageEntity.setPriority( entity.getPriority() );
        emailMessageEntity.setCreatedOn( entity.getCreatedOn() );

        return emailMessageEntity;
    }
}
