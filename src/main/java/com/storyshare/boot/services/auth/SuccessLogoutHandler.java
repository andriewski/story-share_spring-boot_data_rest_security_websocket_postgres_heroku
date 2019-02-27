package com.storyshare.boot.services.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessLogoutHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp,
                                Authentication auth) throws IOException, ServletException {
        req.getSession().invalidate();
        super.onLogoutSuccess(req, resp, auth);
    }
}
