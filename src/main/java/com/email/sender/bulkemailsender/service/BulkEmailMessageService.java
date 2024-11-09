package com.email.sender.bulkemailsender.service;

import com.email.sender.bulkemailsender.data.BulkEmailMessageDto;
import com.email.sender.bulkemailsender.data.EmailMessageDto;
import com.email.sender.bulkemailsender.data.RecipientDetailsDto;
import com.email.sender.bulkemailsender.entity.BulkEmailMessageEntity;
import com.email.sender.bulkemailsender.entity.RecipientDetails;
import com.email.sender.bulkemailsender.googlesheet.SheetsQuickstart;
import com.email.sender.bulkemailsender.mapper.BulkEmailMessageMapper;
import com.email.sender.bulkemailsender.repository.BulkEmailMessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.kafka.support.serializer.DelegatingSerializer;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BulkEmailMessageService {
    private final BulkEmailMessageRepository repository;
    private final BulkEmailMessageMapper mapper;
    private final EmailMessageService emailMessageService;
    private final SheetsQuickstart sheetsQuickstart;

    public void create(BulkEmailMessageDto bulkEmailMessageDto) throws GeneralSecurityException, IOException {
        if(bulkEmailMessageDto.isGoogleSheet())
        {
            bulkEmailMessageDto=sheetsQuickstart.getDataFromSheet(bulkEmailMessageDto);
        }
        else{
            bulkEmailMessageDto = readXLSXFile(bulkEmailMessageDto);
        }
        BulkEmailMessageEntity entity = mapper.to(bulkEmailMessageDto);
        List<RecipientDetails> recipientDetailsEntity= mapper.mapRecipientDetailsDtoList(bulkEmailMessageDto.getRecipientDetails());
        entity.setRecipientDetails(recipientDetailsEntity);
        repository.save(entity);
        for (RecipientDetailsDto recipientDetails : bulkEmailMessageDto.getRecipientDetails()) {
            EmailMessageDto emailDto = new EmailMessageDto();
            emailDto.setFrom("vipulkapoor35@gmail.com");
            emailDto.setTo(recipientDetails.getTo());
            emailDto.setSubject(bulkEmailMessageDto.getSubject());
            emailDto.setBody(bulkEmailMessageDto.getBody());
            emailDto.setPriority(bulkEmailMessageDto.getPriority());
            emailDto.setCreatedOn(Timestamp.from(Instant.now()));
            emailMessageService.create(emailDto);
        }
    }

    private static BulkEmailMessageDto readXLSXFile(BulkEmailMessageDto emailMessageDto) {

        try {
            XSSFWorkbook work = new XSSFWorkbook(new FileInputStream(emailMessageDto.getPath()));
            XSSFSheet sheet = work.getSheet( "Sheet1" );
            List<RecipientDetailsDto> recipientDetailsList=new ArrayList<>();
            int numberOfRows = sheet.getPhysicalNumberOfRows();
            int i = 0;
            while (i < numberOfRows) {
                XSSFRow row = sheet.getRow(i);

                if (row.getCell( 0 ) == null || row.getCell( 0 ).getCellType() == Cell.CELL_TYPE_BLANK ) {
                    break; // Break the loop if the first cell is null or empty
                }
                String name;
                String email = null;
                String company = null;
                RecipientDetailsDto recipientDetails=new RecipientDetailsDto();
                try {
                    name = row.getCell( 0 ).getStringCellValue();
                    recipientDetails.setFirstName(name);
                } catch (Exception e) {
                    name = null;
                }
                try {
                    company = row.getCell( 1 ).getStringCellValue();
                    recipientDetails.setCompanyName(company);
                } catch ( Exception e ) {
                    company = null;
                }
                try {
                    email = row.getCell( 2 ).getStringCellValue();
                    recipientDetails.setTo(email);
                } catch (Exception e) {
                    email = null;
                }
                i++;
                recipientDetailsList.add(recipientDetails);
            }
            emailMessageDto.setRecipientDetails(recipientDetailsList);
            work.close();
        } catch ( IOException e) {
            System.out.println("Exception is Customer fetch data :: " + e.getMessage());
            e.printStackTrace();
        }
        return emailMessageDto;
    }
}
