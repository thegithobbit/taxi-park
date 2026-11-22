package com.taxipark.ui;

import com.taxipark.model.Car;
import com.taxipark.service.TaxiPark;

import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Minimal console menu for working with the taxi park.
 */
public class ConsoleMenu {

    private final TaxiPark park;
    private final Scanner in;
    private final PrintStream out;

    public ConsoleMenu(TaxiPark park, Scanner in, PrintStream out) {
        this.park = park;
        this.in = in;
        this.out = out;
    }

    /** Handles user choices until exit or the end of input. */
    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = readLine();
            if (choice == null) {
                break;
            }
            switch (choice) {
                case "1":
                    printCars(park.getCars());
                    break;
                case "2":
                    out.printf(Locale.US, "Total cost: $%.2f%n",
                            park.calculateTotalCost());
                    break;
                case "3":
                    printCars(park.sortByFuelConsumption());
                    break;
                case "4":
                    findBySpeedRange();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    out.println("Unknown option, try again.");
                    break;
            }
        }
    }

    private void printMenu() {
        out.println();
        out.println("1 - Show all cars");
        out.println("2 - Calculate total cost");
        out.println("3 - Sort by fuel consumption");
        out.println("4 - Find cars by speed range");
        out.println("0 - Exit");
        out.print("Your choice: ");
    }

    private void findBySpeedRange() {
        try {
            int minSpeed = readInt("Min speed (km/h): ");
            int maxSpeed = readInt("Max speed (km/h): ");
            List<Car> found = park.findBySpeedRange(minSpeed, maxSpeed);
            if (found.isEmpty()) {
                out.println("No cars found in this speed range.");
            } else {
                printCars(found);
            }
        } catch (NumberFormatException e) {
            out.println("Speed must be an integer number.");
        } catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
    }

    private void printCars(List<Car> cars) {
        if (cars.isEmpty()) {
            out.println("Taxi park is empty.");
            return;
        }
        for (int i = 0; i < cars.size(); i++) {
            out.println((i + 1) + ". " + cars.get(i));
        }
    }

    private int readInt(String prompt) {
        out.print(prompt);
        return Integer.parseInt(readLine());
    }

    private String readLine() {
        return in.hasNextLine() ? in.nextLine().trim() : null;
    }
}
