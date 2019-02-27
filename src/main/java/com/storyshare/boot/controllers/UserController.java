package com.storyshare.boot.controllers;

import com.storyshare.boot.pojos.User;
import com.storyshare.boot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()) != null) {
            return new ResponseEntity(HttpStatus.RESET_CONTENT);
        }

        user.setRole("user");
        user.setStatus("active");
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{userID}/role", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_BOSS")
    public ResponseEntity changeUserRole(
            @PathVariable("userID") long userID,
            @RequestBody String role,
            HttpSession session) {
        if ("user".equals(role)) {
            session.setAttribute(String.valueOf(userID), userService.assignAdmin(userID));

            return new ResponseEntity(HttpStatus.OK);
        } else if ("admin".equals(role)) {
            session.setAttribute(String.valueOf(userID), userService.assignUser(userID));

            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_BOSS', 'ROLE_ADMIN')")
    @PatchMapping(value = "/{userID}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity changeUserStatus(
            @PathVariable("userID") long userID,
            @RequestBody String status,
            HttpSession session) {
        User user = (User) session.getAttribute(String.valueOf(userID));

        if (user == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        if ("active".equals(status)) {
            session.setAttribute(String.valueOf(user.getId()), userService.banUser(user));

            return new ResponseEntity(HttpStatus.OK);
        } else if ("banned".equals(status)) {
            session.setAttribute(String.valueOf(user.getId()), userService.unbanUser(user));

            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/{userID}/avatar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserAvatar(@PathVariable("userID") long userID) {
        return new ResponseEntity<>(userService.getUserAvatar(userID), HttpStatus.OK);
    }

    @GetMapping(value = "/{userID}/name", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getUserName(@PathVariable("userID") long userID) {
        return new ResponseEntity<>(userService.getUserName(userID), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_BOSS', 'ROLE_ADMIN')")
    @GetMapping(value = "/{userID}/role,status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserRoleAndStatus(@PathVariable("userID") long userID, HttpSession session) {
        User user = userService.get(userID);
        session.setAttribute(String.valueOf(userID), user);

        return new ResponseEntity<>(userService.getUserRoleAndStatus(userID).toString(), HttpStatus.OK);
    }
}