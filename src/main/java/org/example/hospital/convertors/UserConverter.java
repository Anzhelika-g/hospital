package org.example.hospital.convertors;

import org.example.hospital.dto.UserDTO;
import org.example.hospital.entity.User;
import org.example.hospital.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, UserDTO>{
    @Override
    public UserDTO convertToDTO(User entity, UserDTO dto) {
        dto.setUserId(entity.getUserId());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole().name());
        return dto;
    }

    @Override
    public User convertToEntity(UserDTO dto, User entity) {
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(Role.valueOf(dto.getRole()));
        return entity;
    }
}
