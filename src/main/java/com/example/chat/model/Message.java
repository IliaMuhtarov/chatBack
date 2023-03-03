package com.example.chat.model;

import lombok.Data;

/**
 * Класс, необходимый
 * для передачи информации о
 * сообщениях через веб-сокет
 * подключение
 */
@Data
public class Message {
    private String messageContent;
    private String senderLogin;
    private String receiverLogin;
    private String senderName;
    private String timestamp;
}
