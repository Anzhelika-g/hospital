package org.example.hospital.service;


import org.example.hospital.convertors.LabAssistantConvertor;
import org.example.hospital.convertors.LabTestResultConvertor;
import org.example.hospital.dto.LabAssistantDTO;
import org.example.hospital.dto.LabTestResultDTO;
import org.example.hospital.entity.LabAssistant;
import org.example.hospital.entity.LabTest;
import org.example.hospital.entity.LabTestResult;
import org.example.hospital.repository.LabTestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LabTestResultService {
    private final LabTestResultRepository labTestResultRepository;
    private final LabTestResultConvertor labTestResultConvertor;

    @Autowired
    public LabTestResultService(LabTestResultRepository labTestResultRepository, LabTestResultConvertor labTestResultConvertor) {
        this.labTestResultRepository = labTestResultRepository;
        this.labTestResultConvertor = labTestResultConvertor;
    }
    @Transactional
    public void addLabTestResult(LabTestResultDTO labTestResultDTO, LabTest labTest){


        LabTestResult labTestResult = labTestResultConvertor.convertToEntity(labTestResultDTO, new LabTestResult());
        labTestResult.setLabTest(labTest);

        labTestResultRepository.save(labTestResult);
    }

    public LabTestResultDTO getLabTestResultById(Long id){
        if (labTestResultRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("lab test result not found with id: " + id);
        }
        return labTestResultConvertor.convertToDTO(labTestResultRepository.findById(id).get(), new LabTestResultDTO());
    }

    @Transactional
    public void updateLabTestResult(Long id, LabTestResultDTO labTestResultDTO){
        if (labTestResultRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("lab test result not found with id: " + id);
        }
        LabTestResult labTestResult = labTestResultRepository.findById(id).get();

        labTestResult = labTestResultConvertor.convertToEntity(labTestResultDTO, labTestResult);

        labTestResultRepository.save(labTestResult);
    }

    @Transactional
    public void deleteLabTestResult(Long id){
        if (labTestResultRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Lab test result not found with id: " + id);
        }
        labTestResultRepository.deleteById(id);
    }

    public List<LabTestResultDTO> getAllLabTestResult(){
        List<LabTestResult> labTestResults = labTestResultRepository.findAll();
        List<LabTestResultDTO> labTestResultDTOS = new ArrayList<>();
        for(LabTestResult labTestResult: labTestResults)
        {
            LabTestResultDTO labTestResultDTO = labTestResultConvertor.convertToDTO(labTestResult, new LabTestResultDTO());
            labTestResultDTOS.add(labTestResultDTO);
        }
        return labTestResultDTOS;
    }
}