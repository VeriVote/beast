package edu.pse.beast.Gui;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.highlevel.CentralObjectProvider;
import edu.pse.beast.highlevel.PSECentralObjectProvider;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.propertylist.PropertyList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

/**
 * Created by holger on 07.03.17.
 */
public class GuiTestHelper {
    private CentralObjectProvider centralObjectProvider;


    public void startNewBEASTInstance() throws InterruptedException {
        BEASTCommunicator communicator = new BEASTCommunicator();
        centralObjectProvider = new PSECentralObjectProvider(communicator);
        communicator.setCentralObjectProvider(centralObjectProvider);
        while(!centralObjectProvider.getParameterSrc().getView().isVisible())
            Thread.sleep(100);
    }

    public ParameterEditor getParamEditorOfCurrentInstace() {
        return (ParameterEditor) centralObjectProvider.getParameterSrc();
    }

    public CElectionDescriptionEditor getCEditorOfCurrentInstace() {
        return (CElectionDescriptionEditor) centralObjectProvider.getElectionDescriptionSource();
    }

    public BooleanExpEditor getBooleanExpEditorOfCurrentInstance() {
        return ((PropertyList) (centralObjectProvider.getPostAndPrePropertiesSource())).getEditor();
    }

    public PropertyList getPropListOfCurrentInstance() {
        return (PropertyList) (centralObjectProvider.getPostAndPrePropertiesSource());
    }

    public void clickMenuItemInCEditor(int subMenu, int item, long waittime) throws InterruptedException {
        JMenuBar menuBar = getCEditorOfCurrentInstace().getView().getMainMenuBar();
        Thread.sleep(waittime);
        menuBar.getMenu(subMenu).doClick();
        Thread.sleep(waittime);
        menuBar.getMenu(subMenu).getItem(item).doClick();
        Thread.sleep(waittime);
    }

    public void endInstance() {
        ParameterEditor ed = (ParameterEditor) centralObjectProvider.getParameterSrc();
        ed.getView().dispose();
    }

    public void performKeystrokesConcurrently(int[] strokes, long timeBefore, long timeBetween) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(timeBefore);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                performKeystrokes(strokes, timeBetween);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    public void performShortcut(int key, long timeoutafter) throws InterruptedException {
        Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(key);
        r.keyRelease(key);
        r.keyPress(KeyEvent.VK_CONTROL);
        Thread.sleep(timeoutafter);
    }

    public void performKeystrokes(int[] keys, long waittimeBetweenStrokes) throws InterruptedException {
        Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for (int i = 0; i < keys.length; i++) {
            r.keyPress(keys[i]);
            r.keyRelease(keys[i]);
            Thread.sleep(waittimeBetweenStrokes);
        }
    }
}
