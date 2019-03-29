package edu.pse.beast.toolbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

/**
 *
 * @author Niels Hanselmann
 */
public class CodeArrayListBeautifierTest {
    /**
     * Test of add method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        String e = "test";
        CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.add(e);
        assertEquals("test", (instance.getCodeArrayList().get(0)));
        instance.add(null);
    }

    /**
     * Test of addTab method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testAddTab() {
        System.out.println("addTab");
        System.out.println("add");
        String e = "test";
        CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.addTab();
        instance.add(e);
        assertEquals(CodeArrayListBeautifier.TAB + "test",
                     (instance.getCodeArrayList().get(0)));
    }

    /**
     * Test of deleteTab method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testDeleteTab() {
        System.out.println("deleteTab");
        String e = "test";
        CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.addTab();

        instance.deleteTab();
        instance.add(e);
        assertEquals("test", (instance.getCodeArrayList().get(0)));

        instance.deleteTab();
        e = "test2";
        instance.add(e);
        assertEquals("test2", (instance.getCodeArrayList().get(1)));

        e = "test3";
        instance.addTab();
        instance.add(e);
        instance.deleteTab();
        assertEquals(CodeArrayListBeautifier.TAB + "test3",
                     (instance.getCodeArrayList().get(2)));
    }

    /**
     * Test of getCodeArrayList method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testGetCodeArrayList() {
        System.out.println("getCodeArrayList");
        String e = "test";
        CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.add(e);
        assertEquals("test", (instance.getCodeArrayList().get(0)));
    }

    /**
     * Test of addArrayList method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testAddArrayList() {
        System.out.println("addArrayList");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("test");
        CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.addList(arrayList);
        ArrayList<String> resultList = instance.getCodeArrayList();
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