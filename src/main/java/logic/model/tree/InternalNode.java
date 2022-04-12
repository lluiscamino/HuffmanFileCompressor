package logic.model.tree;

public class InternalNode extends TreeNode {
    public InternalNode(TreeNode leftChild, TreeNode rightChild) {
        super(leftChild.getFrequency() + rightChild.getFrequency(), leftChild, rightChild);
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
        getLeftChild().accept(visitor);
        getRightChild().accept(visitor);
    }
}
