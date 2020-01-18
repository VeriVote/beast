//package edu.pse.beast.propertychecker;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.util.Iterator;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import edu.pse.beast.gui.GuiTestHelper;
//import edu.pse.beast.datatypes.Project;
//import edu.pse.beast.parametereditor.ParameterEditor;
//import edu.pse.beast.propertylist.PropertyList;
//import edu.pse.beast.propertylist.model.PropertyItem;
//import edu.pse.beast.saverloader.ProjectSaverLoader;
//
//public class HighLevelPropertycheckerTest {
//    GuiTestHelper helper = new GuiTestHelper();
//    private long waittime = 2000;
//    @Before
//    public void setUp() throws InterruptedException {
//        helper.startNewBEASTInstance();
//    }
//
//    @Test
//    public void testCBMCCheck() throws InterruptedException {
//        ParameterEditor parameterEditor = helper.getParamEditorOfCurrentInstace();
//        Thread.sleep(waittime);
//        String pathToProject = "./src/test/testfiles/SingleChoiceAnonymity.beast";
//        Thread.sleep(waittime);
//        ProjectSaverLoader projectSaverLoader = new ProjectSaverLoader();
//        Thread.sleep(waittime);
//        Project loadedProject =
//            (Project)
//                projectSaverLoader.createFromSaveString(readProject(pathToProject));
//        Thread.sleep(waittime);
//        parameterEditor.loadProject(loadedProject);
//        Thread.sleep(waittime);
//        Thread.sleep(waittime);
//        Thread.sleep(waittime);
//        PropertyList propList = helper.getPropListOfCurrentInstance();
//        Thread.sleep(waittime);
//        List<PropertyItem> list = propList.getList();
//
//        for (Iterator<PropertyItem> iterator = list.iterator(); iterator.hasNext();) {
//            // the first example should not be zero, because there the assertion fails
//            PropertyItem propertyItem = (PropertyItem) iterator.next();
//            assertNotNull(propertyItem.getExample());
//            // the second example should lead to null, because the assetion holds
//            propertyItem = (PropertyItem) iterator.next();
//            assertNull(propertyItem.getExample());
//        }
//        helper.endInstance();
//    }
//
//    @Test
//    public void testCheckAbort() throws InterruptedException {
//        ParameterEditor parameterEditor = helper.getParamEditorOfCurrentInstace();
//        Thread.sleep(waittime);
//        String pathToProject = "./src/test/testfiles/SingleChoiceAnonymity.beast";
//        Thread.sleep(waittime);
//        ProjectSaverLoader projectSaverLoader = new ProjectSaverLoader();
//        Thread.sleep(waittime);
//        Project loadedProject =
//            (Project)
//                projectSaverLoader.createFromSaveString(readProject(pathToProject));
//        Thread.sleep(waittime);
//        parameterEditor.loadProject(loadedProject);
//        Thread.sleep(waittime);
//        Thread.sleep(waittime);
//        PropertyList propList = helper.getPropListOfCurrentInstance();
//        Thread.sleep(waittime);
//        List<PropertyItem> list = propList.getList();
//
//        for (Iterator<PropertyItem> iterator = list.iterator(); iterator.hasNext();) {
//            // the first example should not be zero, because there the assertion fails
//            PropertyItem propertyItem = (PropertyItem) iterator.next();
//            assertNull(propertyItem.getExample());
//            // the second example should lead to null, because the assetion holds
//            propertyItem = (PropertyItem) iterator.next();
//            assertNull(propertyItem.getExample());
//        }
//        helper.endInstance();
//    }
//
//    @Test
//    public void testCBMCCheckTimeOut() throws InterruptedException {
//        ParameterEditor parameterEditor = helper.getParamEditorOfCurrentInstace();
//        String pathToProject = "./src/test/testfiles/timeoutTest.beast";
//        ProjectSaverLoader projectSaverLoader = new ProjectSaverLoader();
//        Project loadedProject =
//            (Project)
//                projectSaverLoader.createFromSaveString(readProject(pathToProject));
//        parameterEditor.loadProject(loadedProject);
//        boolean running = true;
//        while (running) {
//            if (parameterEditor.getReacts()) {
//                running = false;
//            } else {
//                Thread.sleep(1000);
//            }
//        }
//
//        PropertyList propList = helper.getPropListOfCurrentInstance();
//        List<PropertyItem> list = propList.getList();
//        for (Iterator<PropertyItem> iterator = list.iterator(); iterator.hasNext();) {
//            PropertyItem propertyItem = (PropertyItem) iterator.next();
//            assertNull(propertyItem.getExample());
//        }
//        helper.endInstance();
//    }
//
//    private String readProject(String pathToProject) throws InterruptedException {
//        // create a new saveString that contains a beast-project
//        File selectedFile = new File(pathToProject);
//        BufferedReader inputReader = null;
//
//        String toReturn = "";
//
//        try {
//            inputReader =
//                new BufferedReader(
//                    new InputStreamReader(
//                        new FileInputStream(selectedFile),
//                        "UTF8"
//                    )
//                );
//        } catch (UnsupportedEncodingException e) {
//        } catch (FileNotFoundException e) {
//        }
//        String sCurrentLine;
//        try {
//            while ((sCurrentLine = inputReader.readLine()) != null) {
//                toReturn += (sCurrentLine + "\n");
//            }
//        } catch (IOException e) {
//        }
//        return toReturn;
//    }
//}
