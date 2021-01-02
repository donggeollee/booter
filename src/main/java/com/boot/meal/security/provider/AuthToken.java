package com.boot.meal.security.provider;

public interface AuthToken<T> {
    boolean validate();
    T getData();
}