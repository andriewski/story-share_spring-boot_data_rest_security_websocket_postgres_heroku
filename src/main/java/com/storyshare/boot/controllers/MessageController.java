package com.storyshare.boot.controllers;

import com.storyshare.boot.repositories.dto.MessageDTO;
import com.storyshare.boot.services.MessageService;
import com.storyshare.boot.services.auth.MVCUser;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/chat", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<MessageDTO>> getChatMessages(
            @RequestParam("userID") long userID,
            @RequestParam("anotherUserID") long anotherUserID,
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit,
            Authentication auth) {
        if (auth == null || !auth.isAuthenticated() || ((MVCUser) auth.getPrincipal()).getId() != userID) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(messageService.getUserMessagesWithOtherUserWithOffset(
                userID, anotherUserID, PageRequest.of(offset/limit, limit)), HttpStatus.OK);
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<MessageDTO>> getMessages(@RequestParam("userID") long userID, Authentication auth) {
        if (auth == null || !auth.isAuthenticated() || ((MVCUser) auth.getPrincipal()).getId() != userID) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(messageService.getLastMessagesInUsersDialogs(userID), HttpStatus.OK);
    }

    @GetMapping(value = "/history")
    public ResponseEntity<byte[]> downloadHistoryMessage(
            @RequestParam("userID") long userID,
            @RequestParam("anotherUserID") long anotherUserID,
            Authentication auth) throws Exception {
        MVCUser mvcUser;

        if (auth == null || !auth.isAuthenticated() || (mvcUser = (MVCUser) auth.getPrincipal()).getId() != userID) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        File file = new File("history_message.txt");

        try (BufferedWriter writer = new BufferedWriter(new PrintWriter(file, "UTF-8"))) {
            List<MessageDTO> messageList = messageService.getAllUserMessagesWithOtherUser(userID, anotherUserID);
            StringBuilder sb = new StringBuilder();

            for (MessageDTO message : messageList) {
                sb.append(mvcUser.getId().equals(message.getSenderID()) ? "Me: \t" : message.getSenderName() + ": \t")
                        .append(message.getText())
                        .append("\t")
                        .append(message.getDate())
                        .append("\n");
            }

            writer.write(sb.toString());
            writer.flush();

            byte[] content = FileUtils.readFileToByteArray(file);

            return new ResponseEntity<>(content, HttpStatus.OK);
        }
    }
}