package br.com.thomasborges.todolist.dto;

import jakarta.validation.constraints.NotBlank;

public record ListaRequest(@NotBlank String nome, String descricao) {
}
