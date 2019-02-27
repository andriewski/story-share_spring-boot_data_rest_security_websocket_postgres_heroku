package com.storyshare.boot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleGlobalException(HttpServletRequest req, Exception e) {
        log.error("requestURI: " + req.getRequestURI(), e);

        return "error.html";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public void handleSecurityException(HttpServletRequest req, Exception e) {
        log.warn("requestURI: " + req.getRequestURI(), e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public void handleServiceException(HttpServletRequest req, Exception e) {
        log.warn("requestURI: " + req.getRequestURI(), e);
    }

    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public void handleOptimisticException(HttpServletRequest req, Exception e) {
        log.info("requestURI: " + req.getRequestURI(), e);
    }
}
