package logic.model.transformation;

import java.io.File;
import java.time.Duration;

public record Transformation(File inputFile, File outputFile, long inputFileSizeBytes, long outputFileSizeBytes,
                             Duration duration) {
    public float fileSizeRatio() {
        return (float) inputFileSizeBytes / outputFileSizeBytes;
    }

    public String humanReadableDuration() {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }
}