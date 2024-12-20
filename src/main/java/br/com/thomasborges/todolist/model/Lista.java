package br.com.thomasborges.todolist.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Lista {

    private String nome;
    private String descricao;
    private Usuario usuario;
    private List<Tarefa> tarefas;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Lista(String nome) {
        this.nome = nome;
        this.tarefas = new ArrayList<>();
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Lista(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.tarefas = new ArrayList<>();
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void adicionarTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
        tarefa.setLista(this);
        atualizaData();
    }

    public void removerTarefa(Tarefa tarefa) {
        this.tarefas.remove(tarefa);
        tarefa.setLista(null);
        atualizaData();
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

    private void atualizaData() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        atualizaData();
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

}
