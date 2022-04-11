package logic.controller;

import logic.decoder.Decoder;
import logic.encoder.Encoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ApplicationControllerTest {
    private Encoder encoder;
    private Decoder decoder;
    private FileFactory fileFactory;
    private InputStreamFactory inputStreamFactory;
    private OutputStreamFactory outputStreamFactory;
    private Controller controller;

    @BeforeEach
    void setUp() {
        encoder = mock(Encoder.class);
        decoder = mock(Decoder.class);
        fileFactory = mock(FileFactory.class);
        inputStreamFactory = mock(InputStreamFactory.class);
        outputStreamFactory = mock(OutputStreamFactory.class);
        controller = new ApplicationController(encoder, decoder, fileFactory, inputStreamFactory, outputStreamFactory);
    }

    @Test
    void getCompressedFileExtension() {
        assertEquals(".huff", controller.getCompressedFileExtension());
    }

    @Test
    void exceptionIsThrownOnNoSelectedFile() {
        assertThrows(IllegalStateException.class, () -> controller.compressFile());
        assertThrows(IllegalStateException.class, () -> controller.decompressFile());
    }

    @Test
    void compressFileNoArgs() throws IOException {
        File inputFile = mock(File.class), outputFile = mock(File.class);
        when(fileFactory.createFile(any(String.class))).thenReturn(outputFile);
        controller.selectFile(inputFile);
        controller.compressFile();
        verify(inputStreamFactory, times(2)).createInputStream(inputFile);
        verify(outputStreamFactory).createOutputStream(outputFile);
        verify(encoder).encode(any(), any(), any());
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

    @Test
    void decompressNoArgsFile() throws IOException {
        File inputFile = mock(File.class), outputFile = mock(File.class);
        when(inputFile.getAbsolutePath()).thenReturn("input_file.txt.huff");
        when(fileFactory.createFile(any(String.class))).thenReturn(outputFile);
        controller.selectFile(inputFile);
        controller.decompressFile();
        verify(inputStreamFactory).createInputStream(inputFile);
        verify(outputStreamFactory).createOutputStream(outputFile);
        verify(decoder).decode(any(), any());
    }
}