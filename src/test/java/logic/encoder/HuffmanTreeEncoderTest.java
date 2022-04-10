package logic.encoder;

import logic.model.bits.BitSequence;
import logic.model.tree.HuffmanTree;
import logic.model.tree.InternalNode;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HuffmanTreeEncoderTest {
    private OutputStream outputStream;
    private HuffmanTreeEncoder huffmanTreeEncoder;

    @BeforeEach
    void setUp() {
        outputStream = mock(OutputStream.class);
        huffmanTreeEncoder = new HuffmanTreeEncoder();
    }

    @Test
    void encode() throws IOException {
        TreeNode nodeA = new LeafNode((byte) 'a'),
                nodeB = new LeafNode((byte) 'b'),
                root = new InternalNode(nodeA, nodeB);
        HuffmanTree tree = new HuffmanTree(root);
        BitSequence expectedBitEncoding = new BitSequence(
                "01" + toBinaryString((byte) 'a') + "1" + toBinaryString((byte) 'b'));
        huffmanTreeEncoder.encode(tree, outputStream);
        verify(outputStream).write(new byte[]{0, 0, 0, (byte) expectedBitEncoding.numBits()});
        verify(outputStream).write(expectedBitEncoding.toByteArray());
    }

    private String toBinaryString(byte b) {
        return new StringBuilder(
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')).reverse().toString();
    }
}