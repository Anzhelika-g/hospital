package org.example.hospital.service;


import org.example.hospital.converter.LabAssistantConverter;
import org.example.hospital.dto.LabAssistantDTO;
import org.example.hospital.dto.UserDTO;
import org.example.hospital.entity.LabAssistant;
import org.example.hospital.entity.User;
import org.example.hospital.repository.LabAssistantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LabAssistantService {
    private final LabAssistantRepository labAssistantRepository;
    private final LabAssistantConverter labAssistantConverter;
    private  final UserService userService;

    @Autowired
    public LabAssistantService(LabAssistantRepository labAssistantRepository, LabAssistantConverter labAssistantConverter, UserService userService) {
        this.labAssistantRepository = labAssistantRepository;
        this.labAssistantConverter = labAssistantConverter;
        this.userService = userService;
    }

    @Transactional
    public void addLabAssistant(LabAssistantDTO labAssistantDTO, UserDTO userDTO){

        LabAssistant labAssistant = labAssistantConverter.convertToEntity(labAssistantDTO, new LabAssistant());
        userService.addUser(userDTO);
        User user = userService.getUserByEmail(userDTO.getEmail());
        labAssistant.setUser(user);
        labAssistantRepository.save(labAssistant);
    }

    public LabAssistantDTO getLabAssistantById(Long id){
        if (labAssistantRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("labAssistant not found with id: " + id);
        }
        return labAssistantConverter.convertToDTO(labAssistantRepository.findById(id).get(), new LabAssistantDTO());
    }

    @Transactional
    public void updateLabAssistant(Long id,LabAssistantDTO labAssistantDTO){
        if (labAssistantRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + id);
        }
        LabAssistant labAssistant = labAssistantRepository.findById(id).get();

        labAssistant = labAssistantConverter.convertToEntity(labAssistantDTO, labAssistant);
        labAssistantRepository.save(labAssistant);
    }

    @Transactional
    public void deleteLabAssistant(Long id){
        if (labAssistantRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("labAssistant not found with id: " + id);
        }
        labAssistantRepository.deleteById(id);
    }

    public List<LabAssistantDTO> getAllLabAssistant(){
        List<LabAssistant> labAssistants = labAssistantRepository.findAll();
        List<LabAssistantDTO> labAssistantDTOS = new ArrayList<>();
        for(LabAssistant labAssistant: labAssistants)
        {
            LabAssistantDTO labAssistantDTO = labAssistantConverter.convertToDTO(labAssistant, new LabAssistantDTO());
            labAssistantDTOS.add(labAssistantDTO);
        }
        return labAssistantDTOS;
    }

}
