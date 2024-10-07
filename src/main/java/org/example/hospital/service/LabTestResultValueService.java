package org.example.hospital.service;


import org.example.hospital.converter.LabTestResultValueConverter;
import org.example.hospital.dto.LabTestResultValueDTO;
import org.example.hospital.entity.LabTestResultValue;
import org.example.hospital.repository.LabTestResultValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LabTestResultValueService {
    private final LabTestResultValueRepository labTestResultValueRepository;
    private final LabTestResultValueConverter labTestResultValueConverter;

    @Autowired
    public LabTestResultValueService(LabTestResultValueRepository labTestResultValueRepository, LabTestResultValueConverter labTestResultValueConverter) {
        this.labTestResultValueRepository = labTestResultValueRepository;
        this.labTestResultValueConverter = labTestResultValueConverter;
    }
    @Transactional
    public void addLabTestResultValue(LabTestResultValueDTO labTestResultValueDTO){

        LabTestResultValue labTestResultValue = labTestResultValueConverter.convertToEntity(labTestResultValueDTO, new LabTestResultValue());

        labTestResultValueRepository.save(labTestResultValue);
    }

    public LabTestResultValueDTO getLabTestResultValue(Long id){
        if (labTestResultValueRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("lab test result value not found with id: " + id);
        }
        return labTestResultValueConverter.convertToDTO(labTestResultValueRepository.findById(id).get(), new LabTestResultValueDTO());
    }

    @Transactional
    public void updateLabTestResultValue(Long id, LabTestResultValueDTO labTestResultValueDTO){
        if (labTestResultValueRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("lab test result value not found with id: " + id);
        }
        LabTestResultValue labTestResultValue = labTestResultValueRepository.findById(id).get();

        labTestResultValue = labTestResultValueConverter.convertToEntity(labTestResultValueDTO, labTestResultValue);

        labTestResultValueRepository.save(labTestResultValue);
    }

    @Transactional
    public void deleteLabTestResultValue(Long id){
        if (labTestResultValueRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("lab test result value not found with id: " + id);
        }
        labTestResultValueRepository.deleteById(id);
    }

    public List<LabTestResultValueDTO> getAllLabTestResultValue(){
        List<LabTestResultValue> labTestResultValues = labTestResultValueRepository.findAll();
        List<LabTestResultValueDTO> labTestResultValueDTOS = new ArrayList<>();
        for(LabTestResultValue labTestResultValue: labTestResultValues)
        {
            LabTestResultValueDTO labTestResultValueDTO = labTestResultValueConverter.convertToDTO(labTestResultValue, new LabTestResultValueDTO());
            labTestResultValueDTOS.add(labTestResultValueDTO);
        }
        return labTestResultValueDTOS;
    }
}
