package logic.controller;

import logic.model.transformation.Transformation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class TransformationFactory {
    public Transformation createTransformation(File inputFile, File outputFile, long nanoTime) throws IOException {
        return new Transformation(inputFile, outputFile, Files.size(inputFile.toPath()),
                Files.size(outputFile.toPath()), Duration.ofNanos(nanoTime));
    }
}
