package edu.pse.beast.options.ParametereditorOptions;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.options.Options;
import edu.pse.beast.stringresource.StringResourceLoader;

public class DeleteCFilesOptions extends Options {

    private static boolean deleteFiles = false;

    public DeleteCFilesOptions(StringResourceLoader stringResLoader) {
        super("delete_file");
        String choosenOption = stringResLoader.getStringFromID("delete_file");
        if (choosenOption != null && choosenOption.equals("not_keep_files")) {
            deleteFiles = true;
        }
    }

    public DeleteCFilesOptions() {
        super("delete_file");
        deleteFiles = false;
    }

    @Override
    protected void reapplySpecialized() {
        if (optElements.size() > 0) {
            if (optElements.get(0).getChosenOption().equals("not_keep_files")) {
                deleteFiles = true;
            } else {
                deleteFiles = false;
            }
        } else {
            deleteFiles = false;
        }
    }

    public static boolean deleteTmpFiles() {
        return deleteFiles;
    }

}
