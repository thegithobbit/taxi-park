package com.taxipark.service;

import com.taxipark.io.CarReader;
import com.taxipark.model.Car;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Taxi park: a collection of cars with operations on it.
 */
public class TaxiPark {

    private final List<Car> cars;

    public TaxiPark(CarReader reader) throws IOException {
        Objects.requireNonNull(reader, "Reader must not be null");
        this.cars = new ArrayList<>(reader.readCars());
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

    public double calculateTotalCost() {
        double total = 0;
        for (Car car : cars) {
            total += car.getPrice();
        }
        return total;
    }

    /** Sorts cars from the lowest to the highest fuel consumption. */
    public List<Car> sortByFuelConsumption() {
        cars.sort(Comparator.comparingDouble(Car::getFuelConsumption));
        return getCars();
    }

    /** Finds cars with max speed in the range, both bounds inclusive. */
    public List<Car> findBySpeedRange(int minSpeed, int maxSpeed) {
        if (minSpeed < 0 || maxSpeed < 0) {
            throw new IllegalArgumentException(
                    "Speed must not be negative");
        }
        if (minSpeed > maxSpeed) {
            throw new IllegalArgumentException(
                    "Min speed must not be greater than max speed");
        }
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getMaxSpeed() >= minSpeed
                    && car.getMaxSpeed() <= maxSpeed) {
                result.add(car);
            }
        }
        return result;
    }
}
