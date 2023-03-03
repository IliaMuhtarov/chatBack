package com.example.chat.db_model;

import jakarta.persistence.*;

import java.util.List;

/**
 * Класс, который отражает
 * сущность БД, необходимую
 * для хранения данных о
 * переписке двух пользователей
 */
@Entity
@Table(name = "dialogue")
public class Dialogue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long dialogueId;

    @Column(name = "first_member_login")
    private String firstMemberLogin;

    @Column(name = "second_member_login")
    private String secondMemberLogin;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dialogue_id", referencedColumnName = "dialogueId")
    private List<PrivateMessage> messages;

    public Dialogue(){
        super();
    }

    public Dialogue(String firstMemberLogin, String secondMemberLogin) {
        this.firstMemberLogin = firstMemberLogin;
        this.secondMemberLogin = secondMemberLogin;
    }

    public long getDialogueId() {
        return dialogueId;
    }

    public void setDialogueId(long dialogueId) {
        this.dialogueId = dialogueId;
    }

    public String getFirstMemberLogin() {
        return firstMemberLogin;
    }

    public void setFirstMemberLogin(String firstMemberLogin) {
        this.firstMemberLogin = firstMemberLogin;
    }

    public String getSecondMemberLogin() {
        return secondMemberLogin;
    }

    public void setSecondMemberLogin(String secondMemberLogin) {
        this.secondMemberLogin = secondMemberLogin;
    }

    public List<PrivateMessage> getMessages(){return messages;}
}
