package com.eastnets.validation;

public interface ValidationStrategy<T> {
    public void validate(T object);
}
