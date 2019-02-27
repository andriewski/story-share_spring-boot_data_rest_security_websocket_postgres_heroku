package com.storyshare.boot.repositories;

import com.storyshare.boot.repositories.dto.PostDTO;
import com.storyshare.boot.pojos.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT new com.storyshare.boot.repositories.dto.PostDTO(" +
            "p.id, p.user.id, p.text, p.date, p.user.name, p.user.avatar, p.picture, p.likes.size, " +
            "CASE WHEN ((SELECT count(userLike.id) FROM Post p1 INNER JOIN p1.likes AS userLike WHERE p1.id = p.id " +
            "AND userLike.id = :userID) > 0) THEN true ELSE false END) " +
            "FROM Post p INNER JOIN p.user ORDER BY p.date DESC")
    List<PostDTO> getPostsWithPagination(@Param("userID") long userID, Pageable pageable);

    @Query("SELECT new com.storyshare.boot.repositories.dto.PostDTO(" +
            "p.id, p.user.id, p.text, p.date, p.user.name, p.user.avatar, p.picture, p.likes.size, " +
            "CASE WHEN ((SELECT COUNT(userLike.id) FROM Post p1 INNER JOIN p1.likes AS userLike WHERE p1.id = p.id " +
            "AND userLike.id = :userID) > 0) THEN true ELSE false END) " +
            "FROM Post p INNER JOIN p.user WHERE p.id = :postID")
    PostDTO getSinglePostDTO(@Param("userID") long userID, @Param("postID") long postID);

    @Query("SELECT COUNT(*) FROM Post")
    long getNumberOfAllPosts();
}
