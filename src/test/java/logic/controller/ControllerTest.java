package logic.controller;

import logic.decoder.Decoder;
import logic.encoder.Encoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ControllerTest {
    private Encoder encoder;
    private Decoder decoder;
    private InputStreamFactory inputStreamFactory;
    private OutputStreamFactory outputStreamFactory;
    private Controller controller;

    @BeforeEach
    void setUp() {
        encoder = mock(Encoder.class);
        decoder = mock(Decoder.class);
        inputStreamFactory = mock(InputStreamFactory.class);
        outputStreamFactory = mock(OutputStreamFactory.class);
        controller = new Controller(encoder, decoder, inputStreamFactory, outputStreamFactory);
    }

    @Test
    void compressFile() throws IOException {
        File inputFile = mock(File.class), outputFile = mock(File.class);
        controller.compressFile(inputFile, outputFile);
        verify(inputStreamFactory, times(2)).createInputStream(inputFile);
        verify(outputStreamFactory).createOutputStream(outputFile);
        verify(encoder).encode(any(), any(), any());
    }

    @Test
    void decompressFile() throws IOException {
        File inputFile = mock(File.class), outputFile = mock(File.class);
        controller.decompressFile(inputFile, outputFile);
        verify(inputStreamFactory).createInputStream(inputFile);
        verify(outputStreamFactory).createOutputStream(outputFile);
        verify(decoder).decode(any(), any());
    }
}