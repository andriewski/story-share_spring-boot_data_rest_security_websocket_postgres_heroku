package com.storyshare.boot.controllers;

import com.storyshare.boot.repositories.dto.CommentDTO;
import com.storyshare.boot.services.CommentService;
import com.storyshare.boot.services.auth.MVCUser;
import com.storyshare.boot.controllers.wrappers.CommentWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CommentDTO>> getComments(
            @RequestParam("postID") long postID,
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit) {
        return new ResponseEntity<>(commentService.getCommentsInThePostWithOffsetAndLimit(
                postID, PageRequest.of(offset / limit, limit)), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createComment(@RequestBody CommentWrapper comment, Authentication auth) {
        if (auth != null && auth.isAuthenticated()
                && ((MVCUser) auth.getPrincipal()).getId().equals(comment.getUserID())) {
            commentService.save(comment.getUserID(), comment.getPostID(), comment.getText(), LocalDateTime.now());

            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/{commentID}")
    public ResponseEntity deleteComment(@PathVariable long commentID, @RequestParam Long userID, Authentication auth) {
        if (auth != null && auth.isAuthenticated() && ((MVCUser) auth.getPrincipal()).getId().equals(userID)) {
            commentService.delete(commentID);

            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/length", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getCommentsLength(@RequestParam("postID") long postID) {
        return new ResponseEntity<>(commentService.getNumberOfCommentsInThePost(postID), HttpStatus.OK);
    }
}