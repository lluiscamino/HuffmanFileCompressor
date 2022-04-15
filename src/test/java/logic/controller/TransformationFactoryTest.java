package logic.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransformationFactoryTest {
    private TransformationFactory transformationFactory;

    @BeforeEach
    void setUp() {
        transformationFactory = new TransformationFactory();
    }

    @Test
    void createTransformation() throws IOException {
        File inputFile = File.createTempFile("input", null),
                outputFile = File.createTempFile("output", null);
        assertNotNull(transformationFactory.createTransformation(inputFile, outputFile, 34, null));
        assertNotNull(transformationFactory.createTransformation(inputFile, outputFile, null, 34, null, null));
    }
}