package com.login.loginexpiration.config;

import com.login.loginexpiration.entity.User;
import com.login.loginexpiration.model.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        jwtTokenProvider = new JwtTokenProvider("secret", 60);
    }

    @Test
    public void testGenerateToken() {
        String token = jwtTokenProvider.generateToken(new UserPrincipal(new User()));

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
}
