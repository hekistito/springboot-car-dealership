package com.example.springboot_car_dealership.infrastructure.repository;

import com.example.springboot_car_dealership.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Indica que es un repositorio JPA y que accede a la base de datos
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {  //JpaRepository --> Permite realizar operaciones CRUD

    // Buscar vehiculo por marca
    List<Vehicle> findByBrand(String brand);
    // Buscar vehiculo si es nuevo o no
    List<Vehicle> findByIsNew (boolean isNew);

}
