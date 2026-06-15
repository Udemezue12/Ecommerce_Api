package com.uchechukwu.store.email.gmail;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.uchechukwu.store.config.NotificationProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class GmailConfig {

    private final NotificationProperties properties;

    @Bean
    public JavaMailSender javaMailSender() {

        var sender =
                new JavaMailSenderImpl();

        sender.setHost(
                properties.getEmailHost());

        sender.setPort(
                properties.getEmailPort());

        sender.setUsername(
                properties.getEmailUsername());

        sender.setPassword(
                properties.getEmailPassword());

        Properties props =
                sender.getJavaMailProperties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put(
                "mail.smtp.starttls.enable",
                String.valueOf(properties.getEmailUseTls()));
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.debug", "false");

        return sender;
    }
}