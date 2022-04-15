package logic.model.transformation;

import logic.model.bits.BitSequence;
import logic.model.tree.HuffmanTree;

import java.io.File;
import java.time.Duration;
import java.util.Map;

public record Transformation(File inputFile, File outputFile, long inputFileSizeBytes, long outputFileSizeBytes,
                             Float entropy, Duration duration, HuffmanTree huffmanTree,
                             Map<Byte, BitSequence> bitEncodingMap) {
    public float fileSizeRatio() {
        return (float) inputFileSizeBytes / outputFileSizeBytes;
    }

    public long theoreticalExpectedFileSize() {
        return (long) (entropy * inputFileSizeBytes) / 8;
    }

    public String humanReadableDuration() {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }
}