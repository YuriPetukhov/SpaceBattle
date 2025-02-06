package org.example.entity;

import lombok.Data;

@Data
public class Angle {
    private int d;
    private final int n;

    public Angle(int d, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Denominator must be positive.");
        }
        this.d = ((d % n) + n) % n;
        this.n = n;
    }

    public void add(Angle other) {
        if (this.n != other.n) {
            throw new IllegalArgumentException("Angles must have the same denominator.");
        }
        this.d = (this.d + other.d) % this.n;
    }

    @Override
    public String toString() {
        return "Angle(" + d + "/" + n + ")";
    }
}
