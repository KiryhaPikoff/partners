package com.mediasoft.partnersservice.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuthentication extends AbstractAuthenticationToken {

    private String token;

    public TokenAuthentication(String token) {
        super(null);
        this.token = token;
    }

    public TokenAuthentication(String token,
                               Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}