package com.stackroute.hotelpropertyservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Address {

    @Id
    private String addressId;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String country;

    @NotNull
    private String zipCode;

    public Address(String city, String state, String country, String zipCode) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }
}
