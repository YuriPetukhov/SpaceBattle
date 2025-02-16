package org.example.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.command.Command;
import org.example.exceptions.type.NotEnoughFuelException;

@Data
@RequiredArgsConstructor
public class FuelSystem implements Command {
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

    @Override
    public void execute() throws Exception {

    }
}
