package CircuitOjects;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Resistor extends CircuitElement {
    private Logger logger;
    private boolean stamped;

    /**
     * This constructor accepts value as String with or without metric prefix.
     *
     * @param name          name of resistor.
     * @param positiveNode  name of positive node.
     * @param negativeNode  name of negative node.
     * @param value         value of resistor with or without metric prefix.
     */
    public Resistor(String name, String positiveNode, String negativeNode, String value) {
        super(name, positiveNode, negativeNode, value);
        stamped = false;
        logger = Logger.getLogger(Resistor.class.getName());
    }

    /**
     * This constructor accepts value as type double. So no metric prefix.
     *
     * @param name          name of resistor.
     * @param positiveNode  name of positive node.
     * @param negativeNode  name of negative node.
     * @param value         value of resistance in Ohm.
     */
    public Resistor(String name, String positiveNode, String negativeNode, double value) {
        super(name, positiveNode, negativeNode, value);
        stamped = false;
        logger = Logger.getLogger(Resistor.class.getName());
    }

    /**
     * This is the stamping method for resistor. The stamping goes like this,
     *      N+      N-
     *  N+  1/R     -1/R
     *  N-  -1/R    1/R
     *
     *  In cases of when positive node or negative node is connected to ground, the entire row or column will not be stamped.
     *
     * @param positiveNode  position of positve node in the List.
     * @param negativeNode  position of negative node in the List.
     * @param value         resistance value.
     * @param matrixA       the left hand matrix A to be stamped on.
     * @return              Returns true if it is stamped successfully. Otherwise, returns false.
     */
    public boolean stamp(int positiveNode, int negativeNode, double value, double[][] matrixA){
        if (stamped) {
            logger.log(Level.INFO, name + " is already stamped.");
            return false;
        }

        if(Double.isNaN(value) || matrixA.length < 2 || matrixA.length != matrixA[0].length) {
            logger.log(Level.INFO, name + "'s stamp method has issues. It's either because value is NaN OR matrix A size is less than 2 OR matrix A is non square.");
            return false;
        }

        if(positiveNode == -1 && withinBound(negativeNode, matrixA)) {    // if positive node is connected to ground
            matrixA[negativeNode][negativeNode] += (1 / value);
            stamped = true;
            return true;
        }
        else if (negativeNode == -1 && withinBound(positiveNode, matrixA)) {  // if negative node is connected to ground
            matrixA[positiveNode][positiveNode] += (1 / value);
            stamped = true;
            return true;
        }
        else if (withinBound(positiveNode, matrixA) && withinBound(negativeNode, matrixA)){
            matrixA[positiveNode][positiveNode] += (1 / value);
            matrixA[positiveNode][negativeNode] -= (1 / value);
            matrixA[negativeNode][positiveNode] -= (1 / value);
            matrixA[negativeNode][negativeNode] += (1 / value);
            stamped = true;
            return true;
        }

        logger.log(Level.INFO, name + "'s stamp method has issues. It's either node position OutOfBound or other issues!");
        return false;
    }

    /**
     * This is a private method that is used to check if a particular node is within the size of the matrix.
     * This is to prevent ArrayOutOfBound exceptions.
     * @param bound is the node to be checked.
     * @param A is the matrix to be compared against.
     * @return Returns true if bound is within the size of matrix. Otherwise, returns false.
     */
    private boolean withinBound(int bound, double[][] A) {
        if(bound >= 0 && bound <= A.length - 1 && bound <= A[0].length - 1) {
            return true;
        }
        logger.log(Level.INFO, name + "'s private withinBound method returned false! bound: " + bound);
        return false;
    }

    public boolean isStamped() {
        return stamped;
    }
}
