package br.com.douglasvinicius.estacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.douglasvinicius.estacionamento.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
  Vehicle findByPlate(String plate);

}
