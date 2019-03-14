package CircuitSim;

import CircuitOjects.CircuitElement;
import CircuitOjects.ICS;
import CircuitOjects.IVS;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class construct the circuit with graph data structure.
 * Also contains useful method for determine validity of the input netlist.
 *
 */
public class Circuit {
    private Graph<String, CircuitElement> graph;    // nodes(String) as vertexes
    private List<CircuitElement> elementList;   // list that stores all element
    private List<CircuitElement> voltageSourceList; // list that stores all voltage source
    private List<CircuitElement> currentSourceList; // list that stores all current source
    private HashMap<String, Integer> nodeList;
    private Logger logger;

    public Circuit(){
        graph = new SparseMultigraph<>();
        elementList = new ArrayList<>();
        voltageSourceList = new ArrayList<>();
        currentSourceList = new ArrayList<>();
        nodeList = new HashMap<>();
        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * This method adds CircuitElement to the elementList, voltageSourceList, currentSourceList,
     * and graph. Returns true if added successfully. Otherwise, returns false.
     *
     * @param element   CircuitElement to be added.
     * @return  returns true if added successfully. Otherwise, returns false.
     */
    public boolean addElement(CircuitElement element) {
        if (element == null || Double.isNaN(element.getValue())) {
            //logger.log(Level.INFO, "Circuit addElement has null input OR value is NaN.");
            return false;
        }

        if (elementList.indexOf(element) == -1) {   // if it is a unique name, add to the list
            elementList.add(element);
            graph.addVertex(element.getPositiveNode());
            graph.addVertex(element.getNegativeNode());
            graph.addEdge(element, element.getPositiveNode(), element.getNegativeNode(), EdgeType.UNDIRECTED);
            if (element instanceof IVS) {
                voltageSourceList.add(element);
            }
            if (element instanceof ICS) {
                currentSourceList.add(element);
            }

            if(element.getPositiveNode().compareTo("0") != 0) { // if positive node is not ground
                //  if the positive node is present in the node list, add one to the integer
                if (nodeList.containsKey(element.getPositiveNode())) {
                    int i = nodeList.get(element.getPositiveNode()) + 1;
                    nodeList.replace(element.getPositiveNode(), i);
                }
                else {
                    nodeList.put(element.getPositiveNode(), 1);
                }
            }

            if(element.getNegativeNode().compareTo("0") != 0) { // if negative node is not ground
                //  if the negative node is present in the node list, add one to the integer
                if (nodeList.containsKey(element.getNegativeNode())) {
                    int i = nodeList.get(element.getNegativeNode());
                    nodeList.replace(element.getNegativeNode(), i);
                }
                else {
                    nodeList.put(element.getNegativeNode(), 1);
                }
            }
            logger.log(Level.INFO, "");
            return true;
        }
        //logger.log(Level.INFO, "The CircuitElement to add already exist.");
        return false;
    }

    /**
     * This method removes CircuitElement from elementList at specified index.
     *
     * @param index     CircuitElement to be removed.
     * @return  Returns true if removed successfully. Otherwise, returns false.
     */
    public boolean removeElement(int index) {
        if (index < 1 || index >= elementList.size()) {
            //logger.log(Level.INFO, "removeElement from Circuit has invalid index.");
            return false;
        }

        CircuitElement temp = elementList.remove(index);
        if (temp == null || !graph.removeEdge(temp)) {
            return false;
        }

        if (temp instanceof IVS) {
            if (voltageSourceList.remove(temp) == false) {
                logger.log(Level.SEVERE, "removeElement in Circuit failed. Item mismatch between elementList and voltageSourceList.");
                return false;
            }
        }

        if (temp instanceof ICS) {
            if (currentSourceList.remove(temp) == false) {
                logger.log(Level.SEVERE, "removeElement in Circuit failed. Item mismatch between elementList and currentSourceList.");
                return false;
            }
        }

        if (nodeList.get(temp.getPositiveNode()) != null) {
            //logger.log(Level.INFO, "We in removeElement removing positive node section.");
            int i = nodeList.get(temp.getPositiveNode()) - 1;
            if (i == 0) {
                Integer rm = nodeList.remove(temp.getPositiveNode());
                if (rm == null || !graph.removeVertex(temp.getPositiveNode())){
                    logger.log(Level.SEVERE, "removeElement in Circuit failed. Remove in nodeList or graph failed.");
                    return false;
                }
            }
            else {
                nodeList.replace(temp.getPositiveNode(), i);
            }
        }

        if (nodeList.get(temp.getNegativeNode()) != null) {
            //logger.log(Level.INFO, "We in removeElement removing negative node section.");
            int i = nodeList.get(temp.getNegativeNode()) - 1;
            if (i == 0) {
                Integer rm = nodeList.remove(temp.getNegativeNode());
                if (rm == null || !graph.removeVertex(temp.getNegativeNode())) {
                    logger.log(Level.SEVERE, "removeElement in Circuit failed. Remove in nodeList or graph failed.");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValid() {
        if (elementList.size() > 1 && nodeList.size() > 1 && (voltageSourceList.size() < 1 && currentSourceList.size() < 1)){
            return true;
        }
        return false;
    }

    public Graph<String, CircuitElement> getGraph() {
        return graph;
    }

    public List<CircuitElement> getElementList() {
        return elementList;
    }

    public List<CircuitElement> getVoltageSourceList() {
        return voltageSourceList;
    }

    public List<CircuitElement> getCurrentSourceList() {
        return currentSourceList;
    }

    public List<String> getNodeList() {
        return new ArrayList<>(nodeList.keySet());}
}
