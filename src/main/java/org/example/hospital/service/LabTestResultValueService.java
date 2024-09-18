package org.example.hospital.service;


import org.example.hospital.convertors.LabTestResultValueConvertor;
import org.example.hospital.dto.LabTestResultValueDTO;
import org.example.hospital.entity.LabTestResultValue;
import org.example.hospital.repository.LabTestResultValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LabTestResultValueService {
    private final LabTestResultValueRepository labTestResultValueRepository;
    private final LabTestResultValueConvertor labTestResultValueConvertor;

    @Autowired
    public LabTestResultValueService(LabTestResultValueRepository labTestResultValueRepository, LabTestResultValueConvertor labTestResultValueConvertor) {
        this.labTestResultValueRepository = labTestResultValueRepository;
        this.labTestResultValueConvertor = labTestResultValueConvertor;
    }
    @Transactional
    public void addLabTestResultValue(LabTestResultValueDTO labTestResultValueDTO){

        LabTestResultValue labTestResultValue = labTestResultValueConvertor.convertToEntity(labTestResultValueDTO, new LabTestResultValue());

        labTestResultValueRepository.save(labTestResultValue);
    }

    public LabTestResultValue getLabTestResultValue(Long id){
        if (labTestResultValueRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("lab test result value not found with id: " + id);
        }
        return labTestResultValueRepository.findById(id).get();
    }

    @Transactional
    public void updateLabTestResultValue(Long id, LabTestResultValueDTO labTestResultValueDTO){
        if (labTestResultValueRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("lab test result value not found with id: " + id);
        }
        LabTestResultValue labTestResultValue = labTestResultValueRepository.findById(id).get();

        labTestResultValue = labTestResultValueConvertor.convertToEntity(labTestResultValueDTO, labTestResultValue);

        labTestResultValueRepository.save(labTestResultValue);
    }

    @Transactional
    public void deleteLabTestResultValue(Long id){
        if (labTestResultValueRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("lab test result value not found with id: " + id);
        }
        labTestResultValueRepository.deleteById(id);
    }

    public List<LabTestResultValue> getAllLabTestResultValue(){
        return labTestResultValueRepository.findAll();
    }
}
