package CircuitOjects;

import sun.rmi.runtime.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class IVS extends CircuitElement {
    private boolean stamped;
    private Logger ivsLogger;

    /**
     * This constructor accepts value as String with or without metric prefix.
     *
     * @param name          name of independent voltage source.
     * @param positiveNode  name of positive node.
     * @param negativeNode  name of negative node.
     * @param value         value with or without metric prefix.
     */
    public IVS (String name, String positiveNode, String negativeNode, String value) {
        super(name, positiveNode, negativeNode, value);
        stamped = false;
        ivsLogger = Logger.getLogger(IVS.class.getName());
    }

    /**
     * This constructor accepts value as type double. So no metric prefix.
     * @param name          name of independent voltage source.
     * @param positiveNode  name of positive node.
     * @param negativeNode  name of negative node.
     * @param value         value of voltage in volt.
     */
    public IVS (String name, String positiveNode, String negativeNode, double value){
        super(name, positiveNode, negativeNode, value);
        stamped = false;
        ivsLogger = Logger.getLogger(IVS.class.getName());
    }

    /**
     * This is the stamping method for independent voltage source. The stamping goes like this,
     *      N+      N-      i_k     RHS
     *  N+  0       0       1       0
     *  N-  0       0       -1      0
     *  i_k 1       -1      0       V_k
     *
     *  In the cases of when positive node or negative node is connected to ground, the entire row or column will not be stamped.
     *
     * @param positiveNode  position of positive node in the List.
     * @param negativeNode  position of negative node in the List.
     * @param branchK       position of IVS in the voltage source List.
     * @param value         voltage value.
     * @param matrixA       the left hand matrix A to be stamped on.
     * @param vectorB       the right hand vector B to be stamped on.
     * @return              Returns true if stamped successfully. Otherwise, returns false.
     */
    public boolean stamp (int positiveNode, int negativeNode, int branchK, double value, double[][] matrixA, double[] vectorB) {
        if(stamped){
            ivsLogger.log(Level.INFO, name + " is already stamped!");
            return false;
        }
        if(Double.isNaN(value) || matrixA.length < 2 || matrixA.length != matrixA[0].length || matrixA.length != vectorB.length) {
            ivsLogger.log(Level.INFO, name + "'s stamp method has issues. It's either because value is NaN OR matrix A length is less than 2 OR matrix A is not a square matrix OR matrix A length is not equal to vector B!!");
            return false;
        }

        if(positiveNode == -1 && withinBound(negativeNode, matrixA) && withinBound(branchK, matrixA) && withinBound(branchK, vectorB)) { // if N+ is connected to ground
            matrixA[negativeNode][branchK] -= 1;
            matrixA[branchK][negativeNode] -= 1;

            vectorB[branchK] = value;
            stamped = true;
            return true;
        }
        else if(negativeNode == -1 && withinBound(positiveNode, matrixA) && withinBound(branchK, vectorB)) {    // if N- is connected to ground
            matrixA[positiveNode][branchK] += 1;
            matrixA[branchK][positiveNode] += 1;

            vectorB[branchK] = value;
            stamped = true;
            return true;
        }
        else if(withinBound(positiveNode, matrixA) && withinBound(negativeNode, matrixA) && withinBound(branchK, vectorB)) {
            matrixA[positiveNode][branchK] += 1;
            matrixA[negativeNode][branchK] -= 1;
            matrixA[branchK][positiveNode] += 1;
            matrixA[branchK][negativeNode] -= 1;

            vectorB[branchK] = value;
            stamped = true;
            return true;
        }
        ivsLogger.log(Level.INFO, name + "'s stamp method has issues. It's either node position OutOfBound or other issues!");
        return false;
    }

    /**
     * This is a private method that is used to check if a particular node is within the size of the matrix.
     * This is to prevent ArrayOutOfBound exceptions.
     * @param node is the node to be checked.
     * @param A is the matrix to be compared against.
     * @return Returns true if node is within the size of matrix. Otherwise, returns false.
     */
    private boolean withinBound(int node, double[][] A) {
        if(node >= 0 && node <= A.length - 1 && node <= A[0].length - 1) {
            return true;
        }
        ivsLogger.log(Level.INFO, name + "'s private withinBound(int, double[][]) method returned false! Node: " + node);
        return false;
    }

    /**
     * This is a private method that is used to check if branch k is within the size of vector B.
     * This is to prevent ArrayOutOfBound exceptions.
     * @param k is the branch to be checked.
     * @param B is the vector B to be compared against.
     * @return  Returns true if branch K is within the size of vector B. Otherwise, returns false.
     */
    private boolean withinBound(int k, double[] B) {
        if(k >= 0 && k <= B.length - 1){
            return true;
        }
        ivsLogger.log(Level.INFO, name + "'s private withinBound(int, double[]) method returned false! K: " + k);
        return false;
    }

    public boolean isStamped() {
        return stamped;
    }
}
