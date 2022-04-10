package logic.model.tree;

public class LeafNode extends TreeNode {
    private final byte value;

    public LeafNode(byte value) {
        super(0);
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
