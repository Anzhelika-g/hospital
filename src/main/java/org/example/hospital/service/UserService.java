package org.example.hospital.service;

import org.example.hospital.dto.UserDTO;
import org.example.hospital.entity.User;
import org.example.hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addUser(User user){
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
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setRole(user.getRole());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser (Long id){
        if (userRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
