package logic.model.tree.visitors;

import logic.model.tree.HuffmanTree;
import logic.model.tree.InternalNode;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DOTGraphVisitorTest {
    private HuffmanTree tree;
    private DOTGraphVisitor visitor;

    @BeforeEach
    void setUp() {
        tree = mock(HuffmanTree.class);
        visitor = new DOTGraphVisitor(tree);
    }

    @Test
    void graphTypeIsSetToDigraph() {
        TreeNode root = new LeafNode((byte) 9);
        when(tree.getRoot()).thenReturn(root);
        String graph = visitor.getDOTGraph();
        assertTrue(graph.contains("digraph"));
    }

    @Test
    void frequenciesAreNotIncludedInInternalNodesIfNotAvailable() {
        TreeNode root = new InternalNode(new LeafNode((byte) 34), new LeafNode((byte) 27));
        when(tree.getRoot()).thenReturn(root);
        String graph = visitor.getDOTGraph();
        assertTrue(graph.contains("label=\"*\""));
    }

    @Test
    void frequenciesAreNotIncludedInLeafNodesIfNotAvailable() {
        TreeNode root = new LeafNode((byte) 84);
        when(tree.getRoot()).thenReturn(root);
        String graph = visitor.getDOTGraph();
        assertFalse(graph.contains("|0"));
    }

    @Test
    void frequenciesAreIncludedInInternalNodesIfAvailable() {
        TreeNode leftChild = mock(LeafNode.class), rightChild = mock(LeafNode.class);
        when(leftChild.getFrequency()).thenReturn(7);
        when(rightChild.getFrequency()).thenReturn(13);
        TreeNode root = new InternalNode(leftChild, rightChild);
        when(tree.getRoot()).thenReturn(root);
        String graph = visitor.getDOTGraph();
        assertTrue(graph.contains("\"20\""));
    }

    @Test
    void frequenciesAreIncludedInLeafNodesIfAvailable() {
        TreeNode root = mock(LeafNode.class);
        doCallRealMethod().when(root).accept(visitor);
        when(root.getFrequency()).thenReturn(34);
        when(tree.getRoot()).thenReturn(root);
        String graph = visitor.getDOTGraph();
        assertTrue(graph.contains("|" + 34));
    }

    @Test
    void charRepresentationIsNotIncludedInNonValidChars() {
        TreeNode root = new LeafNode((byte) -16);
        when(tree.getRoot()).thenReturn(root);
        String graph = visitor.getDOTGraph();
        assertTrue(graph.contains("label=\"{{0xF0}}\""));
    }

    @Test
    void charRepresentationIsIncludedInValidChars() {
        TreeNode root = new LeafNode((byte) 'a');
        when(tree.getRoot()).thenReturn(root);
        String graph = visitor.getDOTGraph();
        assertTrue(graph.contains("label=\"{{0x61|a}}\""));
    }
}