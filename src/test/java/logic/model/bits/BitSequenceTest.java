package logic.model.bits;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BitSequenceTest {

    @Test
    void numBits() {
        BitSequence bitSequence = new BitSequence("100101");
        assertEquals(6, bitSequence.numBits());
    }

    @Test
    void isSet() {
        BitSequence bitSequence = new BitSequence("10");
        assertTrue(bitSequence.isSet(0));
        assertFalse(bitSequence.isSet(1));
        assertThrows(StringIndexOutOfBoundsException.class, () -> bitSequence.isSet(2));
    }

    @Test
    void addBit() {
        BitSequence bitSequence = new BitSequence();
        bitSequence.addBit(true);
        bitSequence.addBit(false);
        assertEquals("10", bitSequence.toString());
    }

    @Test
    void toByteArray() {
        BitSequence bitSequence1 = new BitSequence("");
        assertEquals(0, bitSequence1.toByteArray().length);
        BitSequence bitSequence2 = new BitSequence("11001");
        assertArrayEquals(new byte[]{(byte) 0b10011}, bitSequence2.toByteArray());
        BitSequence bitSequence3 = new BitSequence("1100101010101001");
        assertArrayEquals(new byte[]{(byte) 0b01010011, (byte) 0b10010101}, bitSequence3.toByteArray());
    }

    @Test
    void toByte() {
        BitSequence bitSequence1 = new BitSequence("10100110");
        assertEquals((byte) 0b01100101, bitSequence1.toByte());
        BitSequence bitSequence2 = new BitSequence("101");
        assertEquals((byte) 0b101, bitSequence2.toByte());
        BitSequence bitSequence3 = new BitSequence("1010101010101010");
        assertThrows(RuntimeException.class, bitSequence3::toByte);
    }

    @Test
    void testEquals() {
        BitSequence bitSequence1 = new BitSequence("10010"),
                bitSequence2 = new BitSequence("10010"),
                bitSequence3 = new BitSequence("1110");
        assertEquals(bitSequence1, bitSequence1);
        assertEquals(bitSequence1, bitSequence2);
        assertNotEquals(bitSequence1, bitSequence3);
        assertNotEquals(bitSequence1, null);
    }

    @Test
    void testHashCode() {
        String bits = "1001001";
        assertEquals(Objects.hash(bits), new BitSequence(bits).hashCode());
    }

    @Test
    void testToString() {
        BitSequence bitSequence = new BitSequence("1011001000");
        assertEquals("1011001000", bitSequence.toString());
    }
}