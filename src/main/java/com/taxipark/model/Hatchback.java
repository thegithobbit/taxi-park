package com.taxipark.model;

/**
 * Hatchback: a compact car with a rear door that opens upwards.
 */
public class Hatchback extends Car {

    private static final int PASSENGER_CAPACITY = 4;
    private static final int THREE_DOORS = 3;
    private static final int FIVE_DOORS = 5;

    private final int doorCount;

    public Hatchback(String brand, String model, double price,
            double fuelConsumption, int maxSpeed, int doorCount) {
        super(brand, model, price, fuelConsumption, maxSpeed);
        if (doorCount != THREE_DOORS && doorCount != FIVE_DOORS) {
            throw new IllegalArgumentException(
                    "Hatchback must have 3 or 5 doors: " + doorCount);
        }
        this.doorCount = doorCount;
    }

    public int getDoorCount() {
        return doorCount;
    }

    @Override
    public String getBodyType() {
        return "Hatchback";
    }

    @Override
    public int getPassengerCapacity() {
        return PASSENGER_CAPACITY;
    }

    @Override
    protected String describeFeatures() {
        return "doors: " + doorCount;
    }
}
