package logic.decoder;

import logic.model.bits.BitSequence;
import logic.model.tree.HuffmanTree;
import logic.model.tree.InternalNode;
import logic.model.tree.LeafNode;
import logic.model.tree.TreeNode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class HuffmanTreeDecoder {
    private int bitPosition;

    public HuffmanTree decode(InputStream inputStream) throws IOException {
        BitSequence sequence = getHuffmanTreeBitSequence(inputStream);
        bitPosition = 0;
        return new HuffmanTree(decode(sequence));
    }

    private BitSequence getHuffmanTreeBitSequence(InputStream inputStream) throws IOException {
        byte[] treeLengthInBitsBuffer = new byte[Integer.BYTES];
        inputStream.read(treeLengthInBitsBuffer);
        int treeLengthInBits = ByteBuffer.wrap(treeLengthInBitsBuffer).getInt();
        int treeLengthInBytes = (int) Math.ceil((float) treeLengthInBits / Byte.SIZE);
        byte[] tree = new byte[treeLengthInBytes];
        inputStream.read(tree);
        BitSequence sequence = new BitSequence();
        outerLoop:
        for (byte treeByte : tree) {
            for (int i = 0; i < Byte.SIZE; i++) {
                sequence.addBit(isSet(treeByte, i));
                if (sequence.numBits() == treeLengthInBits) {
                    break outerLoop;
                }
            }
        }
        return sequence;
    }

    private TreeNode decode(BitSequence sequence) {
        if (sequence.isSet(bitPosition)) {
            bitPosition++;
            BitSequence byteBits = new BitSequence(
                    sequence.toString().substring(bitPosition, Math.min(bitPosition + Byte.SIZE, sequence.numBits())));
            bitPosition += byteBits.numBits();
            return new LeafNode(byteBits.toByte());
        } else {
            bitPosition++;
            TreeNode leftChild = decode(sequence);
            TreeNode rightChild = decode(sequence);
            return new InternalNode(leftChild, rightChild);
        }
    }

    private boolean isSet(byte value, int bitPosition) {
        return (value & (1 << bitPosition)) != 0;
    }
}
