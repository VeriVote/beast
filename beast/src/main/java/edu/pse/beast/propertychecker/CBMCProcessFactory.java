package edu.pse.beast.propertychecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;

public class CBMCProcessFactory extends CheckerFactory {


    protected CBMCProcessFactory(FactoryController controller, ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescriptionSource postAndPrepPropDescSrc, ParameterSource paramSrc, Result result) {
        super(controller, electionDescSrc, postAndPrepPropDescSrc, paramSrc, result);
    }

    @Override
    protected Result createCounterExample(List<String> result) {
        return null;
    }

    @Override
    protected void startProcess(File toCheck, String callParams) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Result> getFittingResult(int size) {
        for (int i = 0; i < size; i++) {
        }
    }

    @Override
    public boolean checkResult(List<String> toCheck) {
        // TODO Auto-generated method stub
        return false;
    }
}
