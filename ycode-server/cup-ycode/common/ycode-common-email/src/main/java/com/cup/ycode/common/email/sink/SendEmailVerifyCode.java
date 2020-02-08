package com.cup.ycode.common.email.sink;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SendEmailVerifyCode {
    String INPUT = "send_email_verify_code_input";

    @Input("send_email_verify_code_input")
    SubscribableChannel input();
}
