package edu.pse.beast.types.cbmctypes;

import edu.pse.beast.types.OutputType;

public abstract class CBMCOutputType extends OutputType {

    @Override
    protected void getHelper() {
        super.helper = new CbmcHelpMethods();
    }

}
