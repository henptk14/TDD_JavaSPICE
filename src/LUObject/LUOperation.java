package LUObject;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.linear.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides static methods to get upper matrix, lower matrix, or the solution for Ax = b.
 * It also provides methods for printing out 2D and 1D arrays.
 *
 * @author Pyi Thein Khine
 */
public class LUOperation {
    private static Logger logger = Logger.getLogger(LUOperation.class.getName());

    /**
     * Returns upper matrix of LU decomposition.
     *
     * @param A             The matrix to be decomposed.
     * @return              Returns the upper matrix if successful. Otherwise, returns null.
     */
    public static double[][] UpperMatrix(double[][] A) {
        if (A.length != A[0].length) {
            logger.log(Level.SEVERE, "Matrix A and upperMatrix are not of the same size! They need to be same in dimension.");
            return null;
        }

        RealMatrix upper_temp;
        try {
            upper_temp = new Array2DRowRealMatrix(A);
        } catch (DimensionMismatchException e) {
            logger.log(Level.SEVERE, "UpperMatrix method from LUOperation has DimensionMismatchException. Check if the matrix is square.");
            return null;
        } catch (NoDataException e) {
            logger.log(Level.SEVERE, "UpperMatrix method from LUOperation has NoDataException. Check if row or column dimension is zero.");
            return null;
        } catch (NullArgumentException e) {
            logger.log(Level.SEVERE, "UpperMatrix method from LUOperation has NullArgumentException. Check if the matrix is null.");
            return null;
        }

        RealMatrix u = new LUDecomposition(upper_temp).getU();
        double[][] upper;
        try {
            upper = u.getData();
            return upper;
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "UpperMatrix from LUOperation has NullPointerException. u.getData() is not successfull.");
            return null;
        }
    }

    /**
     * Returns the lower matrix of LU decomposition.
     *
     * @param A             The matrix to be decomposed.
     * @return              Returns the lower matrix if successful. Otherwise, returns null.
     */
    public static double[][] LowerMatrix(double[][] A) {
        if (A.length != A[0].length) {
            logger.log(Level.SEVERE, "Matrix A and lowerMatrix are not of the same size! They need to be same in dimension.");
            return null;
        }

        if(A.length < 2) {
            return A;
        }

        RealMatrix lower_temp;
        try {
            lower_temp = new Array2DRowRealMatrix(A);
        } catch (DimensionMismatchException e) {
            logger.log(Level.SEVERE, "LowerMatrix method from LUOperation has DimensionMismatchException. Check if the matrix is square.");
            return null;
        } catch (NoDataException e) {
            logger.log(Level.SEVERE, "LowerMatrix method from LUOperation has NoDataException. Check if row or column dimension is zero.");
            return null;
        } catch (NullArgumentException e) {
            logger.log(Level.SEVERE, "LowerMatrix method from LUOperation has NullArgumentException. Check if the matrix is null.");
            return null;
        }

        RealMatrix l = new LUDecomposition(lower_temp).getL();
        double[][] lower;
        try {
            lower = l.getData();
            return lower;
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "LowerMatrix method from LUOperation has NullPointerException. l.getData() is not successful.");
            return null;
        }
    }

    /**
     * Returns the solution for Ax = b.
     *
     * @param matrixA   Matrix A to be solved.
     * @param vectorB   Vector on the right hand side.
     * @return          Returns the solution as an array if successful. Otherwise, returns null.
     */
    public static double[] solveLU(double[][] matrixA, double[] vectorB) {
        if (matrixA.length != matrixA[0].length || matrixA.length != vectorB.length) {
            logger.log(Level.SEVERE, "solveLU has issues. It's either because matrixA is not square or vector B and answer array doesn't have the same size.");
            return null;
        }

        Array2DRowRealMatrix A;
        RealVector B = new ArrayRealVector(vectorB);
        RealVector ans_temp;
        try {
            A = new Array2DRowRealMatrix(matrixA);
        } catch (DimensionMismatchException e) {
            logger.log(Level.SEVERE, "solveLU method from LUOperation has DimensionMismatchException. Check if the matrix is square.");
            return null;
        } catch (NoDataException e) {
            logger.log(Level.SEVERE, "solveLU method from LUOperation has NoDataException. Check if row or column dimension is zero.");
            return null;
        } catch (NullArgumentException e) {
            logger.log(Level.SEVERE, "solveLU method from LUOperation has NullArgumentException. Check if the matrix is null.");
            return null;
        }
        DecompositionSolver solver = new LUDecomposition(A).getSolver();

        try {
            ans_temp = solver.solve(B);
        } catch (DimensionMismatchException e) {
            logger.log(Level.SEVERE, "solveLU method from LUOperation has DimensionMismatchException. Check if the matrices dimension match or not.");
            return null;
        } catch (SingularMatrixException e) {
            logger.log(Level.SEVERE, "solveLU method from LUOperation has SingularMatrixException. Check if the decomposed matrix is singular.");
            return null;
        }

        return ans_temp.toArray();
    }

    /**
     * Prints out the 2D array with description.
     *
     * @param a             The 2D array to be printed.
     * @param description   Description of the array
     */
    public static void print2D(double[][] a, String description) {
        System.out.println(description + ":");
        for(int i = 0; i < a.length; i++) {
            System.out.print("| ");
            for(int j = 0; j < a[i].length; j++){
                System.out.printf("%10.4f", a[i][j]);
            }
            System.out.print(" |\n");
        }
    }

    /**
     * Prints out the array with description.
     *
     * @param a             The array to be printed.
     * @param description   Description of the array.
     */
    public static void print1D(double[] a, String description) {
        System.out.println(description + ":");
        for(int i = 0; i < a.length; i++) {
            System.out.printf("|%10.4f |\n", a[i]);
        }
    }
}
