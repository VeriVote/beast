package edu.pse.beast.toolbox;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

public class FileSaver {
    
    
    public static void writeStringLinesToFile(List<String> text, String path, String fileName) 
            throws FileNotFoundException, UnsupportedEncodingException {        
        
        
        PrintWriter writer = new PrintWriter(path + '/' + fileName, "UTF-8");
        
        for (Iterator iterator = text.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();
            writer.println(line);
        }
        writer.close();
    }
}
