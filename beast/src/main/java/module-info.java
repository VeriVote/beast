import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

module edu.pse.beast {
    uses edu.pse.beast.types.InputType;
    uses edu.pse.beast.types.OutputType;
    uses edu.pse.beast.propertychecker.Result;

    exports edu.pse.beast.propertychecker;
    exports edu.pse.beast.types.cbmctypes;
    exports edu.pse.beast.saverloader.adapter;
    exports edu.pse.beast.types.cbmctypes.inputplugins;
    exports edu.pse.beast.codearea.codeinput;
    exports edu.pse.beast.toolbox;
    exports edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;
    exports edu.pse.beast.types;
    exports edu.pse.beast.booleanexpeditor.booleanexpcodearea;
    exports edu.pse.beast.codearea.useractions;
    exports edu.pse.beast.gui;
    exports edu.pse.beast.saverloader.staticsaverloaders;
    exports edu.pse.beast.propertylist.model;
    exports edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;
    exports edu.pse.beast.types.cbmctypes.outputplugins;
    exports edu.pse.beast.parametereditor;
    exports edu.pse.beast.pluginmanager;
    exports edu.pse.beast.codearea.autocompletion;
    exports edu.pse.beast.toolbox.antlr.booleanexp;
    exports edu.pse.beast.codearea;
    exports edu.pse.beast.stringresource;
    exports edu.pse.beast.toolbox.antlr.booleanexp.generateast;
    exports edu.pse.beast.toolbox.valueContainer;
    exports edu.pse.beast.codearea.actionlist.textaction;
    exports edu.pse.beast.codearea.actionlist;
    exports edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;
    exports edu.pse.beast.datatypes.booleanexpast.othervaluednodes;
    exports edu.pse.beast.datatypes.propertydescription;
    exports edu.pse.beast.datatypes.booleanexpast;
    exports edu.pse.beast.codeareajavafx;
    exports edu.pse.beast.propertychecker.jna;
    exports edu.pse.beast.saverloader;
    exports edu.pse.beast.codearea.actionadder;
    exports edu.pse.beast.codearea.errorhandling;
    exports edu.pse.beast.codearea.syntaxhighlighting;
    exports edu.pse.beast.types.cbmctypes.cbmcstructs;
    exports edu.pse.beast.parametereditor.view;
    exports edu.pse.beast.datatypes.electiondescription;
    exports edu.pse.beast.booleanexpeditor;
    exports edu.pse.beast.datatypes.electioncheckparameter;

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

    opens edu.pse.beast.highlevel.javafx to javafx.fxml;
    opens edu.pse.beast.gui to javafx.fxml;
    opens edu.pse.beast.gui.options to javafx.fxml;
    opens edu.pse.beast.gui.testconfigeditor.testconfig to javafx.fxml;
    opens edu.pse.beast.gui.testconfigeditor.testconfig.cbmc to javafx.fxml;
    opens edu.pse.beast.gui.testconfigeditor to javafx.fxml;
    opens edu.pse.beast.gui.runs to javafx.fxml;
    opens edu.pse.beast.datatypes.electiondescription to gson;
    opens edu.pse.beast.datatypes.propertydescription to gson;
    opens edu.pse.beast.types to gson;
    opens edu.pse.beast.types.cbmctypes.inputplugins to gson;
    opens edu.pse.beast.propertychecker to gson;
    opens edu.pse.beast.toolbox.valueContainer to gson;

    // TODO maybe extract the types into their own modules
    provides InputType with edu.pse.beast.types.cbmctypes.inputplugins.Approval,
            edu.pse.beast.types.cbmctypes.inputplugins.Preference,
            edu.pse.beast.types.cbmctypes.inputplugins.SingleChoice,
            edu.pse.beast.types.cbmctypes.inputplugins.SingleChoiceStack,
            edu.pse.beast.types.cbmctypes.inputplugins.WeightedApproval;

    provides OutputType
            with edu.pse.beast.types.cbmctypes.outputplugins.CandidateList,
            edu.pse.beast.types.cbmctypes.outputplugins.Parliament,
            edu.pse.beast.types.cbmctypes.outputplugins.ParliamentStack,
            edu.pse.beast.types.cbmctypes.outputplugins.SingleCandidate;

    provides Result with edu.pse.beast.propertychecker.CBMCResult; 
}
