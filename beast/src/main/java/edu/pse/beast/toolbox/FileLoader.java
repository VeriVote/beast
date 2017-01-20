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
     * @param file the file that gets loaded into the String
     * @return A LinkedList of String elements which are in the same order as in the file
     * @throws FileNotFoundException
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
            stringlist.add(line);
            line = br.readLine();
        }

        br.close();
        return stringlist;
    }
}
