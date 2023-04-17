package com.login.loginexpiration.service;

import com.login.loginexpiration.entity.User;
import com.login.loginexpiration.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername_ValidUsername_ReturnsUserDetails() {
        String username = "john.doe";
        User userEntity = new User();
        userEntity.setUsername(username);
        userEntity.setPassword("password");

        when(userRepository.findByUsername(username)).thenReturn(userEntity);

        UserDetails result = userDetailsService.loadUserByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testLoadUserByUsername_InvalidUsername_ThrowsUsernameNotFoundException() {
        String username = "nonexistent.user";

        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }
}
