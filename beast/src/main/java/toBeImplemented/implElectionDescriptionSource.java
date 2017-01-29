package toBeImplemented;

import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;

public class implElectionDescriptionSource implements ElectionDescriptionSource{
    
    private final ElectionDescription desc;
    
    public implElectionDescriptionSource(ElectionDescription desc) {
        this.desc = desc;
    }

    @Override
    public ElectionDescription getElectionDescription() {
        return desc;
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
