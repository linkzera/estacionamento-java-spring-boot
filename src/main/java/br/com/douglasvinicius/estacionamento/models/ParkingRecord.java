package br.com.douglasvinicius.estacionamento.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "parking_records")
public class ParkingRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull(message = "O campo de id do veículo não pode ser vazio")
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "vehicle_id")
  private Vehicle vehicle;

  @NotNull(message = "O campo de id do estabelecimento não pode ser vazio")
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "establishment_id")
  private Establishment establishment;

  @DateTimeFormat
  private LocalDateTime entryTime;

  @DateTimeFormat
  private LocalDateTime exitTime;

  @NumberFormat
  private Double value;

  public ParkingRecord() {
  }

  public ParkingRecord(@NotNull(message = "O campo de id do veículo não pode ser vazio") Vehicle vehicle,
      @NotNull(message = "O campo de id do estabelecimento não pode ser vazio") Establishment establishment,
      LocalDateTime entryTime, LocalDateTime exitTime, Double value) {
    this.vehicle = vehicle;
    this.establishment = establishment;
    this.entryTime = entryTime;
    this.exitTime = exitTime;
    this.value = value;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  public Establishment getEstablishment() {
    return establishment;
  }

  public void setEstablishment(Establishment establishment) {
    this.establishment = establishment;
  }

  public LocalDateTime getEntryTime() {
    return entryTime;
  }

  public void setEntryTime(LocalDateTime entryTime) {
    this.entryTime = entryTime;
  }

  public LocalDateTime getExitTime() {
    return exitTime;
  }

  public void setExitTime(LocalDateTime exitTime) {
    this.exitTime = exitTime;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

}
