package logic.model.tree;

public interface TreeVisitor {
    void visit(InternalNode node);

    void visit(LeafNode node);
}
