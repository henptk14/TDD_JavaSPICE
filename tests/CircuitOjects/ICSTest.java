package CircuitOjects;

import org.junit.Test;

import java.util.zip.CheckedInputStream;

import static org.junit.Assert.*;

public class ICSTest {

    @Test
    public void stamp_noGround_allValid() {
        CircuitElement is = new ICS("a1", "1", "2", "10");
        double[] B = new double[4];
        boolean stamped = ((ICS) is).stamp(0, 1, is.getValue(), B);
        double[] b1 = {-10, 10, 0, 0};
        assertArrayEquals(b1, B, 0);
        assertTrue(stamped);
    }

    @Test
    public void stamp_positiveGround_negativeValid() {
        CircuitElement is = new ICS("a1", "0", "3", "10");
        double[] B = new double[4];
        boolean stamped = ((ICS) is).stamp(-1, 2, is.getValue(), B);
        double[] b1 = {0, 0, 10, 0};
        assertArrayEquals(b1, B, 0);
        assertTrue(stamped);
    }

    @Test
    public void stamp_positiveGround_negativeOutOfBound() {
        CircuitElement is = new ICS("a1_negativeOutOfBound", "0", "3", "10");
        double[] B = new double[4];
        boolean stamped = ((ICS) is).stamp(-1, 4, is.getValue(), B);
        assertFalse(stamped);
        assertFalse(((ICS) is).isStamped());
    }

    @Test
    public void stamp_negativeGround_positiveValid() {
        CircuitElement is = new ICS("a1", "3", "0", 20);
        double[] B = new double[4];
        boolean stamped = ((ICS) is).stamp(3, -1, is.getValue(), B);
        double[] b1 = {0, 0, 0, -20};
        assertTrue(stamped);
        assertArrayEquals(b1, B, 0);
    }

    @Test
    public void stamp_negativeGround_positiveOutOfBound() {
        CircuitElement is = new ICS("a1_positiveOutOfBound", "2", "0", "10m");
        double[] B = new double[4];
        boolean stamped = ((ICS) is).stamp(10, -1, is.getValue(), B);
        assertFalse(stamped);
        assertFalse(((ICS) is).isStamped());
    }

    @Test
    public void stamp_NaNValue() {
        CircuitElement is = new ICS("a1_NaNValue", "1", "2", "wtf");
        double[] B = new double[4];
        boolean stamped = ((ICS) is).stamp(0, 2, is.getValue(), B);
        assertFalse(stamped);
        assertFalse(((ICS) is).isStamped());
    }
}