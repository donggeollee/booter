package com.boot.meal.security.exception;

import com.boot.meal.common.util.Header;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerManager {

    @ExceptionHandler(RuntimeException.class)
    protected Header handleRuntimeException(RuntimeException e) {
        log.info("handleRuntimeException", e);
        return Header.ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler(LoginFailedException.class)
    protected Header handleLoginFailedException(LoginFailedException e) {
        log.info("handleLoginFailedException", e);
        return Header.ERROR(401, HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected Header handleBadCredentialsException(BadCredentialsException e) {
        log.info("handleBadCredentialsException", e);
        return Header.ERROR(401, HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected Header handleAccessDeniedException(AccessDeniedException e) {
        log.info("handleAccessDeniedException", e);
        return Header.ERROR(401, HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    protected Header handleInsufficientAuthenticationException(InsufficientAuthenticationException e) {
        log.info("handleInsufficientAuthenticationException : {}", e.getMessage());
        return Header.ERROR(401, HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }
}