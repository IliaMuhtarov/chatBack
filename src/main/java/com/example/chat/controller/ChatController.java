package com.example.chat.controller;


import com.example.chat.db_model.Dialogue;
import com.example.chat.db_model.PrivateMessage;
import com.example.chat.db_model.PublicMessage;
import com.example.chat.model.Message;
import com.example.chat.repository.DialogueRepository;
import com.example.chat.repository.PublicMessageRepository;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс контроллера,
 * необходимый для получения
 * данных о переписках
 */
@RestController
public class ChatController {


    @Autowired
    DialogueRepository dialogueRepository;

    @Autowired
    PublicMessageRepository publicMessageRepository;

    /**
     * Метод необходимый
     * для получения всех
     * сообщений переписки
     * с каким-либо человеком
     * @param userLogin
     * @param companionLogin
     * @return
     */
    @GetMapping("/saved-messages")
    @CrossOrigin("*")
    public List<Message> getDialogueMessages(
            @RequestParam String userLogin,
            @RequestParam String companionLogin
    ){
        ArrayList<Message> messages = new ArrayList<>();
        List<PrivateMessage> messagesInfo;
        Dialogue dialogue = null;
        dialogue = dialogueRepository.findByFirstMemberLoginAndSecondMemberLogin(userLogin, companionLogin);
        if (dialogue == null) {
            dialogue = dialogueRepository.findByFirstMemberLoginAndSecondMemberLogin(companionLogin, userLogin);
        }
        if (dialogue == null) {
            dialogue = new Dialogue();
            dialogue.setFirstMemberLogin(userLogin);
            dialogue.setSecondMemberLogin(companionLogin);
            dialogue = dialogueRepository.save(dialogue);
        }
        messagesInfo = dialogue.getMessages();
        if(messagesInfo != null) {
            for (PrivateMessage privateMessage : messagesInfo) {
                messages.add(privateMessage.mapToMessage());
            }
        }
        //сортировка по времени
        messages = (ArrayList<Message>) messages.stream().sorted((Comparator.comparing(Message::getTimestamp))).collect(Collectors.toList());
        return messages;
    }


    /**
     * Данный метод необходим
     * для получения сообщений
     * диалога в определенном
     * временном диапазоне
     * @param userLogin
     * @param companionLogin
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/saved-messages-by-date")
    @CrossOrigin("*")
    public List<Message> getDialogueMessagesByDate(
            @RequestParam String userLogin,
            @RequestParam String companionLogin,
            @RequestParam String startDate,
            @RequestParam String endDate
    ){
        ArrayList<Message> messages = new ArrayList<>();
        List<PrivateMessage> messagesInfo;
        Dialogue dialogue = null;
        dialogue = dialogueRepository.findByFirstMemberLoginAndSecondMemberLogin(userLogin, companionLogin);
        if (dialogue == null) {
            dialogue = dialogueRepository.findByFirstMemberLoginAndSecondMemberLogin(companionLogin, userLogin);
        }
        DateTimeFormatter clientDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter serverDateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm:ss");
        LocalDate startDateObject = LocalDate.parse(startDate, clientDateTimeFormatter);
        LocalDate endDateObject = LocalDate.parse(endDate, clientDateTimeFormatter);
        messagesInfo = dialogue.getMessages();
        if(messagesInfo != null) {
            for (PrivateMessage privateMessage : messagesInfo) {
                LocalDate messageDateObject = LocalDate.parse(privateMessage.getMessageTimestamp(), serverDateTimeFormatter);
                int startDateComparing = messageDateObject.compareTo(startDateObject);
                int endDateComparing = messageDateObject.compareTo(endDateObject);
                if (startDateComparing >= 0 && endDateComparing <= 0) {
                    messages.add(privateMessage.mapToMessage());
                }
            }
        }
        //сортировка по времени
        messages = (ArrayList<Message>) messages.stream().sorted((Comparator.comparing(Message::getTimestamp))).collect(Collectors.toList());
        return messages;
    }

    /**
     * Метод, необходимый
     * для получения
     * всех сохраненных
     * сообщений беседы
     */
    @GetMapping("/saved-public-messages")
    @CrossOrigin("*")
    public List<Message> getCommunicationMessage(){
        ArrayList<Message> messages = new ArrayList<>();
        List<PublicMessage> messagesInfo;
        messagesInfo = publicMessageRepository.findAll();
        if (messagesInfo != null) {
            for (PublicMessage publicMessage : messagesInfo) {
                messages.add(publicMessage.mapToMessage());
            }
        }
        //сортировка по времени
        messages = (ArrayList<Message>) messages.stream().sorted((Comparator.comparing(Message::getTimestamp))).collect(Collectors.toList());
        return messages;
    }

    /**
     * Метод, необходимый для
     * получения всех
     * сохраненных сообщений
     * беседы по дате
     */
    @GetMapping("/saved-public-messages-by-date")
    @CrossOrigin("*")
    public List<Message> getCommunicationMessageByDate(
            @RequestParam String startDate,
            @RequestParam String endDate
    ){
        ArrayList<Message> messages = new ArrayList<>();
        List<PublicMessage> messagesInfo;
        messagesInfo = publicMessageRepository.findAll();
        DateTimeFormatter clientDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter serverDateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm:ss");
        LocalDate startDateObject = LocalDate.parse(startDate, clientDateTimeFormatter);
        LocalDate endDateObject = LocalDate.parse(endDate, clientDateTimeFormatter);
        if (messagesInfo != null) {
            for (PublicMessage publicMessage : messagesInfo) {
                LocalDate messageDateObject = LocalDate.parse(publicMessage.getMessageTimestamp(), serverDateTimeFormatter);
                int startDateComparing = messageDateObject.compareTo(startDateObject);
                int endDateComparing = messageDateObject.compareTo(endDateObject);
                if (startDateComparing >= 0 && endDateComparing <= 0) {
                    messages.add(publicMessage.mapToMessage());
                }
            }
        }
        //сортировка по времени
        messages = (ArrayList<Message>) messages.stream().sorted((Comparator.comparing(Message::getTimestamp))).collect(Collectors.toList());
        return messages;
    }

}
