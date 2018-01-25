package edu.pse.beast.propertychecker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class HelperClassesTest {

	@Test
	public void wrapperLongTest() {
		CBMCResultWrapperLong longWrap = new CBMCResultWrapperLong(2, "votes");
		assertEquals(longWrap.getMainIndex(), 2);
		assertEquals(longWrap.getName(), "votes");
		assertEquals(longWrap.getValue(), -1l);
	}

	@Test
	public void wrapperLongArrTest() {
		CBMCResultWrapperSingleArray longArrWrap = new CBMCResultWrapperSingleArray(2, "votes");
		assertEquals(longArrWrap.getMainIndex(), 2);
		assertEquals(longArrWrap.getName(), "votes");
		assertNotNull(longArrWrap.getList());
	}
	
	@Test
	public void wrapperLongArrAddGetTest() {
		CBMCResultWrapperSingleArray longArrWrap = new CBMCResultWrapperSingleArray(2, "votes");
		assertEquals((long)longArrWrap.getArray().length, 0);
		longArrWrap.addTo(3, 5);
		longArrWrap.addTo(2, 3);
		assertEquals((long)longArrWrap.getArray()[0], 0);
		assertEquals((long)longArrWrap.getArray()[1], 0);
		assertEquals((long)longArrWrap.getArray()[2], 3);
		assertEquals((long)longArrWrap.getArray()[3], 5);
		
	}
	
	
	@Test
	public void wrapperLongArrArrTest() {
		CBMCResultWrapperMultiArray longArrArrWrap = new CBMCResultWrapperMultiArray(2, "votes");
		assertEquals(longArrArrWrap.getMainIndex(), 2);
		assertEquals(longArrArrWrap.getName(), "votes");
		assertNotNull(longArrArrWrap.getList());
	}
	
	@Test
	public void wrapperLongArrArrAddGetTest() {
		CBMCResultWrapperMultiArray longArrArrWrap = new CBMCResultWrapperMultiArray(2, "votes");
	//n
		assertEquals((long)longArrArrWrap.getArray().length, 0);
		longArrArrWrap.addTo(2, 3, 5);
		longArrArrWrap.addTo(1, 4, 3);
		assertEquals((long)longArrArrWrap.getArray()[0][0], 0);
		assertEquals((long)longArrArrWrap.getArray()[2][3], 5);
		assertEquals((long)longArrArrWrap.getArray()[1][4], 3);
		assertEquals((long)longArrArrWrap.getArray()[1][3], 0);
		
	}
}
