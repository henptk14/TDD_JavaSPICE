package CircuitOjects;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResistorTest {

    @Test
    public void stamp_noGround_allValid() {
        CircuitElement res = new Resistor("r1", "1", "2", "2k");
        double[][] A = new double[4][4];
        boolean stamped = ((Resistor) res).stamp(1, 2, res.getValue(), A);
        double[] r1 = {0, 0, 0, 0};
        double[] r2 = {0, 5E-4, -5E-4, 0};
        double[] r3 = {0, -5E-4, 5E-4, 0};
        double[] r4 = {0, 0, 0, 0};
        assertTrue(stamped);
        assertArrayEquals(r1, A[0], 0);
        assertArrayEquals(r2, A[1], 0);
        assertArrayEquals(r3, A[2], 0);
        assertArrayEquals(r4, A[3], 0);
    }

    @Test
    public void stamp_positiveGround_negativeValid() {
        CircuitElement res = new Resistor("r1", "0", "3", "2k");
        double[][] A = new double[4][4];
        double[] r1 = {0, 0, 0, 0};
        double[] r2 = {0, 0, 0, 0};
        double[] r3 = {0, 0, 0, 0};
        double[] r4 = {0, 0, 0, 5E-4};
        boolean stamped = ((Resistor) res).stamp(-1, 3, res.getValue(), A);

        assertTrue(stamped);
        assertArrayEquals(r1, A[0], 0);
        assertArrayEquals(r2, A[1], 0);
        assertArrayEquals(r3, A[2], 0);
        assertArrayEquals(r4, A[3], 0);
    }

    @Test
    public void stamp_positiveGround_negativeOutOfBound() {
        CircuitElement res = new Resistor("r1", "0", "4", "2k");
        double[][] A = new double[4][4];
        boolean stamped = ((Resistor) res).stamp(-1, 4, res.getValue(), A);
        assertFalse(stamped);
    }

    @Test
    public void stamp_negativeGround_positiveValid() {
        CircuitElement res = new Resistor("r1", "1", "0", "2k");
        double[][] A = new double[4][4];
        double[] r1 = {5E-4, 0, 0, 0};
        double[] r2 = {0, 0, 0, 0};
        double[] r3 = {0, 0, 0, 0};
        double[] r4 = {0, 0, 0, 0};
        boolean stamped = ((Resistor) res).stamp(0, -1, res.getValue(), A);

        assertTrue(stamped);
        assertArrayEquals(r1, A[0], 0);
        assertArrayEquals(r2, A[1], 0);
        assertArrayEquals(r3, A[2], 0);
        assertArrayEquals(r4, A[3], 0);
    }

    @Test
    public void stamp_negativeGround_positiveOutOfBount() {
        CircuitElement res = new Resistor("r1", "8", "0", "2k");
        double[][] A = new double[4][4];
        boolean stamped = ((Resistor) res).stamp(7, -1, res.getValue(), A);
        assertFalse(stamped);
    }

    @Test
    public void stamp_NaNValue() {
        CircuitElement res = new Resistor("r1", "2", "0", "2h");
        double[][] A = new double[4][4];
        boolean stamped = ((Resistor) res).stamp(1, -1, res.getValue(), A);
        assertEquals(Double.NaN, res.getValue(), 0);
        assertFalse(stamped);
    }

    @Test
    public void stamp_NonSquareMatrixA() {
        CircuitElement res = new Resistor("r1", "2", "0", "2k");
        double[][] A = new double[4][3];
        boolean stamped = ((Resistor) res).stamp(1, -1, res.getValue(), A);
        assertFalse(stamped);
    }

    @Test
    public void stamp_InvalidMatrixA() {
        CircuitElement res = new Resistor("r1", "2", "0", "2h");
        double[][] A = new double[1][1];
        boolean stamped = ((Resistor) res).stamp(1, -1, res.getValue(), A);
        assertFalse(stamped);
    }
}