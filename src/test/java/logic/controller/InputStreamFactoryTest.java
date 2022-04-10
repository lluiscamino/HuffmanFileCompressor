package logic.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputStreamFactoryTest {
    private static final File NON_EXISTENT_FILE = new File("non_existent_input");

    private InputStreamFactory inputStreamFactory;

    @BeforeEach
    void setUp() {
        NON_EXISTENT_FILE.delete();
        inputStreamFactory = new InputStreamFactory();
    }

    @Test
    void createInputStream() throws IOException {
        assertNotNull(inputStreamFactory.createInputStream(File.createTempFile("temp", null)));
        assertThrows(FileNotFoundException.class, () -> inputStreamFactory.createInputStream(NON_EXISTENT_FILE));
    }
}