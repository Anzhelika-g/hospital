package org.example.hospital.service;


import org.example.hospital.convertors.LabTestValueConvertor;
import org.example.hospital.dto.LabTestValueDTO;
import org.example.hospital.entity.LabTestValue;
import org.example.hospital.repository.LabTestValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LabTestValueService {
    private final LabTestValueRepository labTestValueRepository;
    private final LabTestValueConvertor labTestValueConvertor;

    @Autowired
    public LabTestValueService(LabTestValueRepository labTestValueRepository, LabTestValueConvertor labTestValueConvertor) {
        this.labTestValueRepository = labTestValueRepository;
        this.labTestValueConvertor = labTestValueConvertor;
    }

    @Transactional
    public void addLabTestValue(LabTestValueDTO labTestValueDTO)
    {
        LabTestValue labTestValue = labTestValueConvertor.convertToEntity(labTestValueDTO, new LabTestValue());

        labTestValueRepository.save(labTestValue);
    }

    public LabTestValueDTO getLabTestValue(Long id)
    {
        if(labTestValueRepository.findById(id).isEmpty())
        {
            throw new NoSuchElementException("Lab test value not found");
        }
        return labTestValueConvertor.convertToDTO(labTestValueRepository.findById(id).get(), new LabTestValueDTO());
    }

    @Transactional
    public void updateLabTestValue(Long id, LabTestValueDTO labTestValueDTO)
    {
        if(labTestValueRepository.findById(id).isEmpty())
        {
            throw new NoSuchElementException("Lab test value not found by id");
        }
        LabTestValue labTestValue = labTestValueRepository.findById(id).get();

        labTestValue = labTestValueConvertor.convertToEntity(labTestValueDTO, labTestValue);

        labTestValueRepository.save(labTestValue);
    }
    @Transactional
    public void deleteLabTestValue(Long id)
    {
        if(labTestValueRepository.findById(id).isEmpty())
        {
            throw new NoSuchElementException("Lab test value not found by id");
        }
        labTestValueRepository.deleteById(id);
    }

    public List<LabTestValueDTO> getAllLabTestValues(Long labTestId){
        List<LabTestValue> labTestValues = labTestValueRepository.findAllByLabTestLabTestId(labTestId);
        List<LabTestValueDTO> labTestValueDTOS = new ArrayList<>();
        for(LabTestValue labTestValue: labTestValues)
        {
            labTestValueDTOS.add(labTestValueConvertor.convertToDTO(labTestValue, new LabTestValueDTO()));
        }

        return labTestValueDTOS;
    }




}
