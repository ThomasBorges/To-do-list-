package br.com.thomasborges.todolist.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ListaResponse(Long id, String nome, String descricao) {
}
