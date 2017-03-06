package edu.pse.beast.propertylist.Controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.propertylist.Model.PropertyItem;

public class PropertyListControllerTest {
	
	static PropertyList list;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		list = new PropertyList(new PLModel());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		list.addNewProperty();
		list.addNewProperty();
		list.addNewProperty();
	}

	@After
	public void tearDown() throws Exception {
		list.setNewList();
	}

	@Test
	public void changeNameTest() {
		list.changeName(list.getList().get(0), "testname");
		assertEquals(list.getList().get(0).getDescription().getName(), "testname");
	}
	
	@Test
	public void changeTestStatusTest() {
		boolean before = list.getList().get(0).getTestStatus();
		list.setTestStatus(list.getList().get(0), !before);
		assertEquals(list.getList().get(0).getTestStatus(), !before);
	}
	
	@Test
	public void deleteItemTest() {
		list.deleteProperty(list.getList().get(0));
		assertEquals(list.getList().size(), 2);
	}
	
	@Test
	public void addDescriptionTest() {
		PropertyItem item = new PropertyItem();
		item.setDescriptionName("testname");
		list.addDescription(item);
		assertEquals(list.getList().size(), 4);
		assertEquals(list.getList().get(3), item);
	}
	
	@Test
	public void addPropertyTest() {
		list.addNewProperty();
		assertEquals(list.getList().size(), 4);
	}
	
	

}
