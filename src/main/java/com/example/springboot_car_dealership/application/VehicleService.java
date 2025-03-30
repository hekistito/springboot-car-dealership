package com.example.springboot_car_dealership.application;

import com.example.springboot_car_dealership.domain.Vehicle;
import com.example.springboot_car_dealership.infrastructure.dto.VehicleDTO;
import com.example.springboot_car_dealership.infrastructure.exception.ResourceNotFoundException;
import com.example.springboot_car_dealership.infrastructure.mapper.VehicleMapper;
import com.example.springboot_car_dealership.infrastructure.repository.VehicleRepository;
import org.springframework.stereotype.Service;

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
        return vehicles.stream().map(vehicleMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<VehicleDTO> getVehicleById(Long id) {
        return vehicleRepository.findById(id).map(vehicleMapper::toDTO);
    }

    public List<VehicleDTO> searchVehicle(String brand, Boolean isNew){
        List<Vehicle> results;

        if (brand != null && isNew != null) {
            results = vehicleRepository.findByBrandIgnoreCaseAndIsNew(brand, isNew);
        } else if (brand != null) {
            results = vehicleRepository.findByBrandIgnoreCase(brand);
        } else if (isNew != null) {
            results = vehicleRepository.findByIsNew(isNew);
        } else {
            results = vehicleRepository.findAll();
        }

        return vehicleMapper.toDTOList(results);
    }

    public VehicleDTO saveVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDTO(savedVehicle);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public List<VehicleDTO> getVehiclesByBrand(String brand) {
        List<Vehicle> vehicles = vehicleRepository.findByBrandIgnoreCase(brand);

        return vehicles.stream()
                .map(vehicleMapper::toDTO)
                .collect(Collectors.toList());
    }
}
