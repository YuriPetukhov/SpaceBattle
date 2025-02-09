package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Velocity {

    private int x;
    private int y;

    public void setX(int x) {
        if (x < 0) throw new IllegalArgumentException("X velocity cannot be negative");
        this.x = x;
    }

    public void setY(int y) {
        if (y < 0) throw new IllegalArgumentException("Y velocity cannot be negative");
        this.y = y;
    }
}

