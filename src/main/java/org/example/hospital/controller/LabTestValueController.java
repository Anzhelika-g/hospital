package org.example.hospital.controller;


import org.example.hospital.convertors.LabTestValueConvertor;
import org.example.hospital.dto.LabTestDTO;
import org.example.hospital.dto.LabTestResultValueDTO;
import org.example.hospital.dto.LabTestValueDTO;
import org.example.hospital.entity.LabTestValue;
import org.example.hospital.service.LabTestValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/labTest/{labTestId}/lab-test-value")
public class LabTestValueController {
    private final LabTestValueService labTestValueService;
    private final LabTestValueConvertor labTestValueConvertor = new LabTestValueConvertor();

    @Autowired
    public LabTestValueController(LabTestValueService labTestValueService) {
        this.labTestValueService = labTestValueService;
    }

    @RequestMapping(value = "/{labTestValueId}", method = RequestMethod.GET)
    public ResponseEntity<LabTestValueDTO> getLabTestValue(@PathVariable Long labTestValueId, @PathVariable Long labTestId)
    {
        try {
            LabTestValueDTO labTestValueDTO = labTestValueService.getLabTestValue(labTestValueId);
            LabTestValue labTestValue = labTestValueConvertor.convertToEntity(labTestValueDTO, new LabTestValue());
            if (labTestValue.getLabTest().getLabTestId() != labTestId) {
                throw new NoSuchElementException("Id doesnt exist");
            }

            return new ResponseEntity<>(labTestValueDTO, HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping( value = "/{labTestValueId}", method = RequestMethod.DELETE )
    public ResponseEntity<String> deleteLabTestValue(@PathVariable Long labTestValueId)
    {
        try {
            labTestValueService.deleteLabTestValue(labTestValueId);
            return new ResponseEntity<>("lab test value deleted", HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{labTestValueId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateLabTestValue(@PathVariable Long labTestValueId, @RequestBody LabTestValueDTO labTestValueDTO)
    {
        try {
            labTestValueService.updateLabTestValue(labTestValueId, labTestValueDTO);
            return new ResponseEntity<>("lab test value updated", HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<String> addLabTestValue(@RequestBody LabTestValueDTO labTestValueDTO)
    {
        labTestValueService.addLabTestValue(labTestValueDTO);
        return new ResponseEntity<>("Lab test value created",HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<LabTestValueDTO>> getAllLabTestValues(@PathVariable Long labTestId)
    {
        try {
            List<LabTestValueDTO> labTestDTOS = labTestValueService.getAllLabTestValues(labTestId);
            return new ResponseEntity<>(labTestDTOS, HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



















}
