/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Holger-Desktop
 */
public class ImageResourceProvider {

    private static ImageResourceProvider toolbar;
    public static ImageResourceProvider getToolbarImages() {
        if(toolbar == null) {
            String[] ids = {"new", "load", "save", "save_as",
                    "undo", "redo", "cut", "copy", "paste",
                    "start", "stop", "showHelpToUser", "simulation"};
            toolbar =
                    new ImageResourceProvider("/core/images/toolbar/",
                    ids);
        }
        return toolbar;
    }
    
    private final HashMap<String, Image> images = new HashMap<>();
    private final String folder;
    private final String[] ids;
    
    public ImageResourceProvider(String folder, String[] ids) {
        this.folder = folder;
        this.ids = ids;
        for(String s : this.ids) {
            
            File toRead =
                    new File(SuperFolderFinder.getSuperFolder() + this.folder + s + ".png");
            
    //        String location = folder + s + ".png";
            Image img = null;
     //       img = FileLoader.loadFileAsImage(getClass().getClassLoader().getResourceAsStream(location));
            img = FileLoader.loadFileAsImage(toRead);
            images.put(s, img);
        }
    }
    
    public Image getImageById(String id) {
        return images.get(id);
    }
}
