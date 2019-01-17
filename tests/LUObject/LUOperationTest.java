package LUObject;

import org.junit.Test;

import static org.junit.Assert.*;

public class LUOperationTest {

    @Test
    public void upperMatrix_differentSizes() {
        double[][] A = new double[4][3];
        assertNull(LUOperation.UpperMatrix(A));
    }

    @Test
    public void lowerMatrix_matrixSize1() {
        double[][] A = {{1}};
        double[][] expected = {{1}};
        assertArrayEquals(expected[0], LUOperation.LowerMatrix(A)[0], 0);
    }

    @Test
    public void lowerMatrix_matrixSize2() {
        double[][] A = new double[4][4];
        assertNull(LUOperation.LowerMatrix(A));
    }

    @Test
    public void solveLU_differentSizes() {
        double[][] A = new double[3][4];
        double[] B = new double[4];

        assertNull(LUOperation.solveLU(A, B));
    }

    @Test
    public void solveLU_allZeros() {
        double[][] A = new double[4][4];
        double[] B = new double[4];

        assertNull(LUOperation.solveLU(A, B));
    }
}