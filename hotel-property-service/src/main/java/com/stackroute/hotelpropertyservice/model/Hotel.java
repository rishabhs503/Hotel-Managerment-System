package com.stackroute.hotelpropertyservice.model;

import com.stackroute.hotelpropertyservice.payload.UserDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Hotel {

    @Id
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private List<Room> rooms;

    @NotNull
    @Field("Hotel building number")
    private String hNumber;

    private Contacts contacts;

    @DBRef
    private Address address;

    @DBRef
    private Images images;

    private UserDto user;

    private List<String> aminities;
}
