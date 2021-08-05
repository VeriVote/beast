package edu.pse.beast.api.os;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class OSHelper {
    public static OS getOS() {
        final String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("win")) {
            return OS.WINDOWS;
        } else if (osName.toLowerCase().contains("nux")) {
            return OS.LINUX;
        } else {
            return OS.UNKNOWN;
        }
    }
}
