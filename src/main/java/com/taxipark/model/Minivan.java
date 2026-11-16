package com.taxipark.model;

/**
 * Minivan: a spacious car for a larger group of passengers.
 */
public class Minivan extends Car {

    private static final int MIN_SEAT_COUNT = 5;
    private static final int DRIVER_SEATS = 1;

    private final int seatCount;

    public Minivan(String brand, String model, double price,
            double fuelConsumption, int maxSpeed, int seatCount) {
        super(brand, model, price, fuelConsumption, maxSpeed);
        if (seatCount < MIN_SEAT_COUNT) {
            throw new IllegalArgumentException("Minivan must have at least "
                    + MIN_SEAT_COUNT + " seats: " + seatCount);
        }
        this.seatCount = seatCount;
    }

    public int getSeatCount() {
        return seatCount;
    }

    @Override
    public String getBodyType() {
        return "Minivan";
    }

    @Override
    public int getPassengerCapacity() {
        return seatCount - DRIVER_SEATS;
    }

    @Override
    protected String describeFeatures() {
        return "seats: " + seatCount;
    }
}
