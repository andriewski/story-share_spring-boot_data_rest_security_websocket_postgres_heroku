package com.storyshare.boot.services.auth;

import com.storyshare.boot.pojos.User;
import com.storyshare.boot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service(value = "authService")
public class AuthenticationService implements UserDetailsService {
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);
        System.out.println("User: " + user);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new MVCUser(user.getId(), user.getEmail(), user.getPassword(),
                "active".equals(user.getStatus()), true, true, true,
                new ArrayList<GrantedAuthority>() {{
                    add(new SimpleGrantedAuthority(
                            "ROLE_" + user.getRole().toUpperCase()));
                }});
    }
}
