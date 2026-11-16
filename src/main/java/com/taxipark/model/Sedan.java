package com.taxipark.model;

/**
 * Sedan: a car with a separate trunk compartment.
 */
public class Sedan extends Car {

    private static final int PASSENGER_CAPACITY = 4;

    private final int trunkVolume;

    public Sedan(String brand, String model, double price,
            double fuelConsumption, int maxSpeed, int trunkVolume) {
        super(brand, model, price, fuelConsumption, maxSpeed);
        this.trunkVolume = requirePositive(trunkVolume, "Trunk volume");
    }

    public int getTrunkVolume() {
        return trunkVolume;
    }

    @Override
    public String getBodyType() {
        return "Sedan";
    }

    @Override
    public int getPassengerCapacity() {
        return PASSENGER_CAPACITY;
    }

    @Override
    protected String describeFeatures() {
        return "trunk: " + trunkVolume + " l";
    }
}
