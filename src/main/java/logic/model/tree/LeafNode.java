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

    public String getValueHexString() {
        return "0x" + String.format("%02X", value);
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }
}
