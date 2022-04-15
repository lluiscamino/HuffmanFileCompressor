package logic.controller;

import logic.decoder.Decoder;
import logic.encoder.Encoder;
import logic.model.bits.BitSequence;
import logic.model.transformation.Transformation;
import logic.model.tree.HuffmanTree;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class ApplicationController implements Controller {
    private static final String COMPRESSED_FILE_EXTENSION = ".huff";
    private static final String DECOMPRESSED_FILE_EXTENSION = ".huff.decomp";

    private final Encoder encoder;
    private final Decoder decoder;
    private final FileFactory fileFactory;
    private final InputStreamFactory inputStreamFactory;
    private final OutputStreamFactory outputStreamFactory;
    private final TransformationFactory transformationFactory;

    private File selectedFile = null;

    public ApplicationController(Encoder encoder, Decoder decoder, FileFactory fileFactory,
                                 InputStreamFactory inputStreamFactory,
                                 OutputStreamFactory outputStreamFactory,
                                 TransformationFactory transformationFactory) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.fileFactory = fileFactory;
        this.inputStreamFactory = inputStreamFactory;
        this.outputStreamFactory = outputStreamFactory;
        this.transformationFactory = transformationFactory;
    }

    @Override
    public String getCompressedFileExtension() {
        return COMPRESSED_FILE_EXTENSION;
    }

    @Override
    public void selectFile(File file) {
        selectedFile = file;
    }

    @Override
    public Transformation compressFile() throws IOException {
        if (selectedFile == null) {
            throw new IllegalStateException("No file has been selected");
        }
        File outputFile = fileFactory.createFile(selectedFile.getAbsolutePath() + COMPRESSED_FILE_EXTENSION);
        return compressFile(selectedFile, outputFile);
    }

    @Override
    public Transformation compressFile(File inputFile, File outputFile) throws IOException {
        long startTime = System.nanoTime();
        InputStream inputStream1 = inputStreamFactory.createInputStream(inputFile),
                inputStream2 = inputStreamFactory.createInputStream(inputFile);
        OutputStream outputStream = outputStreamFactory.createOutputStream(outputFile);
        encoder.encode(inputStream1, inputStream2, outputStream);
        HuffmanTree huffmanTree = encoder.getHuffmanTree();
        Map<Byte, BitSequence> bitEncodingMap = encoder.getBitEncodingMap();
        return transformationFactory.createTransformation(inputFile, outputFile, encoder.getEntropy(),
                System.nanoTime() - startTime, huffmanTree, bitEncodingMap);
    }

    @Override
    public Transformation decompressFile() throws IOException {
        if (selectedFile == null) {
            throw new IllegalStateException("No file has been selected");
        }
        String inputPath = selectedFile.getAbsolutePath();
        String outputPath = inputPath.contains(COMPRESSED_FILE_EXTENSION) ?
                inputPath.replace(COMPRESSED_FILE_EXTENSION, "") :
                inputPath + DECOMPRESSED_FILE_EXTENSION;
        File outputFile = fileFactory.createFile(outputPath);
        return decompressFile(selectedFile, outputFile);
    }

    @Override
    public Transformation decompressFile(File inputFile, File outputFile) throws IOException {
        long startTime = System.nanoTime();
        InputStream inputStream = inputStreamFactory.createInputStream(inputFile);
        OutputStream outputStream = outputStreamFactory.createOutputStream(outputFile);
        decoder.decode(inputStream, outputStream);
        HuffmanTree huffmanTree = decoder.getHuffmanTree();
        return transformationFactory.createTransformation(inputFile, outputFile, System.nanoTime() - startTime,
                huffmanTree);
    }
}
