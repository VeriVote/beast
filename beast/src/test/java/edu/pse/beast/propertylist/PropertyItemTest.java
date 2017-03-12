package edu.pse.beast.propertylist;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.propertylist.Model.PropertyItem;

public class PropertyItemTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		PropertyItem it1 = new PropertyItem();
		PropertyItem it2 = new PropertyItem();
		assertFalse(it1.equals(null));
		assertFalse(it1.equals(it2));
		it2.setDescriptionName(it1.getDescription().getName());
		assertTrue(it1.equals(it2));
	}

}
