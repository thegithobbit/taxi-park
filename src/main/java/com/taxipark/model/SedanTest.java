package com.taxipark.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for Sedan and common validation of Car.
 */
public class SedanTest {

    private static final double DELTA = 1e-9;

    @Test
    public void constructorSetsAllFields() {
        Sedan sedan = new Sedan("Toyota", "Camry", 32000, 7.8, 210, 493);

        assertEquals("Toyota", sedan.getBrand());
        assertEquals("Camry", sedan.getModel());
        assertEquals(32000, sedan.getPrice(), DELTA);
        assertEquals(7.8, sedan.getFuelConsumption(), DELTA);
        assertEquals(210, sedan.getMaxSpeed());
        assertEquals(493, sedan.getTrunkVolume());
    }

    @Test
    public void constructorTrimsBrandAndModel() {
        Sedan sedan = new Sedan(" Toyota ", " Camry ", 1, 1, 1, 1);

        assertEquals("Toyota", sedan.getBrand());
        assertEquals("Camry", sedan.getModel());
    }

    @Test
    public void bodyTypeIsSedan() {
        assertEquals("Sedan", createSedan().getBodyType());
    }

    @Test
    public void passengerCapacityIsFour() {
        assertEquals(4, createSedan().getPassengerCapacity());
    }

    @Test
    public void toStringContainsMainInfo() {
        String text = createSedan().toString();

        assertTrue(text.contains("Sedan Toyota Camry"));
        assertTrue(text.contains("price: $32000.00"));
        assertTrue(text.contains("fuel: 7.8 l/100km"));
        assertTrue(text.contains("max speed: 210 km/h"));
        assertTrue(text.contains("passengers: 4"));
        assertTrue(text.contains("trunk: 493 l"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullBrandIsRejected() {
        new Sedan(null, "Camry", 32000, 7.8, 210, 493);
    }

    @Test(expected = IllegalArgumentException.class)
    public void blankModelIsRejected() {
        new Sedan("Toyota", "  ", 32000, 7.8, 210, 493);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroPriceIsRejected() {
        new Sedan("Toyota", "Camry", 0, 7.8, 210, 493);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeFuelConsumptionIsRejected() {
        new Sedan("Toyota", "Camry", 32000, -1, 210, 493);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroMaxSpeedIsRejected() {
        new Sedan("Toyota", "Camry", 32000, 7.8, 0, 493);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroTrunkVolumeIsRejected() {
        new Sedan("Toyota", "Camry", 32000, 7.8, 210, 0);
    }

    private static Sedan createSedan() {
        return new Sedan("Toyota", "Camry", 32000, 7.8, 210, 493);
    }
}
