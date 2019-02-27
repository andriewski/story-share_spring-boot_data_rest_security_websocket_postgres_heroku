package com.storyshare.boot.services.impl;

import com.storyshare.boot.repositories.dto.MessageDTO;
import com.storyshare.boot.pojos.Message;
import com.storyshare.boot.repositories.MessageRepository;
import com.storyshare.boot.repositories.UserRepository;
import com.storyshare.boot.services.MessageService;
import com.storyshare.boot.services.ServiceException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@Service(value = "messageService")
@Transactional
@NoArgsConstructor
public class MessageServiceImpl extends BaseService<Message> implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Message save(String text, LocalDateTime date, long senderID, long receiverID) {
        try {
            return save(new Message(text, date, userRepository.getOne(senderID), userRepository.getOne(receiverID)));
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<MessageDTO> getLastMessagesInUsersDialogs(long userID) {
        try {
            return messageRepository.getLastMessagesInUsersDialogs(userID);
        } catch (Exception e) {
            throw new ServiceException("Error getting Comments Last Messages In Users Dialogs by ID " + userID);
        }
    }

    @Override
    public List<MessageDTO> getUserMessagesWithOtherUserWithOffset(long userID, long otherUser, Pageable pageable) {
        try {
            List<MessageDTO> list = messageRepository.getUserMessagesWithOtherUserWithOffset(userID, otherUser, pageable);
            return list;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting User Messages With Other User With Offset by userID ")
                    .append(userID)
                    .append(" anotherUserID ")
                    .append(otherUser)
                    .append(" and pagination ")
                    .append(pageable);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public List<MessageDTO> getAllUserMessagesWithOtherUser(long userID, long otherUser) {
        try {
            return messageRepository.getAllUserMessagesWithOtherUser(userID, otherUser);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting All User Messages With Other User userID ")
                    .append(userID)
                    .append(" and anotherUserID ")
                    .append(otherUser);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public void deleteMessageInCertainUser(long userID, long messageID) {
        try {
            messageRepository.deleteMessageInCertainUser(userID, messageID);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error deleting delete Message In Certain User by userID ")
                    .append(userID)
                    .append(" and messageID ")
                    .append(messageID);

            throw new ServiceException(sb.toString());
        }
    }
}
