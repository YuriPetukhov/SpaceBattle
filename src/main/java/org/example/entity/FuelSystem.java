package org.example.entity;

import lombok.Data;
import lombok.Getter;
import org.example.command.Command;
import org.example.exceptions.type.NotEnoughFuelException;

@Getter
@Data
public class FuelSystem {
    private int fuelAmount;

    public FuelSystem(int initialFuel) {
        this.fuelAmount = initialFuel;
    }

    public boolean hasEnoughFuel(int requiredFuel) {
        return fuelAmount >= requiredFuel;
    }

    public void burnFuel(int fuelConsumed, Command command) throws NotEnoughFuelException {
        if (fuelAmount < fuelConsumed) {
            throw new NotEnoughFuelException(command, new Exception("Not enough fuel to perform this action."));
        }
        fuelAmount -= fuelConsumed;
    }
    public void setFuelAmount(int fuelAmount) {
        this.fuelAmount = fuelAmount;
    }
}
