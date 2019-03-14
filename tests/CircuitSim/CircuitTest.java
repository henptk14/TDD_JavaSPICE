package CircuitSim;

import CircuitOjects.ICS;
import CircuitOjects.IVS;
import CircuitOjects.Resistor;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CircuitTest {
    private Circuit c;

    @Before
    public void setup() {
        c = new Circuit();
    }

    @Test
    public void addElement_nullObject() {
        assertFalse(c.addElement(null));
    }

    @Test
    public void addElement_valid() {
        boolean add1 = c.addElement(new IVS("v1", "1", "0", 21));
        boolean add2 = c.addElement(new Resistor("r1", "1", "2", "1k"));

        assertTrue(add1);
        assertTrue(add2);
        assertEquals(2, c.getElementList().size());
        assertEquals(1, c.getVoltageSourceList().size());
        assertEquals(0, c.getCurrentSourceList().size());
        System.out.println(c.getGraph().toString());
    }

    @Test
    public void addElement_sameElement() {
        boolean add1 = c.addElement(new IVS("v1", "1", "0", 12));
        boolean add2 = c.addElement(new IVS("v1", "1", "2", "20"));
        boolean add3 = c.addElement(new Resistor("r2", "1", "2", 30));

        assertTrue(add1);
        assertFalse(add2);
        assertTrue(add3);
        assertEquals(2, c.getElementList().size());
        assertEquals(1, c.getVoltageSourceList().size());
        assertEquals(0, c.getCurrentSourceList().size());
        assertEquals(2, c.getNodeList().size());
    }

    @Test
    public void addElement_invalidValue() {
        boolean add1 = c.addElement(new Resistor("r1", "1", "2", "30y"));

        assertFalse(add1);
    }

    @Test
    public void removeElement_invalidIndex() {
        c.addElement(new IVS("v1", "1", "0", 12));
        c.addElement(new Resistor("r2", "1", "2", "10k"));
        c.addElement(new Resistor("r3", "2", "3", 1000));
        c.addElement(new Resistor("r4", "2", "0", "2k"));
        c.addElement(new ICS("i1", "3", "0", 10));

        assertEquals(5, c.getElementList().size());
        assertEquals(3, c.getNodeList().size());
        assertEquals(1, c.getVoltageSourceList().size());
        assertEquals(1, c.getCurrentSourceList().size());
        assertEquals(4, c.getGraph().getVertexCount());
        assertEquals(5, c.getGraph().getEdgeCount());

        boolean remove1 = c.removeElement(-1);
        boolean remove2 = c.removeElement(5);

        assertFalse(remove1);
        assertFalse(remove2);
//        assertEquals(4, c.getElementList().size());
//        assertEquals(3, c.getNodeList().size());
//        assertEquals(1, c.getVoltageSourceList().size());
//        assertEquals(1, c.getCurrentSourceList().size());
//        assertEquals(4, c.getGraph().getVertexCount());
//        assertEquals(4, c.getGraph().getEdgeCount());
    }

    @Test
    public void removeElement_valid() {
        c.addElement(new IVS("v1", "1", "0", 12));
        c.addElement(new Resistor("r2", "1", "2", "10k"));
        c.addElement(new Resistor("r3", "2", "3", 1000));
        c.addElement(new Resistor("r4", "2", "0", "2k"));
        c.addElement(new ICS("i1", "3", "0", 10));

        assertEquals(5, c.getElementList().size());
        assertEquals(3, c.getNodeList().size());
        assertEquals(1, c.getVoltageSourceList().size());
        assertEquals(1, c.getCurrentSourceList().size());
        assertEquals(4, c.getGraph().getVertexCount());
        assertEquals(5, c.getGraph().getEdgeCount());

        boolean remove1 = c.removeElement(4);
        boolean remove2 = c.removeElement(2);
        boolean remove3 = c.removeElement(2);
        boolean remove4 = c.removeElement(1);

        assertTrue(remove1);
        assertTrue(remove2);
        assertTrue(remove3);
        assertTrue(remove4);

        assertEquals(1, c.getElementList().size());
        assertEquals(1, c.getNodeList().size());
        assertEquals(0, c.getCurrentSourceList().size());
        assertEquals(1, c.getVoltageSourceList().size());
        assertEquals(2, c.getGraph().getVertexCount());
        assertEquals(1, c.getGraph().getEdgeCount());
    }
}