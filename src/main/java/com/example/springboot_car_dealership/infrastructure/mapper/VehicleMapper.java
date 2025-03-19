package com.example.springboot_car_dealership.infrastructure.mapper;

import com.example.springboot_car_dealership.domain.Vehicle;
import com.example.springboot_car_dealership.infrastructure.dto.VehicleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    // Convierte una entidad Vehicle en un DTO
    VehicleDTO toDTO(Vehicle vehicle);

    // Convierte un DTO en una entidad Vehicle
    Vehicle toEntity(VehicleDTO vehicleDTO);

    // Convierte una lista de entidades en una lista de DTOs
    List<VehicleDTO> toDTOList(List<Vehicle> vehicles);

    List<Vehicle> toEntitiesList(List<VehicleDTO> vehicleDTOSList);
}
