package com.stackroute.feedbackservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Ratings {

    private Double hospitality;
    private Double valueformoney;
    private Double location;
    private Double facilities;
    private Double cleanliness;
}
