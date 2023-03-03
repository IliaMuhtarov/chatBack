package com.example.chat.repository;

import com.example.chat.db_model.Dialogue;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс, необходимый
 * для взаимодействия с
 * сущностью Диалога
 * в БД
 */
public interface DialogueRepository extends JpaRepository<Dialogue, Long> {

    /**
     * Метод для
     * поиска диалога по
     * логинам первого
     * и второго пользователя
     * @param firstMemberLogin
     * @param secondMemberLogin
     * @return
     */
    Dialogue findByFirstMemberLoginAndSecondMemberLogin (String firstMemberLogin, String secondMemberLogin);
}
