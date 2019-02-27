package com.storyshare.boot.services;

import com.storyshare.boot.repositories.dto.PostDTO;
import com.storyshare.boot.pojos.Post;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService extends IService<Post> {
    Post save(String text, LocalDateTime date, long userID, String picture);

    void delete(long postID);

    List<PostDTO> getPostsWithPagination(long userID, Pageable pageable);

    void likePostByUser(long postID, long userID);

    void unlikePostByUser(long postID, long userID);

    PostDTO getSinglePostDTO(long userID, long postID);

    long getNumberOfAllPosts();
}
