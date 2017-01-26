package toBeImplemented;

public class SymbolicVariable {
    private String ident;
    private InternalTypeRep internalType;
    
    public SymbolicVariable(String identifier, InternalTypeRep internalType) {
        this.ident = identifier;
        this.internalType = internalType;
    }

    public String getIdent() {
        return ident;
    }

    public InternalTypeRep getInternalType() {
        return internalType;
    }
    
    
}
