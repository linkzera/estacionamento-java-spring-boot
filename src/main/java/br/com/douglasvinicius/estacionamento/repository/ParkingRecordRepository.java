package br.com.douglasvinicius.estacionamento.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.douglasvinicius.estacionamento.models.ParkingRecord;

public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, UUID> {
  ParkingRecord findByVehicleIdAndExitTimeIsNull(Long vehicleId);
}