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
    
}
