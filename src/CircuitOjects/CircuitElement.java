package CircuitOjects;

public class CircuitElement {
    protected String name;
    protected String positiveNode, negativeNode;
    protected double value;

    /**
     * This is the base constructor used by all other public constructors
     *
     * @param name              name of the element.
     * @param positiveNode      name of the positive node
     * @param negativeNode      name of the negative node
     */
    private CircuitElement (String name, String positiveNode, String negativeNode) {
        this.name = name;
        this.positiveNode = positiveNode;
        this.negativeNode = negativeNode;
    }

    /**
     * This constructor accepts value as String.
     *
     * @param name          name of the element.
     * @param positiveNode  name of the positive node.
     * @param negativeNode  name of the negative node.
     * @param value         value of the element as String with or without metric prefixes
     */
    public CircuitElement (String name, String positiveNode, String negativeNode, String value) {
        this(name, positiveNode, negativeNode);
        this.value = StringToDoubleConverter(value);
    }

    /**
     * This constructor accepts value as Double.
     *
     * @param name          name of the element.
     * @param positiveNode  name of the positive node.
     * @param negativeNode  name of the negative node.
     * @param value         value of the element as Double.
     */
    public CircuitElement (String name, String positiveNode, String negativeNode, double value) {
        this(name, positiveNode, negativeNode);
        this.value = value;
    }

    /**
     * This method is only going to be used by one of the constructor that accepts value of the circuit component as String.
     * This method converts value passed in as String to Double type.
     *
     * @param v value in String format
     * @return  Returns value in Double if the String value is either numeric or follows the format of numeric value followed by a valid SI unit prefix.
     */
    private double StringToDoubleConverter(String v) {
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
