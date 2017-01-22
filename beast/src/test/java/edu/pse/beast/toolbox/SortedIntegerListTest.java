/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Holger-Desktop
 */
public class SortedIntegerListTest {
    private SortedIntegerList list;
    public SortedIntegerListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        list = new SortedIntegerList();
    }
    
    @After
    public void tearDown() {
        list = null;
    }

    /**
     * Test of add method, of class SortedIntegerList.
     */
    @Test
    public void testAdd() {
        int numbers[] = {5,3,1};
        for(int num : numbers) {
            list.add(num);
        }
        
        assertEquals(3, list.size());
        
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(5, list.get(2));
    }

    /**
     * Test of remove method, of class SortedIntegerList.
     */
    @Test
    public void testRemove() {
        int numbers[] = {5,3,1};
        for(int num : numbers) {
            list.add(num);
        }
        
        list.remove(3);
        assertEquals(2, list.size());
        
        assertEquals(1, list.get(0));
        assertEquals(5, list.get(1));
    }

    /**
     * Test of removeBetween method, of class SortedIntegerList.
     */
    @Test
    public void testRemoveBetween() {
        int numbers[] = {0,1,2,3,4,5,6,7,8,9,10};
        for(int num : numbers) {
            list.add(num);
        }
        list.removeBetween(0, 10);
        
        assertEquals(1, list.size());        
        assertEquals(10, list.get(0));
    }

   
    /**
     * Test of getBiggestSmallerOrEqual method, of class SortedIntegerList.
     */
    @Test
    public void testGetBiggestSmallerOrEqual() {
        int numbers[] = {0,1,2,4,5,6,7,8,9,10};
        for(int num : numbers) {
            list.add(num);
        }
        
        int test = list.getBiggestSmallerOrEqual(7);
        
        assertEquals(7, test);
        
        test = list.getBiggestSmallerOrEqual(3);
        
        assertEquals(2, test);
    }

    
    /**
     * Test of subtractIfBigger method, of class SortedIntegerList.
     */
    @Test
    public void testSubtractIfBigger() {
        System.out.println("subtractIfBigger");
        int numbers[] = {0,1,2,4,5,6,7,8,9,10};
        for(int num : numbers) {
            list.add(num);
        }
        list.remove(5);
        list.remove(4);
        assertEquals(8, list.size());
        
        list.subtractIfBigger(5, 2);
        
        assertEquals(4, list.get(3));
        assertEquals(5, list.get(4));
        assertEquals(6, list.get(5));
        
    }

   

    /**
     * Test of addIfBigger method, of class SortedIntegerList.
     */
    @Test
    public void testAddIfBigger() {
        int numbers[] =     {0,1,2,3,4,5,6,7,8,9,10};
        int newnumbers[] =  {0,1,2,3,4,5,8,9,10,11,12};
        
        for(int num : numbers) {
            list.add(num);
        }
        list.addIfBigger(5, 2);
        assertArrayEquals(newnumbers, list.getArr());
    }

    /**
     * Test of contains method, of class SortedIntegerList.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        int numbers[] =     {0,1,2,5,4,3,6,7,10,9,8};
        
        for(int num : numbers) {
            list.add(num);
        }
        
        assertTrue(list.contains(4));
        assertFalse(list.contains(11));
    }

    /**
     * Test of getPositionOf method, of class SortedIntegerList.
     */
    @Test
    public void testGetPositionOf() {
        System.out.println("getPositionOf");
        int numbers[] =     {0,1,2,5,4,3,6,7,10,9,8};
        
        for(int num : numbers) {
            list.add(num);
        }
        
        int pos = list.getPositionOf(6);
        assertEquals(6, pos);
        pos = list.getPositionOf(0);
        assertEquals(0, pos);        
    }

    /**
     * Test of getAmountBefore method, of class SortedIntegerList.
     */
    @Test
    public void testGetAmountBefore() {
        System.out.println("getAmountBefore");
        int numbers[] =     {0,1,2,5,4,3,6,10,9,8};
        
        for(int num : numbers) {
            list.add(num);
        }
        System.out.println(list.toString());
        int pos = list.getAmountBefore(7);
        assertEquals(7, pos);  
    }
    
}