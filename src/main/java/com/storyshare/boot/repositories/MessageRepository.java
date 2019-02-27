package com.storyshare.boot.repositories;

import com.storyshare.boot.repositories.dto.MessageDTO;
import com.storyshare.boot.pojos.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT new com.storyshare.boot.repositories.dto.MessageDTO(" +
            "m.text, m.date, m.sender.id, m.receiver.id, senders.name, receivers.name) FROM Message m " +
            "INNER JOIN User AS senders ON m.sender.id = senders.id " +
            "INNER JOIN User AS receivers ON m.receiver.id = receivers.id " +
            "WHERE ((senders.id = :userID AND m.deletedBySender = false) " +
            "OR (receivers.id = :userID AND m.deletedByReceiver = false)) " +
            "AND m.id IN (SELECT MAX(m2.id) FROM Message m2 GROUP BY CASE " +
            "WHEN (m2.sender.id > m2.receiver.id AND ((m2.sender.id = :userID AND m2.deletedBySender = false ) OR " +
            "(m2.receiver.id = :userID AND m2.deletedByReceiver = false ))) THEN m2.receiver.id ELSE m2.sender.id END," +
            "CASE WHEN (m2.sender.id < m2.receiver.id AND ((m2.sender.id = :userID AND m2.deletedBySender = false) OR " +
            "(m2.receiver.id = :userID AND m2.deletedByReceiver = false))) THEN m2.receiver.id ELSE m2.sender.id END)" +
            "ORDER BY m.date DESC")
    List<MessageDTO> getLastMessagesInUsersDialogs(@Param("userID") Long userID);

    @Query("SELECT new com.storyshare.boot.repositories.dto.MessageDTO(" +
            "m.text, m.date, m.sender.id, m.receiver.id, senders.name, receivers.name) FROM Message m " +
            "INNER JOIN User AS senders ON m.sender.id = senders.id " +
            "INNER JOIN User AS receivers ON m.receiver.id = receivers.id WHERE (m.sender.id = :userID AND " +
            "m.receiver.id = :otherUser AND m.deletedBySender = false) OR " +
            "(m.sender.id = :otherUser AND m.receiver.id = :userID AND m.deletedByReceiver = false) ORDER BY m.date DESC")
    List<MessageDTO> getUserMessagesWithOtherUserWithOffset(@Param("userID") long userID,
                                                            @Param("otherUser") long otherUser, Pageable pageable);

    @Query("SELECT new com.storyshare.boot.repositories.dto.MessageDTO(" +
            "m.text, m.date, m.sender.id, m.receiver.id, senders.name, receivers.name) FROM Message m " +
            "INNER JOIN User AS senders ON m.sender.id = senders.id " +
            "INNER JOIN User AS receivers ON m.receiver.id = receivers.id WHERE (m.sender.id = :userID AND " +
            "m.receiver.id = :otherUser AND m.deletedBySender = false) OR (m.sender.id = :otherUser " +
            "AND m.receiver.id = :userID AND m.deletedByReceiver = false)")
    List<MessageDTO> getAllUserMessagesWithOtherUser(@Param("userID") long userID, @Param("otherUser") long otherUser);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Message m SET m.deletedBySender = " +
            "CASE WHEN (m.sender.id = :userID) THEN true ELSE m.deletedBySender END, " +
            "m.deletedByReceiver = CASE WHEN (m.receiver.id = :userID) THEN true ELSE m.deletedByReceiver END " +
            "WHERE m.id = :messageID")
    void deleteMessageInCertainUser(@Param("userID") long userID, @Param("messageID") long messageID);
}
