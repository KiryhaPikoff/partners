package com.mediasoft.partnersservice.security.provider;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mediasoft.partnersservice.security.authentication.TokenAuthentication;
import com.mediasoft.partnersservice.security.authority.Role;
import com.mediasoft.partnersservice.security.properties.SecurityConfigProperties;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final SecurityConfigProperties securityConfigProperties;

    private RSAPrivateKey privateKey;

    private RSAPublicKey publicKey;

    @Autowired
    public TokenAuthenticationProvider(SecurityConfigProperties securityConfigProperties) {
        this.securityConfigProperties = securityConfigProperties;
        this.createPrivateKey();
        this.createPublicKey();
    }

    @Override
    @SneakyThrows
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var token = authentication.getPrincipal().toString();
        Role role = this.getRole(token);
        List<GrantedAuthority> authorities = Objects.isNull(role) ? Collections.emptyList() : List.of(role);
        TokenAuthentication validatedAuthentication =
                new TokenAuthentication(authentication.getPrincipal().toString(), authorities);
        validatedAuthentication.setAuthenticated(true);
        return validatedAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(TokenAuthentication.class);
    }

    private Role getRole(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.RSA256(this.publicKey, this.privateKey))
                .build()
                .verify(token);

        var subject = decodedJWT.getSubject();
        Role role = null;
        if (subject.equals(securityConfigProperties.getRoleAdmin())) {
            role = Role.ADMIN;
        }
        if (subject.equals(securityConfigProperties.getRoleUser())) {
            role = Role.USER;
        }
        return role;
    }

    @SneakyThrows
    private void createPrivateKey() {
        String privateKeyContent = securityConfigProperties.getPrivateKey();

        privateKeyContent = privateKeyContent
                .replaceAll("\\n", "")
                .replace(securityConfigProperties.getPrivateBegin(), "")
                .replace(securityConfigProperties.getPrivateEnd(), "");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        this.privateKey = (RSAPrivateKey) kf.generatePrivate(keySpecPKCS8);
    }

    @SneakyThrows
    private void createPublicKey() {
        String publicKeyContent = securityConfigProperties.getPublicKey();

        publicKeyContent = publicKeyContent
                .replaceAll("\\n", "")
                .replace(securityConfigProperties.getPublicBegin(), "")
                .replace(securityConfigProperties.getPublicEnd(), "");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        this.publicKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
    }
}