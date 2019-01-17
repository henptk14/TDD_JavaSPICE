package CircuitSim;

import LUObject.*;
import CircuitOjects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CircuitSim {
    private Logger logger;
    private List<CircuitElement> elementList;
    private List<CircuitElement> voltageSourceList;
    private List<String> nodeList;

    /**
     * Default constructor.
     */
    public CircuitSim() {
        elementList = new ArrayList<>();
        voltageSourceList = new ArrayList<>();
        nodeList = new ArrayList<>();
        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * This constructor accepts three parameters.
     *
     * @param elementList           list of circuit elements.
     * @param voltageSourceList     list of voltage sources.
     * @param nodeList              list of nodes.
     */
    public CircuitSim(List<CircuitElement> elementList, List<CircuitElement> voltageSourceList, List<String> nodeList) {
        this.elementList = new ArrayList<>(elementList);
        this.voltageSourceList = new ArrayList<>(voltageSourceList);
        this.nodeList = new ArrayList<>(nodeList);
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
            if (element.getPositiveNode().compareTo("0") != 0 && !nodeList.contains(element.getPositiveNode())) {
                nodeList.add(element.getPositiveNode());
            }
            if (element.getNegativeNode().compareTo("0") != 0 && !nodeList.contains(element.getNegativeNode())) {
                nodeList.add(element.getNegativeNode());
            }
            return true;
        }

        return false;
    }

    /**
     * This method removes CircuitElement from the list. Returns true if removed successfully. Otherwise, returns false.
     *
     * @param element   CircuitElement to be removed.
     * @return          Returns true if removed successfully. Otherwise, returns false.
     */
    public boolean removeElement(CircuitElement element) {
        if (element == null || Double.isNaN(element.getValue())) {
            logger.log(Level.INFO, "CircuitSim removeElement has null input OR invalid object");
            return false;
        }

        return false;
    }

    public List<CircuitElement> getElementList() {
        return elementList;
    }

    public List<CircuitElement> getVoltageSourceList() {
        return voltageSourceList;
    }

    public List<String> getNodeList() {
        return nodeList;
    }
}
