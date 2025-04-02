package org.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.command.Command;
import org.example.exceptions.type.NotEnoughFuelException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class FuelSystem {
    private int fuelAmount;

    public FuelSystem(int initialFuel) {
        this.fuelAmount = initialFuel;
    }

    public boolean hasEnoughFuel(int requiredFuel) {
        return fuelAmount >= requiredFuel;
    }

    public void burnFuel(int fuelConsumed) throws NotEnoughFuelException {
        if (fuelAmount < fuelConsumed) {
            throw new NotEnoughFuelException(getClass().getSimpleName(), "Not enough fuel");
        }
        fuelAmount -= fuelConsumed;
    }
    public void setFuelAmount(int fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

}
