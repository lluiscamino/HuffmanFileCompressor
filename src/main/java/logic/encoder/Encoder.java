package logic.encoder;

import logic.model.bits.BitSequence;
import logic.model.tree.HuffmanTree;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Map;

public class Encoder {
    private final HuffmanTreeBuilder huffmanTreeBuilder;
    private final BitEncodingMapBuilder bitEncodingMapBuilder;
    private final HuffmanTreeEncoder huffmanTreeEncoder;

    public Encoder(HuffmanTreeBuilder huffmanTreeBuilder, BitEncodingMapBuilder bitEncodingMapBuilder,
                   HuffmanTreeEncoder huffmanTreeEncoder) {
        this.huffmanTreeBuilder = huffmanTreeBuilder;
        this.bitEncodingMapBuilder = bitEncodingMapBuilder;
        this.huffmanTreeEncoder = huffmanTreeEncoder;
    }

    public void encode(InputStream inputStream1, InputStream inputStream2,
                       OutputStream outputStream) throws IOException {
        try (outputStream) {
            HuffmanTree tree = huffmanTreeBuilder.buildTree(inputStream1);
            huffmanTreeEncoder.encode(tree, outputStream);
            writeNumSymbols(tree.getRoot().getFrequency(), outputStream);
            Map<Byte, BitSequence> bitEncodingMap = bitEncodingMapBuilder.buildEncodingMap(tree);
            writeBitSequence(inputStream2, outputStream, bitEncodingMap);
        }
    }

    private void writeNumSymbols(int numSymbols, OutputStream outputStream) throws IOException {
        byte[] numSymbolsBuffer = ByteBuffer.allocate(Integer.BYTES).putInt(numSymbols).array();
        outputStream.write(numSymbolsBuffer);
    }

    private void writeBitSequence(InputStream inputStream, OutputStream outputStream,
                                  Map<Byte, BitSequence> bitEncodingMap) throws IOException {
        BitSequence buffer = new BitSequence();
        while (inputStream.available() > 0) {
            byte byteVal = (byte) inputStream.read();
            BitSequence bitSequence = bitEncodingMap.get(byteVal);
            for (int i = 0; i < bitSequence.numBits(); i++) {
                buffer.addBit(bitSequence.isSet(i));
                if (buffer.numBits() == Byte.SIZE) {
                    outputStream.write(buffer.toByte());
                    buffer = new BitSequence();
                }
            }
        }
        if (buffer.numBits() > 0) {
            outputStream.write(buffer.toByte());
        }
    }
}
