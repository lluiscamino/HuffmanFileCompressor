package logic.decoder;

import logic.model.bits.BitSequence;
import logic.model.tree.HuffmanTree;
import logic.model.tree.InternalNode;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DecoderTest {
    private HuffmanTreeDecoder huffmanTreeDecoder;
    private Decoder decoder;

    @BeforeEach
    void setUp() {
        huffmanTreeDecoder = mock(HuffmanTreeDecoder.class);
        decoder = new Decoder(huffmanTreeDecoder);
    }

    @Test
    void exceptionIsThrownOnNullHuffmanTree() {
        assertThrows(IllegalStateException.class, () -> decoder.getHuffmanTree());
    }

    @Test
    void getHuffmanTree() throws IOException {
        InputStream inputStream = mock(InputStream.class);
        OutputStream outputStream = mock(OutputStream.class);
        HuffmanTree huffmanTree = new HuffmanTree(new LeafNode((byte) 3));
        when(huffmanTreeDecoder.decode(inputStream)).thenReturn(huffmanTree);
        decoder.decode(inputStream, outputStream);
        assertEquals(huffmanTree, decoder.getHuffmanTree());
    }

    @Test
    void decode() throws IOException {
        BitSequence sequence = new BitSequence("10100010");
        byte[] numSymbolsBuffer = ByteBuffer.allocate(Integer.BYTES).putInt(sequence.numBits()).array();
        byte[] inputBytes = concatenateArrays(numSymbolsBuffer, sequence.toByteArray());
        InputStream inputStream = new ByteArrayInputStream(inputBytes);
        OutputStream outputStream = mock(OutputStream.class);
        TreeNode treeRoot = new InternalNode(new LeafNode((byte) 'a'), new LeafNode((byte) 'b'));
        when(huffmanTreeDecoder.decode(inputStream)).thenReturn(new HuffmanTree(treeRoot));
        decoder.decode(inputStream, outputStream);
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(outputStream, times(sequence.numBits())).write(argument.capture());
        List<Integer> expectedDecodedBytes = List.of((int) 'b', (int) 'a', (int) 'b', (int) 'a', (int) 'a',
                (int) 'a', (int) 'b', (int) 'a');
        assertEquals(expectedDecodedBytes, argument.getAllValues());
    }

    private byte[] concatenateArrays(byte[] arr1, byte[] arr2) {
        byte[] concatenatedArray = Arrays.copyOf(arr1, arr1.length + arr2.length);
        System.arraycopy(arr2, 0, concatenatedArray, arr1.length, arr2.length);
        return concatenatedArray;
    }
}