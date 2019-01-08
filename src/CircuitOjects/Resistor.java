package CircuitOjects;

public class Resistor extends CircuitElement {
    public Resistor(String name, String positiveNode, String negativeNode, String value) {
        super(name, positiveNode, negativeNode, value);
    }

    public Resistor(String name, String positiveNode, String negativeNode, double value) {
        super(name, positiveNode, negativeNode, value);
    }

    /**
     * This is the stamping method for resistor. The stamping goes like this,
     *      N+      N-
     *  N+  1/R     -1/R
     *  N-  -1/R    1/R
     *
     *  In cases of when positive node or negative node is connected to ground, the entire row or column will not be stamped.
     *
     * @param positiveNode  position of positve node.
     * @param negativeNode  position of negative node.
     * @param value         resistance value.
     * @param matrixA       the left hand matrix A to be stamped on.
     * @return              Returns true if it is stamped successfully. Otherwise, returns false.
     */
    public boolean stamp(int positiveNode, int negativeNode, double value, double[][] matrixA){
        if(Double.isNaN(value) || matrixA.length < 2 || matrixA.length != matrixA[0].length) {
            return false;
        }

        if(positiveNode == -1 && withinBound(negativeNode, matrixA)) {    // if positive node is connected to ground
            matrixA[negativeNode][negativeNode] += (1 / value);
            return true;
        }
        else if (negativeNode == -1 && withinBound(positiveNode, matrixA)) {  // if negative node is connected to ground
            matrixA[positiveNode][positiveNode] += (1 / value);
            return true;
        }
        else if (withinBound(positiveNode, matrixA) && withinBound(negativeNode, matrixA)){
            matrixA[positiveNode][positiveNode] += (1 / value);
            matrixA[positiveNode][negativeNode] -= (1 / value);
            matrixA[negativeNode][positiveNode] -= (1 / value);
            matrixA[negativeNode][negativeNode] += (1 / value);
            return true;
        }
        return false;
    }

    /**
     * This is a private class that is used to check if a particular node is within the size of the matrix.
     * This is to prevent ArrayOutOfBound exceptions.
     * @param bound is the node to be checked.
     * @param A is the matrix to be compared against.
     * @return Returns true if bound is within the size of matrix. Otherwise, returns false.
     */
    private boolean withinBound(int bound, double[][] A) {
        if(bound >= 0 && bound <= A.length && bound <= A[0].length) {
            return true;
        }
        return false;
    }
}
