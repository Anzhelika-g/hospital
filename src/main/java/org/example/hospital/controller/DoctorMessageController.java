package org.example.hospital.controller;

import org.example.hospital.dto.MessageDTO;
import org.example.hospital.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/send")
    public ResponseEntity<String> sendMessage(@PathVariable Long doctorId, @RequestBody MessageDTO messageDTO){
        Long senderId = 3L;
        messageService.addMessage(doctorId, senderId, messageDTO);
        return new ResponseEntity<>("Message sent.", HttpStatus.OK);
    }

    @RequestMapping(value = "/list")
    private ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long doctorId){
        Long senderId = 3L;
        try {
            List<MessageDTO> messageDTOS = messageService.getMessagesSentToUser(senderId ,doctorId);
            return new ResponseEntity<>(messageDTOS, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
