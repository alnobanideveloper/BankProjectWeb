package com.eastnets.validation;
import java.util.List;

public class Validator<T> {
    private final List<ValidationStrategy<T>> strategies;

    public Validator(List<ValidationStrategy<T>> strategies) {
        this.strategies = strategies;
    }

    public void validate(T object) {
        for (ValidationStrategy<T> strategy : strategies) {
            strategy.validate(object);
        }
    }
}


