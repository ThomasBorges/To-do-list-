package br.com.thomasborges.todolist.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private Senha senha;

    private List<Lista> listas;

    public Usuario(String nome, String email, Senha senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.listas = new ArrayList<>();
    }

    public void adicionarLista(Lista lista) {
        this.listas.add(lista);
        lista.setUsuario(this);
    }

    public void removerLista(Lista lista) {
        this.listas.remove(lista);
        lista.setUsuario(null);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Senha getSenha() {
        return senha;
    }

    public void setSenha(Senha senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
