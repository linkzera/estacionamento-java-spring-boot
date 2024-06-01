package br.com.douglasvinicius.estacionamento.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import br.com.douglasvinicius.estacionamento.models.ParkingRecord;
import br.com.douglasvinicius.estacionamento.models.Enum.VehicleType;
import br.com.douglasvinicius.estacionamento.repository.EstablishmentRepository;
import br.com.douglasvinicius.estacionamento.repository.ParkingRecordRepository;
import br.com.douglasvinicius.estacionamento.repository.VehicleRepository;
import jakarta.transaction.Transactional;

@Service
public class ParkingService {
  private final VehicleRepository vehicleRepository;
  private final EstablishmentRepository establishmentRepository;
  private final ParkingRecordRepository parkingRecordRepository;

  public ParkingService(VehicleRepository vehicleRepository, EstablishmentRepository establishmentRepository,
      ParkingRecordRepository parkingRecordRepository) {
    this.vehicleRepository = vehicleRepository;
    this.establishmentRepository = establishmentRepository;
    this.parkingRecordRepository = parkingRecordRepository;
  }

  @Transactional
  public Object parkVehicle(Long vehicleId, Long establishmentId) {
    var vehicle = vehicleRepository.findById(vehicleId).orElse(null);
    var establishment = establishmentRepository.findById(establishmentId).orElse(null);
    if (vehicle == null || establishment == null) {
      throw new RuntimeException("Veículo ou estabelecimento não encontrado");
    }

    var existingParkingRecord = parkingRecordRepository.findByVehicleIdAndExitTimeIsNull(vehicleId);
    if (existingParkingRecord != null) {
      throw new RuntimeException("Veículo já estacionado");
    }

    if (vehicle.getType().equals(VehicleType.CAR)) {
      if (establishment.getParkingSpacesCar() > 0) {
        establishment.setParkingSpacesCar(establishment.getParkingSpacesCar() - 1);
        var parkingRecord = new ParkingRecord(vehicle, establishment, LocalDateTime.now(), null, 10.00);
        parkingRecordRepository.save(parkingRecord);
        vehicle.setEstablishment(establishment);
        vehicleRepository.save(vehicle);
        return Map.of("message", "Veículo estacionado com sucesso");
      } else {
        throw new RuntimeException("Estacionamento lotado");
      }
    } else if (vehicle.getType().equals(VehicleType.MOTORCYCLE)) {
      if (establishment.getParkingSpacesMotorcycle() > 0) {
        establishment.setParkingSpacesMotorcycle(establishment.getParkingSpacesMotorcycle() - 1);
        vehicle.setEstablishment(establishment);
        vehicleRepository.save(vehicle);
        var parkingRecord = new ParkingRecord(vehicle, establishment, LocalDateTime.now(), null, 10.00);
        parkingRecordRepository.save(parkingRecord);
        return Map.of("message", "Veículo estacionado com sucesso");
      } else {
        throw new RuntimeException("Estacionamento lotado");
      }
    } else {
      throw new RuntimeException("Tipo de veículo inválido");
    }
  }

  @Transactional
  public String unparkVehicle(Long vehicleId) {
    var vehicle = vehicleRepository.findById(vehicleId).orElse(null);
    if (vehicle == null) {
      throw new RuntimeException("Veículo não encontrado");
    }

    System.out.println(vehicle.getParkingRecord().getLast().getId());
    var record = parkingRecordRepository.findById(vehicle.getParkingRecord().getLast().getId()).orElse(null);
    if (record == null) {
      throw new RuntimeException("Registro de estacionamento não encontrado");
    }

    if (record.getExitTime() != null) {
      throw new RuntimeException("Veículo já retirado");
    }

    var establishment = establishmentRepository.findById(record.getEstablishment().getId()).orElse(null);
    if (establishment == null) {
      throw new RuntimeException("Estabelecimento não encontrado");
    }

    if (vehicle.getType().equals(VehicleType.CAR)) {
      establishment.setParkingSpacesCar(establishment.getParkingSpacesCar() + 1);
    } else if (vehicle.getType().equals(VehicleType.MOTORCYCLE)) {
      establishment.setParkingSpacesMotorcycle(establishment.getParkingSpacesMotorcycle() + 1);
    }
    establishmentRepository.save(establishment);

    vehicle.setEstablishment(null);
    vehicleRepository.save(vehicle);

    record.setExitTime(LocalDateTime.now());
    parkingRecordRepository.save(record);

    return "Veículo retirado com sucesso";
  }

}
