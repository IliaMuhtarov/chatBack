package com.example.chat.db_model;


import com.example.chat.model.Message;
import jakarta.persistence.*;

/**
 * Класс, который отражает
 * сущность БД, необходимую
 * для хранения информации
 * о личном сообщении
 */
@Entity
@Table(name = "private_message")
public class PrivateMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long privateMessageId;

    @Column(name = "message_content")
    private String messageContent;

    @Column(name = "message_timestamp")
    private String messageTimestamp;

    @Column(name = "sender_login")
    private String senderLogin;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dialogue_id", referencedColumnName = "dialogueId")
    private Dialogue dialogue;

    public PrivateMessage(String messageContent, String messageTimestamp, String senderLogin) {
        this.messageContent = messageContent;
        this.messageTimestamp = messageTimestamp;
        this.senderLogin = senderLogin;
    }

    public PrivateMessage(){
        super();
    }

    public long getPrivateMessageId() {
        return privateMessageId;
    }

    public void setPrivateMessageId(long privateMessageId) {
        this.privateMessageId = privateMessageId;
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

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public Message mapToMessage(){
        Message message = new Message();
        message.setMessageContent(this.messageContent);
        message.setSenderLogin(this.senderLogin);
        message.setTimestamp(this.messageTimestamp);
        return message;
    }

}
