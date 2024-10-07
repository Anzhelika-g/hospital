package org.example.hospital.controller;


import org.example.hospital.converter.LabTestConverter;
import org.example.hospital.dto.LabTestDTO;
import org.example.hospital.dto.LabTestResultDTO;
import org.example.hospital.entity.LabTest;
import org.example.hospital.service.LabTestResultService;
import org.example.hospital.service.LabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/labTest")
public class LabTestController {
    private final LabTestService labTestService;
    private LabTestConverter labTestConverter = new LabTestConverter();
    private final LabTestResultService labTestResultService;
    @Autowired
    public LabTestController(LabTestService labTestService, LabTestResultService labTestResultService) {
        this.labTestService = labTestService;
        this.labTestResultService = labTestResultService;
    }
    @RequestMapping(value = "/{labTestId}", method = RequestMethod.GET)
    public ResponseEntity<LabTestDTO> getLabTest(@PathVariable Long labTestId )
    {
        try {
            LabTestDTO labTestDTO = labTestService.getLabTest(labTestId);
            return new ResponseEntity<>(labTestDTO, HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{labTestId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteLabTest(@PathVariable Long labTestId)
    {
        try {
            labTestService.deleteLabTest(labTestId);
            return new ResponseEntity<>("Lab test deleted", HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/{labTestId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateLabTest(@PathVariable Long labTestId, @RequestBody LabTestDTO labTestDTO)
    {
        try {
            labTestService.updateLabTest(labTestId, labTestDTO);
            return new ResponseEntity<>("Lab test updated", HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> addLabTest(@RequestBody LabTestDTO labTestDTO)
    {
        labTestService.addLabTest(labTestDTO);
        return new ResponseEntity<>("Lab test created", HttpStatus.OK);
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<LabTestDTO>> getAllLabTests()
    {
        try {
            List<LabTestDTO> labTestDTOS = labTestService.getAllLabTests();
            return new ResponseEntity<>(labTestDTOS, HttpStatus.OK);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{labTestId}/labTestResult", method = RequestMethod.POST)
    public ResponseEntity<String> createLabTestResult(@RequestBody LabTestResultDTO labTestResultDTO, @PathVariable Long labTestId)
    {
        try {
            labTestResultService.addLabTestResult(labTestResultDTO, labTestConverter.convertToEntity(labTestService.getLabTest(labTestId), new LabTest()));
            return new ResponseEntity<>("Lab test result created", HttpStatus.CREATED);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}












