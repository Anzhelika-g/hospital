package org.example.hospital.controller;

import org.example.hospital.dto.MessageDTO;
import org.example.hospital.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/patient/{patientId}/message")
public class PatientMessageController {
    private final MessageService messageService;

    public PatientMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<String> sendMessage(@PathVariable Long patientId,@PathVariable Long doctorId, @RequestBody MessageDTO messageDTO){
        messageService.addMessage(doctorId, patientId, messageDTO);
        return new ResponseEntity<>("Message sent.", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long patientId,@PathVariable Long doctorId){
        try {
            List<MessageDTO> messageDTOS = messageService.getMessagesBetweenUsers(doctorId, patientId);
            return new ResponseEntity<>(messageDTOS, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
