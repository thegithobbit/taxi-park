package com.taxipark.model;

import java.util.Locale;

/**
 * Base class for all passenger cars of the taxi park.
 */
public abstract class Car {

    private final String brand;
    private final String model;
    private final double price;
    private final double fuelConsumption;
    private final int maxSpeed;

    protected Car(String brand, String model, double price,
            double fuelConsumption, int maxSpeed) {
        this.brand = requireText(brand, "Brand");
        this.model = requireText(model, "Model");
        this.price = requirePositive(price, "Price");
        this.fuelConsumption = requirePositive(fuelConsumption,
                "Fuel consumption");
        this.maxSpeed = requirePositive(maxSpeed, "Max speed");
    }

    protected static double requirePositive(double value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(
                    name + " must be positive: " + value);
        }
        return value;
    }

    protected static int requirePositive(int value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(
                    name + " must be positive: " + value);
        }
        return value;
    }

    private static String requireText(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be empty");
        }
        return value.trim();
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public abstract String getBodyType();

    /** Number of passengers without the driver. */
    public abstract int getPassengerCapacity();

    protected abstract String describeFeatures();

    @Override
    public String toString() {
        return String.format(Locale.US,
                "%s %s %s | price: $%.2f | fuel: %.1f l/100km"
                + " | max speed: %d km/h | passengers: %d | %s",
                getBodyType(), brand, model, price, fuelConsumption,
                maxSpeed, getPassengerCapacity(), describeFeatures());
    }
}
