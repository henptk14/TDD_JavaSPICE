package CircuitSim;

import CircuitOjects.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */

public class CircuitSim {
    private Logger logger;
    private List<CircuitElement> elementList;
    private List<CircuitElement> voltageSourceList;
    private List<CircuitElement> currentSourceList;
    private HashMap<String, Integer> nodeList;

    /**
     * Default constructor.
     */
    public CircuitSim() {
        elementList = new ArrayList<>();
        voltageSourceList = new ArrayList<>();
        nodeList = new HashMap<>();
        currentSourceList = new ArrayList<>();
        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * This constructor accepts three parameters.
     *
     * @param elementList           list of circuit elements.
     * @param voltageSourceList     list of voltage sources.
     * @param nodeList              list of nodes.
     */
    public CircuitSim(List<CircuitElement> elementList, List<CircuitElement> voltageSourceList, HashMap<String, Integer> nodeList) {
        this.elementList = new ArrayList<>(elementList);
        this.voltageSourceList = new ArrayList<>(voltageSourceList);
        this.nodeList = new HashMap<>(nodeList);
        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * This method adds a CircuitElement to the list. Returns true if added successfully. Otherwise, returns false.
     *
     * @param element   CircuitElement to be added.
     * @return          Returns true if added successfully. Otherwise, returns false.
     */
    public boolean addElement(CircuitElement element) {
        if (element == null || Double.isNaN(element.getValue())) {
            logger.log(Level.INFO, "CircuitSim addElement has null input OR value is NaN because of invalid value String.");
            return false;
        }

        if (elementList.indexOf(element) == -1) {   // if it is a unique name, add to the list
            elementList.add(element);
            if (element instanceof IVS) {
                voltageSourceList.add(element);
            }
            if (element instanceof ICS) {
                currentSourceList.add(element);
            }

            if (element.getPositiveNode().compareTo("0") != 0) {
                if (nodeList.containsKey(element.getPositiveNode())) {
                    int i = nodeList.get(element.getPositiveNode()) + 1;
                    nodeList.replace(element.getPositiveNode(), i);
                }
                else {
                    nodeList.put(element.getPositiveNode(), 1);
                }
            }
            if (element.getNegativeNode().compareTo("0") != 0) {
                if (nodeList.containsKey(element.getNegativeNode())) {
                    int i = nodeList.get(element.getNegativeNode()) + 1;
                    nodeList.replace(element.getNegativeNode(), i);
                }
                else {
                    nodeList.put(element.getNegativeNode(), 1);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * This method removes CircuitElement from the list. Returns true if removed successfully. Otherwise, returns false.
     *
     * @param index     index of CircuitElement to be removed.
     * @return          Returns true if removed successfully. Otherwise, returns false.
     */
    public boolean removeElement(int index) {
        if (index < 0 || index >= elementList.size()) {
            logger.log(Level.INFO, "removeElement in CircuitSim failed. The passed in index to be removed: " + index);
            return false;
        }

        CircuitElement temp = elementList.get(index);
        if (temp instanceof IVS) {
            if (voltageSourceList.remove(temp) == false) {
                logger.log(Level.SEVERE, "removeElement in CircuitSim failed. Item mismatch between element and voltage source lists.");
                return false;
            }
        }
        if (temp instanceof ICS) {
            if (currentSourceList.remove(temp) == false) {
                logger.log(Level.SEVERE, "removeElement in CircuitSim failed. Item mismatch between element and current source list.");
                return false;
            }
        }

        if (nodeList.get(temp.getPositiveNode()) != null) {
            int i = nodeList.get(temp.getPositiveNode()) - 1;
            if (i == 0) {
                nodeList.remove(temp.getPositiveNode());
            }
            else {
                nodeList.replace(temp.getPositiveNode(), i);
            }
        }

        if (nodeList.get(temp.getNegativeNode()) != null) {
            int i = nodeList.get(temp.getNegativeNode()) - 1;
            if (i == 0) {
                nodeList.remove(temp.getNegativeNode());
            }
            else {
                nodeList.replace(temp.getNegativeNode(), i);
            }
        }

        elementList.remove(index);
        return true;
    }


    /**
     * Calculates node voltages and branch current.
     * @return  Return CircuitResult object if successful. Otherwise, returns null.
     */
    public CircuitResult calculate() {
        if (elementList.size() > 1 && nodeList.size() > 1 && !(voltageSourceList.size() < 1 && currentSourceList.size() < 1)) {
            List<String> nodes = new ArrayList<>(nodeList.keySet());
            int size = nodes.size() + voltageSourceList.size();
            double[][] matrixA = new double[size][size];
            double[] vecB = new double[size];

            if (stamp(matrixA, vecB, nodes)) {
                double[] ans = LUOperation.solveLU(matrixA, vecB);
                if (ans != null) {
                    CircuitResult result = new CircuitResult(ans, nodes);
                    return result;
                }
            }
        }
        logger.log(Level.SEVERE, "calculate method from CircuitSim returned null. Unknown issue.");
        return null;
    }

    /**
     * This private method stamps all elements to the matrix and vector.
     * @param matrixA   Left hand side matrix A to be stamped.
     * @param b         Right hand side vector B to be stamped.
     * @return          Returns true if stamped successfully. Otherwise, returns false.
     */
    public boolean stamp(double[][] matrixA, double[] b, List<String> nodes) {
        if (nodes.size() < 1) {
            logger.log(Level.INFO, "stamp method from CircuitSim returned false because node list less than 1.");
            return false;
        }

        if (matrixA.length < 1 || matrixA.length != matrixA[0].length || matrixA.length != b.length) {
            logger.log(Level.INFO, "stamp method from CircuitSim returned false because matrix size is invalid.");
            return false;
        }

        for (CircuitElement c : elementList) {
            if (c instanceof IVS) {
                int pn = nodes.indexOf(c.getPositiveNode());
                int nn = nodes.indexOf(c.getNegativeNode());
                int k = nodes.size() + voltageSourceList.indexOf(c);
                if (!((IVS) c).stamp(pn, nn, k, c.getValue(), matrixA, b)) {
                    return false;
                }
            }
            if (c instanceof ICS) {
                int pn = nodes.indexOf(c.getPositiveNode());
                int nn = nodes.indexOf(c.getNegativeNode());
                if (!((ICS) c).stamp(pn, nn, c.getValue(), b)) {
                    return false;
                }
            }
            if (c instanceof Resistor) {
                int pn = nodes.indexOf(c.getPositiveNode());
                int nn = nodes.indexOf(c.getNegativeNode());
                if (!((Resistor) c).stamp(pn, nn, c.getValue(), matrixA)) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<CircuitElement> getElementList() {
        return elementList;
    }

    public List<CircuitElement> getVoltageSourceList() {
        return voltageSourceList;
    }

    public List<String> getNodeList() {
        return new ArrayList<>(nodeList.keySet());
    }
}
