package br.com.douglasvinicius.estacionamento.models.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateEstablishmentDTO(
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters") 
    String name,

    // @CNPJ(message = "The provided EIN is invalid")
    String ein,

    @Size(min = 5, max = 100, message = "Address should be between 5 and 100 characters") 
    String address,
    
    @Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message = "Invalid phone number") 
    String phone,
   
    @Min(value = 1, message = "Must be greater than or equal to 1")
    Integer parkingSpacesCar,

    @Min(value = 1, message = "Must be greater than or equal to 1")
    Integer parkingSpacesMotorcycle) {}