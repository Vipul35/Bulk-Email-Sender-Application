package com.email.sender.bulkemailsender.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class RecipientDetails {

    @Column(name = "\"to\"")
    private String to;

    private String firstName;

    private String companyName;
}
