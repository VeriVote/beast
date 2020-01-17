package edu.pse.beast.highlevel.javafx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import edu.pse.beast.options.OptionsNew;
import edu.pse.beast.saverloader.SaverLoader;

/**
 * The Class OptionsSaverLoader.
 *
 * @author Lukas Stapelbroek
 */
public class OptionsSaverLoader extends edu.pse.beast.codeareajavafx.SaverLoader
        implements SaverLoader<OptionsNew> {

    /** The saver loader. */
    private static Gson saverLoader;

    /**
     * Instantiates a new options saver loader.
     *
     * @param fileEnding
     *            the file ending
     * @param fileExtensionDescription
     *            the file extension description
     */
    public OptionsSaverLoader(final String fileEnding,
                              final String fileExtensionDescription) {
        super(fileEnding, fileExtensionDescription, null);
        // TODO Auto-generated constructor stub
    }

    static { // here you have the chance to register typeAdapters
        GsonBuilder builder = new GsonBuilder();
        saverLoader = builder.create();
    }

    @Override
    public OptionsNew createFromSaveString(final String toLoad)
            throws JsonSyntaxException {
        return saverLoader.fromJson(toLoad, OptionsNew.class);
    }

    @Override
    public String createSaveString(final OptionsNew toSave) {
        return saverLoader.toJson(toSave, OptionsNew.class);
    }
}
