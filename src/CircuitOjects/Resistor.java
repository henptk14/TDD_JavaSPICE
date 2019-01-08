package CircuitOjects;

public class Resistor extends CircuitElement {
    public Resistor(String name, String positiveNode, String negativeNode, String value) {
        super(name, positiveNode, negativeNode, value);
    }

    public Resistor(String name, String positiveNode, String negativeNode, double value) {
        super(name, positiveNode, negativeNode, value);
    }

    public boolean stamp(int positiveNode, int negativeNode, double value, double[][] matrixA){
        if(positiveNode == -1) {    // if positive node is connected to ground
            matrixA[negativeNode][negativeNode] += (1 / value);
            return true;
        }
        else if (negativeNode == -1) {  // if negative node is connected to ground
            matrixA[positiveNode][positiveNode] += (1 / value);
            return true;
        }
        else {
            
        }
    }
}
