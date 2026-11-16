package com.taxipark.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for Hatchback.
 */
public class HatchbackTest {

    @Test
    public void fiveDoorHatchbackIsCreated() {
        Hatchback car = new Hatchback("VW", "Golf", 24000, 5.6, 210, 5);

        assertEquals(5, car.getDoorCount());
    }

    @Test
    public void threeDoorHatchbackIsCreated() {
        Hatchback car = new Hatchback("Mini", "Cooper", 29000, 6.2, 235, 3);

        assertEquals(3, car.getDoorCount());
    }

    @Test
    public void bodyTypeAndCapacity() {
        Hatchback car = new Hatchback("VW", "Golf", 24000, 5.6, 210, 5);

        assertEquals("Hatchback", car.getBodyType());
        assertEquals(4, car.getPassengerCapacity());
    }

    @Test
    public void toStringContainsDoors() {
        Hatchback car = new Hatchback("VW", "Golf", 24000, 5.6, 210, 5);

        assertTrue(car.toString().startsWith("Hatchback VW Golf"));
        assertTrue(car.toString().contains("doors: 5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void fourDoorsAreRejected() {
        new Hatchback("VW", "Golf", 24000, 5.6, 210, 4);
    }
}
