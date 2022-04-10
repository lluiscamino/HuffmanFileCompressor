package logic.controller;

import logic.decoder.Decoder;
import logic.encoder.Encoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Controller {
    private final Encoder encoder;
    private final Decoder decoder;
    private final InputStreamFactory inputStreamFactory;
    private final OutputStreamFactory outputStreamFactory;

    public Controller(Encoder encoder, Decoder decoder, InputStreamFactory inputStreamFactory,
                      OutputStreamFactory outputStreamFactory) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.inputStreamFactory = inputStreamFactory;
        this.outputStreamFactory = outputStreamFactory;
    }

    public void compressFile(File inputFile, File outputFile) throws IOException {
        InputStream inputStream1 = inputStreamFactory.createInputStream(inputFile),
                inputStream2 = inputStreamFactory.createInputStream(inputFile);
        OutputStream outputStream = outputStreamFactory.createOutputStream(outputFile);
        encoder.encode(inputStream1, inputStream2, outputStream);
    }

    public void decompressFile(File inputFile, File outputFile) throws IOException {
        InputStream inputStream = inputStreamFactory.createInputStream(inputFile);
        OutputStream outputStream = outputStreamFactory.createOutputStream(outputFile);
        decoder.decode(inputStream, outputStream);
    }
}
