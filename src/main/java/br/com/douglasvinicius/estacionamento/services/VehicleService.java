package br.com.douglasvinicius.estacionamento.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.douglasvinicius.estacionamento.models.Vehicle;
import br.com.douglasvinicius.estacionamento.repository.VehicleRepository;

@Service
public class VehicleService {
  private VehicleRepository vehicleRepository;

  public VehicleService(VehicleRepository vehicleRepository) {
    this.vehicleRepository = vehicleRepository;
  }

  public List<Vehicle> create(Vehicle vehicle) {
    var vehicleExists = vehicleRepository.findByPlate(vehicle.getPlate());
    if (vehicleExists != null) {
      return null;
    }

    vehicleRepository.save(vehicle);
    return findAll();
  }

  public Vehicle findById(Long id) {
    return vehicleRepository.findById(id).orElse(null);
  }

  public List<Vehicle> findAll() {
    return vehicleRepository.findAll();
  }

  public List<Vehicle> update(Long id, Vehicle vehicle) {
    var vehicleToUpdate = vehicleRepository.findById(id).orElse(null);

    if (vehicleToUpdate == null) {
      return null;
    }

    BeanUtils.copyProperties(vehicle, vehicleToUpdate);

    vehicleRepository.save(vehicleToUpdate);

    return findAll();
  }

  public List<Vehicle> delete(Long id) {
    var vehicle = vehicleRepository.findById(id).orElse(null);
    if (vehicle == null) {
      return null;
    }
    vehicleRepository.deleteById(id);
    return findAll();
  }
}
