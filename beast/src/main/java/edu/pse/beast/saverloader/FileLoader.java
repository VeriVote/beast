package edu.pse.beast.saverloader;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

public final class FileLoader {
	
	public static String loadFileAsString(File file) throws IOException {
		if (!file.canRead()) return null;
		
		return new String(Files.readAllBytes(file.toPath()));
	}

	public static Image loadFileAsImage(File file) throws IOException {
		if (!file.canRead()) return null;
		
		return ImageIO.read(file);
	}
}
