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

    public boolean addElement(CircuitElement element) {
        if (element == null) {
            logger.log(Level.SEVERE, "CircuitSim addElement has null input.");
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
