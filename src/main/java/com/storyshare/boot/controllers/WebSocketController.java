package com.storyshare.boot.controllers;

import com.storyshare.boot.repositories.dto.MessageDTO;
import com.storyshare.boot.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private MessageService messageService;

    @MessageMapping("/websocket/{room}")
    public void greeting(@DestinationVariable String room, MessageDTO message) throws Exception {
        this.template.convertAndSend("/websocket/@" + room, message);
        this.template.convertAndSend("/websocket/" + message.getSenderID(), message);
        this.template.convertAndSend("/websocket/" + message.getReceiverID(), message);
        messageService.save(message.getText(), message.getDate().toLocalDateTime(),
                message.getSenderID(), message.getReceiverID());
    }
}
