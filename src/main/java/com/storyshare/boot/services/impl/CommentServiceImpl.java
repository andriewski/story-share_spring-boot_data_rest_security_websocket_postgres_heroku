package com.storyshare.boot.services.impl;

import com.storyshare.boot.repositories.dto.CommentDTO;
import com.storyshare.boot.pojos.Comment;
import com.storyshare.boot.repositories.CommentRepository;
import com.storyshare.boot.repositories.PostRepository;
import com.storyshare.boot.repositories.UserRepository;
import com.storyshare.boot.services.CommentService;
import com.storyshare.boot.services.ServiceException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@Service(value = "commentService")
@Transactional
@NoArgsConstructor
public class CommentServiceImpl extends BaseService<Comment> implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment save(long userID, long postID, String text, LocalDateTime date) {
        try {
            return save(new Comment(userRepository.getOne(userID), postRepository.getOne(postID), text, date));
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(long commentID) {
        try {
            commentRepository.deleteById(commentID);
        } catch (Exception e) {
            throw new ServiceException("Error deleting by ID " + commentID);
        }
    }

    @Override
    public List<CommentDTO> getCommentsInThePostWithOffsetAndLimit(long postID, Pageable pageable) {
        try {
            return commentRepository.getCommentsInThePostWithOffsetAndLimit(postID, pageable);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting Comments In The Post With Offset And Limit by ID ")
                    .append(postID)
                    .append(" and Pagination")
                    .append(pageable);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public long getNumberOfCommentsInThePost(long postID) {
        try {
            return commentRepository.getNumberOfCommentsInThePost(postID);
        } catch (Exception e) {
            throw new ServiceException("Error getting Number Of Comments In The Post by id " + postID);
        }
    }
}
