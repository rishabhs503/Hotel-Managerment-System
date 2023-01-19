package com.stackroute.smsservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Configuration
public class TwilioConfigureDetails {

    @Value("${sms.config.twilio_account_sid}")
    private String twilioAccountSid;
    @Value("${sms.config.twilio_auth_token}")
    private String twilioAuthToken;
    @Value("${sms.config.sms_sender_number}")
    private String smsSenderNumber;
    @Value("${sms.config.whatsapp_sender_number}")
    private String whatsappSenderNumber;
}
