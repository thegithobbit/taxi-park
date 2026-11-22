package com.taxipark.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.taxipark.model.Car;
import com.taxipark.model.Hatchback;
import com.taxipark.model.Minivan;
import com.taxipark.model.Sedan;

import org.junit.Test;

/**
 * Tests for CarParser.
 */
public class CarParserTest {

    private static final double DELTA = 1e-9;

    private final CarParser parser = new CarParser();

    @Test
    public void parsesSedan() {
        Car car = parser.parse("SEDAN;Toyota;Camry;32000;7.8;210;493");

        assertTrue(car instanceof Sedan);
        assertEquals("Toyota", car.getBrand());
        assertEquals("Camry", car.getModel());
        assertEquals(32000, car.getPrice(), DELTA);
        assertEquals(7.8, car.getFuelConsumption(), DELTA);
        assertEquals(210, car.getMaxSpeed());
        assertEquals(493, ((Sedan) car).getTrunkVolume());
    }

    @Test
    public void parsesHatchback() {
        Car car = parser.parse("HATCHBACK;VW;Golf;24000;5.6;210;5");

        assertTrue(car instanceof Hatchback);
        assertEquals(5, ((Hatchback) car).getDoorCount());
    }

    @Test
    public void parsesMinivan() {
        Car car = parser.parse("MINIVAN;VW;Touran;34000;7.1;200;7");

        assertTrue(car instanceof Minivan);
        assertEquals(7, ((Minivan) car).getSeatCount());
    }

    @Test
    public void typeIsCaseInsensitiveAndSpacesAreIgnored() {
        Car car = parser.parse(" sedan ; Skoda ; Octavia ; 26000 ; 6.1"
                + " ; 225 ; 600 ");

        assertTrue(car instanceof Sedan);
        assertEquals("Skoda", car.getBrand());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullLineIsRejected() {
        parser.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongFieldCountIsRejected() {
        parser.parse("SEDAN;Toyota;Camry;32000");
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownTypeIsRejected() {
        parser.parse("TRUCK;Volvo;FH;90000;30;120;2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidNumberIsRejected() {
        parser.parse("SEDAN;Toyota;Camry;cheap;7.8;210;493");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidCarDataIsRejected() {
        parser.parse("SEDAN;Toyota;Camry;-5;7.8;210;493");
    }
}
