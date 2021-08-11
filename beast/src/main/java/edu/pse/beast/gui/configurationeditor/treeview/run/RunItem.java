package edu.pse.beast.gui.configurationeditor.treeview.run;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.pse.beast.api.io.PathHandler;
import edu.pse.beast.api.runner.propertycheck.run.PropertyCheckRun;
import edu.pse.beast.gui.configurationeditor.treeview.ConfigurationItem;
import edu.pse.beast.gui.configurationeditor.treeview.ConfigurationItemType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class RunItem extends ConfigurationItem {
    private static final String EMPTY = "";
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

    private PropertyCheckRun run;

    public RunItem(final PropertyCheckRun tr) {
        super(ConfigurationItemType.CBMC_RUN);
        this.run = tr;
    }

    public final String getTemplate() {
        return PathHandler.getTemplate(FILE_KEY, TEMPLATES, EMPTY, this.getClass());
    }

    public final PropertyCheckRun getRun() {
        return run;
    }

    @Override
    public final String toString() {
        String template = getTemplate();
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
