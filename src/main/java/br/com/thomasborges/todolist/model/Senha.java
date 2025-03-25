package br.com.thomasborges.todolist.model;

public class Senha {  //verificar onde inserir Senha

    private Usuario usuario;
    private String salt;
    private String hashPassword;

    public Senha(Usuario usuario, String salt, String hashPassword) {
        this.usuario = usuario;
        this.salt = salt;
        this.hashPassword = hashPassword;
    }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getSalt() { return salt; }

    public void setSalt(String salt) { this.salt = salt; }

    public String getHashPassword() { return hashPassword; }

    public void setHashPassword(String hashPassword) { this.hashPassword = hashPassword; }
}
