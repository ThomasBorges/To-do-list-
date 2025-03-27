package br.com.thomasborges.todolist.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;

    @Column(name = "lista_id")
    private Long listaId;

    @ManyToOne
    @JoinColumn(name = "lista_Id", referencedColumnName = "id", insertable = false, updatable = false)
    private Lista lista;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreatedDate
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    public enum Status { TODO, DONE }

    public Tarefa(){}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getListaId() {
        return listaId;
    }

    public void setListaId(Long listaId) {
        this.listaId = listaId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
