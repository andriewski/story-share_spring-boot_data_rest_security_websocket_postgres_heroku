package com.storyshare.boot.services;

import com.storyshare.boot.repositories.dto.MessageDTO;
import com.storyshare.boot.pojos.Message;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService extends IService<Message> {
    Message save(String text, LocalDateTime date, long senderID, long receiverID);

    List<MessageDTO> getLastMessagesInUsersDialogs(long userID);

    List<MessageDTO> getUserMessagesWithOtherUserWithOffset(long userID, long otherUser, Pageable pageable);

    List<MessageDTO> getAllUserMessagesWithOtherUser(long userID, long otherUser);

    void deleteMessageInCertainUser(long userID, long messageID);
}
