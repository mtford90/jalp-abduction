/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.List;
import uk.co.mtford.abduction.logic.LogicalFormulaeInstance;

/**
 *
 * @author mtford
 */
public interface ASystemInferable extends Cloneable {
    /** Applies the ASystem inference rule relevant to this particular
     *  logical formula.
     * @param s
     * @return 
     */
    public List<ASystemState> applyInferenceRule(List<LogicalFormulaeInstance> currentGoals, ASystemState s);
    public List<ASystemState> applyDenialInferenceRule(List<LogicalFormulaeInstance> currentGoals, ASystemState s);
    public Object clone();

}
