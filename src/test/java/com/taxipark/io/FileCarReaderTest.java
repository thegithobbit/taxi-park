package com.taxipark.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.taxipark.model.Car;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Tests for FileCarReader.
 */
public class FileCarReaderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void readsCarsAndSkipsCommentsAndEmptyLines() throws IOException {
        Path file = createFile(
                "# comment",
                "SEDAN;Toyota;Camry;32000;7.8;210;493",
                "",
                "   ",
                "MINIVAN;VW;Touran;34000;7.1;200;7");

        List<Car> cars = new FileCarReader(file, new CarParser())
                .readCars();

        assertEquals(2, cars.size());
        assertEquals("Camry", cars.get(0).getModel());
        assertEquals("Touran", cars.get(1).getModel());
    }

    @Test
    public void emptyFileGivesEmptyList() throws IOException {
        Path file = createFile();

        assertTrue(new FileCarReader(file, new CarParser())
                .readCars().isEmpty());
    }

    @Test(expected = IOException.class)
    public void missingFileThrowsIoException() throws IOException {
        Path file = folder.getRoot().toPath().resolve("missing.csv");

        new FileCarReader(file, new CarParser()).readCars();
    }

    @Test
    public void invalidLineReportsLineNumber() throws IOException {
        Path file = createFile(
                "# comment",
                "SEDAN;Toyota;Camry;32000;7.8;210;493",
                "SEDAN;broken");
        try {
            new FileCarReader(file, new CarParser()).readCars();
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().startsWith("Line 3:"));
        }
    }

    @Test(expected = NullPointerException.class)
    public void nullFileIsRejected() {
        new FileCarReader(null, new CarParser());
    }

    @Test(expected = NullPointerException.class)
    public void nullParserIsRejected() {
        new FileCarReader(folder.getRoot().toPath(), null);
    }

    private Path createFile(String... lines) throws IOException {
        File file = folder.newFile();
        Files.write(file.toPath(), Arrays.asList(lines),
                StandardCharsets.UTF_8);
        return file.toPath();
    }
}
