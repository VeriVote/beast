package edu.pse.beast.gui.testconfigeditor.treeview.runs;

import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemType;

public class TestRunCBMCTreeItem extends TestConfigTreeItemSuper {
    private CBMCTestRunWithSymbolicVars run;

    public TestRunCBMCTreeItem(final CBMCTestRunWithSymbolicVars tr) {
        super(TestConfigTreeItemType.CBMC_RUN);
        this.run = tr;
    }

    public final CBMCTestRunWithSymbolicVars getRun() {
        return run;
    }

    @Override
    public final String toString() {
        String template = "V=AMT_VOTER, C=AMT_CAND, S=AMT_SEAT | STATUS";
        if (run.isDescrChanged()) {
            template += " | c descr changed";
        }
        if (run.isPropDescrChanged()) {
            template += " | prop descr changed";
        }
        template =
                template.replaceAll("AMT_VOTER", String.valueOf(run.getV()))
                .replaceAll("AMT_CAND", String.valueOf(run.getC()))
                .replaceAll("AMT_SEAT", String.valueOf(run.getS()));
        template = template.replaceAll("STATUS", run.getStatusString());
        return template;
    }
}
