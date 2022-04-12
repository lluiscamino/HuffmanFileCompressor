package logic.model.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LeafNodeTest {
    @Test
    void frequencyIsSetToZero() {
        TreeNode node = new LeafNode((byte) 0);
        assertEquals(0, node.getFrequency());
    }

    @Test
    void increaseFrequency() {
        TreeNode node = new LeafNode((byte) 3);
        node.increaseFrequency();
        assertEquals(1, node.getFrequency());
    }

    @Test
    void getValue() {
        byte value = (byte) 6;
        LeafNode node = new LeafNode(value);
        assertEquals(value, node.getValue());
    }

    @Test
    void compareTo() {
        TreeNode node1 = new LeafNode((byte) 3), node2 = new LeafNode((byte) 7);
        node1.increaseFrequency();
        node1.increaseFrequency();
        node2.increaseFrequency();
        assertEquals(2, node1.getFrequency());
        assertEquals(1, node2.getFrequency());
        assertTrue(node1.compareTo(node2) > 0);
        assertTrue(node2.compareTo(node1) < 0);
    }

    @Test
    void accept() {
        TreeVisitor visitor = mock(TreeVisitor.class);
        LeafNode node = new LeafNode((byte) -7);
        node.accept(visitor);
        verify(visitor).visit(node);
    }
}