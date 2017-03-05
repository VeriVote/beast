package edu.pse.beast.propertylist.View;

import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.propertylist.PLControllerInterface;
import edu.pse.beast.propertylist.PropertyList;
import org.junit.*;

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
