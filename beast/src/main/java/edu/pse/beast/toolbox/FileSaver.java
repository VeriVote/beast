package edu.pse.beast.toolbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class FileSaver {
    
    
    public static void writeStringLinesToFile(List<String> text, File file) {        
        
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        for (Iterator<String> iterator = text.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();
            writer.println(line);
        }
        writer.close();
    }
}
