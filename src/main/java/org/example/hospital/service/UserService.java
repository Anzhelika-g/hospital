package org.example.hospital.service;

import org.example.hospital.converter.UserConverter;
import org.example.hospital.dto.UserDTO;
import org.example.hospital.entity.User;
import org.example.hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Transactional
    public void addUser(UserDTO userDTO){
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }
        if (userDTO.getUsername() == null) {
            throw new RuntimeException("Username must be specified");
        }
        User user = userConverter.convertToEntity(userDTO, new User());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);
    }

    public User getUserById(Long id){
        if (userRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("User not found with id: " + id);
        }
        return userRepository.findById(id).get();
    }

    @Transactional
    public void updateUser(Long id, UserDTO userDTO){
        if (userRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("User not found with id: " + id);
        }
        User user = userRepository.findById(id).get();
        user = userConverter.convertToEntity(userDTO, user);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser (Long id){
        if (userRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).get();
    }

    public User getUserReferenceById(Long id) {
        return userRepository.getReferenceById(id);
    }
}