package com.example.springboot_car_dealership.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, equals, hashCode y toString automáticamente.
@NoArgsConstructor // Constructor vacío.
@AllArgsConstructor // Constructor con todos los argumentos.
public class VehicleDTO {
    private Long id;

    @JsonProperty("marca") // En JSON se verá como "marca" en vez de "brand"
    private String brand;

    @JsonProperty("modelo")
    private String model;

    @JsonProperty("patente")
    private String licensePlate;

    @JsonProperty("kilometraje")
    private int kilometers;

    @JsonProperty("es_nuevo")
    private boolean isNew;
}
