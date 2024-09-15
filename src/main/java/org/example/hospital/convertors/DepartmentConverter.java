package org.example.hospital.convertors;

import org.example.hospital.dto.DepartmentDTO;
import org.example.hospital.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverter implements Converter<Department, DepartmentDTO> {
    @Override
    public DepartmentDTO convertToDTO(Department entity, DepartmentDTO dto) {
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    @Override
    public Department convertToEntity(DepartmentDTO dto, Department entity) {
        if (entity.getDepartmentId() == null) {
            entity.setDepartmentId(dto.getDepartmentId());
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
