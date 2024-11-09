package com.email.sender.bulkemailsender.mapper;

import com.email.sender.bulkemailsender.data.BulkEmailMessageDto;
import com.email.sender.bulkemailsender.data.RecipientDetailsDto;
import com.email.sender.bulkemailsender.entity.BulkEmailMessageEntity;
import com.email.sender.bulkemailsender.entity.RecipientDetails;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-09T17:53:24+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class BulkEmailMessageMapperImpl implements BulkEmailMessageMapper {

    @Override
    public BulkEmailMessageDto from(BulkEmailMessageEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BulkEmailMessageDto bulkEmailMessageDto = new BulkEmailMessageDto();

        bulkEmailMessageDto.setFrom( entity.getFrom() );
        bulkEmailMessageDto.setRecipientDetails( recipientDetailsListToRecipientDetailsDtoList( entity.getRecipientDetails() ) );
        bulkEmailMessageDto.setSubject( entity.getSubject() );
        bulkEmailMessageDto.setBody( entity.getBody() );
        bulkEmailMessageDto.setPriority( entity.getPriority() );

        return bulkEmailMessageDto;
    }

    @Override
    public BulkEmailMessageEntity to(BulkEmailMessageDto entity) {
        if ( entity == null ) {
            return null;
        }

        BulkEmailMessageEntity bulkEmailMessageEntity = new BulkEmailMessageEntity();

        bulkEmailMessageEntity.setFrom( entity.getFrom() );
        bulkEmailMessageEntity.setRecipientDetails( mapRecipientDetailsDtoList( entity.getRecipientDetails() ) );
        bulkEmailMessageEntity.setSubject( entity.getSubject() );
        bulkEmailMessageEntity.setBody( entity.getBody() );
        bulkEmailMessageEntity.setPriority( entity.getPriority() );

        return bulkEmailMessageEntity;
    }

    @Override
    public List<RecipientDetails> mapRecipientDetailsDtoList(List<RecipientDetailsDto> recipientDetailsDtoList) {
        if ( recipientDetailsDtoList == null ) {
            return null;
        }

        List<RecipientDetails> list = new ArrayList<RecipientDetails>( recipientDetailsDtoList.size() );
        for ( RecipientDetailsDto recipientDetailsDto : recipientDetailsDtoList ) {
            list.add( toRecipientDetails( recipientDetailsDto ) );
        }

        return list;
    }

    @Override
    public RecipientDetails toRecipientDetails(RecipientDetailsDto dto) {
        if ( dto == null ) {
            return null;
        }

        RecipientDetails recipientDetails = new RecipientDetails();

        recipientDetails.setTo( dto.getTo() );
        recipientDetails.setFirstName( dto.getFirstName() );
        recipientDetails.setCompanyName( dto.getCompanyName() );

        return recipientDetails;
    }

    protected RecipientDetailsDto recipientDetailsToRecipientDetailsDto(RecipientDetails recipientDetails) {
        if ( recipientDetails == null ) {
            return null;
        }

        RecipientDetailsDto recipientDetailsDto = new RecipientDetailsDto();

        recipientDetailsDto.setTo( recipientDetails.getTo() );
        recipientDetailsDto.setFirstName( recipientDetails.getFirstName() );
        recipientDetailsDto.setCompanyName( recipientDetails.getCompanyName() );

        return recipientDetailsDto;
    }

    protected List<RecipientDetailsDto> recipientDetailsListToRecipientDetailsDtoList(List<RecipientDetails> list) {
        if ( list == null ) {
            return null;
        }

        List<RecipientDetailsDto> list1 = new ArrayList<RecipientDetailsDto>( list.size() );
        for ( RecipientDetails recipientDetails : list ) {
            list1.add( recipientDetailsToRecipientDetailsDto( recipientDetails ) );
        }

        return list1;
    }
}
