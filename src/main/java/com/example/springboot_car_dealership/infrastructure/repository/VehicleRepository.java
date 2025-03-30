package com.example.springboot_car_dealership.infrastructure.repository;

import com.example.springboot_car_dealership.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Indica que es un repositorio JPA y que accede a la base de datos
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {  //JpaRepository --> Permite realizar operaciones CRUD

    List<Vehicle> findByIsNew (boolean isNew);

    List<Vehicle> findByBrandIgnoreCaseAndIsNew(String brand, boolean isNew);

    List<Vehicle> findByBrandIgnoreCase(String brand);

    Optional<Vehicle> findByLicensePlate(String licensePlate);
}
