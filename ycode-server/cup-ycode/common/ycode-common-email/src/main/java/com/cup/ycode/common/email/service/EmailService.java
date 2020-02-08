package com.cup.ycode.common.email.service;

import com.cup.ycode.commons.dto.message.MessageEmail;
import com.cup.ycode.commons.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 发送邮件  标题为 send_email_input
     * @param messageEmailJson 邮件json字符串
     * @throws Exception
     */
    @StreamListener("send_email_input")
    public void sendRegistSucceed(String messageEmailJson) throws Exception {
        MessageEmail messageEmail = MapperUtils.json2pojo(messageEmailJson, MessageEmail.class);
        sendEmail(messageEmail.getSubject(), messageEmail.getBody(), messageEmail.getTo());
    }

    /**
     * 发送邮件  标题为 send_email_verify_code_input
     * @param messageEmailJson 邮件json字符串
     * @throws Exception
     */
    @StreamListener("send_email_verify_code_input")
    public void sendRegistVerifyCode(String messageEmailJson) throws Exception {
        MessageEmail messageEmail = MapperUtils.json2pojo(messageEmailJson, MessageEmail.class);
        sendEmail(messageEmail.getSubject(), messageEmail.getBody(), messageEmail.getTo());
    }

    /**
     * 发送普通邮件
     * @param subject
     * @param body
     * @param to
     */
    @Async
    public void sendEmail(String subject, String body, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(applicationContext.getEnvironment().getProperty("spring.mail.username"));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}
