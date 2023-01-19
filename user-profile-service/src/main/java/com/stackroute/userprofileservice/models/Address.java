package com.stackroute.userprofileservice.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class Address {

    private String city ;
    private String state ;
    private String country ;
    private String pincode;





}
