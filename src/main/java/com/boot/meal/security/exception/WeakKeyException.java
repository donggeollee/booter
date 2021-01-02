package com.boot.meal.security.exception;

import java.security.InvalidKeyException;

public class WeakKeyException extends InvalidKeyException {

    public WeakKeyException(String message) {
        super(message);
    }
}
