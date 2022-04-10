package main;

import logic.controller.Controller;
import logic.controller.InputStreamFactory;
import logic.controller.OutputStreamFactory;
import logic.decoder.Decoder;
import logic.decoder.HuffmanTreeDecoder;
import logic.encoder.BitEncodingMapBuilder;
import logic.encoder.Encoder;
import logic.encoder.HuffmanTreeBuilder;
import logic.encoder.HuffmanTreeEncoder;

import java.io.File;
import java.io.IOException;

public class Main {
    private static final File ORIGINAL_FILE = new File("hello.txt");
    private static final File COMPRESSED_FILE = new File("hello_compressed.txt");
    private static final File DECOMPRESSED_FILE = new File("hello_decompressed.txt");

    public static void main(String[] args) throws IOException {
        Encoder encoder = new Encoder(new HuffmanTreeBuilder(), new BitEncodingMapBuilder(), new HuffmanTreeEncoder());
        Decoder decoder = new Decoder(new HuffmanTreeDecoder());
        Controller controller = new Controller(encoder, decoder, new InputStreamFactory(), new OutputStreamFactory());
        controller.compressFile(ORIGINAL_FILE, COMPRESSED_FILE);
        controller.decompressFile(COMPRESSED_FILE, DECOMPRESSED_FILE);
    }
}
