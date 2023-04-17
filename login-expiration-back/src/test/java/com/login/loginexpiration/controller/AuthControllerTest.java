package com.login.loginexpiration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.login.loginexpiration.model.AuthenticationResponse;
import com.login.loginexpiration.model.LoginRequest;
import com.login.loginexpiration.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationService authService;

    @BeforeEach
    public void setUp() {
        authService = mock(AuthenticationService.class);
        mockMvc = standaloneSetup(new AuthController(authService)).build();
    }

    @Test
    public void testAuthenticate_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setToken("jwt-token");

        when(authService.authenticateUser(eq(loginRequest))).thenReturn(authResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(authService, times(1)).authenticateUser(eq(loginRequest));
    }
}
