package com.storyshare.boot.services.auth;

import com.google.gson.Gson;
import com.storyshare.boot.pojos.User;
import com.storyshare.boot.repositories.dto.UserDTO;
import com.storyshare.boot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp,
                                        Authentication auth) throws IOException {
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        User user = userService.getUserByEmail(email);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter pw = resp.getWriter()) {
            pw.write(new Gson().toJson(new UserDTO(user.getId(), user.getName(), user.getAvatar(),
                    user.getRole())));
        }
    }
}
