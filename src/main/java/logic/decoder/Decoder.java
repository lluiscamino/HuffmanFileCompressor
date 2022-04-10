package logic.decoder;

import logic.model.tree.HuffmanTree;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Decoder {
    private final HuffmanTreeDecoder huffmanTreeDecoder;

    public Decoder(HuffmanTreeDecoder huffmanTreeDecoder) {
        this.huffmanTreeDecoder = huffmanTreeDecoder;
    }

    public void decode(InputStream inputStream, OutputStream outputStream) throws IOException {
        HuffmanTree huffmanTree = huffmanTreeDecoder.decode(inputStream);
        TreeNode node = huffmanTree.getRoot();
        int numSymbols = readNumSymbols(inputStream), numReadSymbols = 0;
        outerLoop:
        while (inputStream.available() > 0) {
            byte byteVal = (byte) inputStream.read();
            for (int i = 0; i < Byte.SIZE; i++) {
                if (node instanceof LeafNode leafNode) {
                    outputStream.write(leafNode.getValue());
                    node = huffmanTree.getRoot();
                    if (++numReadSymbols == numSymbols) {
                        break outerLoop;
                    }
                }
                node = isSet(byteVal, i) ? node.getRightChild() : node.getLeftChild();
            }
        }
        if (node instanceof LeafNode leafNode) {
            outputStream.write(leafNode.getValue());
        }
        outputStream.close();
    }

    private int readNumSymbols(InputStream inputStream) throws IOException {
        byte[] numSymbolsBuffer = new byte[Integer.BYTES];
        inputStream.read(numSymbolsBuffer);
        return ByteBuffer.wrap(numSymbolsBuffer).getInt();
    }

    private boolean isSet(byte value, int bitPosition) {
        return (value & (1 << bitPosition)) != 0;
    }
}
