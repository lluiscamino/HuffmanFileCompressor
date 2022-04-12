package logic.model.tree.visitors;

import logic.model.tree.HuffmanTree;
import logic.model.tree.InternalNode;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;
import logic.model.tree.TreeVisitor;

public class DOTGraphVisitor implements TreeVisitor {
    private final HuffmanTree huffmanTree;
    private final StringBuilder dotContents = new StringBuilder("digraph huffman_tree {");

    private boolean containsFrequencies;
    private boolean containsCharacters = true;

    public DOTGraphVisitor(HuffmanTree huffmanTree) {
        this.huffmanTree = huffmanTree;
    }

    public String getDOTGraph() {
        TreeNode root = huffmanTree.getRoot();
        containsFrequencies = root.getFrequency() > 0;
        root.accept(this);
        return dotContents.append("}").toString();
    }

    @Override
    public void visit(InternalNode node) {
        int code = node.hashCode();
        int leftChildCode = node.getLeftChild().hashCode(),
                rightChildCode = node.getRightChild().hashCode();
        String label = containsFrequencies ? Integer.toString(node.getFrequency()) : "*";
        dotContents
                .append(code).append(" [label=\"").append(label).append("\"];")
                .append(code).append(" -> ").append(leftChildCode).append("[label=\"0\"];")
                .append(code).append(" -> ").append(rightChildCode).append("[label=\"1\"];");
    }

    @Override
    public void visit(LeafNode node) {
        int code = node.hashCode();
        byte value = node.getValue();
        String label = "{{" + node.getValueHexString();
        String charRepresentation = getCharRepresentation(value);
        if (containsCharacters && !charRepresentation.isEmpty()) {
            label += "|" + charRepresentation;
        }
        label += containsFrequencies ? "}|" + node.getFrequency() + "}" : "}}";
        dotContents.append(code).append(" [label=\"").append(label).append("\", shape=record];");
    }

    private String getCharRepresentation(byte value) {
        if (!Character.isValidCodePoint(value)) {
            containsCharacters = false;
            return "";
        }
        return Character.isAlphabetic(value) ? Character.toString(value) : Character.getName(value);
    }
}
