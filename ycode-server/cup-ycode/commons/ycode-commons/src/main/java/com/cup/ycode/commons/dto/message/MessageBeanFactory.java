package com.cup.ycode.commons.dto.message;

public class MessageBeanFactory {
    public static AbstractMessage build(String subject, String body, String to){
        MessageEmail messageEmail = new MessageEmail();
        messageEmail.setSubject(subject);
        messageEmail.setBody(body);
        messageEmail.setTo(to);
        return messageEmail;
    }
}
