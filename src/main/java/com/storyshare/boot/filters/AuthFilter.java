package com.storyshare.boot.filters;

import com.storyshare.boot.services.UserService;
import com.storyshare.boot.services.auth.MVCUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class AuthFilter implements Filter {
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        if (auth.getPrincipal().equals("anonymousUser")) {
            resp.setHeader("authorization", "false");
        } else if ("banned".equals(userService.getUserStatus(((MVCUser) auth.getPrincipal()).getId()))) {
            resp.setStatus(403);
            new SecurityContextLogoutHandler().logout(req, resp, auth);

            return;
        }

        chain.doFilter(req, resp);
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> loggingFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter());
        registrationBean.addUrlPatterns(
                "posts", "comments/{commentID}", "messages/chat", "messages/list", "messages/history", "comments",
                "posts/{postID}/like", "posts/{postID}"
        );

        return registrationBean;
    }
}
