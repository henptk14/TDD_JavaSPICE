package CircuitOjects;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CircuitElementTest {

    @Test
    public void stringToDoubleConverter_NoUnit() {
        CircuitElement element = new CircuitElement("v1", "1", "2", "12");
        assertEquals(12, element.getValue(), 0);
    }

    @Test
    public void stringToDoubleConverter_InvalidUnit() {
        CircuitElement element = new CircuitElement("v1", "1", "2", "12h");
        assertEquals(Double.NaN, element.getValue(), 0);
    }

    @Test
    public void stringToDoubleConverter_GarbageValue() {
        CircuitElement element = new CircuitElement("v1", "1", "2", "afda");
        assertEquals(Double.NaN, element.getValue(), 0);
    }

    @Test
    public void stringToDoubleConverter_InvalidFormat() {
        CircuitElement element = new CircuitElement("v1", "1", "2", "12m");
        assertEquals(0.012, element.getValue(), 0);
    }

    @Test
    public void equals_equal() {
        CircuitElement e1 = new IVS("v1", "1", "2", 12);
        CircuitElement e2 = new Resistor("v1", "0", "3", 12);
        assertTrue(e1.equals(e2));
    }

    @Test
    public void equals_notEqual() {
        CircuitElement e1 = new IVS("v1", "1", "2", 12);
        CircuitElement e2 = new IVS("v2", "1", "2", 12);
        assertFalse(e1.equals(e2));
    }
}