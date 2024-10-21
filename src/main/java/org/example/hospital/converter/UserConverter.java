package org.example.hospital.converter;

import org.example.hospital.dto.UserDTO;
import org.example.hospital.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, UserDTO>{
    @Override
    public UserDTO convertToDTO(User entity, UserDTO dto) {
        dto.setUserId(entity.getUserId());
        dto.setEmail(entity.getEmail());
        dto.setEmail(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        return dto;
    }

    @Override
    public User convertToEntity(UserDTO dto, User entity) {
        entity.setUserId(dto.getUserId());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        return entity;
    }
}
