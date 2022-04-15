package logic.encoder;

import logic.model.tree.HuffmanTree;
import logic.model.tree.InternalNode;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HuffmanTreeBuilder {
    private static final int NUM_UNIQUE_BYTES = 256;

    private Float entropy;

    public float getEntropy() {
        if (entropy == null) {
            throw new IllegalStateException("Entropy has not been calculated yet");
        }
        return entropy;
    }

    public HuffmanTree buildTree(InputStream inputStream) throws IOException {
        PriorityQueue<TreeNode> treeNodes = buildLeafNodes(inputStream);
        entropy = calculateEntropy(treeNodes);
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

    private float calculateEntropy(Collection<TreeNode> leafNodes) {
        int frequenciesSum = leafNodes.stream().map(TreeNode::getFrequency).mapToInt(f -> f).sum();
        Function<Float, Double> log2 = val -> Math.log(val) / Math.log(2);
        return (float) leafNodes
                .stream()
                .map(TreeNode::getFrequency)
                .map(frequency -> (float) frequency / frequenciesSum)
                .map(percentage -> percentage * log2.apply((float) 1 / percentage))
                .mapToDouble(f -> f)
                .sum();
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
