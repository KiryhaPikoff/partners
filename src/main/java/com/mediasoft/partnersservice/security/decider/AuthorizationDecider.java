package com.mediasoft.partnersservice.security.decider;

import com.mediasoft.partnersservice.security.authority.Role;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("authDecider")
public class AuthorizationDecider {

    public boolean canCreate(Authentication auth) {
        return auth.getAuthorities().contains(Role.ADMIN);
    }
}
