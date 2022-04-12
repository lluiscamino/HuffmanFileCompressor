package logic.model.transformation;

import logic.model.bits.BitSequence;
import logic.model.tree.HuffmanTree;

import java.io.File;
import java.time.Duration;
import java.util.Map;

public record Transformation(File inputFile, File outputFile, long inputFileSizeBytes, long outputFileSizeBytes,
                             Duration duration, HuffmanTree huffmanTree, Map<Byte, BitSequence> bitEncodingMap) {
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