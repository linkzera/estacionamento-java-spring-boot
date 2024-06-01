package br.com.douglasvinicius.estacionamento.models.DTO;

import jakarta.validation.constraints.NotNull;

public record RegisterVehicleInEstablishmentDTO(
    @NotNull(message = "O campo de id do veículo não pode ser vazio") Long vehicleId
  ) {
}
