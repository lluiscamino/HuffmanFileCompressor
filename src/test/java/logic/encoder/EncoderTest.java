package logic.encoder;

import logic.model.bits.BitSequence;
import logic.model.tree.HuffmanTree;
import logic.model.tree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EncoderTest {
    private HuffmanTreeBuilder huffmanTreeBuilder;
    private BitEncodingMapBuilder bitEncodingMapBuilder;
    private HuffmanTreeEncoder huffmanTreeEncoder;
    private Encoder encoder;

    @BeforeEach
    void setUp() {
        huffmanTreeBuilder = mock(HuffmanTreeBuilder.class);
        bitEncodingMapBuilder = mock(BitEncodingMapBuilder.class);
        huffmanTreeEncoder = mock(HuffmanTreeEncoder.class);
        encoder = new Encoder(huffmanTreeBuilder, bitEncodingMapBuilder, huffmanTreeEncoder);
    }

    @Test
    void encode() throws IOException {
        byte[] input = new byte[]{'a', 'a', 'b'};
        InputStream inputStream1 = new ByteArrayInputStream(input),
                inputStream2 = new ByteArrayInputStream(input);
        OutputStream outputStream = mock(OutputStream.class);
        TreeNode treeRoot = mock(TreeNode.class);
        HuffmanTree tree = new HuffmanTree(treeRoot);
        when(huffmanTreeBuilder.buildTree(inputStream1)).thenReturn(tree);
        when(treeRoot.getFrequency()).thenReturn(input.length);
        Map<Byte, BitSequence> bitEncodingMap = Map.of(
                (byte) 'a', new BitSequence("1"),
                (byte) 'b', new BitSequence("0")
        );
        when(bitEncodingMapBuilder.buildEncodingMap(tree)).thenReturn(bitEncodingMap);
        encoder.encode(inputStream1, inputStream2, outputStream);
        verify(outputStream).write(input.length);
        verify(outputStream).write(new byte[]{0, 0, 0, new BitSequence("110").toByte()});
    }
}