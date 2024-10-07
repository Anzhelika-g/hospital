package org.example.hospital.service;

import org.example.hospital.dto.MessageDTO;
import org.example.hospital.entity.Message;
import org.example.hospital.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Transactional
    public void addMessage(Long senderId, Long receiverId, MessageDTO messageDTO){
        Message message = new Message();
        message.setSender(userService.getUserReferenceById(senderId));
        message.setReceiver(userService.getUserReferenceById(receiverId));
        message.setContent(messageDTO.getContent());
        message.setTime(LocalDateTime.now());
        messageRepository.save(message);
    }

    public MessageDTO getMessageById(Long id){
        if (messageRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Message not found with id" + id);
        }
        Message message = messageRepository.findById(id).get();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(message.getMessageId());
        messageDTO.setSenderId(message.getSender().getUserId());
        messageDTO.setReceiverId(message.getReceiver().getUserId());
        messageDTO.setContent(message.getContent());
        messageDTO.setTime(message.getTime());
        return messageDTO;
    }

    @Transactional
    public void updateMessage(Long id, MessageDTO messageDTO){
        if (messageRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Message not found with id" + id);
        }
        Message message = messageRepository.findById(id).get();
        message.setContent(messageDTO.getContent());
        messageRepository.save(message);
    }

    @Transactional
    public void deleteMessage(Long id){
        if (messageRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Message not found with id" + id);
        }
        messageRepository.deleteById(id);
    }


    public List<MessageDTO> getMessagesBetweenUsers(Long senderId, Long receiverId) {
        if (userService.getUserById(receiverId) == null) {
            throw new NoSuchElementException("User not found with id " + receiverId);
        }

        List<Message> messages = messageRepository.findAllMessagesBetween(senderId, receiverId);
        if (messages.isEmpty()) {
            throw new NoSuchElementException("No messages exchanged between sender with id " + senderId + " and receiver with id " + receiverId + ".");
        }

        List<MessageDTO> messageDTOS = new ArrayList<>();
        for (Message message : messages) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setMessageId(message.getMessageId());
            messageDTO.setSenderId(message.getSender().getUserId());
            messageDTO.setReceiverId(message.getReceiver().getUserId());
            messageDTO.setContent(message.getContent());
            messageDTO.setTime(message.getTime());
            messageDTOS.add(messageDTO);
        }
        return messageDTOS;
    }
}
