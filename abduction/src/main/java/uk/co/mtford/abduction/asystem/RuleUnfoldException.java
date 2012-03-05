/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

/**
 *
 * @author mtford
 */
public class RuleUnfoldException extends Exception {
    

    public RuleUnfoldException(Throwable thrwbl) {
        super(thrwbl);
    }

    public RuleUnfoldException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public RuleUnfoldException(String string) {
        super(string);
    }

    public RuleUnfoldException() {
    }
    
}
