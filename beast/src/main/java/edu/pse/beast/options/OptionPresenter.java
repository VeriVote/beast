package edu.pse.beast.options;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;

/**
 * This class uses the OptionPresenterFrame class to present given option objects
 * to the user and thus give him the possiblity of changing settings.
 * @author Holger
 *
 */
public final class OptionPresenter implements DisplaysStringsToUser {
    
    private StringResourceLoader srl;
    /**
     * Constructor
     * @param srl StringResourceLoader
     */
    public OptionPresenter(StringResourceLoader srl) {
        this.srl = srl;
    }
    
    /**
     * Presents options to the user
     * @param opt the options to be presented
     */
    public void presentOptionsToUser(Options opt) {
        OptionPresenterFrame frame = new OptionPresenterFrame(
                opt, srl);        
        frame.setVisible(true);        
    }    

    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        this.srl = stringResIF.getOptionStringResProvider().getOptionStringRes();
    }
    

}
