package edu.pse.beast.api.os;

public class OSHelper {
	public static OS getOS() {
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().contains("win"))
			return OS.WINDOWS;
		else if (osName.toLowerCase().contains("nux"))
			return OS.LINUX;
		else
			return OS.UNKNOWN;
	}
}
