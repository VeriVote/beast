package edu.pse.beast.gui.processHandler;

import java.io.File;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

import edu.pse.beast.api.os.OSHelper;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandler;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandlerLinux;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandlerSource;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandlerWindows;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CBMCProcessHandlerCreator implements CBMCProcessHandlerSource {
    private static final String DOT = "'.";
    private static final String NAVIGATE_TO = "Please navigate to the file '";

    private static final String VS_DEV_COMMAND_FILE = "vsDevCmd.bat";

    private static final String VS_DEV_COMMAND_EXPLANATION_STRING =
            "It seems like you are using Windows. "
                    + "For running CBMC on Windows, you need the file '"
                    + VS_DEV_COMMAND_FILE + "', which is usually bundled with "
                    + "Visual Studio that is commonly found under the relative path "
                    + "'Visual studio\\Common7\\Tools\\'. "
                    + "Please navigate there, choose the file '"
                    + VS_DEV_COMMAND_FILE + "', and click OK."
                    + "Without this file, you can still create test runs, but not start them.";

    private CBMCProcessHandler processHandler;
    private String vsDevCmdPath;

    private File getVsDevCmdFromUser() {
        final Alert needVsDevCmdAlert = new Alert(AlertType.INFORMATION);
        needVsDevCmdAlert.setContentText(VS_DEV_COMMAND_EXPLANATION_STRING);
        needVsDevCmdAlert.getButtonTypes().add(ButtonType.CANCEL);
        final Optional<ButtonType> result = needVsDevCmdAlert.showAndWait();

        final File file;
        if (result.isPresent() && !result.get().getButtonData().isCancelButton()) {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(NAVIGATE_TO + VS_DEV_COMMAND_FILE + DOT);
            if (vsDevCmdPath != null) {
                fileChooser.setInitialDirectory(new File(vsDevCmdPath).getParentFile());
            }
            file = fileChooser.showOpenDialog(null);
        } else {
            file = null;
        }
        return file;
    }

    public final boolean testIsVsDevCmd(final File vsDevCmd) {
        return vsDevCmd != null && vsDevCmd.exists()
                && VS_DEV_COMMAND_FILE.toLowerCase().equals(vsDevCmd.getName().toLowerCase());
    }

    public final void askUserForCBMCProcessHandler() {
        switch (OSHelper.getOS()) {
        case LINUX:
            processHandler = new CBMCProcessHandlerLinux();
            break;
        case WINDOWS:
            final File vsDevCmd = getVsDevCmdFromUser();
            if (testIsVsDevCmd(vsDevCmd)) {
                processHandler =
                        new CBMCProcessHandlerWindows(vsDevCmd.getAbsolutePath());
                vsDevCmdPath = vsDevCmd.getAbsolutePath();
            }
            break;
        case UNKNOWN:
            break;
        default:
            break;
        }
    }

    public final CBMCProcessHandler getProcessHandler() {
        return processHandler;
    }

    public final boolean hasProcessHandler() {
        return processHandler != null;
    }

    public final String getVsDevCmdPath() {
        return vsDevCmdPath;
    }

    public final void setVsDevCmdPath(final String vsDevCmdPathString) {
        if (testIsVsDevCmd(new File(vsDevCmdPathString))) {
            this.vsDevCmdPath = vsDevCmdPathString;
            processHandler = new CBMCProcessHandlerWindows(vsDevCmdPathString);
        }
    }
}
