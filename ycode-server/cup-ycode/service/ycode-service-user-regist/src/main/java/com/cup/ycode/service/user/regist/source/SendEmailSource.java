package com.cup.ycode.service.user.regist.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SendEmailSource {
    String OUTPUT = "send_email_output";

    @Output("send_email_output")
    MessageChannel output();
}
