package com.example.chat.db_model;


import com.example.chat.model.Message;
import jakarta.persistence.*;

/**
 * Класс, который отражает
 * сущность БД, необходимую
 * для храения информации
 * о сообщении беседы
 */
@Entity
@Table(name = "public_message")
public class PublicMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long publicMessageId;

    @Column(name = "message_content")
    private String messageContent;

    @Column(name = "message_timestamp")
    private String messageTimestamp;

    @Column(name = "sender_login")
    private String senderLogin;

    @Column(name = "sender_name")
    private String senderName;

    public PublicMessage(){
        super();
    }

    public PublicMessage(String messageContent, String messageTimestamp, String senderLogin, String senderName) {
        super();
        this.messageContent = messageContent;
        this.messageTimestamp = messageTimestamp;
        this.senderLogin = senderLogin;
        this.senderName = senderName;
    }

    public long getPublicMessageId() {
        return publicMessageId;
    }

    public void setPublicMessageId(long publicMessageId) {
        this.publicMessageId = publicMessageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(String messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }

    public Message mapToMessage(){
        Message message = new Message();
        message.setMessageContent(this.messageContent);
        message.setSenderLogin(this.senderLogin);
        message.setSenderName(this.senderName);
        message.setTimestamp(this.messageTimestamp);
        return message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
