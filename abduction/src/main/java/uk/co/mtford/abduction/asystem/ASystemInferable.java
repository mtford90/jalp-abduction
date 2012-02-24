/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.List;
import java.util.Set;
import uk.co.mtford.abduction.asystem.ASystemStore;
import uk.co.mtford.abduction.logic.LogicalFormulaeInstance;

/**
 *
 * @author mtford
 */
public interface ASystemInferable {
    /** Applies the ASystem inference rule relevant to this particular
     *  logical formula.
     * @param s
     * @return 
     */
    public boolean applyInferenceRule(ASystemState s);
    
}
