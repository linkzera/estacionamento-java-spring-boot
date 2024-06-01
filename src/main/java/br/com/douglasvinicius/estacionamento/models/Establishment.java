package br.com.douglasvinicius.estacionamento.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "establishments")
public class Establishment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O campo de nome não pode ser vazio")
  private String name;

  @NotBlank(message = "O campo de CNPJ não pode ser vazio")
  private String ein;

  @NotBlank(message = "O campo de endereço não pode ser vazio")
  private String address;

  @NotBlank(message = "O campo de telefone não pode ser vazio")
  private String phone;

  @NotNull(message = "O campo de vagas de carro não pode ser vazio")
  private Integer parkingSpacesCar;

  @NotNull(message = "O campo de vagas de moto não pode ser vazio")
  private Integer parkingSpacesMotorcycle;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "establishment")
  private List<Vehicle> vehicles;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "establishment")
  private List<User> employees;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "establishment")
  private List<ParkingRecord> parkingRecords;

  public Establishment() {
  }

  public Establishment(@NotBlank(message = "O campo de nome não pode ser vazio") String name,
      @NotBlank(message = "O campo de CNPJ não pode ser vazio") String ein,
      @NotBlank(message = "O campo de endereço não pode ser vazio") String address,
      @NotBlank(message = "O campo de telefone não pode ser vazio") String phone,
      @NotNull(message = "O campo de vagas de carro não pode ser vazio") Integer parkingSpacesCar,
      @NotNull(message = "O campo de vagas de moto não pode ser vazio") Integer parkingSpacesMotorcycle,
      List<Vehicle> vehicles) {
    this.name = name;
    this.ein = ein;
    this.address = address;
    this.phone = phone;
    this.parkingSpacesCar = parkingSpacesCar;
    this.parkingSpacesMotorcycle = parkingSpacesMotorcycle;
    this.vehicles = vehicles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEin() {
    return ein;
  }

  public void setEin(String ein) {
    this.ein = ein;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getParkingSpacesCar() {
    return parkingSpacesCar;
  }

  public void setParkingSpacesCar(Integer parkingSpacesCar) {
    this.parkingSpacesCar = parkingSpacesCar;
  }

  public Integer getParkingSpacesMotorcycle() {
    return parkingSpacesMotorcycle;
  }

  public void setParkingSpacesMotorcycle(Integer parkingSpacesMotorcycle) {
    this.parkingSpacesMotorcycle = parkingSpacesMotorcycle;
  }

  public List<Vehicle> getVehicles() {
    return vehicles;
  }

  public void setVehicles(List<Vehicle> vehicles) {
    this.vehicles = vehicles;
  }

  public void setVehicle(Vehicle vehicle) {
    vehicles.add(vehicle);
  }

  public List<User> getEmployees() {
    return employees;
  }

  public void setEmployees(List<User> employees) {
    this.employees = employees;
  }
}
