package logic.encoder;

import logic.model.bits.BitSequence;
import logic.model.tree.HuffmanTree;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class HuffmanTreeEncoder {
    public void encode(HuffmanTree huffmanTree, OutputStream outputStream) throws IOException {
        BitSequence sequence = new BitSequence();
        encodeHelper(huffmanTree.getRoot(), sequence);
        byte[] encodedTree = sequence.toByteArray();
        byte[] treeLengthInBits = ByteBuffer.allocate(Integer.BYTES).putInt(sequence.numBits()).array();
        outputStream.write(treeLengthInBits);
        outputStream.write(encodedTree);
    }

    private void encodeHelper(TreeNode node, BitSequence bitSequence) {
        if (node == null) {
            return;
        }
        if (node instanceof LeafNode leafNode) {
            bitSequence.addBit(true);
            for (int i = 0; i < Byte.SIZE; i++) {
                bitSequence.addBit(isSet(leafNode.getValue(), i));
            }
        } else {
            bitSequence.addBit(false);
            encodeHelper(node.getLeftChild(), bitSequence);
            encodeHelper(node.getRightChild(), bitSequence);
        }
    }

    private boolean isSet(byte value, int bitPosition) {
        return (value & (1 << bitPosition)) != 0;
    }
}
