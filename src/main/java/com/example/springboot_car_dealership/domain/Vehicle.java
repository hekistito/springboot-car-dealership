package com.example.springboot_car_dealership.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok genera automaticamente Getters, Setters, Equals y HashCode
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
@Entity // Marca la clase como una entidad JPA
@Table(name = "vehicles") // Define la tabla en la base de datos
public class Vehicle {

    @Id // Define el campo como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Genera IDs automaticamente
    private Long id;
    @Column(nullable = false) // Hace que el campo no pueda ser nulo
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column(unique = true, nullable = false) // Hace que licensePlate sea única en la BD.
    private String licensePlate;
    @Column(nullable = false)
    private int kilometers;
    @Column(nullable = false)
    private boolean isNew;

}
