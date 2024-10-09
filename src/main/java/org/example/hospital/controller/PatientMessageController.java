package org.example.hospital.controller;

import org.example.hospital.dto.MessageDTO;
import org.example.hospital.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<String> sendMessage(@PathVariable Long patientId, @RequestBody MessageDTO messageDTO){
        Long senderId = 8L;
        messageService.addMessage(senderId, patientId, messageDTO);
        return new ResponseEntity<>("Message sent.", HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long patientId){
        Long senderId = 8L;
        try {
            List<MessageDTO> messageDTOS = messageService.getMessagesBetweenUsers(senderId, patientId);
            return new ResponseEntity<>(messageDTOS, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
