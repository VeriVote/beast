///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package edu.pse.beast.celectiondescriptioneditor;
//
//import java.util.ArrayList;
//
//import edu.pse.beast.celectiondescriptioneditor.View.CCodeEditorWindow;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.toolbox.ActionIdAndListener;
//import edu.pse.beast.toolbox.MenuBarHandler;
//
///**
// * creates and updates the menubar of a celectiondescriptioneditor. It also
// * links the useractions to the menuitems
// * 
// * @author Holger-Desktop
// */
//public class CElectionEditorMenubarHandler extends MenuBarHandler {
//
//    private final CCodeEditorWindow cgui;
//
//    /**
//     * constructor for the menu bar handler
//     * @param headingIds the ids for the headings
//     * @param cgui the code editor gui
//     * @param actionIdAndListener the action id listener
//     * @param stringif the string interface
//     */
//    public CElectionEditorMenubarHandler(String[] headingIds, CCodeEditorWindow cgui,
//            ArrayList<ArrayList<ActionIdAndListener>> actionIdAndListener, StringLoaderInterface stringif) {
//        super(headingIds, actionIdAndListener, stringif.getCElectionEditorStringResProvider().getMenuStringRes());
//        this.cgui = cgui;
//        this.cgui.setMenuBar(createdMenuBar);
//    }
//
//    @Override
//    public void updateStringRes(StringLoaderInterface stringResIF) {
//        updateStringResLoader(stringResIF.getCElectionEditorStringResProvider().getMenuStringRes());
//        cgui.setMenuBar(createdMenuBar);
//    }
//}
