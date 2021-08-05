package edu.pse.beast.gui.testconfigeditor.testconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class TestConfiguration {
    private CElectionDescription description;
    private PreAndPostConditionsDescription propertyDescription;
    private String name;

    private Map<String, CBMCTestConfiguration> cbmcTestConfigsByName = new HashMap<>();

    public TestConfiguration(final CElectionDescription descr,
                             final PreAndPostConditionsDescription propDescr,
                             final String nameString) {
        this.description = descr;
        this.propertyDescription = propDescr;
        this.name = nameString;
    }

    public final CElectionDescription getDescr() {
        return description;
    }

    public final PreAndPostConditionsDescription getPropDescr() {
        return propertyDescription;
    }

    public final String getName() {
        return name;
    }

    public final void addCBMCTestConfiguration(final CBMCTestConfiguration config) {
        cbmcTestConfigsByName.put(config.getName(), config);
    }

    public final Map<String, CBMCTestConfiguration> getCbmcTestConfigsByName() {
        return Collections.unmodifiableMap(cbmcTestConfigsByName);
    }

    public final boolean contains(final CBMCTestConfiguration config) {
        return cbmcTestConfigsByName.containsKey(config.getName());
    }

    public final void addCBMCTestConfigurations(final List<CBMCTestConfiguration> cbmcTestConfigs) {
        for (final CBMCTestConfiguration config : cbmcTestConfigs) {
            addCBMCTestConfiguration(config);
        }
    }

    public final void handleDescrChange() {
        for (final CBMCTestConfiguration cbmctc : cbmcTestConfigsByName.values()) {
            cbmctc.handleDescrCodeChange();
        }
    }

    public final List<CBMCTestConfiguration> getCBMCTestConfigs() {
        final List<CBMCTestConfiguration> list = new ArrayList<>();
        list.addAll(cbmcTestConfigsByName.values());
        return list;
    }

    @Override
    public final String toString() {
        return "CONFIG_NAME: DESCR_NAME + PROP_NAME"
                .replaceAll("CONFIG_NAME", name)
                .replaceAll("DESCR_NAME", description.getName())
                .replaceAll("PROP_NAME", propertyDescription.getName());
    }

    public final void handlePropDescrChanged() {
        // TODO this can be made smarter by giving the run items a reference to
        // the current
        // propdescr and descr as well as a copy of the state they had when the
        // runs were
        // created. Then, the runs can figure out themselves whether they are
        // out of
        // date
        for (final CBMCTestConfiguration cbmctc : cbmcTestConfigsByName.values()) {
            cbmctc.handlePropDescrChanged();
        }
    }
}
