package CircuitSim;

import CircuitOjects.CircuitElement;
import CircuitOjects.ICS;
import CircuitOjects.IVS;
import CircuitOjects.Resistor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CircuitSimTest {

    private CircuitSim c;

    @Before
    public void setup() {
        c = new CircuitSim();
    }

    @Test
    public void addElement_nullObject() {
        assertFalse(c.addElement(null));
    }

    @Test
    public void addElement_valid() {
        boolean add1 = c.addElement(new IVS("v1", "1", "2", 12));
        boolean add2 = c.addElement(new IVS("v2", "3", "0", "15k"));
        assertTrue(add1);
        assertEquals(2, c.getElementList().size());
        assertEquals(2, c.getVoltageSourceList().size());
        assertEquals(3, c.getNodeList().size());
    }

    @Test
    public void addElement_sameElement() {
        boolean add1 = c.addElement(new Resistor("r1", "1", "2", 10));
        boolean add2 = c.addElement(new ICS("r1", "2", "0", "13"));

        assertTrue(add1);
        assertFalse(add2);
        assertEquals(1, c.getElementList().size());
        assertEquals(0, c.getVoltageSourceList().size());
        assertEquals(2, c.getNodeList().size());
    }

    @Test
    public void addElement_invalidValue() {
        boolean add1 = c.addElement(new Resistor("r1", "1", "2", "2h"));

        assertFalse(add1);
    }

    @Test
    public void removeElement_invalidIndex() {
        c.addElement(new IVS("v1", "1", "0", 12));
        c.addElement(new Resistor("r2", "2", "3", "15k"));
        c.addElement(new Resistor("r3", "2", "3", "20k"));
        c.addElement(new Resistor("r4", "3", "0", 500));
        c.addElement(new ICS("a1", "3", "0", 12));

        assertFalse(c.removeElement(-1));
        assertFalse(c.removeElement(5));

        assertEquals(5, c.getElementList().size());
        assertEquals(1, c.getVoltageSourceList().size());
        assertEquals(3, c.getNodeList().size());
    }

    @Test
    public void removeElement_valid() {
        c.addElement(new IVS("v1", "1", "0", 12));
        c.addElement(new Resistor("r2", "2", "3", "15k"));
        c.addElement(new Resistor("r3", "2", "3", "20k"));
        c.addElement(new Resistor("r4", "3", "0", 500));
        c.addElement(new ICS("a1", "3", "0", 12));

        assertTrue(c.removeElement(0));
        assertTrue(c.removeElement(3));
        assertTrue(c.removeElement(1));

        assertEquals(2, c.getElementList().size());
        assertEquals(0, c.getVoltageSourceList().size());
        assertEquals(2, c.getNodeList().size());
    }
}