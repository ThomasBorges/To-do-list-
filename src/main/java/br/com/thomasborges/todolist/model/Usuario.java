package br.com.thomasborges.todolist.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;

    @Embedded
    private Senha senha;

    @OneToMany(mappedBy = "usuario")
    private List<Lista> listas;

    public Usuario(){}

    public Usuario(String nome, String email, Senha senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void adicionarLista(Lista lista) {
        this.listas.add(lista);
        lista.setUsuario(this);
    }

    public void removerLista(Lista lista) {
        this.listas.remove(lista);
        lista.setUsuario(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Lista> getListas() {
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
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
