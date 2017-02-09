package toBeImplemented;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.highlevel.ParameterSource;

public class implParameterSource implements ParameterSource {

    private final ElectionCheckParameter parm;
    
    public implParameterSource(ElectionCheckParameter parm) {
        this.parm = parm;
    }
    
    @Override
    public ElectionCheckParameter getParameter() {
        return parm;
    }

    @Override
    public boolean isCorrect() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void stopReacting() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resumeReacting() {
        // TODO Auto-generated method stub
        
    }

}
