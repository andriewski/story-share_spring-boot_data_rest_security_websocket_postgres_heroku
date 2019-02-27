package com.storyshare.boot.services.impl;

import com.storyshare.boot.repositories.dto.PostDTO;
import com.storyshare.boot.pojos.Post;
import com.storyshare.boot.repositories.PostRepository;
import com.storyshare.boot.repositories.UserRepository;
import com.storyshare.boot.services.PostService;
import com.storyshare.boot.services.ServiceException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@Service(value = "postService")
@Transactional
@NoArgsConstructor
public class PostServiceImpl extends BaseService<Post> implements PostService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post save(String text, LocalDateTime date, long userID, String picture) {
        try {
            return save(new Post(text, date, userRepository.getOne(userID), picture));
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(long postID) {
        try {
            postRepository.deleteById(postID);
        } catch (Exception e) {
            throw new ServiceException("Error deleting by ID " + postID);
        }
    }

    @Override
    public List<PostDTO> getPostsWithPagination(long userID, Pageable pageable) {
//        try {
            return postRepository.getPostsWithPagination(userID, pageable);
//        } catch (Exception e) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("Error getting Posts With Pagination by userID ")
//                    .append(userID)
//                    .append(" and Pagination")
//                    .append(pageable);
//
//            throw new ServiceException(sb.toString());
//        }
    }

    @Override
    public void likePostByUser(long postID, long userID) {
        try {
            postRepository.getOne(postID).getLikes().add(userRepository.getOne(userID));
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error liking Post by id ")
                    .append(postID)
                    .append(" and userID ")
                    .append(userID);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public void unlikePostByUser(long postID, long userID) {
        try {
            postRepository.getOne(postID).getLikes().remove(userRepository.getOne(userID));
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error unliking Post by id ")
                    .append(postID)
                    .append(" and userID ")
                    .append(userID);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public PostDTO getSinglePostDTO(long userID, long postID) {
        try {
            return postRepository.getSinglePostDTO(userID, postID);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting Single PostDTO userID ")
                    .append(userID)
                    .append(" and postID ")
                    .append(postID);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public long getNumberOfAllPosts() {
        try {
            return postRepository.getNumberOfAllPosts();
        } catch (Exception e) {
            throw new ServiceException("Error getting Number Of All Posts ");
        }
    }
}
