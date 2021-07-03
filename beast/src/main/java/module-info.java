import edu.pse.beast.zzz.propertychecker.Result;

module edu.pse.beast {
    uses edu.pse.beast.zzz.propertychecker.Result;

    
    exports edu.pse.beast.zzz.propertychecker;
    exports edu.pse.beast.zzz.toolbox;
    exports edu.pse.beast.gui;
    exports edu.pse.beast.toolbox.antlr.booleanexp;
    exports edu.pse.beast.api.propertydescription;
    exports edu.pse.beast.zzz.codeareajavafx;
    exports edu.pse.beast.zzz.propertychecker.jna;

    requires transitive com.google.gson;
    requires com.sun.jna;
    requires com.sun.jna.platform;
    requires flowless;
    requires java.datatransfer;
    requires transitive java.desktop;
    requires java.logging;
    requires transitive javafx.base;
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing;
    requires transitive org.antlr.antlr4.runtime;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires reactfx;
    requires org.fxmisc.richtext;
    requires undofx;
    requires wellbehavedfx;
    requires java.base;
	requires org.json;
	requires antlr4;

    opens edu.pse.beast.gui.options.process_handler to javafx.fxml;
    opens edu.pse.beast.gui.options.ceditor to javafx.fxml;
    opens edu.pse.beast.gui to javafx.fxml;
    opens edu.pse.beast.gui.options to javafx.fxml;
    opens edu.pse.beast.gui.testconfigeditor.testconfig to javafx.fxml;
    opens edu.pse.beast.gui.testconfigeditor.testconfig.cbmc to javafx.fxml;
    opens edu.pse.beast.gui.testconfigeditor to javafx.fxml;
    opens edu.pse.beast.gui.runs to javafx.fxml;
    opens edu.pse.beast.api.propertydescription to gson;
    opens edu.pse.beast.zzz.propertychecker to gson;

    provides Result with edu.pse.beast.zzz.propertychecker.CBMCResult; 
}
