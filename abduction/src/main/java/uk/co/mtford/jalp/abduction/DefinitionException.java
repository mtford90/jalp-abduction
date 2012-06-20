package uk.co.mtford.jalp.abduction;

import uk.co.mtford.jalp.JALPException;

/**
 @author Michael Ford
 */
public class DefinitionException extends JALPException {

    public DefinitionException() {
    }

    public DefinitionException(String s) {
        super(s);
    }

    public DefinitionException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DefinitionException(Throwable throwable) {
        super(throwable);
    }
}
