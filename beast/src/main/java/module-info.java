
module edu.kit.kastel.formal.beast {

    exports edu.kit.kastel.formal.beast.api.cparser;
    exports edu.kit.kastel.formal.beast.api.codegen.ast;
    exports edu.kit.kastel.formal.beast.api.codegen.ast.expression;
    exports edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool;
    exports edu.kit.kastel.formal.beast.api.codegen.ast.expression.type;
    exports edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election;
    exports edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.integer;
    exports edu.kit.kastel.formal.beast.api.codegen.ccode;
    exports edu.kit.kastel.formal.beast.api.codegen.cbmc;
    exports edu.kit.kastel.formal.beast.api.codegen.cbmc.info;
    exports edu.kit.kastel.formal.beast.api.codegen.loopbound;
    exports edu.kit.kastel.formal.beast.api.method;
    exports edu.kit.kastel.formal.beast.api.method.function;
    exports edu.kit.kastel.formal.beast.api.codegen.init;
    exports edu.kit.kastel.formal.beast.api.method.antlr;
    exports edu.kit.kastel.formal.beast.gui;
    exports edu.kit.kastel.formal.beast.api.property.antlr;
    exports edu.kit.kastel.formal.beast.api.test;
    exports edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.symbolic;
    exports edu.kit.kastel.formal.beast.api.property;

    requires transitive com.google.gson;
    requires com.sun.jna;
    requires com.sun.jna.platform;
    requires org.fxmisc.flowless;
    requires java.datatransfer;
    requires transitive java.desktop;
    requires java.logging;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.base;
    requires javafx.swing;
    requires transitive javafx.graphics;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.elusive;
    requires org.kordamp.ikonli.fontawesome5;
    requires transitive org.antlr.antlr4.runtime;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires reactfx;
    requires org.fxmisc.richtext;
    requires org.fxmisc.undo;
    requires wellbehavedfx;
    requires java.base;
    requires org.json;
    requires antlr4;

    opens edu.kit.kastel.formal.beast.gui.options.processhandler to javafx.fxml;
    opens edu.kit.kastel.formal.beast.gui.options.ceditor to javafx.fxml;
    opens edu.kit.kastel.formal.beast.gui.error to javafx.fxml;
    opens edu.kit.kastel.formal.beast.gui to javafx.fxml;
    opens edu.kit.kastel.formal.beast.gui.options to javafx.fxml;
    opens edu.kit.kastel.formal.beast.gui.configurationeditor.configuration to javafx.fxml;
    opens edu.kit.kastel.formal.beast.gui.configurationeditor.configuration.cbmc to javafx.fxml;
    opens edu.kit.kastel.formal.beast.gui.configurationeditor to javafx.fxml;
    opens edu.kit.kastel.formal.beast.gui.run to javafx.fxml;
    opens edu.kit.kastel.formal.beast.api.method to gson;
}
