/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import uk.co.mtford.abduction.asystem.State;

/**
 *
 * @author mtford
 */
public interface Inferable {
    /** Applies the ASystem inference rule relevant to this particular
     *  logical sentence.
     * @param s
     * @return 
     */
    public State[] applyInferenceRule(State s);
}
