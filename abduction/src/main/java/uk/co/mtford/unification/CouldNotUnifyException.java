/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.unification;

/**
 *
 * @author mtford
 */
public class CouldNotUnifyException extends Exception {

    public CouldNotUnifyException(Throwable thrwbl) {
        super(thrwbl);
    }

    public CouldNotUnifyException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public CouldNotUnifyException(String string) {
        super(string);
    }

    public CouldNotUnifyException() {
    }
    
}
