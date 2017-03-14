package edu.pse.beast.options.ParametereditorOptions;


import java.util.Arrays;
import java.util.List;

import edu.pse.beast.options.Options;
import edu.pse.beast.stringresource.StringResourceLoader;

public class DeleteCFilesOptions extends Options {

	private static boolean deleteFiles = false;
	
	public DeleteCFilesOptions(StringResourceLoader stringResLoader) {
        super("file_opts");
        String choosenOption = stringResLoader.getStringFromID("deleteTmpFiles");
        if (choosenOption != null && choosenOption.equals("true")) {
        	deleteFiles = true;
        }
        List<String> choosableOptionList = Arrays.asList("Don't keep tmp files", "Keep tmp files");
        DeleteCFilesElement fileOptElem = new DeleteCFilesElement(choosableOptionList, choosenOption);
        optElements.add(fileOptElem);
        
    }
	
	public DeleteCFilesOptions() {
		super("file_opts");
	}

	@Override
	protected void reapplySpecialized() {
		if (optElements.get(0).equals("true")) {
			deleteFiles = true;
		} else {
			deleteFiles = false;
		}
	}
	
	public static boolean deleteTmpFiles() {
		
		return deleteFiles;
	}

}
