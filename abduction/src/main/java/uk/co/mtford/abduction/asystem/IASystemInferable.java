/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.List;
import uk.co.mtford.abduction.logic.LogicInstance;

/**
 *
 * @author mtford
 */
public interface IASystemInferable extends LogicInstance {
    /** Applies the ASystem inference rule relevant to this particular
     *  logical formula.
     * @param s
     * @return 
     */
    public List<ASystemState> applyInferenceRule(List<IASystemInferable> currentGoals, ASystemState s);
    public List<ASystemState> applyDenialInferenceRule(List<IASystemInferable> currentGoals, ASystemState s);
    

}
