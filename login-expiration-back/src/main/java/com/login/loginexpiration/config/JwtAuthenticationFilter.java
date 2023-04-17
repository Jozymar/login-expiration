package com.login.loginexpiration.config;

import com.login.loginexpiration.exception.JwtExpiredTokenException;
import com.login.loginexpiration.model.UserPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(new AntPathRequestMatcher("/api/**"));
        setAuthenticationManager(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    public JwtAuthenticationFilter() {
        super("/api/**");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            String username = Jwts.parser()
                    .setSigningKey("your-secret-key")
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            UserPrincipal userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(username);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredTokenException("JWT token expirado", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        UserPrincipal userDetails = (UserPrincipal) authResult.getPrincipal();
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(SignatureAlgorithm.HS512, "your-secret-key")
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }
}
