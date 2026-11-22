package com.taxipark;

import com.taxipark.io.CarParser;
import com.taxipark.io.FileCarReader;
import com.taxipark.service.TaxiPark;
import com.taxipark.ui.ConsoleMenu;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Entry point of the taxi park application.
 */
public final class Main {

    private static final String DEFAULT_CARS_FILE = "cars.csv";

    private Main() {
    }

    public static void main(String[] args) {
        Path file = Paths.get(args.length > 0 ? args[0] : DEFAULT_CARS_FILE);
        try {
            TaxiPark park = new TaxiPark(
                    new FileCarReader(file, new CarParser()));
            new ConsoleMenu(park, new Scanner(System.in), System.out).run();
        } catch (IOException e) {
            System.err.println("Cannot read file: " + file);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid data in file: " + e.getMessage());
        }
    }
}
