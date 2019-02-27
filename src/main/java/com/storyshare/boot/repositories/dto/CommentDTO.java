package com.storyshare.boot.repositories.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDTO implements Serializable {
    private String userName;
    private String text;
    private Timestamp date;
    private Long commentID;
    private Long userID;

    public CommentDTO(String userName, String text, LocalDateTime date, Long commentID, Long userID) {
        this.userName = userName;
        this.text = text;
        this.date = Timestamp.valueOf(date);
        this.commentID = commentID;
        this.userID = userID;
    }
}
