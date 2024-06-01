package br.com.douglasvinicius.estacionamento.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglasvinicius.estacionamento.models.Vehicle;
import br.com.douglasvinicius.estacionamento.services.VehicleService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
  @Autowired
  VehicleService vehicleService;

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody @Valid Vehicle vehicle) {
    var created = vehicleService.create(vehicle);
    
    if (created == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Veículo já cadastrado"));
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @GetMapping
  public ResponseEntity<List<Vehicle>> findAll() {
    return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> findById(@PathVariable Long id) {
    var vehicle = vehicleService.findById(id);
    
    if (vehicle == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Veículo com id " + id + " não encontrado"));
    }

    return ResponseEntity.status(HttpStatus.OK).body(vehicle);
  }

  @PatchMapping("{id}")
  public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Vehicle vehicle) {
    var updated = vehicleService.update(id, vehicle);
    
    if (updated == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Veículo com id " + id + " não encontrado"));
    }
    
    return ResponseEntity.status(HttpStatus.OK).body(updated);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    var deleted = vehicleService.delete(id);
    
    if (deleted == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Veículo com id " + id + " não encontrado"));
    }
    return ResponseEntity.status(HttpStatus.OK).body(deleted);
  }

}
