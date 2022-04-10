package logic.encoder;

import logic.model.bits.BitSequence;
import logic.model.tree.HuffmanTree;
import logic.model.tree.InternalNode;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BitEncodingMapBuilderTest {
    private BitEncodingMapBuilder bitEncodingMapBuilder;

    @BeforeEach
    void setUp() {
        bitEncodingMapBuilder = new BitEncodingMapBuilder();
    }

    @Test
    void buildEncodingMap() {
        TreeNode nodeA = new LeafNode((byte) 'a'),
                nodeB = new LeafNode((byte) 'b'),
                nodeC = new LeafNode((byte) 'c'),
                internalNode = new InternalNode(nodeA, nodeB),
                root = new InternalNode(internalNode, nodeC);
        HuffmanTree tree = new HuffmanTree(root);
        Map<Byte, BitSequence> bitEncodingMap = bitEncodingMapBuilder.buildEncodingMap(tree);
        Map<Byte, BitSequence> expectedMap = Map.of(
                (byte) 'a', new BitSequence("00"),
                (byte) 'b', new BitSequence("01"),
                (byte) 'c', new BitSequence("1")
        );
        assertEquals(expectedMap, bitEncodingMap);
    }
}