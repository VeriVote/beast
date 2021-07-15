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

    private final static String PROCESS_HANDLER_WINDOWS_KEY = "process_handler_windows";
    private static final String PATH_TO_VS_DEV_CMD_KEY = "path_to_vs_dev_cmd";

    private final static String C_EDITOR_KEY = "c_editor";
    private final static String FONT_SIZE_KEY = "font_size";

    private static void addToSaveJSON(
            ProcessHandlerWindowsOptionsGUI processHandlerWindowsOptionsGUI,
            JSONObject saveJson) {
        JSONObject json = new JSONObject();

        String vsDevCmdPath = processHandlerWindowsOptionsGUI
                .getCbmcProcessHandlerCreator().getVsDevCmdPath();
        if (vsDevCmdPath == null) {
            vsDevCmdPath = "";
        }
        json.put(PATH_TO_VS_DEV_CMD_KEY, vsDevCmdPath);

        saveJson.put(PROCESS_HANDLER_WINDOWS_KEY, json);
    }

    private static void addToSaveJSON(CEditorOptionsGUI cEditorOptionsGUI,
            JSONObject saveJson) {
        JSONObject json = new JSONObject();
        json.put(FONT_SIZE_KEY, cEditorOptionsGUI.getOptions().getFontSize());
        saveJson.put(C_EDITOR_KEY, json);
    }

    public static void saveOptions(File f, List<OptionsCategoryGUI> options)
            throws IOException {
        JSONObject json = new JSONObject();
        for (OptionsCategoryGUI option : options) {
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

    private static ProcessHandlerWindowsOptionsGUI processHandlerWindowsFromJson(
            JSONObject json) throws IOException {
        CBMCProcessHandlerCreator cbmcProcessHandlerCreator = new CBMCProcessHandlerCreator();
        String vsDevCmdPath = json.getString(PATH_TO_VS_DEV_CMD_KEY);
        if (!vsDevCmdPath.isBlank()) {
            cbmcProcessHandlerCreator.setVsDevCmdPath(vsDevCmdPath);
        }
        ProcessHandlerWindowsOptionsGUI gui = new ProcessHandlerWindowsOptionsGUI(
                cbmcProcessHandlerCreator);
        return gui;
    }

    private static CEditorOptionsGUI cEditorFromJson(JSONObject json)
            throws IOException {
        CEditorOptions options = new CEditorOptions();
        options.setFontSize(json.getDouble(FONT_SIZE_KEY));
        return new CEditorOptionsGUI(options);
    }

    private static OptionsCategoryGUI fromJson(String key, JSONObject json)
            throws IOException {
        if (key.equals(PROCESS_HANDLER_WINDOWS_KEY)) {
            return processHandlerWindowsFromJson(json);
        } else if (key.equals(C_EDITOR_KEY)) {
            return cEditorFromJson(json);
        }
        throw new NotImplementedException();
    }

    public static List<OptionsCategoryGUI> loadOptions(File f)
            throws IOException {
        List<OptionsCategoryGUI> options = new ArrayList<>();
        String jsonString = SavingLoadingInterface.readStringFromFile(f);
        JSONObject json = new JSONObject(jsonString);

        for (String key : json.keySet()) {
            options.add(fromJson(key, json.getJSONObject(key)));
        }

        return options;
    }
}
