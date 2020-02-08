package com.cup.ycode.common.email.sink;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SendEmail {
    String INPUT = "send_email_input";

    @Input("send_email_input")
    SubscribableChannel input();
}
