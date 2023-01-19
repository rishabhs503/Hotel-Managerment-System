package com.stackroute.userauthenticationservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel(description = "Jwt Request model")
public class JwtRequest {

    @ApiModelProperty(notes = "Email of owner/customer", name = "emailId", required = true, value = "abc@xyz.com")
    private String emailId;
    @ApiModelProperty(notes = "Password of owner/customer", name = "password", required = true, value = "London@123")
    private String password;
}
