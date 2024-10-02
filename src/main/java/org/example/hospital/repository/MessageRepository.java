package org.example.hospital.repository;

import org.example.hospital.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesBySender_UserIdAndReceiver_UserId(Long senderId, Long receiverId);
}
