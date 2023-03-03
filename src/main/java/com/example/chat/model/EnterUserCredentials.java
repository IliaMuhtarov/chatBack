package com.example.chat.model;

import lombok.Data;

/**
 * Класс, необходимый
 * для передачи данных
 * регистрируемого пользователя
 * с клиента на сервер
 */
@Data
public class EnterUserCredentials {
    private String login;
    private String password;
    private String userName;
}
