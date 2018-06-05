package edu.pse.beast.codeareaJAVAFX;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import edu.pse.beast.highlevel.MainClass;
import javafx.stage.FileChooser;

public class SaverLoader {
	private boolean hasSaveFile = false;
	private File saveFile = null;

	private final String initialDir;
	private final String fileEnding;
	private final String fileExtensionDescription;

	public SaverLoader(String fileEnding, String fileExtensionDescription) {
		this.initialDir = System.getenv("SystemDrive");
		this.fileEnding = fileEnding;
		this.fileExtensionDescription = fileExtensionDescription;
	}

	public void save(String fileName, String text) {
		if (hasSaveFile) {
			if (saveFile != null) {
				save(saveFile, text);
			}
		} else {
			saveAs(fileName, text);
		}
	}

	public void save(File toSaveIn, String text) {

		PrintWriter out = null;

		try {
			out = new PrintWriter(toSaveIn);

			out.write(text);
			
			hasSaveFile = true;

		} catch (IOException ioE) {
			ioE.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public void saveAs(String fileName, String text) {
		File selectedFile = showFileSaveDialog(fileName);
		if (selectedFile != null) {
			save(selectedFile, text);
		}
	}
	

	public void saveAs(File file, String text) {
		if (file != null) {
			save(file, text);
		}
	}
	
	public File showFileSaveDialog(String fileName) {
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter(fileExtensionDescription, "*" + fileEnding));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));

		fileChooser.setInitialDirectory(new File(initialDir));
		fileChooser.setInitialFileName(fileName + fileEnding);
		File selectedFile = fileChooser.showSaveDialog(MainClass.getMainStage());
		
		return selectedFile;
	}
	
	public File showFileLoadDialog(String fileName) {
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter(fileExtensionDescription, "*" + fileEnding));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));

		fileChooser.setInitialDirectory(new File(initialDir));
		fileChooser.setInitialFileName(fileName + fileEnding);
		File selectedFile = fileChooser.showOpenDialog(MainClass.getMainStage());
		
		return selectedFile;
	}

	public String load() {
		File selectedFile = showFileLoadDialog("");
		if (selectedFile != null) {
			return load(selectedFile);
		}
		return "";
	}

	public String load(File toLoadFrom) {
		try {
			return readFile(toLoadFrom, StandardCharsets.UTF_8);

		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private static String readFile(File file, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
		return new String(encoded, encoding);
	}
	
	public void resetHasSaveFile() {
		hasSaveFile = false;
	}

}
