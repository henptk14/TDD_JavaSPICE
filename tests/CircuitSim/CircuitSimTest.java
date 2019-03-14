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
        boolean add1 = c.addElement(new IVS("v1", "1", "0", 20));
        boolean add2 = c.addElement(new Resistor("r1", "1", "2", "1k"));
        boolean add3 = c.addElement(new Resistor("r2", "2", "0", 2000));
        boolean add4 = c.addElement(new Resistor("r3", "2", "3", "3k"));
        boolean add5 = c.addElement(new ICS("a1", "3", "0", 1));

        assertTrue(add1);
        assertTrue(add2);
        assertTrue(add3);
        assertTrue(add4);
        assertTrue(add5);

        assertEquals(5, c.getElementList().size());
        assertEquals(1, c.getVoltageSourceList().size());
        assertEquals(1, c.getCurrentSourceList().size());
        assertEquals(3, c.getNodeList().size());
        assertEquals(4, c.getGraph().getVertexCount());
        assertEquals(5, c.getGraph().getEdgeCount());
    }

    @Test
    public void addElement_sameElement() {
        boolean add1 = c.addElement(new IVS("v1", "1", "0", 12));
        boolean add2 = c.addElement(new Resistor("r2", "1", "2", "1k"));
        boolean add3 = c.addElement(new Resistor("r2", "2", "0", "5M"));

        assertTrue(add1);
        assertTrue(add2);
        assertFalse(add3);

        assertEquals(2, c.getElementList().size());
        assertEquals(1, c.getVoltageSourceList().size());
        assertEquals(0, c.getCurrentSourceList().size());
        assertEquals(2, c.getNodeList().size());
        assertEquals(3, c.getGraph().getVertexCount());
        assertEquals(2, c.getGraph().getEdgeCount());
    }

    @Test
    public void addElement_invalidValue() {
        boolean add1 = c.addElement(new IVS("v1", "1", "0", "2o"));

        assertFalse(add1);
        assertEquals(0, c.getElementList().size());
        assertEquals(0, c.getVoltageSourceList().size());
        assertEquals(0, c.getCurrentSourceList().size());
        assertEquals(0, c.getNodeList().size());
        assertEquals(0, c.getGraph().getVertexCount());
        assertEquals(0, c.getGraph().getEdgeCount());
    }

    @Test
    public void removeElement_invalidIndex() {
        boolean add1 = c.addElement(new IVS("v1", "1", "0", "12"));
        boolean add2 = c.addElement(new Resistor("r1", "1", "2", "1k"));
        boolean add3 = c.addElement(new Resistor("r2", "2", "0", "200k"));

        assertTrue(add1);
        assertTrue(add2);
        assertTrue(add3);

        boolean rm1 = c.removeElement(1);
        boolean rm2 = c.removeElement(1);

        assertTrue(rm1);
        assertTrue(rm2);

        assertEquals(1, c.getElementList().size());
        assertEquals(1, c.getVoltageSourceList().size());
        assertEquals(0, c.getCurrentSourceList().size());
        assertEquals(1, c.getNodeList().size());
        assertEquals(2, c.getGraph().getVertexCount());
        assertEquals(1, c.getGraph().getEdgeCount());
    }

    @Test
    public void stamp_valid() {
        c.addElement(new IVS("v1", "3", "0", 12));
        c.addElement(new Resistor("r2", "3", "1", 100));
        c.addElement(new Resistor("r3", "1", "0", 1000));
        c.addElement(new Resistor("r4", "1", "0", 500));

        CircuitResult res = c.calculate();

        assertEquals(4, c.getElementList().size());
        assertEquals(2, c.getNodeList().size());
        assertEquals(1, c.getVoltageSourceList().size());
        assertNotNull(res);

        assertEquals(3, res.getNodeVoltages().length);
        for (int i = 0; i < res.getNodeVoltages().length; i++) {
            System.out.print(res.getNodeVoltages()[i] + ", ");
        }
        System.out.println();
        for (int i = 0; i < res.getNodes().size(); i++) {
            System.out.print(res.getNodes().get(i) + ", ");
        }
    }

    @Test
    public void stamp_openCircuit() {
        c.addElement(new IVS("v1", "3", "0", 12));
        c.addElement(new Resistor("r2", "3", "1", 100));
        c.addElement(new Resistor("r3", "1", "0", 1000));
        c.addElement(new Resistor("r4", "1", "0", 500));
        c.addElement(new Resistor("r5", "1", "2", 500));

        CircuitResult res = c.calculate();

        assertEquals(5, c.getElementList().size());
        assertEquals(3, c.getNodeList().size());
        assertEquals(1, c.getVoltageSourceList().size());

        assertNotNull(res);

        for (int i = 0; i < res.getNodeVoltages().length; i++) {
            System.out.print(res.getNodeVoltages()[i] + ", ");
        }
        System.out.println();
        for (int i = 0; i < res.getNodes().size(); i++) {
            System.out.print(res.getNodes().get(i) + ", ");
        }
    }
}