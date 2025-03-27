package br.com.thomasborges.todolist.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Senha {  //verificar onde inserir Senha

    private String salt;
    private String hashPassword;

    public Senha(){}

    public Senha( String salt, String hashPassword) {
        this.salt = salt;
        this.hashPassword = hashPassword;
    }

    public String getSalt() { return salt; }

    public void setSalt(String salt) { this.salt = salt; }

    public String getHashPassword() { return hashPassword; }

    public void setHashPassword(String hashPassword) { this.hashPassword = hashPassword; }
}
