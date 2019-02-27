package com.storyshare.boot.services;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
