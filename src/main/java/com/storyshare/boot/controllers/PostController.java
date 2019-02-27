package com.storyshare.boot.controllers;

import com.storyshare.boot.repositories.dto.PostDTO;
import com.storyshare.boot.services.PostService;
import com.storyshare.boot.services.UserService;
import com.storyshare.boot.services.auth.MVCUser;
import com.storyshare.boot.controllers.wrappers.Like;
import com.storyshare.boot.controllers.wrappers.PostWrapper;
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
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PostDTO>> getPosts(
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit,
            @RequestParam("userID") long userID,
            Authentication auth) {
        if (auth != null && auth.isAuthenticated() && ((MVCUser) auth.getPrincipal()).getId() != userID) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(postService.getPostsWithPagination(userID,
                PageRequest.of(offset / limit, limit)), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity createPost(@RequestBody PostWrapper post, Authentication auth) {
        if (auth != null && auth.isAuthenticated()
                && ((MVCUser) auth.getPrincipal()).getId().equals(post.getUserID())) {
            postService.save(post.getText(), LocalDateTime.now(), post.getUserID(), post.getPicture());

            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/{postID}")
    public ResponseEntity deletePost(@PathVariable("postID") long postID, @RequestParam Long userID, Authentication auth) {
        if (auth != null && auth.isAuthenticated() && ((MVCUser) auth.getPrincipal()).getId().equals(userID)) {
            postService.delete(postID);

            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/length")
    public ResponseEntity<Long> getPostLength() {
        return new ResponseEntity<>(postService.getNumberOfAllPosts(), HttpStatus.OK);
    }

    @GetMapping(value = "/{postID}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PostDTO> getSinglePost(
            @PathVariable("postID") long postID,
            @RequestParam("userID") long userID,
            Authentication auth) {
        if (auth != null && auth.isAuthenticated() && ((MVCUser) auth.getPrincipal()).getId() != userID) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(postService.getSinglePostDTO(userID, postID), HttpStatus.OK);
    }

    @PatchMapping(value = "/{postID}/like", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity likeUnlikePost(
            @PathVariable("postID") long postID,
            @RequestBody Like like,
            Authentication auth) {
        if (auth != null && auth.isAuthenticated() && ((MVCUser) auth.getPrincipal()).getId()
                .equals(like.getUserID())) {
            if (like.getIsLiked()) {
                postService.likePostByUser(postID, like.getUserID());
            } else {
                postService.unlikePostByUser(postID, like.getUserID());
            }

            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
}