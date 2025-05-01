package br.com.thomasborges.todolist.dto;

import br.com.thomasborges.todolist.model.Tarefa;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record TarefaResponse(Long id, String nome, String descricao, Tarefa.Status status) {
}
