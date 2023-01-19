package com.stackroute.feedbackservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "Feedbacks")
public class Feedback {

//    @ApiModelProperty(hidden = true)
    @MongoId(FieldType.OBJECT_ID)
    private Long id;

    @Field("Feedback_Message")
    private String message;

    @Field("Feedback_Title")
    private String title;

    @Field("Stay_Type")
    private String stayType;

    @Field("Feedback_Filters")
    private Set<String> filters;

    @ApiModelProperty(hidden = true)
    @Field("Created_At")
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private Date createdAt;

    @ApiModelProperty(hidden = true)
    @Field("Updated_At")
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private Date updatedAt;

    @Field("Feedback_Ratings")
    private Ratings ratings;

    @ApiModelProperty(hidden = true)
    @Field("Feedback_average_rating")
    private Double averageRating;

    @ApiModelProperty(hidden = true)
    @Field("Image_Urls")
    private List<String> urls;

    @ApiModelProperty(hidden = true)
    @Field("feedback_reply")
    private String reply;

    @ApiModelProperty(hidden = true)
    @Field("User_Id")
    private Integer userId;

    @ApiModelProperty(hidden = true)
    @Field("Hotel_Id")
    private Integer hotelId;
}
