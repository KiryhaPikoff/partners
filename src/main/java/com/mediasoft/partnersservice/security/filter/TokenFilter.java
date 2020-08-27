package com.mediasoft.partnersservice.security.filter;

import com.mediasoft.partnersservice.security.authentication.TokenAuthentication;
import com.mediasoft.partnersservice.security.properties.SecurityConfigProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Getter
@Setter
public class TokenFilter extends AbstractAuthenticationProcessingFilter {

    private final SecurityConfigProperties securityConfigProperties;

    public TokenFilter(String defaultFilterProcessesUrl,
                       AuthenticationManager manager,
                       SecurityConfigProperties securityConfigProperties) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(manager);
        this.securityConfigProperties = securityConfigProperties;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader(securityConfigProperties.getHeader());
        if (Objects.isNull(token)) {
            throw new UsernameNotFoundException("Token is absent.");
        }
        token = token.replaceAll(securityConfigProperties.getPrefix(), "");
        Authentication auth = new TokenAuthentication(token);
        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}