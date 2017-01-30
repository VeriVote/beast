package edu.pse.beast.propertychecker;

import java.io.File;

public abstract class CBMCProcess extends Checker {

    public CBMCProcess(int voters, int candidates, int seats, String advanced, File toCheck, CheckerFactory parent) {
        super(voters, candidates, seats, advanced, toCheck, parent);
    }
    
}
