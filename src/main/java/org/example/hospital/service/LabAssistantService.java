package org.example.hospital.service;


import org.example.hospital.convertors.LabAssistantConvertor;
import org.example.hospital.dto.LabAssistantDTO;
import org.example.hospital.entity.LabAssistant;
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
    private final LabAssistantConvertor labAssistantConvertor;

    @Autowired
    public LabAssistantService(LabAssistantRepository labAssistantRepository, LabAssistantConvertor labAssistantConvertor) {
        this.labAssistantRepository = labAssistantRepository;
        this.labAssistantConvertor = labAssistantConvertor;
    }

    @Transactional
    public void addLabAssistant(LabAssistantDTO labAssistantDTO){

        LabAssistant labAssistant = labAssistantConvertor.convertToEntity(labAssistantDTO, new LabAssistant());

        labAssistantRepository.save(labAssistant);
    }

    public LabAssistantDTO getLabAssistantById(Long id){
        if (labAssistantRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("labAssistant not found with id: " + id);
        }
        return labAssistantConvertor.convertToDTO(labAssistantRepository.findById(id).get(), new LabAssistantDTO());
    }

    @Transactional
    public void updateLabAssistant(Long id,LabAssistantDTO labAssistantDTO){
        if (labAssistantRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + id);
        }
        LabAssistant labAssistant = labAssistantRepository.findById(id).get();

        labAssistant = labAssistantConvertor.convertToEntity(labAssistantDTO, labAssistant);
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
            LabAssistantDTO labAssistantDTO = labAssistantConvertor.convertToDTO(labAssistant, new LabAssistantDTO());
            labAssistantDTOS.add(labAssistantDTO);
        }
        return labAssistantDTOS;
    }

}