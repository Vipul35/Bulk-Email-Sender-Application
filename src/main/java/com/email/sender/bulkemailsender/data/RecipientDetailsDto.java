package com.email.sender.bulkemailsender.data;


import lombok.Data;

@Data
public class RecipientDetailsDto {

    private String to;

    private String firstName;

    private String companyName;
}
