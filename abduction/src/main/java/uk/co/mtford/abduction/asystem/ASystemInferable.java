/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.List;
import java.util.Set;
import uk.co.mtford.abduction.asystem.Store;
import uk.co.mtford.abduction.logic.LogicalFormulae;

/**
 *
 * @author mtford
 */
public interface ASystemInferable {
    /** Applies the ASystem inference rule relevant to this particular
     *  logical sentence.
     * @param s
     * @return 
     */
    public boolean applyInferenceRule(List<LogicalFormulae> goals, State s);
}
