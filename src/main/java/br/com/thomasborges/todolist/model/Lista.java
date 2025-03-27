package br.com.thomasborges.todolist.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id",insertable = false, updatable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "lista")
    private List<Tarefa> tarefas;

    @CreatedDate
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    public Lista(){}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
