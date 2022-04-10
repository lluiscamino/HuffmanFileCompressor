package logic.model.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class HuffmanTreeTest {
    @Test
    void getRoot() {
        TreeNode root = mock(TreeNode.class);
        HuffmanTree huffmanTree = new HuffmanTree(root);
        assertEquals(root, huffmanTree.getRoot());
    }
}