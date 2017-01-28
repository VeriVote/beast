package edu.pse.beast.propertychecker;

import java.io.File;

public abstract class CBMCProcess extends Checker {

    public CBMCProcess(String arguments, File toCheck, CheckerFactory parent) {
        super(arguments, toCheck, parent);
    }
    
}
