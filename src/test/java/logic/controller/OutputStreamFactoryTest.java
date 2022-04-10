package logic.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OutputStreamFactoryTest {
    private OutputStreamFactory outputStreamFactory;

    @BeforeEach
    void setUp() {
        outputStreamFactory = new OutputStreamFactory();
    }

    @Test
    void createOutputStream() throws IOException {
        assertNotNull(outputStreamFactory.createOutputStream(File.createTempFile("temp", null)));
    }
}