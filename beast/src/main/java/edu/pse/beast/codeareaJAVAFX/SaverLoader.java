package edu.pse.beast.codeareaJAVAFX;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.highlevel.MainClass;
import javafx.stage.FileChooser;

public class SaverLoader {
	private boolean hasSaveFile = false;
	private File saveFile = null;

	private final String initialDir;
	private final String fileEnding;
	private final String fileExtensionDescription;

	public SaverLoader(String fileEnding, String initialDir, String fileExtensionDescription) {
		this.initialDir = initialDir;
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

		} catch (IOException ioE) {
			ioE.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public void saveAs(String fileName, String text) {
		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter(fileExtensionDescription, "*" + fileEnding));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));

		fileChooser.setInitialDirectory(new File(initialDir));
		fileChooser.setInitialFileName(fileName + fileEnding);
		File selectedFile = fileChooser.showSaveDialog(MainClass.getMainStage());
		if (selectedFile != null) {
			save(selectedFile, text);
		}
	}

	public String load() {
		FileChooser fileChooser = new FileChooser();
		// fileChooser.setTitle("Load document");
		fileChooser.setInitialDirectory(new File(initialDir));
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter(fileExtensionDescription, "*" + fileEnding));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));
		File selectedFile = fileChooser.showOpenDialog(MainClass.getMainStage());
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

}
