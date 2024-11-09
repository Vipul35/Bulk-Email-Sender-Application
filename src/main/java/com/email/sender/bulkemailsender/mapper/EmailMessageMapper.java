package com.email.sender.bulkemailsender.mapper;

import com.email.sender.bulkemailsender.entity.EmailMessageEntity;
import com.email.sender.bulkemailsender.data.EmailMessageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMessageMapper {

    EmailMessageDto from( EmailMessageEntity entity);

    EmailMessageEntity to(EmailMessageDto entity);

}
