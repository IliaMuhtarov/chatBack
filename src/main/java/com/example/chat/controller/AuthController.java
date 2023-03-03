package com.example.chat.controller;

import com.example.chat.db_model.User;
import com.example.chat.model.EnterUserCredentials;
import com.example.chat.model.LoginUserCredentials;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Класс контроллера, который
 * обрабатывает http запросы
 * регистрации и авторизации
 */
@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    /**
     * Метод необходимый для
     * регистрации нового
     * пользователя. Возвращает
     * различные коды статуса
     * в зависимости от успешности
     * регистрации
     * 409 - логин уже существует
     * 201 - пользователь добавлен в БД
     * @param userCredentials
     * @return
     */
    @PostMapping("/register")
    @CrossOrigin(origins = "*")
    public ResponseEntity register(
            @RequestBody EnterUserCredentials userCredentials
    ){
        User user = userRepository.findByLogin(userCredentials.getLogin());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else{
            userRepository.save(new User(userCredentials.getUserName(),
                    userCredentials.getLogin(), userCredentials.getPassword()));
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }

    }

    /**
     * Метод необходимый для
     * авторизации пользователя.
     * Возвращает различные коды в
     * зависимости от успешности операции
     * 404 - пользователь не найден
     * 200 и данные - пользователь найден
     * @param login
     * @param password
     * @return
     */
    @GetMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<LoginUserCredentials> login(
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password
    ){
        User user = userRepository.findByLoginAndPassword(login, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            LoginUserCredentials userCredentials = new LoginUserCredentials();
            userCredentials.setLogin(user.getLogin());
            userCredentials.setName(user.getUserName());
            return ResponseEntity.ok(userCredentials);
        }
    }

}
