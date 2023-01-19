package com.stackroute.hotelpropertyservice.payload;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedbackWithAverageRatingDto {
    private Double HotelAverageRating;
    private List<FeedbacksDto> Feedbacks;
}
