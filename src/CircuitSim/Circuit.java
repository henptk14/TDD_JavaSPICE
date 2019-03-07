package CircuitSim;

import CircuitOjects.CircuitElement;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

/**
 * This class construct the circuit with graph data structure.
 * Also contains useful method for determine validity of the input netlist.
 */
public class Circuit {
    private Graph<String, CircuitElement> g;

    public Circuit(){
        this.g = new SparseMultigraph<>();
    }

    public 
}
