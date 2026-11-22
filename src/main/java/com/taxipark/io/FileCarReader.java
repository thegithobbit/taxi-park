package com.taxipark.io;

import com.taxipark.model.Car;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Reads cars from a text file, one car per line.
 * Empty lines and lines starting with # are ignored.
 */
public class FileCarReader implements CarReader {

    private static final String COMMENT_PREFIX = "#";

    private final Path file;
    private final CarParser parser;

    public FileCarReader(Path file, CarParser parser) {
        this.file = Objects.requireNonNull(file, "File must not be null");
        this.parser = Objects.requireNonNull(parser,
                "Parser must not be null");
    }

    @Override
    public List<Car> readCars() throws IOException {
        List<String> lines = Files.readAllLines(file,
                StandardCharsets.UTF_8);
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty() || line.startsWith(COMMENT_PREFIX)) {
                continue;
            }
            try {
                cars.add(parser.parse(line));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Line " + (i + 1) + ": " + e.getMessage(), e);
            }
        }
        return cars;
    }
}
