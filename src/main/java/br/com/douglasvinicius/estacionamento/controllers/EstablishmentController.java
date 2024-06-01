package br.com.douglasvinicius.estacionamento.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglasvinicius.estacionamento.models.Establishment;
import br.com.douglasvinicius.estacionamento.models.DTO.RegisterVehicleInEstablishmentDTO;
import br.com.douglasvinicius.estacionamento.models.DTO.UpdateEstablishmentDTO;
import br.com.douglasvinicius.estacionamento.services.EstablishmentService;
import br.com.douglasvinicius.estacionamento.services.ParkingService;
import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("establishment")
public class EstablishmentController {
  @Autowired
  EstablishmentService establishmentService;
  @Autowired
  ParkingService parkingService;

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody @Valid Establishment establishment) {
    return ResponseEntity.status(HttpStatus.CREATED).body(establishmentService.create(establishment));
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> findById(@PathVariable Long id) {
    var establishment = establishmentService.findById(id);
    if (establishment == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Map.of("error", "Estabelecimento com id " + id + " não encontrado"));
    }
    return ResponseEntity.status(HttpStatus.OK).body(establishment);
  }

  @GetMapping
  public ResponseEntity<Object> findAll() {
    return ResponseEntity.status(HttpStatus.OK).body(establishmentService.findAll());
  }

  @PatchMapping("{id}")
  public ResponseEntity<Object> update(
      @PathVariable Long id,
      @RequestBody @Valid UpdateEstablishmentDTO establishment) {
    var updatedEstablishment = establishmentService.update(id, establishment);

    if (updatedEstablishment == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Map.of("error", "Estabelecimento com id " + id + " não encontrado"));
    }

    return ResponseEntity.status(HttpStatus.OK).body(updatedEstablishment);
  }

  @PutMapping("{id}/vehicle")
  public ResponseEntity<Object> parkVehicle(@PathVariable Long id,
      @RequestBody @Valid RegisterVehicleInEstablishmentDTO body) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(parkingService.parkVehicle(body.vehicleId(), id));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }

  }

  @DeleteMapping("vehicle/{id}")
  public ResponseEntity<Object> unparkVehicle(@PathVariable @Valid Long id) {
    try {
      var response = parkingService.unparkVehicle(id);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    var establishments = establishmentService.delete(id);
    if (establishments == null) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Map.of("error", "Estabelecimento com id " + id + " não encontrado"));
    }

    return ResponseEntity.status(HttpStatus.OK).body(establishments);
  }

}
