package com.example.chat.controller;

import com.example.chat.db_model.User;
import com.example.chat.model.LoginUserCredentials;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс контроллер,
 * необходимый для получения
 * данных о пользователях
 * для дальнейшего
 * взимодействия с ними
 */
@RestController
public class UsersController {

    @Autowired
    UserRepository userRepository;


    /**
     * Метод необходимый
     * для получения
     * списка пользователей
     * для личной переписки
     */
    @GetMapping("/get-users")
    @CrossOrigin(origins = "*")
    public List<LoginUserCredentials> getAllUsers(
            @RequestParam(name = "userLogin") String userLogin
    ){
        List<LoginUserCredentials> usersInfo = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for(User user : users){
            if (!Objects.equals(user.getLogin(), userLogin)) {
                LoginUserCredentials userInfo = new LoginUserCredentials();
                userInfo.setName(user.getUserName());
                userInfo.setLogin(user.getLogin());
                usersInfo.add(userInfo);
            }
        }
        usersInfo = usersInfo.stream().sorted((Comparator.comparing(LoginUserCredentials::getName))).collect(Collectors.toList());
        return usersInfo;
    }
}
