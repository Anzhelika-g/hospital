package org.example.hospital.Services;

import org.example.hospital.dto.MessageDTO;
import org.example.hospital.entity.Message;
import org.example.hospital.entity.User;
import org.example.hospital.repository.MessageRepository;
import org.example.hospital.service.MessageService;
import org.example.hospital.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MesageServiceTest {
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private MessageService messageService;

    private Message createMessage() {
        Message message = new Message();
        message.setMessageId(1L);
        message.setSender(new User());
        message.getSender().setUserId(1L);
        message.setReceiver(new User());
        message.getReceiver().setUserId(2L);
        message.setContent("Test message");
        message.setTime(LocalDateTime.now());
        return message;
    }

    private MessageDTO createMessageDTO() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(1L);
        messageDTO.setSenderId(1L);
        messageDTO.setReceiverId(2L);
        messageDTO.setContent("Test message");
        messageDTO.setTime(LocalDateTime.now());
        return messageDTO;
    }


    @Test
    public void addMessageTest(){
        MessageDTO messageDTO = createMessageDTO();
        User sender = new User();
        sender.setUserId(1L);
        User receiver = new User();
        receiver.setUserId(2L);

        when(userService.getUserReferenceById(sender.getUserId())).thenReturn(sender);
        when(userService.getUserReferenceById(receiver.getUserId())).thenReturn(receiver);

        messageService.addMessage(1L, 2L, messageDTO);

        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    public void getMessagesSentToUserTest() {
        List<Message> messages = new ArrayList<>();
        messages.add(createMessage());
        messages.add(createMessage());

        when(userService.getUserById(2L)).thenReturn(new User());
        when(messageRepository.findMessagesBySender_UserIdAndReceiver_UserId(1L, 2L)).thenReturn(messages);

        List<MessageDTO> result = messageService.getMessagesSentToUser(1L, 2L);

        assertNotNull(result);
        assertEquals("Test message", result.get(0).getContent());
    }

    @Test
    public void getMessagesSentToUserNotFoundTest() {
        when(userService.getUserById(10L)).thenReturn(null);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> messageService.getMessagesSentToUser(10L, 2L));
        assertEquals("User not found with id 2", exception.getMessage());
    }
}
