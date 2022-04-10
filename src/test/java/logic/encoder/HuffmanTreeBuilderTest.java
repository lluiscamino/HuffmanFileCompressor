package logic.encoder;

import logic.model.tree.HuffmanTree;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HuffmanTreeBuilderTest {
    private HuffmanTreeBuilder huffmanTreeBuilder;

    @BeforeEach
    void setUp() {
        huffmanTreeBuilder = new HuffmanTreeBuilder();
    }

    @Test
    void buildTreeFromOneDistinctByte() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("aaaa".getBytes());
        HuffmanTree tree = huffmanTreeBuilder.buildTree(inputStream);
        TreeNode root = tree.getRoot(), leftChild = root.getLeftChild(), rightChild = root.getRightChild();
        assertEquals(4, root.getFrequency());
        assertEquals(4, leftChild.getFrequency());
        assertEquals((byte) 'a', ((LeafNode) leftChild).getValue());
        assertEquals(0, rightChild.getFrequency());
        assertEquals((byte) 0, ((LeafNode) rightChild).getValue());
    }

    @Test
    void buildTree() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("aaabb".getBytes());
        HuffmanTree tree = huffmanTreeBuilder.buildTree(inputStream);
        TreeNode root = tree.getRoot(), leftChild = root.getLeftChild(), rightChild = root.getRightChild();
        assertEquals(5, root.getFrequency());
        assertEquals(2, leftChild.getFrequency());
        assertEquals((byte) 'b', ((LeafNode) leftChild).getValue());
        assertEquals(3, rightChild.getFrequency());
        assertEquals((byte) 'a', ((LeafNode) rightChild).getValue());
    }
}