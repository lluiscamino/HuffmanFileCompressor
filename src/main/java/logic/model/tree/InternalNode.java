package logic.model.tree;

public class InternalNode extends TreeNode {
    public InternalNode(TreeNode leftChild, TreeNode rightChild) {
        super(leftChild.getFrequency() + rightChild.getFrequency(), leftChild, rightChild);
    }
}
