package logic.encoder;

import logic.model.bits.BitSequence;
import logic.model.tree.HuffmanTree;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class BitEncodingMapBuilder {
    public Map<Byte, BitSequence> buildEncodingMap(HuffmanTree tree) {
        Map<Byte, BitSequence> map = new HashMap<>();
        buildHelper(tree.getRoot(), "", map);
        return map;
    }

    private void buildHelper(TreeNode node, String currSequence, Map<Byte, BitSequence> map) {
        if (node instanceof LeafNode leafNode) {
            map.put(leafNode.getValue(), new BitSequence(currSequence));
        } else {
            buildHelper(node.getLeftChild(), currSequence + "0", map);
            buildHelper(node.getRightChild(), currSequence + "1", map);
        }
    }
}
