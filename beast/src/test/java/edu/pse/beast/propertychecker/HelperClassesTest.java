package edu.pse.beast.propertychecker;

import static org.junit.Assert.*;

import org.junit.Test;

public class HelperClassesTest {

	@Test
	public void wrapperLongTest() {
		CBMCResultWrapperLong longWrap = new CBMCResultWrapperLong(2, "votes");
		assertEquals(longWrap.getMainIndex(), 2);
		assertEquals(longWrap.getName(), "votes");
	}

	@Test
	public void wrapperLongArrTest() {
		CBMCResultWrapperSingleArray longArrWrap = new CBMCResultWrapperSingleArray(2, "votes");
		assertEquals(longArrWrap.getMainIndex(), 2);
		assertEquals(longArrWrap.getName(), "votes");
	}
	
	@Test
	public void wrapperLongArrArrTest() {
		CBMCResultWrapperMultiArray longArrArrWrap = new CBMCResultWrapperMultiArray(2, "votes");
		assertEquals(longArrArrWrap.getMainIndex(), 2);
		assertEquals(longArrArrWrap.getName(), "votes");
	}
}
