package com.taxipark;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Tests for Main.
 */
public class MainTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    private InputStream originalIn;
    private PrintStream originalOut;
    private PrintStream originalErr;

    @Before
    public void redirectStreams() {
        originalIn = System.in;
        originalOut = System.out;
        originalErr = System.err;
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(err, true, StandardCharsets.UTF_8));
    }

    @After
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void loadsCarsFromFileAndRunsMenu() throws IOException {
        File file = folder.newFile("cars.csv");
        Files.write(file.toPath(), Arrays.asList(
                "SEDAN;Toyota;Camry;32000;7.8;210;493"),
                StandardCharsets.UTF_8);
        setInput("2", "0");

        Main.main(new String[] {file.getPath()});

        assertTrue(out.toString(StandardCharsets.UTF_8)
                .contains("Total cost: $32000.00"));
    }

    @Test
    public void reportsMissingFile() {
        File missing = new File(folder.getRoot(), "missing.csv");
        setInput("0");

        Main.main(new String[] {missing.getPath()});

        assertTrue(err.toString(StandardCharsets.UTF_8)
                .contains("Cannot read file"));
    }

    @Test
    public void reportsInvalidFileData() throws IOException {
        File file = folder.newFile("bad.csv");
        Files.write(file.toPath(), Arrays.asList("SEDAN;broken"),
                StandardCharsets.UTF_8);
        setInput("0");

        Main.main(new String[] {file.getPath()});

        assertTrue(err.toString(StandardCharsets.UTF_8)
                .contains("Invalid data in file: Line 1"));
    }

    @Test
    public void usesDefaultFileWithoutArguments() {
        setInput("1", "0");

        Main.main(new String[0]);

        assertTrue(out.toString(StandardCharsets.UTF_8)
                .contains("1. Sedan Toyota Camry"));
    }

    private static void setInput(String... lines) {
        String text = String.join(System.lineSeparator(), lines);
        System.setIn(new ByteArrayInputStream(
                text.getBytes(StandardCharsets.UTF_8)));
    }
}
