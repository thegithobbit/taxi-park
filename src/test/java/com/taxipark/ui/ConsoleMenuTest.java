package com.taxipark.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.taxipark.io.CarReader;
import com.taxipark.model.Car;
import com.taxipark.model.Hatchback;
import com.taxipark.model.Sedan;
import com.taxipark.service.TaxiPark;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

/**
 * Tests for ConsoleMenu.
 */
public class ConsoleMenuTest {

    private final Car camry = new Sedan("Toyota", "Camry", 32000, 7.8,
            210, 493);
    private final Car clio = new Hatchback("Renault", "Clio", 17000, 5.0,
            180, 5);

    @Test
    public void showsAllCars() throws IOException {
        String output = run(Arrays.asList(camry, clio), "1", "0");

        assertTrue(output.contains("1. Sedan Toyota Camry"));
        assertTrue(output.contains("2. Hatchback Renault Clio"));
    }

    @Test
    public void showsMessageForEmptyPark() throws IOException {
        String output = run(Collections.emptyList(), "1", "0");

        assertTrue(output.contains("Taxi park is empty."));
    }

    @Test
    public void showsTotalCost() throws IOException {
        String output = run(Arrays.asList(camry, clio), "2", "0");

        assertTrue(output.contains("Total cost: $49000.00"));
    }

    @Test
    public void showsCarsSortedByFuel() throws IOException {
        String output = run(Arrays.asList(camry, clio), "3", "0");

        assertTrue(output.contains("1. Hatchback Renault Clio"));
        assertTrue(output.contains("2. Sedan Toyota Camry"));
    }

    @Test
    public void findsCarsBySpeedRange() throws IOException {
        String output = run(Arrays.asList(camry, clio),
                "4", "200", "220", "0");

        assertTrue(output.contains("1. Sedan Toyota Camry"));
        assertFalse(output.contains("Renault Clio"));
    }

    @Test
    public void reportsWhenNothingFound() throws IOException {
        String output = run(Arrays.asList(camry, clio),
                "4", "300", "400", "0");

        assertTrue(output.contains("No cars found in this speed range."));
    }

    @Test
    public void reportsNonNumericSpeed() throws IOException {
        String output = run(Arrays.asList(camry, clio), "4", "fast", "0");

        assertTrue(output.contains("Speed must be an integer number."));
    }

    @Test
    public void reportsInvalidRange() throws IOException {
        String output = run(Arrays.asList(camry, clio),
                "4", "220", "200", "0");

        assertTrue(output.contains(
                "Min speed must not be greater than max speed"));
    }

    @Test
    public void reportsUnknownOption() throws IOException {
        String output = run(Arrays.asList(camry, clio), "9", "0");

        assertTrue(output.contains("Unknown option, try again."));
    }

    @Test
    public void stopsWhenInputEnds() throws IOException {
        String output = run(Arrays.asList(camry, clio), "2");

        assertTrue(output.contains("Total cost: $49000.00"));
    }

    private static String run(List<Car> cars, String... inputLines)
            throws IOException {
        CarReader reader = mock(CarReader.class);
        when(reader.readCars()).thenReturn(cars);
        TaxiPark park = new TaxiPark(reader);

        String input = String.join(System.lineSeparator(), inputLines);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer, true,
                StandardCharsets.UTF_8);

        new ConsoleMenu(park, new Scanner(input), out).run();
        return buffer.toString(StandardCharsets.UTF_8);
    }
}
