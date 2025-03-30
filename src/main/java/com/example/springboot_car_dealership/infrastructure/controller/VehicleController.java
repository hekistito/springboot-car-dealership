package com.example.springboot_car_dealership.infrastructure.controller;


import com.example.springboot_car_dealership.application.VehicleService;
import com.example.springboot_car_dealership.infrastructure.dto.VehicleDTO;
import com.example.springboot_car_dealership.infrastructure.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/getVehicles")
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles();
        if (vehicles.isEmpty()){
            throw new ResourceNotFoundException("No se encontraron vehiculos.");
        }

        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/getVehicle/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        Optional<VehicleDTO> vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(
                vehicle.orElseThrow(() -> new ResourceNotFoundException("Vehiculo con id " + id + " no existe."))
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<VehicleDTO>> getVehiclesByBrandAndIsNew(
            @RequestParam(required = true) String brand,
            @RequestParam(required = true) Boolean isNew){


        List<VehicleDTO> vehicles = vehicleService.searchVehicle(brand, isNew);

        if (vehicles.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron vehiculos con los filtros proporcionados.");
        }

        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/getVehicleByBrand/{brand}")
    public ResponseEntity<List<VehicleDTO>> getVehiclesByBrand(@PathVariable String brand){
        List<VehicleDTO> vehicles = vehicleService.getVehiclesByBrand(brand);

        if (vehicles.isEmpty()){
            throw new ResourceNotFoundException("No se encontraron vehiculos de la marca " + brand);
        }

        return ResponseEntity.ok(vehicles);
    }

    @PostMapping("/create")
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        VehicleDTO savedVehicle = vehicleService.saveVehicle(vehicleDTO);
        return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        if (vehicleService.getVehicleById(id).isEmpty()) {
            throw new ResourceNotFoundException("No se pudo borrar. vehiculo con id " + id + " no existe");
        }
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
