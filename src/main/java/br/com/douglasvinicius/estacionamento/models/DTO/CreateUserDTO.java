package br.com.douglasvinicius.estacionamento.models.DTO;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
    @NotBlank String name,
    @NotBlank String email,
    @NotBlank String password) {

}
