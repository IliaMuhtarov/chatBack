package com.example.chat.controller;

import com.example.chat.db_model.Dialogue;
import com.example.chat.db_model.PrivateMessage;
import com.example.chat.db_model.PublicMessage;
import com.example.chat.model.Message;
import com.example.chat.repository.DialogueRepository;
import com.example.chat.repository.PrivateMessageRepository;
import com.example.chat.repository.PublicMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс контроллера,
 * необходимый для
 * передачи веб-сокет
 * сообщений
 */
@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private DialogueRepository dialogueRepository;

    @Autowired
    private PrivateMessageRepository privateMessageRepository;

    @Autowired
    private PublicMessageRepository publicMessageRepository;

    /**
     * Метод, необходимый для
     * сохранения отправленного
     * сообщения в БД и его
     * последующей отправки
     * клиенту
     * @param message
     */
    @MessageMapping("/private-message")
    public void sendMessage(@Payload Message message) {
        Dialogue dialogue = null;
        dialogue = dialogueRepository.findByFirstMemberLoginAndSecondMemberLogin(message.getSenderLogin(), message.getReceiverLogin());
        if (dialogue == null) {
            dialogue = dialogueRepository.findByFirstMemberLoginAndSecondMemberLogin(message.getReceiverLogin(), message.getSenderLogin());
        }
        if (dialogue == null) {
            dialogue = new Dialogue();
            dialogue.setFirstMemberLogin(message.getSenderLogin());
            dialogue.setSecondMemberLogin(message.getReceiverLogin());
            dialogue = dialogueRepository.save(dialogue);
            dialogueRepository.flush();
        }
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setDialogue(dialogue);
        privateMessage.setSenderLogin(message.getSenderLogin());
        String timestamp = new SimpleDateFormat("dd.MM.yyyy.HH:mm:ss").format(new Date());
        privateMessage.setMessageTimestamp(timestamp);
        privateMessage.setMessageContent(message.getMessageContent());
        message.setTimestamp(timestamp);
        privateMessageRepository.save(privateMessage);
        simpMessagingTemplate.convertAndSend("/private/messages"+message.getSenderLogin()+""+message.getReceiverLogin(), message);
        simpMessagingTemplate.convertAndSend("/private/messages"+message.getReceiverLogin()+""+message.getSenderLogin(), message);
    }


    /**
     * Метод, необходимый
     * для сохранения
     * публичного сообщения в
     * БД и дальнейшей его
     * отправки на клиент
     * @param message
     */
    @MessageMapping("/public-message")
    public void sendPublicMessage(@Payload Message message){
        PublicMessage publicMessage = new PublicMessage();
        System.out.println(message);
        publicMessage.setSenderLogin(message.getSenderLogin());
        String timestamp = new SimpleDateFormat("dd.MM.yyyy.HH:mm:ss").format(new Date());
        publicMessage.setMessageTimestamp(timestamp);
        publicMessage.setMessageContent(message.getMessageContent());
        publicMessage.setSenderName(message.getSenderName());
        message.setTimestamp(timestamp);
        publicMessageRepository.save(publicMessage);
        simpMessagingTemplate.convertAndSend("/topic/messages", message);
    }

}
