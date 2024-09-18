package org.example.hospital.controller;

import org.example.hospital.convertors.LabAssistantConvertor;
import org.example.hospital.dto.LabAssistantDTO;
import org.example.hospital.entity.LabAssistant;
import org.example.hospital.service.LabAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lab-assistant")
public class LabAssistantController {
    private final LabAssistantService labAssistantService;
    private final LabAssistantConvertor labAssistantConvertor = new LabAssistantConvertor();


    @Autowired
    public LabAssistantController(LabAssistantService labAssistantService) {
        this.labAssistantService = labAssistantService;
    }

    @RequestMapping(value = "/{labAssistantId}", method = RequestMethod.GET)
    public LabAssistantDTO getLabAssistant(@PathVariable Long labAssistantId, @RequestBody LabAssistantDTO labAssistantDTO)
    {
        return labAssistantConvertor.convertToDTO(labAssistantService.getLabAssistantById(labAssistantId), labAssistantDTO);

    }
    @RequestMapping(value = "/{labAssistantId}", method = RequestMethod.DELETE)
    public String deleteLabAssistant(@PathVariable Long labAssistantId)
    {
        labAssistantService.deleteLabAssistant(labAssistantId);
        return "lab assistant deleted";
    }

    @RequestMapping(value = "/{labAssistantId}", method = RequestMethod.PUT)
    public String updateLabAssistant(@PathVariable Long labAssistantId, @RequestBody LabAssistantDTO labAssistantDTO)
    {
        labAssistantService.updateLabAssistant(labAssistantId,labAssistantDTO);
        return "lab assistant updated";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addLabAssistant(@RequestBody LabAssistantDTO labAssistantDTO)
    {

        labAssistantService.addLabAssistant(labAssistantDTO);
        return "lab assistant added";
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<LabAssistantDTO> getAllLabAssistants()
    {
        List<LabAssistantDTO> labAssistantDTOS = new ArrayList<>();
        for (LabAssistant labAssistant: labAssistantService.getAllLabAssistant())
        {
            labAssistantDTOS.add(labAssistantConvertor.convertToDTO(labAssistant, new LabAssistantDTO()));
        }
        return labAssistantDTOS;
    }
}





















