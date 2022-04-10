package logic.encoder;

import logic.model.tree.HuffmanTree;
import logic.model.tree.InternalNode;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class HuffmanTreeBuilder {
    private static final int NUM_UNIQUE_BYTES = 256;

    public HuffmanTree buildTree(InputStream inputStream) throws IOException {
        PriorityQueue<TreeNode> treeNodes = buildLeafNodes(inputStream);
        HuffmanTree tree = buildHuffmanTree(treeNodes);
        if (tree.getRoot() instanceof LeafNode leafNode) {
            TreeNode rightChild = new LeafNode((byte) 0);
            TreeNode newRoot = new InternalNode(leafNode, rightChild);
            tree = new HuffmanTree(newRoot);
        }
        return tree;
    }

    private PriorityQueue<TreeNode> buildLeafNodes(InputStream inputStream) throws IOException {
        final LeafNode[] byteCount = new LeafNode[NUM_UNIQUE_BYTES];
        while (inputStream.available() > 0) {
            byte byteVal = (byte) inputStream.read();
            int unsignedByteVal = byteVal & 0xFF;
            if (byteCount[unsignedByteVal] == null) {
                byteCount[unsignedByteVal] = new LeafNode(byteVal);
            }
            byteCount[unsignedByteVal].increaseFrequency();
        }
        return Arrays.stream(byteCount).filter(Objects::nonNull).collect(Collectors.toCollection(PriorityQueue::new));
    }

    private HuffmanTree buildHuffmanTree(PriorityQueue<TreeNode> treeNodes) {
        while (treeNodes.size() > 1) {
            TreeNode leftChild = treeNodes.poll(), rightChild = treeNodes.poll();
            InternalNode internalNode = new InternalNode(leftChild, rightChild);
            treeNodes.add(internalNode);
        }
        return new HuffmanTree(treeNodes.peek());
    }
}
