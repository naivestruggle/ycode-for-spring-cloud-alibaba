package com.cup.ycode.service.user.regist.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SendEmailVerifyCodeSource {
    String OUTPUT = "send_email_verify_code_output";

    @Output("send_email_verify_code_output")
    MessageChannel output();
}
