/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;

import javax.imageio.ImageIO;

/**
 *
 * @author Niels
 */
public final class FileLoader {
    private FileLoader() {

    }

    /**
     *
     * @param file the file that gets loaded into the String
     * @return A LinkedList of String elements which are in the same order as in the file
     * @throws FileNotFoundException if the file is not found it throws an exception
     * @throws IOException throws Exception
     */
    public static LinkedList<String> loadFileAsString(File file) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        LinkedList<String> stringlist;
        stringlist = new LinkedList<String>();
        String line;

        line = br.readLine();
        while (line != null) {
            stringlist.add(line);
            line = br.readLine();
        }

        br.close();
        return stringlist;
    }
    
    /**
     * 
     * @param file the file to be read as an image
     * @return the image, if it was possible to read it. In case it couldn't be read, the methode returns null
     */
    public static BufferedImage loadFileAsImage(File file) {
        BufferedImage toReturn = null;
        try {
            toReturn = ImageIO.read(file);
        } catch (IOException e) {
            ErrorLogger.log("The specified file: " + file.toString() + " couldn't be loaded");
        }
        
        return toReturn;
    }
}
