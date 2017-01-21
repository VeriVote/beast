/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This class is simple wrapper around a sorted ArrayList (or other type of 
 * sortable list) of Integers. Since working with lists of ints is confusing
 * due to the functions for deleting an element and deleting at an index
 * having the exact same format for them. This class also adds some funcitonallity
 * which takes advantage of the list being sorted, such as removebetween.
 * It is mainly used by the codearea to keep track of linebreaks
 * @author Holger-Desktop
 */
public class SortedIntegerList {
    /**
     * this list contains all the stored integers
     */
    private ArrayList<Integer> container = new ArrayList<Integer>();
    /**
     * a comparator for integers, used to sort container
     */
    private Comparator comp = new Comparator<Integer>() {
        @Override
        public int compare(Integer lhs, Integer rhs) {
           if(lhs < rhs) {
               return -1;
           } else if(rhs < lhs) {
               return 1;
           } else {
               return 0;
           }
        }
    };
    
    /**
     * default constructor
     */
    public SortedIntegerList() {
    }
    
    /**
     * @param index the position of the integer to be returned
     * @return the integer at position index
     */
    public int get(int index) {
        return container.get(index);
    }
    
    /**
     * @return the amount of objects in container
     */
    public int size() {
        return container.size();
    }
    
    /**
     * adds the supplied integer to container while making sure it remains
     * sorted
     * @param i the integer added to container
     */
    public void add(int i) {
        container.add(i);
        container.sort(comp);
    }
    
    /**
     * removes the supplied integer from container. 
     * @param i the integer to be removed
     */
    public void remove(int number) {
        int i = 0;
        for(; i < container.size() && container.get(i) < number; ++i);
        while(container.get(i) == number) container.remove(i);
    }
    
    /**
     * removes all integers value between to supplied borders from container,
     * including lo but excluding hi
     * @param lo the starting number for removal
     * @param hi the ceiling for removal
     */
    public void removeBetween(int lo, int hi) {
        int i = 0;
        while(i < container.size() && container.get(i) < lo){
            ++i;
        } 
        while(i < container.size() && container.get(i) < hi) {
            container.remove(i);
        }
    }
    
    /**
     * @param num
     * @return the biggest integer smaller or equal to than the num
     */
    public int getBiggestSmallerOrEqual(int num) {
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < container.size() && container.get(i) <= num; ++i) {
            max = container.get(i);
        }
        return max;
    }

    /**
     * subtracts subtract from all saved numbers bigger than start
     * @param start
     * @param subtract 
     */
    public void subtractIfBigger(int start, int subtract) {
        int i = 0;
        while(i < container.size() && container.get(i) <= start) {
            ++i;
        }        
        for(; i < container.size(); ++i) {
            container.set(i, container.get(i) - subtract);
        }
    }

    /**
     * adds add to all numbers bigger than startc
     * @param start
     * @param add 
     */
    public void addIfBigger(int start, int add) {
        int i = 0;
        while(i < container.size() && container.get(i) <= start) {
            ++i;
        }        
        for(; i < container.size(); ++i) {
            container.set(i, container.get(i) + add);
        }
    }
    
    /**
     * uses binary search to quickly determine whether container contains i
     * @param i
     * @return 
     */
    public boolean contains(int i) {
        return Arrays.binarySearch(container.toArray(), i) >= 0;
    }
    
    public int getPositionOf(int i) {
        return Arrays.binarySearch(container.toArray(), i);
    }
    
    public int getAmountBefore(int num) {
        int amt = 0;
        for(int i = 0; i < container.size() && container.get(i) < num; ++i) {
            amt++;
        }
        return amt;
    }

    public void clear() {
        container.clear();
    }

    public int[] getArr() {
        int[] arr = new int[container.size()];
        for(int i = 0; i < container.size(); ++i) {
            arr[i] = container.get(i);
        }
        return arr;
    }
    
    @Override
    public String toString() {
        String s = "";
        for(int num : container) {
            s += num + " ";
        }
        return s;
    }
            
}
