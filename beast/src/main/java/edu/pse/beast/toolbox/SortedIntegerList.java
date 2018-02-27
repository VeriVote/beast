/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class is simple wrapper around a sorted ArrayList (or other type of
 * sortable list) of Integers. Since working with lists of ints is confusing due
 * to the functions for deleting an element and deleting at an index having the
 * exact same format for them. This class also adds some funcitonallity which
 * takes advantage of the list being sorted, such as removebetween. It is mainly
 * used by the codearea to keep track of linebreaks
 * 
 * @author Holger-Desktop
 */
public class SortedIntegerList {
    /**
     * this list contains all the stored integers
     */
    private ArrayList<Integer> container = new ArrayList<>();
    /**
     * a comparator for integers, used to sort container
     */
    private final Comparator<Integer> comp = new Comparator<Integer>() {
        @Override
        public int compare(Integer lhs, Integer rhs) {
            if (lhs < rhs) {
                return -1;
            } else if (rhs < lhs) {
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
     * @param index
     *            the position of the integer to be returned
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
     * 
     * @param i
     *            the integer added to container
     */
    public void add(int i) {
        container.add(i);
        container.sort(comp);
    }

    /**
     * removes the supplied integer from container.
     * 
     * @param number the integer to be removed
     */
    public void remove(int number) {
        int i = 0;
        while (i < container.size() && container.get(i) < number) {
            i++;
        }
        while (i < container.size() && container.get(i) == number) {
            container.remove(i);
            i++;
        }
    }

    /**
     * removes all integers value between to supplied borders from container,
     * including lo but excluding hi
     * 
     * @param lo
     *            the starting number for removal
     * @param hi
     *            the ceiling for removal
     */
    public void removeBetween(int lo, int hi) {
        int i = 0;
        while (i < container.size() && container.get(i) < lo) {
            ++i;
        }
        while (i < container.size() && container.get(i) < hi) {
            container.remove(i);
        }
    }

    /**
     * @param num
     *            the number to be compared to
     * @return the biggest integer smaller or equal to than the num
     */
    public int getBiggestSmallerOrEqual(int num) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < container.size() && container.get(i) <= num; ++i) {
            max = container.get(i);
        }
        return max;
    }

    /**
     * subtracts subtract from all saved numbers bigger than start
     * 
     * @param start
     *            defines the exclusive lower bound
     * @param subtract
     *            number to be substracted from all elements > start
     */
    public void subtractIfBigger(int start, int subtract, SortedIntegerListCalleeFunction func) {
        int i = 0;
        while (i < container.size() && container.get(i) <= start) {
            ++i;
        }
        for (; i < container.size(); ++i) {
            if(func != null) func.changedNumber(container.get(i), container.get(i) - subtract);
            container.set(i, container.get(i) - subtract);
        }
    }

    /**
     * adds add to all numbers bigger than startc
     * 
     * @param start
     *            defines the exclusive lower bound
     * @param add
     *            number to be substracted from all elements > start
     */
    public void addIfBigger(int start, int add, SortedIntegerListCalleeFunction func) {
        int i = 0;
        while (i < container.size() && container.get(i) <= start) {
            ++i;
        }
        for (; i < container.size(); ++i) {
            if(func != null) func.changedNumber(container.get(i), container.get(i) + add);            
            container.set(i, container.get(i) + add);
        }
    }

    /**
     * uses binary search to quickly determine whether container contains i
     * 
     * @param i
     *            the value to be searched for
     * @return true, if the the container contains the number "i", false
     *         otherwise
     */
    public boolean contains(int i) {
        return Arrays.binarySearch(container.toArray(), i) >= 0;
    }

    /**
     * 
     * @param i
     *            the value of the number to get the position of
     * @return index of the search key, if it is contained in the array;
     *         otherwise, (-(insertion point) - 1). The insertion point is
     *         defined as the point at which the key would be inserted into the
     *         array: the index of the first element greater than the key, or
     *         a.length if all elements in the array are less than the specified
     *         key. Note that this guarantees that the return value will be >= 0
     *         if and only if the key is found.
     */
    public int getPositionOf(int i) {
        return Arrays.binarySearch(container.toArray(), i);
    }

    /**
     * 
     * @param num
     *            the exclusive upper bound of the check
     * @return the amount of elements that are smaller than num
     */
    public int getAmountBefore(int num) {
        int amt = 0;
        for (int i = 0; i < container.size() && container.get(i) < num; ++i) {
            amt++;
        }
        return amt;
    }

    /**
     * clears the container
     */
    public void clear() {
        container.clear();
    }

    /**
     * 
     * @return this List in array form
     */
    public int[] getArr() {
        int[] ret = new int[container.size()];
        for (int j = 0; j < ret.length; j++) {
            ret[j] = container.get(j);
        }
        return ret;
    }

    @Override
    public String toString() {
        String s = "";
        for (int num : container) {
            s += num + " ";
        }
        return s;
    }
    
    public List<Integer> getCopiedList() {
    	List<Integer> toReturn = new ArrayList<Integer>();
    	Collections.copy(toReturn, container);

    	return toReturn;
    }

}
