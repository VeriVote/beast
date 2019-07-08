import edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes.ResultPresentationType;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

module edu.pse.beast {
    uses edu.pse.beast.types.InputType;
    uses edu.pse.beast.types.OutputType;
    uses edu.pse.beast.propertychecker.Result;
    uses edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes.ResultPresentationType;
    
    exports edu.pse.beast.electionsimulator;
    exports edu.pse.beast.propertychecker;
    exports edu.pse.beast.options.parametereditoroptions;
    exports edu.pse.beast.types.cbmctypes;
    exports edu.pse.beast.saverloader.adapter;
    exports edu.pse.beast.types.cbmctypes.inputplugins;
    exports edu.pse.beast.codearea.codeinput;
    exports edu.pse.beast.toolbox;
    exports edu.pse.beast.types;
    exports edu.pse.beast.booleanexpeditor.booleanexpcodearea;
    exports edu.pse.beast.codearea.useractions;
    exports edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;
    exports edu.pse.beast.highlevel.javafx;
    exports edu.pse.beast.saverloader.staticsaverloaders;
    exports edu.pse.beast.booleanexpeditor.useractions;
    exports edu.pse.beast.propertylist.model;
    exports edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;
    exports edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder;
    exports edu.pse.beast.types.cbmctypes.outputplugins;
    exports edu.pse.beast.highlevel;
    exports edu.pse.beast.electionsimulator.model;
    exports edu.pse.beast.celectiondescriptioneditor.electiontemplates;
    exports edu.pse.beast.parametereditor;
    exports edu.pse.beast.pluginmanager;
    exports edu.pse.beast.codearea.autocompletion;
    exports edu.pse.beast.codearea;
    exports edu.pse.beast.electionsimulator.programaccess;
    exports edu.pse.beast.stringresource;
    exports edu.pse.beast.toolbox.antlr.booleanexp.generateast;
    exports edu.pse.beast.codearea.actionlist.textaction;
    exports edu.pse.beast.celectiondescriptioneditor.view;
    exports edu.pse.beast.codearea.actionlist;
    exports edu.pse.beast.booleanexpeditor.view;
    exports edu.pse.beast.options.codeareaoptions;
    exports edu.pse.beast.pluginhandler;
    exports edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;
    exports edu.pse.beast.celectiondescriptioneditor;
    exports edu.pse.beast.datatypes.booleanexpast.othervaluednodes;
    exports edu.pse.beast.datatypes.propertydescription;
    exports edu.pse.beast.datatypes.booleanexpast;
    exports edu.pse.beast.codeareajavafx;
    exports edu.pse.beast.options;
    exports edu.pse.beast.propertychecker.jna;
    exports edu.pse.beast.saverloader;
    exports edu.pse.beast.codearea.actionadder;
    exports edu.pse.beast.codearea.errorhandling;
    exports edu.pse.beast.codearea.syntaxhighlighting;
    exports edu.pse.beast.parametereditor.view;
    exports edu.pse.beast.datatypes.electiondescription;
    exports edu.pse.beast.booleanexpeditor;
    exports edu.pse.beast.datatypes.electioncheckparameter;
    exports edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr;
    exports edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;
    exports edu.pse.beast.toolbox.valueContainer;
    exports edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;
    
    requires java.sql;
    requires com.sun.jna;
    requires com.sun.jna.platform;
    requires flowless;
    requires gson;
    requires java.datatransfer;
    requires java.desktop;
    requires java.logging;
    requires javafx.base;
    //requires transitive javafx.base;
    requires javafx.controls;
    //requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    //requires transitive javafx.graphics;
    requires javafx.swing;
    //requires transitive antlr4;
    requires org.antlr.antlr4.runtime;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires reactfx;
    requires richtextfx;
    requires undofx;
    requires wellbehavedfx;
    requires java.base;
    
    opens edu.pse.beast.highlevel.javafx to javafx.fxml;
    opens edu.pse.beast.datatypes.electiondescription to gson;
    opens edu.pse.beast.datatypes.propertydescription to gson;
    opens edu.pse.beast.types to gson;
    opens edu.pse.beast.types.cbmctypes.inputplugins to gson;
    opens edu.pse.beast.propertychecker to gson;
    opens edu.pse.beast.toolbox.valueContainer to gson;
    
    //TODO maybe extract the types into their own modules
    provides InputType with edu.pse.beast.types.cbmctypes.inputplugins.Approval,
        edu.pse.beast.types.cbmctypes.inputplugins.Preference,
        edu.pse.beast.types.cbmctypes.inputplugins.SingleChoice,
        edu.pse.beast.types.cbmctypes.inputplugins.SingleChoiceStack,
        edu.pse.beast.types.cbmctypes.inputplugins.WeightedApproval;
    
    
    provides OutputType with edu.pse.beast.types.cbmctypes.outputplugins.CandidateList, 
        edu.pse.beast.types.cbmctypes.outputplugins.Parliament, 
        edu.pse.beast.types.cbmctypes.outputplugins.ParliamentStack,
        edu.pse.beast.types.cbmctypes.outputplugins.SingleCandidate;
    
    provides Result with edu.pse.beast.propertychecker.CBMCResult;
    
    provides ResultPresentationType with edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes.Default,
    	edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes.CBMCOutput,
    	edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes.CBMCError;
    
}
