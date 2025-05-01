package br.com.thomasborges.todolist.dto;

import jakarta.validation.constraints.NotBlank;

public record TarefaRequest(@NotBlank String nome, String descricao) {

}
