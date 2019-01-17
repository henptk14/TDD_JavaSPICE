package CircuitOjects;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ICS extends CircuitElement {
    private Logger icsLogger;
    private boolean stamped;

    /**
     * This constructor accepts value as String with or without metric prefix.
     *
     * @param name          name of independent current source.
     * @param positiveNode  name of positive node.
     * @param negativeNode  name of negative node.
     * @param value         value of current source with or without metric prefix.
     */
    public ICS (String name, String positiveNode, String negativeNode, String value) {
        super(name, positiveNode, negativeNode, value);
        stamped = false;
        icsLogger = Logger.getLogger(ICS.class.getName());
    }

    /**
     * This constructor accepts value as type double. So no metric prefix.
     *
     * @param name          name of independent current source.
     * @param positiveNode  name of positive node.
     * @param negativeNode  name of negative node.
     * @param value         value of current in ampere.
     */
    public ICS(String name, String positiveNode, String negativeNode, double value) {
        super(name, positiveNode, negativeNode, value);
        stamped = false;
        icsLogger = Logger.getLogger(ICS.class.getName());
    }

    /**
     * This is the stamping method for independent current source. The stamping only involes RHS and it goes like this,
     *      RHS
     * N+   -I
     * N-   +I
     *
     * In cases of when positive node or negative node is connected to ground, the entire row will not be stamped.
     *
     * @param positiveNode  position of positive node in the List.
     * @param negativeNode  position of negative node in the List.
     * @param value         Ampere value.
     * @param vectorB       the right hand side vector B to be stamped on.
     * @return              Returns true if stamped successefully. Otherwise, returns false.
     */
    public boolean stamp(int positiveNode, int negativeNode, double value, double[] vectorB) {
        if (stamped) {
            icsLogger.log(Level.INFO, name + " is already stamped!");
            return false;
        }

        if (Double.isNaN(value) || vectorB.length < 1) {
            icsLogger.log(Level.INFO, name + "'s stamp method has issues. It's either because value is NaN OR vector B length is less than 1!");
            return false;
        }

        if (negativeNode == -1 && withinBound(positiveNode, vectorB)) {
            vectorB[positiveNode] -= value;
            stamped = true;
            return true;
        }
        else if (positiveNode == -1 && withinBound(negativeNode, vectorB)) {
            vectorB[negativeNode] += value;
            stamped = true;
            return true;
        }
        else if (withinBound(positiveNode, vectorB) && withinBound(negativeNode, vectorB)) {
            vectorB[positiveNode] -= value;
            vectorB[negativeNode] += value;
            stamped = true;
            return true;
        }
        icsLogger.log(Level.INFO, name + "'s stamp method has issues. It's either node position OutOfBound or other issues!");
        return false;
    }

    /**
     * This is a private method that is used to check if a node is within the size of the vector B.
     * This is to prevent ArrayOutOfBound exceptions.
     * @param node  is the node to be checked.
     * @param B     is the vector B to be compared against.
     * @return      Returns true if node is within the size of vector B. Otherwise, returns false.
     */
    private boolean withinBound(int node, double[] B) {
        if (node >= 0 && node <= B.length - 1) {
            return true;
        }
        icsLogger.log(Level.INFO, name + "'s private withinBound(int, double[]) method returned false. Node: " + node);
        return false;
    }

    public boolean isStamped() {
        return stamped;
    }
}
