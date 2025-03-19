package com.example.springboot_car_dealership.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, equals, hashCode y toString automáticamente.
@NoArgsConstructor // Constructor vacío.
@AllArgsConstructor // Constructor con todos los argumentos.
public class VehicleDTO {
    private Long id;
    private String brand;
    private String model;
    private String licensePlate;
    private int mileage;
    private boolean isNew;
}
