package toBeImplemented;

import java.util.List;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;

public class implPropertyDescriptionSource implements PostAndPrePropertiesDescriptionSource{

    private final List<PostAndPrePropertiesDescription> desc;
    
    public implPropertyDescriptionSource(List<PostAndPrePropertiesDescription> desc) {
        this.desc = desc;
    }
    
    @Override
    public List<PostAndPrePropertiesDescription> getPostAndPrePropertiesDescriptions() {
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
