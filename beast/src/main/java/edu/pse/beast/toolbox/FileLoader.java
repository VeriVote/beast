/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import java.io.*;
import java.util.LinkedList;

/**
 *
 * @author Niels
 */
public final class FileLoader {
    private FileLoader() {

    }

    /**
     *
     * @param file
     *            is the file that gets loaded
     * @return A Stringstack of all Lines
     * @throws FileNotFoundException
     *             Stringpath must be correct
     * @throws IOException 
     */
    public static LinkedList<String> loadFileAsString(File file) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        LinkedList<String> stringlist;
        stringlist = new LinkedList<String>();
        String line;

        line = br.readLine();
        while (line != null) {
            stringlist.push(line);
            line = br.readLine();
        }

        br.close();
        return stringlist;
    }
}
