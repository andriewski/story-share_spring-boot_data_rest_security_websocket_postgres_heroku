package com.storyshare.boot.repositories.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostDTO implements Serializable {
    private Long postID;
    private Long userID;
    private String text;
    private Timestamp date;
    private String userName;
    private String userAvatar;
    private String picture;
    private Integer likes;
    private Boolean isLiked;

    public PostDTO(Long postID, Long userID, String text, LocalDateTime date, String userName, String userAvatar,
                   String picture, Integer likes, Boolean isLiked) {
        this.postID = postID;
        this.userID = userID;
        this.text = text;
        this.date = Timestamp.valueOf(date);
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.picture = picture;
        this.likes = likes;
        this.isLiked = isLiked;
    }
}
