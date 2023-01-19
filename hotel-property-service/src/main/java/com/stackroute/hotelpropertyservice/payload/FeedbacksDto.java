package com.stackroute.hotelpropertyservice.payload;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedbacksDto {

    private Integer id;

    private String message;

    private String title;

    private String stayType;

    private Set<String> filters;

    private Date createdAt;

    private Date updatedAt;

    private RatingsDto ratings;

    private Double averageRating;

}
