package edu.pse.beast.api.os;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class OSHelper {
    private static final String OS_NAME = "os.name";
    private static final String WINDOWS = "win";
    private static final String LINUX = "nux";

    public static OS getOS() {
        final String osName = System.getProperty(OS_NAME);
        if (osName.toLowerCase().contains(WINDOWS)) {
            return OS.WINDOWS;
        } else if (osName.toLowerCase().contains(LINUX)) {
            return OS.LINUX;
        } else {
            return OS.UNKNOWN;
        }
    }
}
