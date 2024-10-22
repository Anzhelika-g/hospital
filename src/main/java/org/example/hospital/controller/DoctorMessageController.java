package org.example.hospital.controller;

import org.example.hospital.dto.MessageDTO;
import org.example.hospital.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/doctor/{doctorId}/message")
public class DoctorMessageController {
    private final MessageService messageService;

    @Autowired
    public DoctorMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<String> sendMessage(@PathVariable Long doctorId, @PathVariable Long patientId, @RequestBody MessageDTO messageDTO){
        messageService.addMessage(doctorId, patientId, messageDTO);
        return new ResponseEntity<>("Message sent.", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long doctorId, @PathVariable Long patientId){
        try {
            List<MessageDTO> messageDTOS = messageService.getMessagesBetweenUsers(patientId ,doctorId);
            return new ResponseEntity<>(messageDTOS, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
