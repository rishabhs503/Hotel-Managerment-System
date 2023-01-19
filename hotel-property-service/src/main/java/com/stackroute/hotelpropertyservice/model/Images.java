package com.stackroute.hotelpropertyservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Images {
    @Id
    private String HotelImageId;

    @Field("Hotel serial number")
    private Integer hotelId;

    @Field("image paths")
    private List<String> imagePaths;
}