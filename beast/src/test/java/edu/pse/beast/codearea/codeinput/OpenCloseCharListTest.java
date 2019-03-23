package edu.pse.beast.codearea.codeinput;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Holger-Desktop
 */
public class OpenCloseCharListTest {

    public OpenCloseCharListTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isOpenChar method, of class OpenCloseCharList.
     */
    @Test
    public void testIsOpenChar() {
        System.out.println("isOpenChar");

        OpenCloseCharList instance = new OpenCloseCharList();
        boolean result = instance.isOpenChar('{');
        assertTrue(result);
        result = instance.isOpenChar('(');
        assertTrue(result);
        result = instance.isOpenChar('"');
        assertTrue(result);
    }

    /**
     * Test of getOpenCloseChar method, of class OpenCloseCharList.
     */
    @Test
    public void testGetOpenCloseChar() {
        System.out.println("getOpenCloseChar");

        OpenCloseCharList instance = new OpenCloseCharList();

        OpenCloseChar result = instance.getOpenCloseChar('{');
        assertEquals('{', result.getOpen());
        assertEquals('}', result.getClose());
    }
}