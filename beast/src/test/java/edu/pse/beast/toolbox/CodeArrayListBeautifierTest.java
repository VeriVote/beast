package edu.pse.beast.toolbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

/**
 * The tests for CodeArrayListBeautifier.
 *
 * @author Niels Hanselmann
 */
public class CodeArrayListBeautifierTest {
    /** The Constant TWO. */
    private static final String TWO = "2";
    /** The Constant THREE. */
    private static final String THREE = "3";

    /** Add string. */
    private static final String ADD = "add";
    /** A test name string. */
    private static final String TEST = "test";
    /** A save test name string. */
    private static final String TEST_TWO = "test2";
    /** Another test name string. */
    private static final String TEST_THREE = "test3";

    /**
     * Test of add method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testAdd() {
        System.out.println(ADD);
        final CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.add(TEST);
        assertEquals(TEST, instance.getCodeArrayList().get(0));
        instance.add(null);
    }

    /**
     * Test of addTab method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testAddTab() {
        System.out.println("addTab");
        System.out.println(ADD);
        final CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.addTab();
        instance.add(TEST);
        assertEquals(CodeArrayListBeautifier.TAB + TEST,
                     instance.getCodeArrayList().get(0));
    }

    /**
     * Test of deleteTab method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testDeleteTab() {
        System.out.println("deleteTab");
        final CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.addTab();

        instance.deleteTab();
        instance.add(TEST);
        assertEquals(TEST, instance.getCodeArrayList().get(0));

        instance.deleteTab();
        instance.add(TEST_TWO);
        assertEquals(TEST_TWO, instance.getCodeArrayList().get(1));

        instance.addTab();
        instance.add(TEST_THREE);
        instance.deleteTab();
        assertEquals(CodeArrayListBeautifier.TAB + TEST_THREE,
                     instance.getCodeArrayList().get(2));
    }

    /**
     * Test of getCodeArrayList method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testGetCodeArrayList() {
        System.out.println("getCodeArrayList");
        final CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.add(TEST);
        assertEquals(TEST, instance.getCodeArrayList().get(0));
    }

    /**
     * Test of addArrayList method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testAddArrayList() {
        System.out.println("addArrayList");
        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(TWO);
        arrayList.add(THREE);
        arrayList.add(TEST);
        final CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.addList(arrayList);
        final ArrayList<String> resultList = instance.getCodeArrayList();
        if (resultList.size() == arrayList.size()) {
            for (int i = 0; i < arrayList.size(); i++) {
                assertEquals(resultList.get(i), arrayList.get(i));
            }
            // TODO review the generated test code and remove the default call to fail.
        } else {
            fail("The ArrayLists are of a different size");
        }
        instance.addList(null);
    }
}
