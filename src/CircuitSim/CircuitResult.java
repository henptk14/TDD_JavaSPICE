package CircuitSim;

import java.util.ArrayList;
import java.util.List;

public class CircuitResult {
    private double[] nodeVoltages;
    private List<String> nodes;
    private double[] branchCurrents;

    public CircuitResult(double[] nodeVoltages, List<String> nodes) {
        this.nodes = new ArrayList<>();
        this.nodeVoltages = new double[nodeVoltages.length];
        for (int i = 0; i < nodeVoltages.length; i++) {
            this.nodeVoltages[i] = nodeVoltages[i];
            this.nodes.add(nodes.get(i));
        }
    }

    public CircuitResult(double[] nodeVoltages, List<String> nodes, double[] branchCurrents) {
        this(nodeVoltages, nodes);
        this.branchCurrents = new double[branchCurrents.length];
        for (int i = 0; i < branchCurrents.length; i++) {
            this.branchCurrents[i] = branchCurrents[i];
        }
    }

    public double[] getNodeVoltages() {
        return nodeVoltages;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public double[] getBranchCurrents() {
        return branchCurrents;
    }
}
