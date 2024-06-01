package br.com.douglasvinicius.estacionamento.models.DTO;

public record LoginResponseDTO(
    String accessToken,
    Long expiresIn

) {

}
