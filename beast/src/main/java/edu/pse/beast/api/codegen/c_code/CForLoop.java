package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

public class CForLoop {
    private CTypeNameBrackets counterVar;
    private String stopCond;
    private String doafter;

    private List<String> code = new ArrayList<>();

    public CForLoop(CTypeNameBrackets counterVar, String stopCond,
            String doafter) {
        this.counterVar = counterVar;
        this.stopCond = stopCond;
        this.doafter = doafter;
    }

}
