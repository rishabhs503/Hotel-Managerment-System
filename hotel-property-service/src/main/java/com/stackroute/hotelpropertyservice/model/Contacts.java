package com.stackroute.hotelpropertyservice.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {
    @NotNull
    @Range(min = 10,max= 10, message = "phone_no should be exact 10 characters." )
    private Long phoneNumber;

    @NotNull
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
}
