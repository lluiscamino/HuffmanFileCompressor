package logic.model.tree;

public abstract class TreeNode implements Comparable<TreeNode> {
    private final TreeNode leftChild, rightChild;
    private int frequency;

    protected TreeNode(int frequency) {
        this(frequency, null, null);
    }

    protected TreeNode(int frequency, TreeNode leftChild, TreeNode rightChild) {
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public int getFrequency() {
        return frequency;
    }

    public void increaseFrequency() {
        frequency++;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    @Override
    public int compareTo(TreeNode o) {
        return Integer.compare(frequency, o.frequency);
    }
}
