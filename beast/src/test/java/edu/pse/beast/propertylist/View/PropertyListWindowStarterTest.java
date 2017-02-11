package edu.pse.beast.propertylist.View;

import static org.junit.Assert.*;

import edu.pse.beast.options.OptionsInterface;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.booleanexpeditor.BooleanExpEditorBuilder;
import edu.pse.beast.propertylist.PLControllerInterface;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.PropertyListBuilder;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

public class PropertyListWindowStarterTest {

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
		// not important
	}
	
	public static void main(String[] args) {
		
		PLModelInterface model = new PLModel();
		PLControllerInterface controller = new PropertyList(model);
		
	//	PropertyListWindowStarter starter = new PropertyListWindowStarter(controller, model);
	//	starter.start();
		controller.addNewProperty();
		
		
		/*OptionsInterface options = new OptionsInterface();
		ObjectRefsForBuilder refs = new ObjectRefsForBuilder(options, new StringLoaderInterface("de"), 
				options.getLanguageOptions(), new SaverLoaderInterface());
        PropertyList propertyList = new PropertyListBuilder().createPropertyList(refs);*/
        
        
		
		/*PropertyListBuilder builder = new PropertyListBuilder();
		ObjectRefsForBuilder objRefsForBuilder = new ObjectRefsForBuilder(new OptionsInterface(),
                new StringLoaderInterface("de"),
                new LanguageOptions(), new SaverLoaderInterface());*/
		//builder.createPropertyList(objRefsForBuilder);
    }

}
