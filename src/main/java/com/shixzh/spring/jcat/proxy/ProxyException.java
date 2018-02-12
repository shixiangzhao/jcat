package com.shixzh.spring.jcat.proxy;

public class ProxyException extends RuntimeException {

    ProxyException(String message, Throwable cause) {
        super(message, cause);
    }

    ProxyException(String message) {
        super(message);
    }
}
