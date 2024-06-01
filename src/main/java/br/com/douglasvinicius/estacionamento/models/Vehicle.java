package br.com.douglasvinicius.estacionamento.models;

import java.util.List;

import br.com.douglasvinicius.estacionamento.models.Enum.VehicleType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A marca do veículo é obrigatória")
    private String brand;

    @NotBlank(message = "O modelo do veículo é obrigatório")
    private String model;

    @NotBlank(message = "A cor do veículo é obrigatória")
    private String color;

    @NotBlank(message = "A placa do veículo é obrigatória")
    @Column(unique = true)
    private String plate;

    @NotNull(message = "O tipo do veículo é obrigatório")
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "establishment_id")
    private Establishment establishment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicle")
    private List<ParkingRecord> parkingRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public List<ParkingRecord> getParkingRecord() {
        return parkingRecord;
    }

    public void setParkingRecord(List<ParkingRecord> parkingRecord) {
        this.parkingRecord = parkingRecord;
    }

    public Vehicle(@NotBlank(message = "A marca do veículo é obrigatória") String brand,
            @NotBlank(message = "O modelo do veículo é obrigatório") String model,
            @NotBlank(message = "A cor do veículo é obrigatória") String color,
            @NotBlank(message = "A placa do veículo é obrigatória") String plate,
            @NotNull(message = "O tipo do veículo é obrigatório") VehicleType type, Establishment establishment,
            List<ParkingRecord> parkingRecord) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.plate = plate;
        this.type = type;
        this.establishment = establishment;
        this.parkingRecord = parkingRecord;
    }

    public Vehicle() {
    }

}
