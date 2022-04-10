package logic.decoder;

import logic.model.bits.BitSequence;
import logic.model.tree.HuffmanTree;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HuffmanTreeDecoderTest {
    private HuffmanTreeDecoder huffmanTreeDecoder;

    @BeforeEach
    void setUp() {
        huffmanTreeDecoder = new HuffmanTreeDecoder();
    }

    @Test
    void decode() throws IOException {
        BitSequence treeBitEncoding = new BitSequence(
                "01" + toBinaryString((byte) 'a') + "1" + toBinaryString((byte) 'b'));
        byte[] treeEncodingBitNum = new byte[]{0, 0, 0, (byte) treeBitEncoding.numBits()};
        byte[] treeBitEncodingBytes = concatenateArrays(treeEncodingBitNum, treeBitEncoding.toByteArray());
        InputStream inputStream = new ByteArrayInputStream(treeBitEncodingBytes);
        HuffmanTree tree = huffmanTreeDecoder.decode(inputStream);
        TreeNode root = tree.getRoot();
        assertEquals((byte) 'a', ((LeafNode) root.getLeftChild()).getValue());
        assertEquals((byte) 'b', ((LeafNode) root.getRightChild()).getValue());
    }

    private String toBinaryString(byte b) {
        return new StringBuilder(
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')).reverse().toString();
    }

    private byte[] concatenateArrays(byte[] arr1, byte[] arr2) {
        byte[] concatenatedArray = Arrays.copyOf(arr1, arr1.length + arr2.length);
        System.arraycopy(arr2, 0, concatenatedArray, arr1.length, arr2.length);
        return concatenatedArray;
    }
}