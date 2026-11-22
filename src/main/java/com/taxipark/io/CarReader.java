package com.taxipark.io;

import com.taxipark.model.Car;

import java.io.IOException;
import java.util.List;

/**
 * Source of cars used to fill the taxi park.
 */
public interface CarReader {

    List<Car> readCars() throws IOException;
}
