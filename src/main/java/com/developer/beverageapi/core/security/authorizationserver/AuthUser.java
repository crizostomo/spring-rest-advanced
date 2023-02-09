package com.developer.beverageapi.core.security.authorizationserver;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthUser extends User {

    private static final long serialVersionUID = 1L;

    private String fullName;
    private Long userId;

    public AuthUser(com.developer.beverageapi.domain.model.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);

        this.fullName = user.getName();
        this.userId = user.getId();
    }
}
