/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem.rules;

/**
 *
 * @author mtford
 */
class InferenceRuleException extends Exception {

    public InferenceRuleException() {
    }

    public InferenceRuleException(Throwable thrwbl) {
        super(thrwbl);
    }

    public InferenceRuleException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public InferenceRuleException(String string) {
        super(string);
    }


    
    
}
