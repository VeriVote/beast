/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

/**
 *
 * @author Holger-Desktop
 */
public class TextPaneSystemInfo {
    
    private static String OS = System.getProperty("os.name").toLowerCase();
        
    private TextPaneSystemInfo() {
        
    }
    
    public static int getCharsPerNewline() {
        if(isWindows()) return 2;
        else return 1;
    }
    
    private static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    private static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    private static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }
}
