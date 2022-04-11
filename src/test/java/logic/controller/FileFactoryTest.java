package logic.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileFactoryTest {
    private FileFactory fileFactory;

    @BeforeEach
    void setUp() {
        fileFactory = new FileFactory();
    }

    @Test
    void createFile() {
        File file = fileFactory.createFile("file.txt");
        assertNotNull(file);
    }
}