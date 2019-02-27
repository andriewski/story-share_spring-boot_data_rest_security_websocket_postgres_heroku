package com.storyshare.boot.services;

import com.storyshare.boot.repositories.dto.CommentDTO;
import com.storyshare.boot.pojos.Comment;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService extends IService<Comment> {
    Comment save(long userID, long postID, String text, LocalDateTime date);

    void delete(long commentID);

    List<CommentDTO> getCommentsInThePostWithOffsetAndLimit(long postID, Pageable pageable);

    long getNumberOfCommentsInThePost(long postID);
}
