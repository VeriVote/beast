package edu.pse.beast.gui.testconfigeditor.treeview.runs;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class TestRunCBMCTreeItem extends TestConfigTreeItemSuper {
    private static final String RESOURCES = "/edu/pse/beast/gui/testconfigeditor/treeview/runs/";
    private static final String FILE_ENDING = ".template";
    private static final String FILE_KEY = "PARAMETERS";

    private static final String AMOUNT_VOTER = "AMT_VOTER";
    private static final String AMOUNT_CAND = "AMT_CAND";
    private static final String AMOUNT_SEAT = "AMT_SEAT";
    private static final String STATUS = "STATUS";

    private static final String ELECTION_FUNCTION = "election function";
    private static final String PROPERTY_DESCRIPTION = "property description";
    private static final String DELIMIT = " | ";
    private static final String HAS_CHANGED = " has changed";

    private static final Map<String, String> TEMPLATES = new LinkedHashMap<String, String>();

    private CBMCTestRunWithSymbolicVars run;

    public TestRunCBMCTreeItem(final CBMCTestRunWithSymbolicVars tr) {
        super(TestConfigTreeItemType.CBMC_RUN);
        this.run = tr;
    }

    public static final String getTemplate(final Class<?> c) {
        final String key = FILE_KEY;
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES + key.toLowerCase() + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            TEMPLATES.put(key, writer.toString());
        }
        return TEMPLATES.get(key);
    }

    public final CBMCTestRunWithSymbolicVars getRun() {
        return run;
    }

    @Override
    public final String toString() {
        final Class<?> c = this.getClass();
        String template = getTemplate(c);
        if (run.isDescrChanged()) {
            template += DELIMIT + ELECTION_FUNCTION + HAS_CHANGED;
        }
        if (run.isPropDescrChanged()) {
            template += DELIMIT + PROPERTY_DESCRIPTION + HAS_CHANGED;
        }
        template =
                template
                .replaceAll(AMOUNT_VOTER, String.valueOf(run.getV()))
                .replaceAll(AMOUNT_CAND, String.valueOf(run.getC()))
                .replaceAll(AMOUNT_SEAT, String.valueOf(run.getS()))
                .replaceAll(STATUS, run.getStatusString());
        return template;
    }
}
