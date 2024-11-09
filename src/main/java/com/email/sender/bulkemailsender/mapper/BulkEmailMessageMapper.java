package com.email.sender.bulkemailsender.mapper;

import com.email.sender.bulkemailsender.entity.BulkEmailMessageEntity;
import com.email.sender.bulkemailsender.data.BulkEmailMessageDto;
import com.email.sender.bulkemailsender.data.RecipientDetailsDto;
import com.email.sender.bulkemailsender.entity.RecipientDetails;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BulkEmailMessageMapper {

    BulkEmailMessageDto from( BulkEmailMessageEntity entity);

    BulkEmailMessageEntity to(BulkEmailMessageDto entity);

    List<RecipientDetails> mapRecipientDetailsDtoList( List<RecipientDetailsDto> recipientDetailsDtoList);

    RecipientDetails toRecipientDetails(RecipientDetailsDto dto);

}
