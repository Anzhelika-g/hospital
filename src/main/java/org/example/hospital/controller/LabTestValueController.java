package org.example.hospital.controller;


import org.example.hospital.convertors.LabTestValueConvertor;
import org.example.hospital.dto.LabTestResultValueDTO;
import org.example.hospital.dto.LabTestValueDTO;
import org.example.hospital.entity.LabTestValue;
import org.example.hospital.service.LabTestValueService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public LabTestValueDTO getLabTestValue(@PathVariable Long labTestValueId,@PathVariable Long labTestId, @RequestBody LabTestValueDTO labTestValueDTO)
    {

        LabTestValue labTestValue = labTestValueService.getLabTestValue(labTestValueId);
        if (labTestValue.getLabTest().getLabTestId() != labTestId) {
            throw new NoSuchElementException("Id doesnt exist");
        }
        return labTestValueConvertor.convertToDTO(labTestValue, labTestValueDTO);
    }

    @RequestMapping( value = "/{labTestValueId}", method = RequestMethod.DELETE )
    public String deleteLabTestValue(@PathVariable Long labTestValueId)
    {
        labTestValueService.deleteLabTestValue(labTestValueId);
        return "lab test value deleted";
    }

    @RequestMapping(value = "/{labTestValueId}", method = RequestMethod.PUT)
    public String updateLabTestValue(@PathVariable Long labTestValueId, @RequestBody LabTestValueDTO labTestValueDTO)
    {
        labTestValueService.updateLabTestValue(labTestValueId, labTestValueDTO);
        return "lab test value updated";
    }

    @RequestMapping( method = RequestMethod.POST)
    public String addLabTestValue(@RequestBody LabTestValueDTO labTestValueDTO)
    {
        labTestValueService.addLabTestValue(labTestValueDTO);
        return "lab test value added";
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<LabTestValueDTO> getAllLabTestValues(@PathVariable Long labTestId)
    {

        return labTestValueService.getAllLabTestValues(labTestId);
    }



















}
