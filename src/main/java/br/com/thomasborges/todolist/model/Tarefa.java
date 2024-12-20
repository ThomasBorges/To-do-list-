package br.com.thomasborges.todolist.model;

import java.time.LocalDateTime;

public class Tarefa {

    private String nome;
    private String descricao;
    private Lista lista;
    private Status status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public enum Status { TODO, DONE }

    public Tarefa(String nome) {
        this.nome = nome;
        this.status = Status.TODO;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Tarefa(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.status = Status.TODO;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    private void atualizaData() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        atualizaData();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
        atualizaData();
    }

    public Lista getLista() {
        return lista;
    }

    void setLista(Lista lista) {
        this.lista = lista;
        atualizaData();
    }

    public Status getStatus() {
        return status;
    }

    public void concluir() {
        this.status = Status.DONE;
        atualizaData();
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

}
