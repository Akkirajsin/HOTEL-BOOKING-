package com.ums.SERVICE;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

    @Service
    public class  TwilioService  {

        @Value("${twilio.accountSid}")
        private String accountSid;

        @Value("${twilio.authToken}")
        private String authToken;

        public void sendSms(String to, String body) { //help to login the twilio account
            Twilio.init(accountSid, authToken);

            Message message=Message.creator(
                    new  com.twilio.type.PhoneNumber(to),
                    new com.twilio.type. PhoneNumber("+18058521203"),
                    body)
            .create();
            System.out.println("SMS SENT WITH SID"+message.getSid());
        }
    }


