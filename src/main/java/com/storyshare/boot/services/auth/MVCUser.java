package com.storyshare.boot.services.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class MVCUser extends User implements Principal {
    private Long id;

    public MVCUser(Long id, String username, String password, boolean enabled, boolean accountNonExpired,
                   boolean credentialsNonExpired, boolean accountNonLocked,
                   Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    @Override
    public String getName() {
        return getUsername();
    }
}
