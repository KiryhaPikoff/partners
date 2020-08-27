package com.mediasoft.partnersservice.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.token")
public class SecurityConfigProperties {

    private String privateKey;

    private String publicKey;

    private String privateBegin;

    private String privateEnd;

    private String publicBegin;

    private String publicEnd;

    private Long expirationTime;

    private String prefix;

    private String header;

    private String roleUser;

    private String roleAdmin;

    private String filterMapping;
}