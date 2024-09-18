package org.example.hospital.controller;


import org.example.hospital.convertors.LabTestConvertor;
import org.example.hospital.convertors.LabTestResultConvertor;
import org.example.hospital.dto.LabTestDTO;
import org.example.hospital.dto.LabTestResultDTO;
import org.example.hospital.entity.LabTest;
import org.example.hospital.entity.LabTestResult;
import org.example.hospital.service.LabTestResultService;
import org.example.hospital.service.LabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/labTest")
public class LabTestController {
    private final LabTestService labTestService;
    private LabTestConvertor labTestConvertor = new LabTestConvertor();
    private final LabTestResultService labTestResultService;
    private  LabTestResultConvertor labTestResultConvertor;

    @Autowired
    public LabTestController(LabTestService labTestService, LabTestResultService labTestResultService) {
        this.labTestService = labTestService;
        this.labTestResultService = labTestResultService;
    }
    @RequestMapping(value = "/{labTestId}", method = RequestMethod.GET)
    public LabTestDTO getLabTest(@PathVariable Long labTestId, @RequestBody LabTestDTO labTestDTO)
    {

        return labTestConvertor.convertToDTO(labTestService.getLabTest(labTestId), labTestDTO);
    }

    @RequestMapping(value = "/{labTestId}", method = RequestMethod.DELETE)
    public String deleteLabTest(@PathVariable Long labTestId)
    {
        labTestService.deleteLabTest(labTestId);
        return "delete lab test";
    }

    @RequestMapping(value = "/{labTestId}", method = RequestMethod.PUT)
    public String updateLabTest(@PathVariable Long labTestId, @RequestBody LabTestDTO labTestDTO)
    {
        labTestService.updateLabTest(labTestId, labTestDTO);
        return "lab test updated";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addLabTest(@RequestBody LabTestDTO labTestDTO)
    {

        labTestService.addLabTest(labTestDTO);
        return  "lab test added";
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<LabTestDTO> getAllLabTests()
    {
        List<LabTestDTO> labTestDTOS = new ArrayList<>();
        for(LabTest labTest: labTestService.getAllLabTests())
        {
            labTestDTOS.add(labTestConvertor.convertToDTO(labTest, new LabTestDTO() ));
        }
        return labTestDTOS;
    }

    @RequestMapping(value = "/{labTestId}/labTestResult", method = RequestMethod.POST)
    public String createLabTestResult(@RequestBody LabTestResultDTO labTestResultDTO, @PathVariable Long labTestId)
    {
        LabTest labTest = labTestService.getLabTest(labTestId);
        labTestResultService.addLabTestResult(labTestResultDTO, labTest);
        return "LabTestResult created";
    }

    // /labtest/{labTestId}/result POST ( create  labtestResult )
}












