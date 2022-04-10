package logic.model.bits;

import java.util.Objects;

public class BitSequence {
    private static final char FALSE_BIT = '0', TRUE_BIT = '1';

    private final StringBuilder sequence;

    public BitSequence() {
        sequence = new StringBuilder();
    }

    public BitSequence(String sequence) {
        this.sequence = new StringBuilder(sequence);
    }

    public int numBits() {
        return sequence.length();
    }

    public boolean isSet(int position) {
        return sequence.charAt(position) == TRUE_BIT;
    }

    public void addBit(boolean isSet) {
        this.sequence.append(isSet ? TRUE_BIT : FALSE_BIT);
    }

    public byte[] toByteArray() {
        int numBytes = (int) Math.ceil((float) numBits() / Byte.SIZE);
        byte[] bytes = new byte[numBytes];
        int allocatedBytes = 0;
        BitSequence currSequence = new BitSequence();
        for (int i = 0; i < numBits(); i++) {
            currSequence.addBit(isSet(i));
            if (currSequence.numBits() == Byte.SIZE) {
                bytes[allocatedBytes++] = currSequence.toByte();
                currSequence = new BitSequence();
            }
        }
        if (currSequence.numBits() != 0) {
            bytes[allocatedBytes] = currSequence.toByte();
        }
        return bytes;
    }

    public byte toByte() {
        if (numBits() > Byte.SIZE) {
            throw new RuntimeException(
                    "Bit sequence cannot be larger than " + Byte.SIZE + " bits to be converted into a byte");
        }
        StringBuilder auxiliar = new StringBuilder(toString());
        while (auxiliar.length() < Byte.SIZE) {
            auxiliar.append(FALSE_BIT);
        }
        return Integer.valueOf(auxiliar.reverse().toString(), 2).byteValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitSequence that = (BitSequence) o;
        return sequence.toString().equals(that.sequence.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence.toString());
    }

    @Override
    public String toString() {
        return sequence.toString();
    }
}
