package com.example.chat.repository;


import com.example.chat.db_model.PublicMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс, необходимый
 * для взаимодействия с
 * сущностью публичного
 * сообщения в БД
 */
public interface PublicMessageRepository extends JpaRepository<PublicMessage, Long> {

}
