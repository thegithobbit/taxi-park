package com.taxipark.io;

import com.taxipark.model.Car;
import com.taxipark.model.Hatchback;
import com.taxipark.model.Minivan;
import com.taxipark.model.Sedan;

/**
 * Converts a text line into a car.
 * Format: TYPE;brand;model;price;fuel;maxSpeed;extra
 * (extra: trunk volume, door count or seat count).
 */
public class CarParser {

    private static final String SEPARATOR = ";";
    private static final int FIELD_COUNT = 7;

    public Car parse(String line) {
        if (line == null) {
            throw new IllegalArgumentException("Line must not be null");
        }
        String[] fields = line.split(SEPARATOR);
        if (fields.length != FIELD_COUNT) {
            throw new IllegalArgumentException("Expected " + FIELD_COUNT
                    + " fields but found " + fields.length + ": " + line);
        }
        String type = fields[0].trim().toUpperCase();
        String brand = fields[1].trim();
        String model = fields[2].trim();
        try {
            double price = Double.parseDouble(fields[3].trim());
            double fuel = Double.parseDouble(fields[4].trim());
            int maxSpeed = Integer.parseInt(fields[5].trim());
            int extra = Integer.parseInt(fields[6].trim());
            switch (type) {
                case "SEDAN":
                    return new Sedan(brand, model, price, fuel, maxSpeed,
                            extra);
                case "HATCHBACK":
                    return new Hatchback(brand, model, price, fuel,
                            maxSpeed, extra);
                case "MINIVAN":
                    return new Minivan(brand, model, price, fuel, maxSpeed,
                            extra);
                default:
                    throw new IllegalArgumentException(
                            "Unknown car type: " + type);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid number in line: " + line, e);
        }
    }
}
