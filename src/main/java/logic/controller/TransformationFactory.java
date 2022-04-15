package logic.controller;

import logic.model.bits.BitSequence;
import logic.model.transformation.Transformation;
import logic.model.tree.HuffmanTree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Map;

public class TransformationFactory {
    public Transformation createTransformation(File inputFile, File outputFile, long nanoTime,
                                               HuffmanTree huffmanTree) throws IOException {
        return createTransformation(inputFile, outputFile, null, nanoTime, huffmanTree, null);
    }

    public Transformation createTransformation(File inputFile, File outputFile, Float entropy, long nanoTime,
                                               HuffmanTree huffmanTree,
                                               Map<Byte, BitSequence> bitEncodingMap) throws IOException {
        return new Transformation(inputFile, outputFile, Files.size(inputFile.toPath()),
                Files.size(outputFile.toPath()), entropy, Duration.ofNanos(nanoTime), huffmanTree,
                bitEncodingMap);
    }
}
