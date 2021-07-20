package edu.pse.beast.api.savingloading.options;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONObject;

import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.gui.options.OptionsCategoryGUI;
import edu.pse.beast.gui.options.ceditor.CEditorOptions;
import edu.pse.beast.gui.options.ceditor.CEditorOptionsGUI;
import edu.pse.beast.gui.options.process_handler.ProcessHandlerWindowsOptionsGUI;
import edu.pse.beast.gui.processHandler.CBMCProcessHandlerCreator;

public class OptionsSaverLoader {
    private static final String PROCESS_HANDLER_WINDOWS_KEY = "process_handler_windows";
    private static final String PATH_TO_VS_DEV_CMD_KEY = "path_to_vs_dev_cmd";

    private static final String C_EDITOR_KEY = "c_editor";
    private static final String FONT_SIZE_KEY = "font_size";

    private static void
                addToSaveJSON(final ProcessHandlerWindowsOptionsGUI processHandlerWindowsOptionsGUI,
                              final JSONObject saveJson) {
        final JSONObject json = new JSONObject();
        String vsDevCmdPath =
                processHandlerWindowsOptionsGUI.getCbmcProcessHandlerCreator().getVsDevCmdPath();
        if (vsDevCmdPath == null) {
            vsDevCmdPath = "";
        }
        json.put(PATH_TO_VS_DEV_CMD_KEY, vsDevCmdPath);
        saveJson.put(PROCESS_HANDLER_WINDOWS_KEY, json);
    }

    private static void addToSaveJSON(final CEditorOptionsGUI cEditorOptionsGUI,
                                      final JSONObject saveJson) {
        final JSONObject json = new JSONObject();
        json.put(FONT_SIZE_KEY, cEditorOptionsGUI.getOptions().getFontSize());
        saveJson.put(C_EDITOR_KEY, json);
    }

    public static void saveOptions(final File f, final List<OptionsCategoryGUI> options)
            throws IOException {
        final JSONObject json = new JSONObject();
        for (final OptionsCategoryGUI option : options) {
            switch (option.getCategory()) {
            case PROCESS_HANDLER_WINDOWS:
                addToSaveJSON((ProcessHandlerWindowsOptionsGUI) option, json);
                break;
            case C_DESCR_EDITOR:
                addToSaveJSON((CEditorOptionsGUI) option, json);
                break;
            default:
                break;
            }
        }
        SavingLoadingInterface.writeStringToFile(f, json.toString());
    }

    private static ProcessHandlerWindowsOptionsGUI
                processHandlerWindowsFromJson(final JSONObject json) throws IOException {
        final CBMCProcessHandlerCreator cbmcProcessHandlerCreator =
                new CBMCProcessHandlerCreator();
        final String vsDevCmdPath = json.getString(PATH_TO_VS_DEV_CMD_KEY);
        if (!vsDevCmdPath.isBlank()) {
            cbmcProcessHandlerCreator.setVsDevCmdPath(vsDevCmdPath);
        }
        return new ProcessHandlerWindowsOptionsGUI(cbmcProcessHandlerCreator);
    }

    private static CEditorOptionsGUI cEditorFromJson(final JSONObject json)
            throws IOException {
        final CEditorOptions options = new CEditorOptions();
        options.setFontSize(json.getDouble(FONT_SIZE_KEY));
        return new CEditorOptionsGUI(options);
    }

    private static OptionsCategoryGUI fromJson(final String key, final JSONObject json)
            throws IOException {
        if (key.equals(PROCESS_HANDLER_WINDOWS_KEY)) {
            return processHandlerWindowsFromJson(json);
        } else if (key.equals(C_EDITOR_KEY)) {
            return cEditorFromJson(json);
        }
        throw new NotImplementedException();
    }

    public static List<OptionsCategoryGUI> loadOptions(final File f)
            throws IOException {
        final List<OptionsCategoryGUI> options = new ArrayList<>();
        final String jsonString = SavingLoadingInterface.readStringFromFile(f);
        final JSONObject json = new JSONObject(jsonString);
        for (final String key : json.keySet()) {
            options.add(fromJson(key, json.getJSONObject(key)));
        }
        return options;
    }
}
