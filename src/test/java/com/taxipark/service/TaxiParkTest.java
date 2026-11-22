package com.taxipark.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.taxipark.io.CarReader;
import com.taxipark.model.Car;
import com.taxipark.model.Hatchback;
import com.taxipark.model.Minivan;
import com.taxipark.model.Sedan;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for TaxiPark. The car source is mocked with Mockito.
 */
public class TaxiParkTest {

    private static final double DELTA = 1e-9;

    private final Car camry = new Sedan("Toyota", "Camry", 32000, 7.8,
            210, 493);
    private final Car clio = new Hatchback("Renault", "Clio", 17000, 5.0,
            180, 5);
    private final Car sienna = new Minivan("Toyota", "Sienna", 41000, 7.4,
            180, 8);
    private final Car cooper = new Hatchback("Mini", "Cooper", 29000, 6.2,
            235, 3);

    private CarReader reader;
    private TaxiPark park;

    @Before
    public void setUp() throws IOException {
        reader = mock(CarReader.class);
        when(reader.readCars())
                .thenReturn(Arrays.asList(camry, clio, sienna, cooper));
        park = new TaxiPark(reader);
    }

    @Test
    public void carsAreLoadedFromReaderOnce() throws IOException {
        verify(reader, times(1)).readCars();
        assertEquals(Arrays.asList(camry, clio, sienna, cooper),
                park.getCars());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void carsCannotBeModifiedOutside() {
        park.getCars().clear();
    }

    @Test(expected = IOException.class)
    public void readerErrorIsPassedOn() throws IOException {
        CarReader failingReader = mock(CarReader.class);
        when(failingReader.readCars()).thenThrow(new IOException("fail"));

        new TaxiPark(failingReader);
    }

    @Test(expected = NullPointerException.class)
    public void nullReaderIsRejected() throws IOException {
        new TaxiPark(null);
    }

    @Test
    public void totalCostIsSumOfPrices() {
        assertEquals(119000, park.calculateTotalCost(), DELTA);
    }

    @Test
    public void totalCostOfEmptyParkIsZero() throws IOException {
        TaxiPark empty = createPark(Collections.emptyList());

        assertEquals(0, empty.calculateTotalCost(), DELTA);
    }

    @Test
    public void sortsByFuelConsumptionAscending() {
        List<Car> sorted = park.sortByFuelConsumption();

        assertEquals(Arrays.asList(clio, cooper, sienna, camry), sorted);
        assertEquals(sorted, park.getCars());
    }

    @Test
    public void findsCarsInSpeedRange() {
        List<Car> found = park.findBySpeedRange(200, 220);

        assertEquals(Collections.singletonList(camry), found);
    }

    @Test
    public void speedRangeBoundsAreInclusive() {
        List<Car> found = park.findBySpeedRange(180, 210);

        assertEquals(Arrays.asList(camry, clio, sienna), found);
    }

    @Test
    public void rangeWithOneValueFindsExactSpeed() {
        assertEquals(Collections.singletonList(cooper),
                park.findBySpeedRange(235, 235));
    }

    @Test
    public void nothingFoundGivesEmptyList() {
        assertTrue(park.findBySpeedRange(300, 400).isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void minGreaterThanMaxIsRejected() {
        park.findBySpeedRange(220, 200);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeMinSpeedIsRejected() {
        park.findBySpeedRange(-1, 200);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeMaxSpeedIsRejected() {
        park.findBySpeedRange(0, -1);
    }

    private static TaxiPark createPark(List<Car> cars) throws IOException {
        CarReader carReader = mock(CarReader.class);
        when(carReader.readCars()).thenReturn(cars);
        return new TaxiPark(carReader);
    }
}
