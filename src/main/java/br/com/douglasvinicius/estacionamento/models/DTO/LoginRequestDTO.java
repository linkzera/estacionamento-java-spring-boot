package br.com.douglasvinicius.estacionamento.models.DTO;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
    @NotNull String email,
    @NotNull String password) {

}
