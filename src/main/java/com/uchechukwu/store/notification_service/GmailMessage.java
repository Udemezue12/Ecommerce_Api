package com.uchechukwu.store.notification_service;

import com.uchechukwu.store.interfaces.Message;
import org.springframework.stereotype.Service;

@Service("gmail")
public class GmailMessage implements Message {

    @Override
    public void sendMessage(String message, String name) {
        System.out.println(message + name);
    }

}
