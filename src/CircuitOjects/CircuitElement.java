package CircuitOjects;

public class CircuitElement {
    protected String name;
    protected String positiveNode, negativeNode;
    protected double value;

    private CircuitElement (String name, String positiveNode, String negativeNode) {
        this.name = name;
        this.positiveNode = positiveNode;
        this.negativeNode = negativeNode;
    }

    public CircuitElement (String name, String positiveNode, String negativeNode, String value) {
        this(name, positiveNode, negativeNode);
        this.value = StringToDoubleConverter(value);
    }

    public CircuitElement (String name, String positiveNode, String negativeNode, double value) {
        this(name, positiveNode, negativeNode);
        this.value = value;
    }

    protected double StringToDoubleConverter(String v) {
        if(isNumeric(v)) {
            return Double.valueOf(v);
        }

        double tempValue;
        try {
            tempValue = Double.valueOf(v.substring(0, v.length() - 1));
        } catch (NumberFormatException e) {
            return Double.NaN;
        }

        char unit = v.charAt(v.length() - 1);
        switch (unit) {
            case 'k':
                tempValue *= 1000;
                break;

            case 'M':
                tempValue *= 1E6;
                break;

            case 'G':
                tempValue *= 1E9;
                break;

            case 'T':
                tempValue *= 1E12;
                break;

            case 'P':
                tempValue *= 1E15;
                break;

            case 'm':
                tempValue *= 1E-3;
                break;

            case 'u':
                tempValue *= 1E-6;
                break;

            case 'n':
                tempValue *= 1E-9;
                break;

            case 'p':
                tempValue *= 1E-12;
                break;

            case 'f':
                tempValue *= 1E-15;
                break;

            default:
                tempValue = Double.NaN;
                break;
        }
        return tempValue;
    }

    /**
     * This function test if a string is numeric or not.
     * @param str this is the string to be tested.
     * @return returns true if str is a number. Otherwise, false.
     */
    private boolean isNumeric(String str) {
        try {
            double v = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public String getPositiveNode() {
        return positiveNode;
    }

    public String getNegativeNode() {
        return negativeNode;
    }

    public double getValue() {
        return value;
    }
}
