package edu.pse.beast.gui.testconfigeditor.testconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

public class TestConfiguration {

    private CElectionDescription descr;
    private PreAndPostConditionsDescription propDescr;
    private String name;

    private Map<String, CBMCTestConfiguration> cbmcTestConfigsByName = new HashMap<>();

    public TestConfiguration(CElectionDescription descr,
            PreAndPostConditionsDescription propDescr, String name) {
        this.descr = descr;
        this.propDescr = propDescr;
        this.name = name;
    }

    public CElectionDescription getDescr() {
        return descr;
    }

    public PreAndPostConditionsDescription getPropDescr() {
        return propDescr;
    }

    public String getName() {
        return name;
    }

    public void addCBMCTestConfiguration(CBMCTestConfiguration config) {
        cbmcTestConfigsByName.put(config.getName(), config);
    }

    public Map<String, CBMCTestConfiguration> getCbmcTestConfigsByName() {
        return Collections.unmodifiableMap(cbmcTestConfigsByName);
    }

    public boolean contains(CBMCTestConfiguration config) {
        return cbmcTestConfigsByName.containsKey(config.getName());
    }

    public void addCBMCTestConfigurations(
            List<CBMCTestConfiguration> cbmcTestConfigs) {
        for (CBMCTestConfiguration config : cbmcTestConfigs) {
            addCBMCTestConfiguration(config);
        }
    }

    public void handleDescrChange() {
        for (CBMCTestConfiguration cbmctc : cbmcTestConfigsByName.values()) {
            cbmctc.handleDescrCodeChange();
        }
    }

    public List<CBMCTestConfiguration> getCBMCTestConfigs() {
        List<CBMCTestConfiguration> list = new ArrayList<>();
        list.addAll(cbmcTestConfigsByName.values());
        return list;
    }

    @Override
    public String toString() {
        String template = "CONFIG_NAME: DESCR_NAME + PROP_NAME";
        template = template.replaceAll("CONFIG_NAME", name)
                .replaceAll("DESCR_NAME", descr.getName())
                .replaceAll("PROP_NAME", propDescr.getName());
        return template;
    }

    public void handlePropDescrChanged() {
        // TODO this can be made smarter by giving the run items a reference to
        // the current
        // propdescr and descr as well as a copy of the state they had when the
        // runs were
        // created. Then, the runs can figure out themselves whether they are
        // out of
        // date
        for (CBMCTestConfiguration cbmctc : cbmcTestConfigsByName.values()) {
            cbmctc.handlePropDescrChanged();
        }
    }

}
