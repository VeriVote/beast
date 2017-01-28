/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

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
     * @param in the inputstream
     * @return A LinkedList of String elements which are in the same order as in
     * the file
     * @throws FileNotFoundException if the file is not found it throws an
     * exception
     * @throws IOException throws Exception
     */
    public static LinkedList<String> loadFileAsString(InputStream in) throws FileNotFoundException, IOException {
        
        
        LinkedList<String> stringlist;
        /**try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), "UTF8"))) { */
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            stringlist = new LinkedList<>();
            String line;
            
            line = br.readLine();
            while (line != null) {
                stringlist.add(line);
                line = br.readLine();
            }
        
        return stringlist;
    }

    /**
     *
     * @param in the file to be read as an image
     * @return the image, if it was possible to read it. In case it couldn't be
     * read, the methode returns null
     */
    public static BufferedImage loadFileAsImage(InputStream in) {
        BufferedImage toReturn = null;
        try {
            toReturn = ImageIO.read(in);
        } catch (IOException e) {
            ErrorLogger.log("The specified file: " + in.toString() + " couldn't be loaded");
        }

        return toReturn;
    }
    
    public static String getFileFromRes(String fileName) {
        return new File("src/res/" + fileName).getAbsolutePath();
    }    
}