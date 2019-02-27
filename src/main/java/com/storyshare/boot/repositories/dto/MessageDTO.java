package com.storyshare.boot.repositories.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString(exclude = "date")
public class MessageDTO implements Serializable {
    private String text;
    private Timestamp date;
    private Long senderID;
    private Long receiverID;
    private String senderName;
    private String receiverName;

    public MessageDTO(String text, LocalDateTime date, Long senderID,
                      Long receiverID, String senderName, String receiverName) {
        this.text = text;
        this.date = Timestamp.valueOf(date);
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }
}
