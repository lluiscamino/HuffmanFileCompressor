package logic.model.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InternalNodeTest {
    @Test
    void childNodesAreSetCorrectly() {
        TreeNode leftChild = mock(TreeNode.class), rightChild = mock(TreeNode.class);
        TreeNode node = new InternalNode(leftChild, rightChild);
        assertEquals(leftChild, node.getLeftChild());
        assertEquals(rightChild, node.getRightChild());
    }

    @Test
    void frequencyIsSetToChildrenFrequencySum() {
        TreeNode leftChild = mock(TreeNode.class), rightChild = mock(TreeNode.class);
        when(leftChild.getFrequency()).thenReturn(4);
        when(rightChild.getFrequency()).thenReturn(7);
        TreeNode node = new InternalNode(leftChild, rightChild);
        assertEquals(11, node.getFrequency());
    }

    @Test
    void compareTo() {
        TreeNode node1 = buildInternalNode(4), node2 = buildInternalNode(7);
        assertEquals(4, node1.getFrequency());
        assertEquals(7, node2.getFrequency());
        assertTrue(node1.compareTo(node2) < 0);
        assertTrue(node2.compareTo(node1) > 0);
    }

    @Test
    void accept() {
        TreeVisitor visitor = mock(TreeVisitor.class);
        InternalNode node = buildInternalNode(8);
        node.accept(visitor);
        verify(visitor).visit(node);
        verify(node.getLeftChild()).accept(visitor);
        verify(node.getRightChild()).accept(visitor);
    }

    private InternalNode buildInternalNode(int frequency) {
        TreeNode leftChild = mock(TreeNode.class), rightChild = mock(TreeNode.class);
        when(leftChild.getFrequency()).thenReturn(frequency);
        when(rightChild.getFrequency()).thenReturn(0);
        return new InternalNode(leftChild, rightChild);
    }
}