package com.storyshare.boot.repositories;

import com.storyshare.boot.repositories.dto.CommentDTO;
import com.storyshare.boot.pojos.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT new com.storyshare.boot.repositories.dto.CommentDTO(u.name, c.text, c.date, c.id, u.id) " +
            "FROM Comment c INNER JOIN c.user u WHERE c.post.id = :postID ORDER BY c.date DESC")
    List<CommentDTO> getCommentsInThePostWithOffsetAndLimit(@Param("postID") long postID, Pageable pageable);

    @Query("SELECT COUNT(*) FROM Comment c WHERE c.post.id = :postID")
    long getNumberOfCommentsInThePost(@Param("postID") long postID);
}
