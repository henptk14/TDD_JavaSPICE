package CircuitOjects;

import org.junit.Test;

import static org.junit.Assert.*;

public class IVSTest {

    @Test
    public void stamp_noGround_allValid() {
        CircuitElement vs = new IVS("v1", "1", "2", "12");
        double[][] A = new double[4][4];
        double[] B = new double[4];
        boolean stamped = ((IVS) vs).stamp(0, 1, 3, vs.getValue(), A, B);
        double[] r1 = {0, 0, 0, 1};
        double[] r2 = {0, 0, 0, -1};
        double[] r3 = {0, 0, 0, 0};
        double[] r4 = {1, -1, 0, 0};

        double[] b1 = {0, 0, 0, 12};

        assertTrue(stamped);
        assertTrue(((IVS) vs).isStamped());
        assertArrayEquals(r1, A[0], 0);
        assertArrayEquals(r2, A[1], 0);
        assertArrayEquals(r3, A[2], 0);
        assertArrayEquals(r4, A[3], 0);

        assertArrayEquals(b1, B, 0);
    }

    @Test
    public void stamp_positiveGround_negativeValid() {
        CircuitElement vs = new IVS("v1", "0", "1", "12m");
        double[][] A = new double[4][4];
        double[] B = new double[4];
        boolean stamped = ((IVS) vs).stamp(-1, 0, 3, vs.getValue(), A, B);
        double[] r1 = {0, 0, 0, -1};
        double[] r2 = {0, 0, 0, 0};
        double[] r3 = {0, 0, 0, 0};
        double[] r4 = {-1, 0, 0, 0};

        double[] b1 = {0, 0, 0, .012};

        assertTrue(stamped);
        assertTrue(((IVS) vs).isStamped());
        assertArrayEquals(r1, A[0], 0);
        assertArrayEquals(r2, A[1], 0);
        assertArrayEquals(r3, A[2], 0);
        assertArrayEquals(r4, A[3], 0);

        assertArrayEquals(b1, B, 0);
    }

    @Test
    public void stamp_positiveGround_negativeOutOfBound() {
        CircuitElement vs = new IVS("v1_negativeOutOfBound", "1", "2", "12");
        double[][] A = new double[4][4];
        double[] B = new double[4];
        boolean stamped = ((IVS) vs).stamp(-1, 4, 3, vs.getValue(), A, B);
        assertFalse(stamped);
        assertFalse(((IVS) vs).isStamped());
    }

    @Test
    public void stamp_negativeGround_positiveValid() {
        CircuitElement vs = new IVS("v1", "2", "0", "12k");
        double[][] A = new double[4][4];
        double[] B = new double[4];
        boolean stamped = ((IVS) vs).stamp(2, -1, 3, vs.getValue(), A, B);
        double[] r1 = {0, 0, 0, 0};
        double[] r2 = {0, 0, 0, 0};
        double[] r3 = {0, 0, 0, 1};
        double[] r4 = {0, 0, 1, 0};

        double[] b1 = {0, 0, 0, 12000};

        assertTrue(stamped);
        assertTrue(((IVS) vs).isStamped());
        assertArrayEquals(r1, A[0], 0);
        assertArrayEquals(r2, A[1], 0);
        assertArrayEquals(r3, A[2], 0);
        assertArrayEquals(r4, A[3], 0);

        assertArrayEquals(b1, B, 0);
    }

    @Test
    public void stamp_negativeGround_positiveOutOfBound() {
        CircuitElement vs = new IVS("v1_positiveOutOfBound", "2", "0", "12");
        double[][] A = new double[4][4];
        double[] B = new double[4];
        boolean stamped = ((IVS) vs).stamp(2, -1, 10, vs.getValue(), A, B);
        assertFalse(stamped);
        assertFalse(((IVS) vs).isStamped());
    }

    @Test
    public void stamp_NaNValue() {
        CircuitElement vs = new IVS("v1_NaNValue", "1", "2", "2s");
        double[][] A = new double[4][4];
        double[] B = new double[4];
        boolean stamped = ((IVS) vs).stamp(0, 2, 3, vs.getValue(), A, B);
        assertEquals(Double.NaN, vs.getValue(), 0);
        assertFalse(stamped);
        assertFalse(((IVS) vs).isStamped());
    }

    @Test
    public void stamp_NonSquareMatrixA() {
        CircuitElement vs = new IVS("v1_NonSquareMatrix", "1", "2", "2");
        double[][] A = new double[4][3];
        double[] B = new double[4];
        boolean stamped = ((IVS) vs).stamp(0, 2, 3, vs.getValue(), A, B);
        assertFalse(stamped);
    }

    @Test
    public void stamp_InvalidMatrix() {
        CircuitElement vs = new IVS("v1_InvalidMatrix", "1", "2", "12");
        double[][] A = new double[1][1];
        double[] B = new double[4];
        boolean stamped = ((IVS) vs).stamp(0, 1, 3, vs.getValue(), A, B);
        assertFalse(stamped);
        assertFalse(((IVS) vs).isStamped());
    }
}