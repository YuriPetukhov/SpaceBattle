package org.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.exceptions.type.InvalidDenominatorException;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
public class Angle {
    private int d;
    private final int n;

    public Angle(int d, int n) throws InvalidDenominatorException {
        if (n <= 0) {
            throw new InvalidDenominatorException(getClass().getSimpleName(), "Denominator must be positive.");
        }
        this.d = ((d % n) + n) % n;
        this.n = n;
    }

    public void add(Angle other) throws InvalidDenominatorException {
        if (this.n != other.n) {
            throw new InvalidDenominatorException(getClass().getSimpleName(), "Angles must have the same denominator.");
        }
        this.d = (this.d + other.d) % this.n;
    }

    @Override
    public String toString() {
        return "Angle(" + d + "/" + n + ")";
    }
}
