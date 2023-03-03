package com.example.chat.repository;

import com.example.chat.db_model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс, необходимый
 * для взаимодействия с сущностью
 * пользователя в БД
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Метод для поиска
     * пользователя по
     * его логину. Нужен для
     * поиска совпадения логина
     * при регистрации
     * @param login
     * @return
     */
    User findByLogin(String login);

    /**
     * Метод для поиска пользователя
     * по логину и паролю. Необходим
     * для авторизации
     * @param login
     * @param password
     * @return
     */
    User findByLoginAndPassword(String login, String password);

}
