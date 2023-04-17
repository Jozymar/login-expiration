package com.login.loginexpiration.exception;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtExpiredTokenException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String mensagem;

    public JwtExpiredTokenException(String mensagem, ExpiredJwtException e) {
        this.mensagem = mensagem;
    }

    @Override
    public String getMessage() {
        return mensagem;
    }
}
