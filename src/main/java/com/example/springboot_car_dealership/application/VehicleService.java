package com.example.springboot_car_dealership.application;

import com.example.springboot_car_dealership.domain.Vehicle;
import com.example.springboot_car_dealership.infrastructure.dto.VehicleDTO;
import com.example.springboot_car_dealership.infrastructure.exception.DuplicateResourceException;
import com.example.springboot_car_dealership.infrastructure.exception.EmptyBrandException;
import com.example.springboot_car_dealership.infrastructure.exception.InvalidVehicleStateException;
import com.example.springboot_car_dealership.infrastructure.exception.ResourceNotFoundException;
import com.example.springboot_car_dealership.infrastructure.mapper.VehicleMapper;
import com.example.springboot_car_dealership.infrastructure.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public List<VehicleDTO> getAllVehicles() {

        List<Vehicle> vehicles = vehicleRepository.findAll();
        vehiclesListIsEmpty(vehicles);
        return vehicles.stream().map(vehicleMapper::toDTO).collect(Collectors.toList());

    }

    public Optional<VehicleDTO> getVehicleById(Long id) {
        return vehicleRepository.findById(id).map(vehicleMapper::toDTO);
    }

    public List<VehicleDTO> getVehicleByBrandAndState(String brand, Boolean isNew){

        validateBrandAndState(brand, isNew);
        List<Vehicle> vehicles = vehicleRepository.findByBrandIgnoreCaseAndIsNew(brand, isNew);
        validateExistBrandAndState(vehicles);
        return vehicleMapper.toDTOList(vehicles);

    }

    public List<VehicleDTO> getVehiclesByBrand(String brand) {

        List<Vehicle> vehicles = vehicleRepository.findByBrandIgnoreCase(brand);
        EmptyVehiclesByBrand(vehicles, brand);
        return vehicles.stream()
                .map(vehicleMapper::toDTO)
                .collect(Collectors.toList());

    }

    public VehicleDTO saveVehicle(VehicleDTO vehicleDTO) {

        validateDuplicateLicensePlate(vehicleDTO.getLicensePlate()); //Valida patentes duplicadas
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO); //Transforma de Vehiculo DTO a Vehiculo Entity JPA
        validateKilometers(vehicle); //Valida los kilometros
        validateVehicleState(vehicle); //Valida el estado del auto dependiendo de los kilometros
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDTO(savedVehicle);

    }

    public void deleteVehicle(Long id) {

        validateExistId(id);
        vehicleRepository.deleteById(id);
    }

    //Funciones SRP

    public void validateDuplicateLicensePlate(String licensePlate){
        if (vehicleRepository.findByLicensePlate(licensePlate).isPresent()){
            throw new DuplicateResourceException("Ya existe un vehículo con la patente: " + licensePlate);
        }
    }

    public void validateKilometers(Vehicle vehicle){
        if (vehicle.getKilometers() < 0) {
            throw new InvalidVehicleStateException("Ingrese un kilometraje válido.");
        }
    }

    private void validateVehicleState(Vehicle vehicle) {
        if (vehicle.getKilometers() > 0 && vehicle.isNew()) {
            throw new InvalidVehicleStateException("Estado del vehículo no es coherente con el kilometraje.");
        }

        if (vehicle.getKilometers() == 0 && !vehicle.isNew()) {
            throw new InvalidVehicleStateException("Estado del vehículo no es coherente con el kilometraje.");
        }
    }

    private void validateBrandAndState(String brand, Boolean isNew){
        if (brand == null || brand.isEmpty()) {
            throw new EmptyBrandException("Debe ingresar una marca de vehiculo");
        }
        if (isNew == null) {
            throw new InvalidVehicleStateException("Debe indicar si el vehículo es nuevo o usado.");
        }
    }

    public void vehiclesListIsEmpty(List<Vehicle> vehicles){
        if (vehicles.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron vehículos.");
        }
    }

    private void validateExistBrandAndState(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron vehiculos con los filtros proporcionados.");
        }
    }

    private void EmptyVehiclesByBrand(List<Vehicle> vehicles, String brand) {
        if (vehicles.isEmpty()){
            throw new ResourceNotFoundException("No se encontraron vehiculos de la marca " + brand);
        }
    }

    private void validateExistId(Long id) {
        if (vehicleRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("No se pudo borrar. vehiculo con id " + id + " no existe");
        }
    }

}
